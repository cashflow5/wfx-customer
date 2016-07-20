package com.yougou.wfx.customer.model.finance;

import java.io.Serializable;

/**
 * <p>Title: SellerAccountInfo</p>
 * <p>Description: </p>
 * @author: zheng.qq
 * @date: 2016年6月27日
 */
public class SellerAccountInfoVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6420640136345440376L;
	
	/**
	 * 分销商Id
	 */
	private String sellerId;
	 /**
     * 真实姓名
     */
    private String realName;

    /**
     * 身份证号
     */
    private String idCode;
    
    /**
     * 银行编号
     */
    private String bankNo;

    /**
     * 银行卡号
     */
    private String cardNo;

    /**
     * 开户行（eg:工商银行,建设银行,...）
     */
    private String bank;

    /**
     * 支行省份（eg:北京）
     */
    private String bankProvince;

    /**
     * 支行城市（eg:北京）
     */
    private String bankCity;

    /**
     * 开户支行(eg:富力城支行)
     */
    private String bankBranch;

    /**
     * 委托书扫描件地址
     */
    private String proxyPicUrl;

    /**
     * 身份证扫描件地址
     */
    private String idPicUrl;
    
    /**
     * 身份证反面
     */
    private String idPicUrlBack;
    
    private int bankCardNoChange; // 银行卡 (1:未改)
    private int idCodeNoChange; // 身份证
    private int uploadImgNoChange ; //上传的身份证图片

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String idCode) {
		this.idCode = idCode;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}

	public String getProxyPicUrl() {
		return proxyPicUrl;
	}

	public void setProxyPicUrl(String proxyPicUrl) {
		this.proxyPicUrl = proxyPicUrl;
	}

	public String getIdPicUrl() {
		return idPicUrl;
	}

	public void setIdPicUrl(String idPicUrl) {
		this.idPicUrl = idPicUrl;
	}

	public String getIdPicUrlBack() {
		return idPicUrlBack;
	}

	public void setIdPicUrlBack(String idPicUrlBack) {
		this.idPicUrlBack = idPicUrlBack;
	}

	public int getBankCardNoChange() {
		return bankCardNoChange;
	}

	public void setBankCardNoChange(int bankCardNoChange) {
		this.bankCardNoChange = bankCardNoChange;
	}

	public int getIdCodeNoChange() {
		return idCodeNoChange;
	}

	public void setIdCodeNoChange(int idCodeNoChange) {
		this.idCodeNoChange = idCodeNoChange;
	}

	public int getUploadImgNoChange() {
		return uploadImgNoChange;
	}

	public void setUploadImgNoChange(int uploadImgNoChange) {
		this.uploadImgNoChange = uploadImgNoChange;
	}
}
