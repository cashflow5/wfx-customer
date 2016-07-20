package com.yougou.wfx.customer.model.usercenter;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 用户登陆的Vo
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/23 下午5:31
 * @since 1.0 Created by lipangeng on 16/3/23 下午5:31. Email:lipg@outlook.com.
 */
public class UserLoginVo implements Serializable {
    @NotBlank(message = "用户名不能为空")
    private String loginName;
    @NotBlank(message = "密码不能为空")
    private String loginPassword;
    /** 加密后的用户密码 */
    private String loginPasswordEncode;
    /** 用户id */
    private String userId;
    /** 是否记录登录信息 */
    private boolean saveLogin = false;
    /** 是否自动登录 */
    private boolean autoLogin = false;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginPasswordEncode() {
        return loginPasswordEncode;
    }

    public void setLoginPasswordEncode(String loginPasswordEncode) {
        this.loginPasswordEncode = loginPasswordEncode;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public boolean isSaveLogin() {
        return saveLogin;
    }

    public void setSaveLogin(boolean saveLogin) {
        this.saveLogin = saveLogin;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }
}
