package com.yougou.wfx.customer.image;

import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.configurations.weixin.WeiXinProperties;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.usercenter.account.UserInfoVo;
import com.yougou.wfx.customer.service.usercenter.IAccountService;
import com.yougou.wfx.customer.service.weixin.IWXService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/19
 */
@Controller
@RequestMapping("/image")
public class ImageController {
    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private WeiXinProperties weiXinProperties;
    @Autowired
    private IWXService wxService;
    @Autowired
    private IAccountService accountService;

    @RequestMapping(value = "/shop/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> uploadShopPic(String serverId) {
        logger.info("微信图片上传成功serverId：{}", serverId);
        return Result.create();
    }

    /**
     * 上传用户头像,通过微信
     *
     * @since 1.0 Created by lipangeng on 16/4/22 上午11:13. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "/headimg/upload", method = RequestMethod.POST)
    @ResponseBody
    @LoginValidate
    public Result<String> uploadHeadImg(String imgId) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        Result<String> uploadResult = wxService.downloadHeadImg(weiXinProperties.getMediaGetUrl(), imgId);
        if (uploadResult.hasError()) {
            logger.error("上传头像失败.用户:{},头像id:{}", loginUserDetails.getUserId(), imgId);
            return uploadResult;
        }
        Result result = accountService.updateUserHeadImage(loginUserDetails.getUserId(), uploadResult.getData());
        if (result.hasError()) {
            logger.error("保存用户头像失败,用户:{},头像地址:{}", loginUserDetails.getUserId(), uploadResult.getData());
            return Result.create(result.isSuccess(), result.getCode(), result.getMessage());
        }
        logger.info("上传头像成功.用户:{},头像id:{}", loginUserDetails.getUserId(), imgId);
        UserInfoVo userInfo = accountService.getUserInfo(loginUserDetails.getUserId());
        return Result.create(true, "上传头像成功", userInfo != null ? userInfo.getUserHeadUrl() : "");
    }
}
