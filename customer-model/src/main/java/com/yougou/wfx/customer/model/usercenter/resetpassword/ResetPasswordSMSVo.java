package com.yougou.wfx.customer.model.usercenter.resetpassword;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 找回密码时短信验证
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/25 下午4:09
 * @since 1.0 Created by lipangeng on 16/3/25 下午4:09. Email:lipg@outlook.com.
 */
public class ResetPasswordSMSVo {
    @NotBlank(message = "验证码不能为空")
    private String smsCode;

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
