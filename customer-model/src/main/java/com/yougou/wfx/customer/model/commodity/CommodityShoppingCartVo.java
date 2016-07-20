package com.yougou.wfx.customer.model.commodity;

import java.io.Serializable;

/**
 * 购物车页面model
 *
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/31
 */
public class CommodityShoppingCartVo implements Serializable {

    private static final long serialVersionUID = 373032272373418577L;

    private String id;

    /**
     * sku===>productNo
     */
    private String skuId;

    private String commodityId;
    /**
     * 商品commodityNo
     */
    private String commodityNo;

    /**
     * 名字
     */
    private String name;

    /**
     * 颜色
     */
    private String specName;

    /**
     * 尺码
     */
    private String size;

    /**
     * 分销价
     */
    private Double wfxPrice;

    /**
     * 数量
     */
    private int count;

    /**
     * 店铺Id
     */
    private String shopId;
    
    /**
     * 店铺编码
     */
    private String shopCode;


    /**
     * 店铺名称
     */
    private String shopName;
    
    /**
     * 商家编码
     */
    private String merchantCode;

    /**
     * 该条记录是否被修改
     */
    private boolean hasModify = false;

    /**
     * 是否被勾选
     */
    private boolean isChecked = true;
    /**
     * 购买模式
     */
    private Integer buyMode;

    /**
     * 微分销平台上下架状态
     */
    private Integer isOnsale;


    /**
     * 图片链接
     */
    private String imageUrl;

    /**
     * 库存
     */
    private int stock;

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Integer getIsOnsale() {
        return isOnsale;
    }

    public void setIsOnsale(Integer isOnsale) {
        this.isOnsale = isOnsale;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Integer getBuyMode() {
		return buyMode;
	}

	public void setBuyMode(Integer buyMode) {
		this.buyMode = buyMode;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean hasModify() {
        return hasModify;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getWfxPrice() {
        return wfxPrice;
    }

    public void setWfxPrice(Double wfxPrice) {
        this.wfxPrice = wfxPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getCommodityNo() {
        return commodityNo;
    }

    public void setCommodityNo(String commodityNo) {
        this.commodityNo = commodityNo;
    }

    public boolean isHasModify() {
        return hasModify;
    }

    public void setHasModify(boolean hasModify) {
        this.hasModify = hasModify;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }
}
