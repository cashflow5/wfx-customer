package com.yougou.wfx.customer.model.weixin.order;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 微信发起js支付请求的Vo
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/20 上午11:33
 * @since 1.0 Created by lipangeng on 16/4/20 上午11:33. Email:lipg@outlook.com.
 */
public class WXJSPayVo {
    private String appId;
    private String timeStamp;
    private String nonceStr;
    @JsonProperty("package")
    private String packages;
    private String signType = "MD5";
    private String paySign;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
