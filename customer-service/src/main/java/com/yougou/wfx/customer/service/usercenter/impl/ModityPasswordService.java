package com.yougou.wfx.customer.service.usercenter.impl;

import com.google.common.base.Strings;
import com.yougou.wfx.customer.common.constant.SessionConstant;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.usercenter.moditypassword.ModityPasswordSessionDetails;
import com.yougou.wfx.customer.service.usercenter.IModityPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * 更改用户密码
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/7 上午9:35
 * @since 1.0 Created by lipangeng on 16/4/7 上午9:35. Email:lipg@outlook.com.
 */
@Service
public class ModityPasswordService implements IModityPasswordService {
    private static final Logger logger = LoggerFactory.getLogger(ModityPasswordService.class);

    /**
     * 在session中缓存注册信息
     *
     * @param details
     *
     * @since 1.0 Created by lipangeng on 16/3/25 下午3:03. Email:lipg@outlook.com.
     */
    @Override
    public void setModityPasswordDetails(ModityPasswordSessionDetails details) {
        HttpSession session = SessionUtils.getRequest().getSession();
        session.setAttribute(SessionConstant.MODITY_PASSWORD_DETAILS, details);
    }

    /**
     * 获取session中缓存的修改密码信息
     *
     * @since 1.0 Created by lipangeng on 16/3/25 下午3:03. Email:lipg@outlook.com.
     */
    @Override
    public ModityPasswordSessionDetails getModityPasswordDetails() {
        Object attribute = SessionUtils.getRequest().getSession().getAttribute(SessionConstant.MODITY_PASSWORD_DETAILS);
        if (attribute != null && attribute instanceof ModityPasswordSessionDetails) {
            return (ModityPasswordSessionDetails) attribute;
        }
        return null;
    }

    /**
     * 删除session中的修改密码信息
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午3:12. Email:lipg@outlook.com.
     */
    @Override
    public void removeModityPasswordDetails() {
        SessionUtils.getRequest().getSession().removeAttribute(SessionConstant.MODITY_PASSWORD_DETAILS);
    }

    /**
     * 检测session中的修改密码信息是否为空
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午2:58. Email:lipg@outlook.com.
     */
    @Override
    public boolean hasModityPasswordDetails() {
        ModityPasswordSessionDetails details = getModityPasswordDetails();
        return ! (details == null || Strings.isNullOrEmpty(details.getLoginName()));
    }
}
