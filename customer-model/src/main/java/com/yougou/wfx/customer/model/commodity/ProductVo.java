package com.yougou.wfx.customer.model.commodity;

import java.io.Serializable;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/31
 */
public class ProductVo implements Serializable {

    private static final long serialVersionUID = 8435812806985103773L;

    private String id;


    private String commodityId;

    /**
     * 品牌编码
     */
    private String brandNo;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 商品类别编码
     */
    private String catNo;
    /**
     * 商品类别名称
     */
    private String catName;
    /**
     * 商品类别结构码
     */
    private String catStructName;
    /**
     * 尺码编号
     */
    private String sizeNo;
    /**
     * 尺码名称
     */
    private String sizeName;

    /**
     * 市场价
     */
    private Double costPrice;
    /**
     * 百丽价
     */
    private Double salePrice;
    /**
     * 货品编号
     */
    private String productNo;
    /**
     * 第三方条码
     */
    private String thirdPartyCode;
    /**
     * 百丽条码
     */
    private String insideCode;
    /**
     * 删除标记
     */
    private Integer deleteFlag;
    /**
     * 销售状态，0是停售、1是销售中
     */
    private Integer sellStatus;
    /**
     * 可售库存
     */
    private Integer inventoryNum;
    /**
     * 预占库存数量
     */
    private Integer prestoreInventoryNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getSizeNo() {
        return sizeNo;
    }

    public void setSizeNo(String sizeNo) {
        this.sizeNo = sizeNo;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getBrandNo() {
        return brandNo;
    }

    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCatNo() {
        return catNo;
    }

    public void setCatNo(String catNo) {
        this.catNo = catNo;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatStructName() {
        return catStructName;
    }

    public void setCatStructName(String catStructName) {
        this.catStructName = catStructName;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Integer getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(Integer sellStatus) {
        this.sellStatus = sellStatus;
    }

    public Integer getInventoryNum() {
        return inventoryNum;
    }

    public void setInventoryNum(Integer inventoryNum) {
        this.inventoryNum = inventoryNum;
    }

    public Integer getPrestoreInventoryNum() {
        return prestoreInventoryNum;
    }

    public void setPrestoreInventoryNum(Integer prestoreInventoryNum) {
        this.prestoreInventoryNum = prestoreInventoryNum;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getThirdPartyCode() {
        return thirdPartyCode;
    }

    public void setThirdPartyCode(String thirdPartyCode) {
        this.thirdPartyCode = thirdPartyCode;
    }

    public String getInsideCode() {
        return insideCode;
    }

    public void setInsideCode(String insideCode) {
        this.insideCode = insideCode;
    }
}
