package com.yougou.wfx.customer.usercenter;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Strings;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.usercenter.UserLoginAccountVo;
import com.yougou.wfx.customer.model.usercenter.UserLoginVo;
import com.yougou.wfx.customer.model.usercenter.register.RegisterVo;
import com.yougou.wfx.customer.service.shoppingcart.IShoppingCartService;
import com.yougou.wfx.customer.service.usercenter.IAccountService;
import com.yougou.wfx.customer.service.usercenter.ILoginService;

/**
 * 注册控制器
 * 
 * @author li.lq
 * @Date 2016年6月2日
 */
@Controller
@RequestMapping
public class RegisterController {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IShoppingCartService shoppingCartService;

	/**
	 * 注册页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register() {
		return "usercenter/register/register";
	}

	/**
	 * AJAX验证图片验证码
	 * 
	 * @param imageCode
	 * @return
	 */
	@RequestMapping(value = "register/checkImageCode", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> checkImageCode(String imageCode) {
		Result<Boolean> result = Result.create();
		// 验证图片验证码
		String sessionimageCode = SessionUtils.getImageCode();
		if (Strings.isNullOrEmpty(imageCode) || !imageCode.toLowerCase().equals(sessionimageCode.toLowerCase())) {
			result.setHasError(Boolean.TRUE).setCode(500).setMessage("输入的图片验证码有误!");
		}
		return result;
	}

	/**
	 * AJAX验证手机号
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "register/checkPhone", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> checkPhone(String phone) {
		return checkPhone_(phone);
	}

	/**
	 * AJAX发送验证短信
	 * 
	 * @param phone
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "register/sendSmsCode", method = RequestMethod.POST)
	@ResponseBody
	public Result sendSmsCode(String phone) {
		return accountService.sendSMSCode(phone);
	}

	/**
	 * AJAX验证手机验证码
	 * 
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "register/checkSmsCode", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> checkSmsCode(String phone, String smsCode) {
		return checkSmsCode_(phone, smsCode);
	}

    /**
	 * 注册协议页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "register/platform-protocol")
	public String platformprotocol() {
		return "usercenter/platform-protocol";
	}
	/**
	 * 注册
	 * 
	 * @param phone
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "register/doRegister", method = RequestMethod.POST)
	public String doRegister(@Valid RegisterVo registerVo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		String regRedirect = "redirect:" + WfxConstant.WFX_REGISTER_URL;// 注册页面
		String regSuccRedirect = "redirect:" + WfxConstant.WFX_REGISTER_SECCESS_URL;// 注册成功页面
		String loginRedirect = "redirect:" + WfxConstant.WFX_LOGIN_URL;// 登录页面

		// 1.验证手机号
		Result<Boolean> result = checkPhone_(registerVo.getLoginName());
		if (result.hasError()) {
			redirectAttributes.addFlashAttribute("errorMsg", "错误提示:" + result.getMessage());
			return regRedirect;
		}

		// 2.验证短信验证码
		result = checkSmsCode_(registerVo.getLoginName(), registerVo.getSmsCode());
		if (result.hasError()) {
			redirectAttributes.addFlashAttribute("errorMsg", "错误提示:" + result.getMessage());
			return regRedirect;
		}

		// 3.验证密码
		if (!registerVo.getPassWord().equals(registerVo.getPassWord2())) {
			redirectAttributes.addFlashAttribute("errorMsg", "错误提示:两次输入的密码不一致");
			return regRedirect;
		}
		 
		// 4.创建用户
		Result accountResult = accountService.createAccount(registerVo.getLoginName(), registerVo.getPassWord(), registerVo.getSmsCode(),SessionUtils.getCurrentShopIdOrDefault());
		if (accountResult.hasError()) {
			redirectAttributes.addFlashAttribute("errorMsg", "错误提示:" + accountResult.getMessage());
			return regRedirect;
		}

		// 5. 执行登陆
		UserLoginVo loginVo = new UserLoginVo();
		loginVo.setLoginName(registerVo.getLoginName());
		loginVo.setLoginPassword(registerVo.getPassWord());
		UserLoginAccountVo userLoginAccount = loginService.doLogin(loginVo);
		if (!userLoginAccount.isLogined()) {
			return loginRedirect;
		}
		// 更新session
		loginService.updateSessionUserDetails(userLoginAccount);
		// 合并购物车
		shoppingCartService.mergeShoppingCartData();
		// 6.跳转至成功页面
		return regSuccRedirect;
	}

	/**
	 * 注册成功页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "register/success")
	public String success() {
		return "usercenter/register/success";
	}

	// ----------------------------------共用方法------------------------------------------//

	/**
	 * 验证手机号
	 * 
	 * @param phone
	 * @return
	 */
	public Result<Boolean> checkPhone_(String phone) {
		Result<Boolean> result = Result.create();
		// 验证手机号码是否已经被注册
		if (accountService.hasPhoneNumber(phone)) {
			result.setHasError(Boolean.TRUE).setCode(500).setMessage("该手机号码已被注册!");
		}
		return result;
	}

	/**
	 * 验证短信验证码
	 * 
	 * @param phone
	 * @param smsCode
	 * @return
	 */
	public Result<Boolean> checkSmsCode_(String phone, String smsCode) {
		@SuppressWarnings("unchecked")
		Result<Boolean> result = accountService.checkSMSCode(phone, smsCode);
		return result;
	}
}
