package com.yougou.wfx.customer.service.usercenter.impl;

import com.google.common.base.Strings;
import com.yougou.wfx.customer.common.constant.SessionConstant;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.usercenter.resetpassword.ResetPasswordPhoneVo;
import com.yougou.wfx.customer.model.usercenter.resetpassword.ResetPasswordSMSVo;
import com.yougou.wfx.customer.model.usercenter.resetpassword.ResetPasswordSessionDetails;
import com.yougou.wfx.customer.service.usercenter.IResetPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 找回密码的Service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/27 下午9:15
 * @since 1.0 Created by lipangeng on 16/3/27 下午9:15. Email:lipg@outlook.com.
 */
@Service
public class ResetPasswordService implements IResetPasswordService {
    private static final Logger logger = LoggerFactory.getLogger(ResetPasswordService.class);
    /**
     * 在session中缓存找回密码相关信息
     *
     * @since 1.0 Created by lipangeng on 16/3/25 下午3:03. Email:lipg@outlook.com.
     */
    @Override
    public void setResetPasswordDetails(ResetPasswordSessionDetails details) {
        HttpServletRequest request = SessionUtils.getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(SessionConstant.RESET_PASSWORD_DETAILS, details);
    }

    /**
     * 获取session中缓存的找回密码相关信息
     *
     * @since 1.0 Created by lipangeng on 16/3/25 下午3:03. Email:lipg@outlook.com.
     */
    @Override
    public ResetPasswordSessionDetails getResetPasswordDetails() {
        HttpServletRequest request = SessionUtils.getRequest();
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(SessionConstant.RESET_PASSWORD_DETAILS);
        if (attribute != null && attribute instanceof ResetPasswordSessionDetails) {
            return (ResetPasswordSessionDetails) attribute;
        }
        return null;
    }

    /**
     * 删除session中缓存的找回密码信息
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午3:12. Email:lipg@outlook.com.
     */
    @Override
    public void removeResetPasswordDetails() {
        HttpServletRequest request = SessionUtils.getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute(SessionConstant.RESET_PASSWORD_DETAILS);
    }

    /**
     * 检测session中的找回密码信息是否为空,用户名是否为空
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午2:58. Email:lipg@outlook.com.
     */
    public boolean hasRegisterDetails() {
        ResetPasswordSessionDetails details = getResetPasswordDetails();
        return ! (details == null || Strings.isNullOrEmpty(details.getLoginName()));
    }

    /**
     * 更新session中的信息
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午3:17. Email:lipg@outlook.com.
     */
    public void updateSessionDetails(ResetPasswordPhoneVo phoneVo) {
        ResetPasswordSessionDetails details = new ResetPasswordSessionDetails();
        details.setLoginName(phoneVo.getPhoneNumber());
        details.setImageCode(phoneVo.getImageCode());
        details.setImageCodeValid(true);
        setResetPasswordDetails(details);
    }

    /**
     * 更新session中的信息
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午3:31. Email:lipg@outlook.com.
     */
    public void updateSessionDetails(ResetPasswordSMSVo smsVo) {
        if (! hasRegisterDetails()) {
            return;
        }
        ResetPasswordSessionDetails details = getResetPasswordDetails();
        details.setSmsCode(smsVo.getSmsCode());
        details.setSmsCodeValid(true);
        setResetPasswordDetails(details);
    }
}
