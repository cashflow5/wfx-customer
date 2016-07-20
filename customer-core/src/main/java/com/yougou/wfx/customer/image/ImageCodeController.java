package com.yougou.wfx.customer.image;

import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.configurations.imagecode.ImageCodeProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 图片验证码处理类
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/24 下午11:17
 * @since 1.0 Created by lipangeng on 16/3/24 下午11:17. Email:lipg@outlook.com.
 */
@RequestMapping
@Controller
public class ImageCodeController {
    private static final Logger logger = LoggerFactory.getLogger(ImageCodeController.class);
    @Autowired
    private ImageCodeProperties imageCodeProperties;

    /**
     * 获取图片验证码
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午11:18. Email:lipg@outlook.com.
     */
    @RequestMapping("getImageCode")
    public void getImageCode(HttpServletResponse response) {
        // 定义图像buffer
        BufferedImage buffImg =
                new BufferedImage(imageCodeProperties.getWidth(), imageCodeProperties.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D gd = buffImg.createGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, imageCodeProperties.getWidth(), imageCodeProperties.getHeight());
        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.PLAIN, imageCodeProperties.getFontHeight());
        // 设置字体。
        gd.setFont(font);

        // 画边框。
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, imageCodeProperties.getWidth() - 1, imageCodeProperties.getHeight() - 1);

        // 随机产生4条干扰线，使图象中的认证码不易被其它程序探测到。
        gd.setColor(Color.BLACK);
        for (int i = 0; i < imageCodeProperties.getLines(); i++) {
            int x = random.nextInt(imageCodeProperties.getWidth());
            int y = random.nextInt(imageCodeProperties.getHeight());
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;

        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < imageCodeProperties.getCodeCount(); i++) {
            // 得到随机产生的验证码数字。
            String strRand =
                    String.valueOf(imageCodeProperties.getCodeSequence()[random.nextInt(imageCodeProperties.getCodeSequence().length)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(125);
            green = random.nextInt(255);
            blue = random.nextInt(200);
            // 用随机产生的颜色将验证码绘制到图像中。
            gd.setColor(new Color(red, green, blue));
            gd.drawString(strRand, (i + 1) * imageCodeProperties.getCodeX(), imageCodeProperties.getCodeY());
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        // 将四位数字的验证码保存到Session中。
        SessionUtils.setImageCode(randomCode.toString());
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。
        try (OutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(buffImg, "jpeg", outputStream);
        } catch (IOException e) {
            logger.error("生成图文验证码失败!", e);
        }
    }
}
