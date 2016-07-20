package com.yougou.wfx.customer.model.seller;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/20
 */
public class SellerinfoVo implements Serializable {
    private static final long serialVersionUID = -1743264133707740175L;
    /**
     * 主键id
     */
    private String id;
    /**
     * tbl_member_loginaccount.id
     */
    private String loginaccountId;
    /**
     * 分销商名称
     */
    private String sellerName;
    /**
     * 枚举：1待审核，2审核不通过，3合作中，4取消合作
     */
    private String state;

    /**
     * 上级代理sellerinfo.id
     */
    private String parentId;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 真实姓名
     */
    private String memberName;

    /**
     * 性别
     */
    private String memberSex;

    /**
     * 生日
     */
    private Date birthday;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginaccountId() {
        return loginaccountId;
    }

    public void setLoginaccountId(String loginaccountId) {
        this.loginaccountId = loginaccountId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberSex() {
        return memberSex;
    }

    public void setMemberSex(String memberSex) {
        this.memberSex = memberSex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
