package com.yougou.wfx.customer.model.mall;

public class Notice {
	
	//主键
	private String id;
	//标题
	private String title;
	//跳转类型
	private String jumpType;
	//跳转链接
	private String jumpLink;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJumpType() {
		return jumpType;
	}
	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}
	public String getJumpLink() {
		return jumpLink;
	}
	public void setJumpLink(String jumpLink) {
		this.jumpLink = jumpLink;
	}
}
