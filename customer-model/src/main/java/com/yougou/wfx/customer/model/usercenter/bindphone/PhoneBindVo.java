package com.yougou.wfx.customer.model.usercenter.bindphone;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 注册
 * 
 * @author zheng.qq
 * @Date 2016年5月31日
 */
public class PhoneBindVo {
	@NotBlank(message = "手机号不能为空")
	@Pattern(regexp = "^[1][3578][0-9]{9}$", message = "手机号格式不正确")
	private String phoneNum;
	@NotBlank(message = "密码不能为空")
	@Pattern(regexp = "(?!^\\d+$)(?!^[a-zA-Z]+$)^[0-9a-zA-Z]{6,18}$", message = "密码仅允许6-18位,数字+密码的组合")
	private String passWord;
	@NotBlank(message = "验证码不能为空")
	private String smsCode;
	
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

}
