package com.yougou.wfx.customer.model.usercenter.register;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 注册
 * 
 * @author li.lq
 * @Date 2016年5月31日
 */
public class RegisterVo {
	@NotBlank(message = "手机号不能为空")
	@Pattern(regexp = "^[1][3578][0-9]{9}$", message = "手机号格式不正确")
	private String loginName;
	@NotBlank(message = "验证码不能为空")
	private String imgCode;
	@NotBlank(message = "密码不能为空")
	@Pattern(regexp = "(?!^\\d+$)(?!^[a-zA-Z]+$)^[0-9a-zA-Z]{6,18}$", message = "密码仅允许6-18位,数字+密码的组合")
	private String passWord;
	@NotBlank(message = "确认密码不能为空")
	@Pattern(regexp = "(?!^\\d+$)(?!^[a-zA-Z]+$)^[0-9a-zA-Z]{6,18}$", message = "密码仅允许6-18位,数字+密码的组合")
	private String passWord2;
	@NotBlank(message = "验证码不能为空")
	private String smsCode;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getImgCode() {
		return imgCode;
	}

	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getPassWord2() {
		return passWord2;
	}

	public void setPassWord2(String passWord2) {
		this.passWord2 = passWord2;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
}
