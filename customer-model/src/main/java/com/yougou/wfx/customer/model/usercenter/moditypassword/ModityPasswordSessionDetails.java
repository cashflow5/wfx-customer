package com.yougou.wfx.customer.model.usercenter.moditypassword;

import java.io.Serializable;

/**
 * 修改密码的session信息
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/5 下午3:25
 * @since 1.0 Created by lipangeng on 16/4/5 下午3:25. Email:lipg@outlook.com.
 */
public class ModityPasswordSessionDetails implements Serializable {
    private static final long serialVersionUID = 7032872748507940006L;
    /** 用户名(手机号) */
    private String loginName;
    /** 短信验证码 */
    private String smsCode;
    /** 短信验证码是否已经效验 */
    private boolean smsCodeValid = false;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public boolean isSmsCodeValid() {
        return smsCodeValid;
    }

    public void setSmsCodeValid(boolean smsCodeValid) {
        this.smsCodeValid = smsCodeValid;
    }
}
