package com.yougou.wfx.customer.model.weixin.base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信获取用户信息
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/18 下午5:32
 * @since 1.0 Created by lipangeng on 16/4/18 下午5:32. Email:lipg@outlook.com.
 */
public class WXUserAccessTokenResponse {
    /** 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同 */
    @JsonProperty("access_token")
    private String accessToken;
    /** access_token接口调用凭证超时时间，单位（秒） */
    @JsonProperty("expires_id")
    /** 用户刷新access_token */ private int expiresIn;
    /** 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID */
    @JsonProperty("refresh_token")
    private String refreshToken;
    /** 用户授权的作用域，使用逗号（,）分隔 */
    private String openid;
    /** 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。 */
    private String unionid;

    // 若果出现错误,才会有
    /** 错误代码 */
    private String errcode;
    /** 错误信息 */
    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
