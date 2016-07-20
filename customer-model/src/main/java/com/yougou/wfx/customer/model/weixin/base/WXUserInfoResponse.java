package com.yougou.wfx.customer.model.weixin.base;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/15
 */
public class WXUserInfoResponse {

	/**
	 * 错误编号
	 */
    @JsonProperty("errcode")
    private int errCode;
    /**
	 * 错误信息
	 */
    @JsonProperty("errmsg")
    private String errMsg;
    /**
	 * 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	 */
    @JsonProperty("subscribe")
    private String subscribe;
    /**
	 * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	 */
    @JsonProperty("subscribe_time")
    private String subscribeTime;
    /**
	 * 用户的唯一标识
	 */
    @JsonProperty("openid")
    private String openid;
    /**
	 * 用户昵称
	 */
    @JsonProperty("nickname")
    private String nickname;
    /**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
    @JsonProperty("sex")
    private String sex;
    /**
	 * 用户个人资料填写的省份
	 */
    @JsonProperty("province")
    private String province;
    /**
	 * 普通用户个人资料填写的城市
	 */
    @JsonProperty("city")
    private String city;
    /**
	 * 国家，如中国为CN
	 */
    @JsonProperty("country")
    private String country;
    /**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 */
    @JsonProperty("headimgurl")
    private String headimgurl;
    /**
	 * 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom）
	 */
    @JsonProperty("privilege")
    private String[] privilege;
    /**
	 * 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。详见：获取用户个人信息（UnionID机制）
	 */
    @JsonProperty("unionid")
    private String unionid;

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}


	public String[] getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String[] privilege) {
		this.privilege = privilege;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
	
	public boolean hasError() {
        return errCode != 0;
    }
    
}
