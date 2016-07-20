package com.yougou.wfx.customer.model.usercenter.account;

/**
 * 用户信息vo
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/30 下午5:42
 * @since 1.0 Created by lipangeng on 16/3/30 下午5:42. Email:lipg@outlook.com.
 */
public class UserAccountVo {
    private String userName;
    private String shortUserName;
    private String userHeadUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShortUserName() {
        return shortUserName;
    }

    public void setShortUserName(String shortUserName) {
        this.shortUserName = shortUserName;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }
}
