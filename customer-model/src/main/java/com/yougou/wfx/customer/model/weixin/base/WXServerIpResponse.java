package com.yougou.wfx.customer.model.weixin.base;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yougou.wfx.customer.model.weixin.WXBaseResponse;

/**
 * <p>Title: WXServerIpResponse</p>
 * <p>Description: </p>
 * @author: zheng.qq
 * @date: 2016年7月18日
 */
public class WXServerIpResponse extends WXBaseResponse{
	 @JsonProperty("ip_list")
     private List<String> ipList;
}
