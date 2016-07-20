package com.yougou.wfx.customer.model.weixin.custom.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>Title: MessageResponse</p>
 * <p>Description: </p>
 * @author: zheng.qq
 * @date: 2016年6月30日
 */
public class MessageResponse {
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
}
