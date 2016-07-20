package com.yougou.wfx.customer.model.commodity;

import java.io.Serializable;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/30
 */
public class CommoditySearchVo implements Serializable {
    private static final long serialVersionUID = -1413933782406539743L;

    /**
     * 分销商ID
     */
    private String sellerId;

    /**
     * 店铺id
     */
    private String shopId;

    /**
     * 分类编码
     */
    private String categoryId;

    private String keyWord;

    /**
     * 当前页
     */
    private int page = 1;
    /**
     * 每页数量
     */
    private int size = 20;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
