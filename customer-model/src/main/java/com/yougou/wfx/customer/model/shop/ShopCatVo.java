package com.yougou.wfx.customer.model.shop;

import java.io.Serializable;
import java.util.Set;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/29
 */
public class ShopCatVo implements Serializable {
    private static final long serialVersionUID = -3809957455457650501L;
    /**
     * 子级分类
     */
    private Set<ShopCatVo> childs;
    private java.lang.String id;
    /**
     * 层级
     */
    private java.lang.Integer level;
    /**
     * 分类名称
     */
    private java.lang.String name;

    private java.lang.Integer num;
    /**
     * 分销商ID
     */
    private java.lang.String sellerId;

    public Set<ShopCatVo> getChilds() {
        return childs;
    }

    public void setChilds(Set<ShopCatVo> childs) {
        this.childs = childs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}
