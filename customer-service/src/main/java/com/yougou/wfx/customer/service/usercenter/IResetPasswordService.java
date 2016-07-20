package com.yougou.wfx.customer.service.usercenter;

import com.yougou.wfx.customer.model.usercenter.resetpassword.ResetPasswordPhoneVo;
import com.yougou.wfx.customer.model.usercenter.resetpassword.ResetPasswordSMSVo;
import com.yougou.wfx.customer.model.usercenter.resetpassword.ResetPasswordSessionDetails;

/**
 * 找回密码的Service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/27 下午9:15
 * @since 1.0 Created by lipangeng on 16/3/27 下午9:15. Email:lipg@outlook.com.
 */
public interface IResetPasswordService {

    /**
     * 在session中缓存找回密码相关信息
     *
     * @since 1.0 Created by lipangeng on 16/3/25 下午3:03. Email:lipg@outlook.com.
     */
    void setResetPasswordDetails(ResetPasswordSessionDetails details);

    /**
     * 获取session中缓存的找回密码相关信息
     *
     * @since 1.0 Created by lipangeng on 16/3/25 下午3:03. Email:lipg@outlook.com.
     */
    ResetPasswordSessionDetails getResetPasswordDetails();

    /**
     * 删除session中缓存的找回密码信息
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午3:12. Email:lipg@outlook.com.
     */
    void removeResetPasswordDetails();

    /**
     * 检测session中的找回密码信息是否为空,用户名是否为空
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午2:58. Email:lipg@outlook.com.
     */
    boolean hasRegisterDetails();

    /**
     * 更新session中的信息
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午3:17. Email:lipg@outlook.com.
     */
    void updateSessionDetails(ResetPasswordPhoneVo phoneVo);

    /**
     * 更新session中的信息
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午3:31. Email:lipg@outlook.com.
     */
    void updateSessionDetails(ResetPasswordSMSVo smsVo);


}
