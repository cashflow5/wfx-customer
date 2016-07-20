package com.yougou.wfx.customer.model.shop;

import java.io.Serializable;

/**
 * <p>Title: SelectItemVo</p>
 * <p>Description: 用户选择的商品封装类（立即购买 、加入购物车等操作使用）</p>
 * @author: zheng.qq
 * @date: 2016年7月13日
 */ 
public class SelectItemVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**商品No**/
    private String commodityNo;
    /**SKU编号**/
    private String skuId;
    /**SKU数量**/
    private int count;
    /**SKU价格**/
    private double price;

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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
}
