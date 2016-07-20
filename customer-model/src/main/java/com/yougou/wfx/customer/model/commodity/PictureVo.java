package com.yougou.wfx.customer.model.commodity;

import java.io.Serializable;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/1
 */
public class PictureVo implements Serializable {

    private static final long serialVersionUID = 6127135096654068458L;
    private Long id;

    /**
     * 商品No
     */
    private String commodityNo;
    /**
     * 图片路径
     */
    private String url;

    /**
     * 图片类型
     */
    private String pictureType;

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommodityNo() {
        return commodityNo;
    }

    public void setCommodityNo(String commodityNo) {
        this.commodityNo = commodityNo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
