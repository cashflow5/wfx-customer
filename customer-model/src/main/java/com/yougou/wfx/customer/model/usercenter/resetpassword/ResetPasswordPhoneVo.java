package com.yougou.wfx.customer.model.usercenter.resetpassword;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 找回密码的vo
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/27 下午9:11
 * @since 1.0 Created by lipangeng on 16/3/27 下午9:11. Email:lipg@outlook.com.
 */
public class ResetPasswordPhoneVo {
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^[1][3578][0-9]{9}$", message = "手机号格式不正确")
    private String phoneNumber;
    @NotBlank(message = "验证码不能为空")
    private String imageCode;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }
}
