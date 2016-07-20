package com.yougou.wfx.customer.model.usercenter.resetpassword;

import java.io.Serializable;

/**
 * 注册时在session中缓存的信息
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/28 下午2:40
 * @since 1.0 Created by lipangeng on 16/3/28 下午2:40. Email:lipg@outlook.com.
 */
public class ResetPasswordSessionDetails implements Serializable{
    private static final long serialVersionUID = 8353502553416106117L;
    /** 用户名 */
    private String loginName;
    /** 短信验证码 */
    private String smsCode;
    /** 图片验证码 */
    private String imageCode;
    /** 短信验证码是否已经效验 */
    private boolean smsCodeValid = false;
    /** 图片验证码是否已经效验 */
    private boolean imageCodeValid = false;

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

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public boolean isImageCodeValid() {
        return imageCodeValid;
    }

    public void setImageCodeValid(boolean imageCodeValid) {
        this.imageCodeValid = imageCodeValid;
    }
}
