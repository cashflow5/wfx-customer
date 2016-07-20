package com.yougou.wfx.customer.model.weixin.base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 获取微信的AccessToken接口的返回值
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/15 下午12:07
 * @since 1.0 Created by lipangeng on 16/4/15 下午12:07. Email:lipg@outlook.com.
 */
public class WXAccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("openid")
    private String openId;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("expires_in")
    private int expiresIn;
    private int errcode = 0;
    private String errmsg;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public boolean hasError() {
        return errcode != 0;
    }

}
