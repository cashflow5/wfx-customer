package com.yougou.wfx.customer.model.shoopingcart;

import java.io.Serializable;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/5
 */
public class ShoppingCartChangeVo implements Serializable {
    private static final long serialVersionUID = 3804464314986360884L;

    private String id;

    private String shopId;
    /**
     * 商品No
     */
    private String commodityNo;

    /**
     * skuid
     */
    private String skuId;

    /**
     * sku数量
     */
    private int skuCount;

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

    public int getSkuCount() {
        return skuCount;
    }

    public void setSkuCount(int skuCount) {
        this.skuCount = skuCount;
    }
}
