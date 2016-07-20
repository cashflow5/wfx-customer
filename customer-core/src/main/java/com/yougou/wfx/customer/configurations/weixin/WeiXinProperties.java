package com.yougou.wfx.customer.configurations.weixin;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/14
 */
@ConfigurationProperties("wx")
public class WeiXinProperties {
    private String appId;

    private String appSecret;

    private String key;

    private String tokenUrl;

    private String ticketUrl;

    private String noncestr;
    
    private String indexUrl = "http://m.yougou.net";

    private String orderNotifyUrl = "http://m.yougou.net";

    private String orderPayUrl = "http://m.yougou.net/pay/weixin.sc";

    private long beforeRefreshTime = 1000 * 60 * 15;

    private String mchId;

    private String wxAuthorizeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code" +
                                    "&scope=snsapi_userinfo&state=%s#wechat_redirect";

    private String mediaGetUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";
    
    private String grantBackUrl = "http://h5test.yougou.us/get-wx-userinfo.sc";

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTicketUrl() {
        return ticketUrl;
    }

    public void setTicketUrl(String ticketUrl) {
        this.ticketUrl = ticketUrl;
    }

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}

	public long getBeforeRefreshTime() {
        return beforeRefreshTime;
    }

    public void setBeforeRefreshTime(long beforeRefreshTime) {
        this.beforeRefreshTime = beforeRefreshTime;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getOrderNotifyUrl() {
        return orderNotifyUrl;
    }

    public void setOrderNotifyUrl(String orderNotifyUrl) {
        this.orderNotifyUrl = orderNotifyUrl;
    }

    public String getMediaGetUrl() {
        return mediaGetUrl;
    }

    public void setMediaGetUrl(String mediaGetUrl) {
        this.mediaGetUrl = mediaGetUrl;
    }

    public String getWxAuthorizeUrl() {
        return wxAuthorizeUrl;
    }

    public void setWxAuthorizeUrl(String wxAuthorizeUrl) {
        this.wxAuthorizeUrl = wxAuthorizeUrl;
    }

    public String getOrderPayUrl() {
        return orderPayUrl;
    }

    public void setOrderPayUrl(String orderPayUrl) {
        this.orderPayUrl = orderPayUrl;
    }

	public String getGrantBackUrl() {
		return grantBackUrl;
	}

	public void setGrantBackUrl(String grantBackUrl) {
		this.grantBackUrl = grantBackUrl;
	}

}
