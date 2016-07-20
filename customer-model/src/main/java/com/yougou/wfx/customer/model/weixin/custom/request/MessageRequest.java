package com.yougou.wfx.customer.model.weixin.custom.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>Title: MessageRequest</p>
 * <p>Description: </p>
 * @author: zheng.qq
 * @date: 2016年6月30日
 */
public class MessageRequest {
	@JsonProperty("touser")
	private String toUser;
	@JsonProperty("msgtype")
	private String msgType;
	
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
}
