package com.yougou.wfx.customer.model.shoopingcart;


import java.io.Serializable;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/5
 */
public class ShoopingCartResultVo implements Serializable {
    private static final long serialVersionUID = 4974314269117235641L;

    /**
     * 总数量
     */
    private double totalPrice = 0;
    /**
     * 总金额
     */
    private int totalCount = 0;

    /**
     * 共页面显示
     */
    private String viewPrice;


    /**
     * sku条目数
     */
    private int totalSize = 0;

    private boolean isSelectAll = false;

    public String getViewPrice() {
        return viewPrice;
    }

    public void setViewPrice(String viewPrice) {
        this.viewPrice = viewPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isSelectAll() {
        return isSelectAll;
    }

    public void setSelectAll(boolean selectAll) {
        isSelectAll = selectAll;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
