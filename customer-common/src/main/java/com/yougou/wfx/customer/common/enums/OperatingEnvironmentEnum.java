package com.yougou.wfx.customer.common.enums;

/**
 * <p>Title: OperatingEnvironmentEnum</p>
 * <p>Description: </p>
 * @author: zheng.qq
 * @date: 2016年6月7日
 */
public enum OperatingEnvironmentEnum {
	WX_PLATFORM("1","微信平台"), 
	BROWSER_PLATFORM("2","浏览器平台"), 
	IOS_APP_PLATFORM("3","IOS_APP平台"), 
	ANDROID_APP_PLATFORM("4","ANDROID_APP平台"); 

	
	private String key;
	private String desc;
	
	
	OperatingEnvironmentEnum(String key,String desc){
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
			for(OperatingEnvironmentEnum st : OperatingEnvironmentEnum.values()){
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
