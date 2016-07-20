package com.yougou.wfx.customer.model.usercenter.resetpassword;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 注册时记录的密码
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/25 下午5:39
 * @since 1.0 Created by lipangeng on 16/3/25 下午5:39. Email:lipg@outlook.com.
 */
public class ResetPasswordPasswordVo {
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "(?!^\\d+$)(?!^[a-zA-Z]+$)^[0-9a-zA-Z]{6,18}$", message = "密码仅允许6-18位,数字+密码的组合")
    private String passWord;
    @NotBlank(message = "确认密码不能为空")
    @Pattern(regexp = "(?!^\\d+$)(?!^[a-zA-Z]+$)^[0-9a-zA-Z]{6,18}$", message = "密码仅允许6-18位,数字+密码的组合")
    private String passWord2;

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPassWord2() {
        return passWord2;
    }

    public void setPassWord2(String passWord2) {
        this.passWord2 = passWord2;
    }
}
