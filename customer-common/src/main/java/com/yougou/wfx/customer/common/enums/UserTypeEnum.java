package com.yougou.wfx.customer.common.enums;

/**
 * <p>Title: UserTypeEnum</p>
 * <p>Description: </p>
 * @author: zheng.qq
 * @date: 2016年6月19日
 */
public enum UserTypeEnum {
	MEMBER_NO_AGENT_0_1("0_1","非代理商会员(仅手机账户)"),
	MEMBER_NO_AGENT_0_2("0_2","非代理商会员(仅微信账户)"),
	MEMBER_NO_AGENT_0_3("0_3","非代理商会员(手机、微信账户)"),
	MEMBER_AND_AGENT_1_1("1_1","代理商会员(仅手机账户)"),
	MEMBER_AND_AGENT_1_2("1_2","代理商会员(仅微信账户)"),
	MEMBER_AND_AGENT_1_3("1_3","代理商会员(手机、微信账户)");

	private String key;
	private String desc;
	
	
	UserTypeEnum(String key,String desc){
		this.key = key;
		this.desc = desc;
	}
	
	/**
	 * 传入key值，获取对应描述
	 * @param state
	 * @return
	 */
	public static String getDescByKey(String keyValue){
		
		if(null != keyValue){
			for(UserTypeEnum st : UserTypeEnum.values()){
				String key = st.getKey();
				if(key.equals(keyValue) ){
					return st.getDesc();
				}
			}
		}
		return null;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
