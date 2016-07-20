package com.yougou.wfx.customer.model.visitor;

import java.io.Serializable;
import java.util.Date;

/**
 * 访客VO
 * @author zhang.f1
 *
 */
public class VisitorVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	private String id;
	/**
	 * 店铺id
	 */
	private String shopId;
	/**
	 * 访客id
	 */
	private String visitorId;
	/**
	 * 访客ip地址
	 */
	private String visitorIp;
	/**
	 * 访客来源,微信：weixin,其他:other
	 */
	private String sourceType;
	/**
	 * 访问日期
	 */
	private Date visitTime;
	/**
	 * 访问类型,1、店铺首页,2、商品页
	 */
	private Integer visitType;
	/**
	 * 商品编码,访问商品页时记录
	 */
	private String commodityNo;
	
	/**
	 * 访客头像
	 */
	private String headShowImg;
	
	/**
	 * 访客名称(昵称)
	 */
	private String visitorName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public String getVisitorIp() {
		return visitorIp;
	}

	public void setVisitorIp(String visitorIp) {
		this.visitorIp = visitorIp;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public Integer getVisitType() {
		return visitType;
	}

	public void setVisitType(Integer visitType) {
		this.visitType = visitType;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public String getHeadShowImg() {
		return headShowImg;
	}

	public void setHeadShowImg(String headShowImg) {
		this.headShowImg = headShowImg;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String visitorName) {
		this.visitorName = visitorName;
	}

	

}
