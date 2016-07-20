package com.yougou.wfx.customer.model.commodity;

import java.io.Serializable;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/30
 */
public class CommodityCookieVo implements Serializable {
    private static final long serialVersionUID = 6155603611132957590L;

    private String id;
    /**
     * 店铺
     */
    private String shopId;
    /**
     * 商家编码
     */
    private String merchantCode;

    /**
     * 商品commodityNo
     */
    private String commodityNo;

    /**
     * skuID
     */
    private String skuid;
    /**
     * sku数量
     */
    private int count;

    /**
     * 是否勾选
     */
    private boolean isChecked;
    
    /**
     * 购买的模式
     */
    private int buyMode;

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

    public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getCommodityNo() {
        return commodityNo;
    }

    public void setCommodityNo(String commodityNo) {
        this.commodityNo = commodityNo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

	public int getBuyMode() {
		return buyMode;
	}

	public void setBuyMode(int buyMode) {
		this.buyMode = buyMode;
	}
}
