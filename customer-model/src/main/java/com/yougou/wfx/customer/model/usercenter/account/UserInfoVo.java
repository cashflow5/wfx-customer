package com.yougou.wfx.customer.model.usercenter.account;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息Vo
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/4 下午1:52
 * @since 1.0 Created by lipangeng on 16/4/4 下午1:52. Email:lipg@outlook.com.
 */
public class UserInfoVo implements Serializable {
    private String userName;
    private String userHeadUrl;
    private Date birthday;
    private Integer sex;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
