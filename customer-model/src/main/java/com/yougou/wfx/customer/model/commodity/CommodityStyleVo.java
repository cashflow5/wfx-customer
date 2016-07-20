package com.yougou.wfx.customer.model.commodity;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/29
 */
public class CommodityStyleVo implements Serializable {
    private static final long serialVersionUID = - 9027940958607588852L;
    /**
     * id
     */
    private String id;
    /**
     * 商品名称
     */
    private String commodityName;
    /**
     * prodDesc
     */
    private String prodDesc;


    private Integer deleteFlag;
    /**
     * 商品编号
     */
    private String no;
    /**
     * 分类编码
     */
    private String catNo;
    /**
     * 品牌编码
     */
    private String brandNo;

    /**
     * 分类结构字符串
     */
    private String catStructname;
    /**
     * 价格
     */
    private Double minPrice;
    /**
     * 分类名称
     */
    private String catName;
    /**
     * 品牌名称
     */
    private String brandName;
    /**
     * 市场价
     */
    private Double publicPrice;
    /**
     * 最高价格
     */
    private Double maxPrice;
    /**
     * 默认显示图片(搜索使用)
     */
    private String defaultPic;
    /**
     * 供应商款色编码
     */
    private String supplierCode;
    /**
     * 销售价
     */
    private Double salePrice;
    /**
     * 成本价
     */
    private Double costPrice;
    /**
     * 款号
     */
    private String styleNo;

    /**
     * 出品年份
     */
    private String years;
    /**
     * 规格编码
     */
    private String specNo;
    /**
     * 规格名称(一般为颜色的名称)
     */
    private String specName;
    /**
     * 成本价2
     */
    private Double costPrice2;
    /**
     * 商家编号
     */
    private String merchantCode;
    /**
     * 卖点
     */
    private String sellingPoint;

    /**
     * 是否微分销商品,1是，0否
     */
    private Integer isWfxCommodity;
    /**
     * 微分销商品编码
     */
    private String wfxCommodityNo;
    /**
     * 微分销平台上下架状态 1上架，2下架，3未上架
     */
    private Integer isOnsale;
    /**
     * wfxPrice
     */
    private Double wfxPrice;

    /**
     * 供应商id
     */
    private String supplierId;

    /**
     * 库存
     */
    private int stock;

    /**
     * sku列表
     */
    private List<ProductVo> products;

    /**
     * 商品图片
     */
    private List<PictureVo> pictures;

    /**
     * 列表mb图，取第一个
     */
    private String pictureMb;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getCatNo() {
        return catNo;
    }

    public void setCatNo(String catNo) {
        this.catNo = catNo;
    }

    public String getBrandNo() {
        return brandNo;
    }

    public void setBrandNo(String brandNo) {
        this.brandNo = brandNo;
    }

    public String getCatStructname() {
        return catStructname;
    }

    public void setCatStructname(String catStructname) {
        this.catStructname = catStructname;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Double getPublicPrice() {
        return publicPrice;
    }

    public void setPublicPrice(Double publicPrice) {
        this.publicPrice = publicPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getDefaultPic() {
        return defaultPic;
    }

    public void setDefaultPic(String defaultPic) {
        this.defaultPic = defaultPic;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Double costPrice) {
        this.costPrice = costPrice;
    }

    public String getStyleNo() {
        return styleNo;
    }

    public void setStyleNo(String styleNo) {
        this.styleNo = styleNo;
    }

    public String getSpecNo() {
        return specNo;
    }

    public void setSpecNo(String specNo) {
        this.specNo = specNo;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Double getCostPrice2() {
        return costPrice2;
    }

    public void setCostPrice2(Double costPrice2) {
        this.costPrice2 = costPrice2;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getSellingPoint() {
        return sellingPoint;
    }

    public void setSellingPoint(String sellingPoint) {
        this.sellingPoint = sellingPoint;
    }

    public Integer getIsWfxCommodity() {
        return isWfxCommodity;
    }

    public void setIsWfxCommodity(Integer isWfxCommodity) {
        this.isWfxCommodity = isWfxCommodity;
    }

    public String getWfxCommodityNo() {
        return wfxCommodityNo;
    }

    public void setWfxCommodityNo(String wfxCommodityNo) {
        this.wfxCommodityNo = wfxCommodityNo;
    }

    public Integer getIsOnsale() {
        return isOnsale;
    }

    public void setIsOnsale(Integer isOnsale) {
        this.isOnsale = isOnsale;
    }

    public Double getWfxPrice() {
        return wfxPrice;
    }

    public void setWfxPrice(Double wfxPrice) {
        this.wfxPrice = wfxPrice;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        if (stock == null) {
            this.stock = 0;
            return;
        }
        this.stock = stock;
    }

    public List<ProductVo> getProducts() {
        return products;
    }

    public void setProducts(List<ProductVo> products) {
        this.products = products;
    }

    public List<PictureVo> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureVo> pictures) {
        this.pictures = pictures;
    }

    public String getPictureMb() {
        return pictureMb;
    }

    public void setPictureMb(String pictureMb) {
        this.pictureMb = pictureMb;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }
}
