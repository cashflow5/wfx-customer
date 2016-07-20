package com.yougou.wfx.customer.model.shoopingcart;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/5/6
 */
public class ShopCartCountVo {
    /**
     * sku数量,即商品条数
     */
    private int totalSku = 0;
    /**
     * sku总数量
     */
    private int totalCount = 0;

    public int getTotalSku() {
        return totalSku;
    }

    public void setTotalSku(int totalSku) {
        this.totalSku = totalSku;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
