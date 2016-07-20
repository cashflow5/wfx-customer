package com.yougou.wfx.customer.common.session;

import java.io.Serializable;

/**
 * 用户信息,session中存储
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/24 下午2:03
 * @since 1.0 Created by lipangeng on 16/3/24 下午2:03. Email:lipg@outlook.com.
 */
public class SessionUserDetails implements Serializable {
    private static final long serialVersionUID = 3745569753584732582L;
    /**
     * 用户id
     */
    private String userId = "";
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户的密码,经过加密后的
     */
    private String passwordEncode;
    /**
     * 绑定的手机号
     */
    private String phoneNumber;
    /**
     * 是否是自动登陆的
     */
    private boolean autoLogin = false;
    /**
     * 地址页面是否是创建订单地址跳转而来,以供操作收货地址时返回提交订单页面.
     */
    private boolean addressPayRedirect = false;
    
    /**
     * 用户分销商的ID
     */
    private String sellerId = "";
    /**
     * 用户店铺的ID
     */
    private String shopId = "";

    /** 订单支付信息 */
    private SessionOrderPayDetails orderPayDetails = new SessionOrderPayDetails();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordEncode() {
        return passwordEncode;
    }

    public void setPasswordEncode(String passwordEncode) {
        this.passwordEncode = passwordEncode;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isAddressPayRedirect() {
        return addressPayRedirect;
    }

    public void setAddressPayRedirect(boolean addressPayRedirect) {
        this.addressPayRedirect = addressPayRedirect;
    }

    public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public void save() {
        SessionUtils.setLoginUserDetails(this);
    }

    public SessionOrderPayDetails getOrderPayDetails() {
        return orderPayDetails;
    }

    public void setOrderPayDetails(SessionOrderPayDetails orderPayDetails) {
        this.orderPayDetails = orderPayDetails;
    }

    public String toLogString() {
        return "SessionUserDetails[" +
               "userId='" + userId + '\'' +
               ", userName='" + userName + '\'' +
               ", autoLogin=" + autoLogin +
               ']';
    }
}
