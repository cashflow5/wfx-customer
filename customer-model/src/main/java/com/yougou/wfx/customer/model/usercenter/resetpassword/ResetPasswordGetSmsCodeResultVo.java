package com.yougou.wfx.customer.model.usercenter.resetpassword;

/**
 * reset_password/getSmsCode 的vo
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/27 下午7:53
 * @since 1.0 Created by lipangeng on 16/3/27 下午7:53. Email:lipg@outlook.com.
 */
public class ResetPasswordGetSmsCodeResultVo {
    public String status;
    public String errorMsg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
