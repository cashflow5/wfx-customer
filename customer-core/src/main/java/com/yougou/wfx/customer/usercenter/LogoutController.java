package com.yougou.wfx.customer.usercenter;

import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.utils.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登出处理
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/25 上午12:33
 * @since 1.0 Created by lipangeng on 16/3/25 上午12:33. Email:lipg@outlook.com.
 */
@Controller
@RequestMapping
public class LogoutController {
    private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);

    /**
     * 登出接口
     *
     * @since 1.0 Created by lipangeng on 16/3/25 上午12:34. Email:lipg@outlook.com.
     */
    @RequestMapping("logout")
    private String logout() {
        logger.info(LogUtils.requestInfo("退出登录"));
        SessionUtils.logout();
        return "redirect:" + WfxConstant.WFX_INDEX_URL;
    }
}
