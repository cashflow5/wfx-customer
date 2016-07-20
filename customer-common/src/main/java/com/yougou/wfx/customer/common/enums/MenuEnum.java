package com.yougou.wfx.customer.common.enums;

/**
 * <p>Title: MenuEnum</p>
 * <p>Description: </p>
 * @author: zheng.qq
 * @date: 2016年6月30日
 */
public enum MenuEnum {
	MENU_SHOP("shop","商城"),
	MENU_FANS("fans","优粉"),
	MENU_DISCOVER("discover","发现"),
	MENU_SHOPCART("shopcart","购物车"),
	MENU_MINE("mine","我的");

	private String key;
	private String desc;
	
	
	MenuEnum(String key,String desc){
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
			for(MenuEnum st : MenuEnum.values()){
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
