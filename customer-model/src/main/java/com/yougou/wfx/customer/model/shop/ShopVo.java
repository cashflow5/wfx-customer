package com.yougou.wfx.customer.model.shop;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by zhang.sj on 2016/3/22.
 */
public class ShopVo implements Serializable {


    private static final long serialVersionUID = -1175371635559737231L;
    /**
     * 店铺id
     **/
    private String id;

    /**
     * 分销商id
     **/
    private String sellerId;

    /**
     * 父级分销商id
     */
    private String parentId;

    /**
     * 登录账号
     **/
    private String loginName;

    /**
     * 店铺名称
     **/
    // @Pattern(regexp = "^[a-zA-Z\\u4e00-\\u9fa5]{1,15}$", message = "店铺名称最多由15个字母和汉字组成!")
    private String name;

    /**
     * 店铺公告
     **/
    private String notice;

    /**
     * 联系人
     **/
    @Pattern(regexp = "^[a-zA-Z\\u4e00-\\u9fa5]{1,15}$", message = "联系人真实姓名最多由15个字母和汉字组成!")
    private String contact;

    /**
     * 联系电话
     **/
    private String mobile;

    /**
     * 店铺URL
     **/
    private String shopUrl;

    /**
     * 店铺Logo图片URL
     **/
    private String logoUrl;

    /**
     * 店招图片URL
     **/
    private String sigUrl;

    /**
     * 二维码图片URL
     **/
    private String qrCodeUrl;
    /**
     * 店铺状态：1开启，2关闭
     */
    private Integer status;
    
    /** 店铺编码 */
	private String shopCode ;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSigUrl() {
        return sigUrl;
    }

    public void setSigUrl(String sigUrl) {
        this.sigUrl = sigUrl;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
    
}
