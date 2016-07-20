package com.yougou.wfx.customer.model.order;

/**
 * <p>Title: OrderCommodityVo</p>
 * <p>Description: 订单中商品的封装类</p>
 * @author: zheng.qq
 * @date: 2016年7月13日
 */
public class OrderCommodityVo {
	private String commodityNo;
	private String skuId;
	private String imageUrl;
	private double wfxPrice;
	private int count;
	public String getCommodityNo() {
		return commodityNo;
	}
	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public double getWfxPrice() {
		return wfxPrice;
	}
	public void setWfxPrice(double wfxPrice) {
		this.wfxPrice = wfxPrice;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
