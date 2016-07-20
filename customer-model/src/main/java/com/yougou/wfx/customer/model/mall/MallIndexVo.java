package com.yougou.wfx.customer.model.mall;

import java.util.ArrayList;
import java.util.List;

import com.yougou.wfx.appserver.vo.supply.BrandInfo;
import com.yougou.wfx.appserver.vo.supply.CategoryInfo;
import com.yougou.wfx.appserver.vo.supply.ShowImage;

public class MallIndexVo {
	
	//轮播图
	private List<ShowImage> showImageList = new ArrayList<>();
	//公告
	private List<Notice> noticeList = new ArrayList<>();
	//品牌
	private Object brandList;
	//分类
	private List<CategoryInfo> catgoryList = new ArrayList<>();
	
	public List<ShowImage> getShowImageList() {
		return showImageList;
	}
	
	public void setShowImageList(List<ShowImage> showImageList) {
		this.showImageList = showImageList;
	}
	
	public List<Notice> getNoticeList() {
		return noticeList;
	}
	
	public void setNoticeList(List<Notice> noticeList) {
		this.noticeList = noticeList;
	}
	
	public Object getBrandList() {
		return brandList;
	}
	
	public void setBrandList(Object brandList) {
		this.brandList = brandList;
	}
	
	public List<CategoryInfo> getCatgoryList() {
		return catgoryList;
	}
	
	public void setCatgoryList(List<CategoryInfo> catgoryList) {
		this.catgoryList = catgoryList;
	}
}
