package com.yougou.wfx.customer.model.weixin.user.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>Title: UserSearchResponse</p>
 * <p>Description: </p>
 * @author: zheng.qq
 * @date: 2016年7月1日
 */
public class UserSearchResponse {
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
	 * 关注该公众账号的总用户数
	 */
    @JsonProperty("total")
    private int total;
	/**
	 * 拉取的OPENID个数，最大值为10000
	 */
    @JsonProperty("count")
    private int count;
    /**
	 * 列表数据，OPENID的列表
	 */
    @JsonProperty("data")
    private Data data;
    /**
	 * 拉取列表的最后一个用户的OPENID
	 */
    @JsonProperty("next_openid")
    private String nextOpenId;
    
    public class Data{
		@JsonProperty("openid")
		private List<String> openIds;
		
		public List<String> getOpenIds() {
			return openIds;
		}
		public void setOpenIds(List<String> openIds) {
			this.openIds = openIds;
		}
		
	}
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
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public String getNextOpenId() {
		return nextOpenId;
	}
	public void setNextOpenId(String nextOpenId) {
		this.nextOpenId = nextOpenId;
	}
}
