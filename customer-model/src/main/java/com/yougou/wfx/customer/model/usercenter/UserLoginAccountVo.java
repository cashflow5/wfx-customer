package com.yougou.wfx.customer.model.usercenter;


import com.yougou.wfx.member.dto.output.MemberAccountOutputDto;

import java.io.Serializable;

/**
 * 用户登录时的账户信息
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/27 下午6:52
 * @since 1.0 Created by lipangeng on 16/3/27 下午6:52. Email:lipg@outlook.com.
 */
public class UserLoginAccountVo implements Serializable{
    private boolean isLogined=false;
    private UserLoginVo userLoginInfo;
    private String errorMsg;
    private MemberAccountOutputDto memberAccount;

    public boolean isLogined() {
        return isLogined;
    }

    public void setLogined(boolean logined) {
        isLogined = logined;
    }

    public UserLoginVo getUserLoginInfo() {
        return userLoginInfo;
    }

    public void setUserLoginInfo(UserLoginVo userLoginInfo) {
        this.userLoginInfo = userLoginInfo;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public MemberAccountOutputDto getMemberAccount() {
        return memberAccount;
    }

    public void setMemberAccount(MemberAccountOutputDto memberAccount) {
        this.memberAccount = memberAccount;
    }
}
