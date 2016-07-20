package com.yougou.wfx.customer.model.usercenter.moditypassword.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 修改密码的Vo
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/7 上午10:58
 * @since 1.0 Created by lipangeng on 16/4/7 上午10:58. Email:lipg@outlook.com.
 */
public class DoModityPasswordFormVo {
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "(?!^\\d+$)(?!^[a-zA-Z]+$)^[0-9a-zA-Z]{6,18}$", message = "密码仅允许6-18位,数字+密码的组合")
    private String password;
    @NotBlank(message = "确认密码不能为空")
    @Pattern(regexp = "(?!^\\d+$)(?!^[a-zA-Z]+$)^[0-9a-zA-Z]{6,18}$", message = "密码仅允许6-18位,数字+密码的组合")
    private String password2;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
