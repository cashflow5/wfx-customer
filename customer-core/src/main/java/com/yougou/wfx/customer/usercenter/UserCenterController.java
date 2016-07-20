package com.yougou.wfx.customer.usercenter;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Strings;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.bean.BeanUtils;
import com.yougou.wfx.customer.common.configurations.WFXProperties;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.datetime.DateUtils;
import com.yougou.wfx.customer.common.enums.MenuEnum;
import com.yougou.wfx.customer.common.enums.UserTypeEnum;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.session.SessionWXUserInfo;
import com.yougou.wfx.customer.configurations.weixin.WeiXinProperties;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.customer.model.usercenter.UserLoginAccountVo;
import com.yougou.wfx.customer.model.usercenter.UserLoginVo;
import com.yougou.wfx.customer.model.usercenter.account.UserInfoVo;
import com.yougou.wfx.customer.model.usercenter.bindphone.PhoneBindVo;
import com.yougou.wfx.customer.model.weixin.base.WXAccessTokenResponse;
import com.yougou.wfx.customer.model.weixin.base.WXUserInfoResponse;
import com.yougou.wfx.customer.service.order.IOrderService;
import com.yougou.wfx.customer.service.shop.ISellerService;
import com.yougou.wfx.customer.service.shop.IShopService;
import com.yougou.wfx.customer.service.usercenter.IAccountService;
import com.yougou.wfx.customer.service.usercenter.ILoginService;
import com.yougou.wfx.customer.service.weixin.IWXService;

/**
 * Created by zhang.sj on 2016/3/22.
 */

@Controller
public class UserCenterController {
	private static final Logger logger = LoggerFactory.getLogger(UserCenterController.class);

	@Autowired
	private ISellerService sellerService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private IWXService wxService;
	@Autowired
	private WeiXinProperties weiXinProperties;
	@Autowired
	private IShopService shopService;
	@Autowired
	private WFXProperties wfxProperties;
	@Autowired
	private ILoginService loginService;

	/**
	 * 个人中心首页
	 *
	 * @since 1.0 Created by lipangeng on 16/3/30 下午4:42.
	 *        Email:lipg@outlook.com.
	 */
	@RequestMapping("/usercenter")
	@LoginValidate
	public String admin(ModelMap model, @RequestParam(name = "shopId", required = false) String shopId,
			@RequestParam(name = "unionId", required = false) String unionId,
			HttpServletRequest request) {
		//设置当前菜单
		SessionUtils.setCurrentMenuID(MenuEnum.MENU_MINE.getKey());
		String flag = request.getParameter("flag");
		//绑定微信的执行结果
		String bindRst = request.getParameter("bindRst");
		
		if (!Strings.isNullOrEmpty(shopId)) {
			ShopVo shop = shopService.getShopModelById(shopId);
			if (shop != null) {
				SessionUtils.setCurrentShopId(shopId);
			}
		}
		if(!Strings.isNullOrEmpty(unionId)){
			WXUserInfoResponse userInfo = new WXUserInfoResponse();
			userInfo.setUnionid(unionId);
			UserLoginAccountVo userLoginAccountVo = loginService.doWXLogin(userInfo);
			//是否绑定微信
			if(userLoginAccountVo.isLogined()){
				if(StringUtils.isEmpty(userLoginAccountVo.getMemberAccount().getLoginName())){
					model.addAttribute("isBindWX", "1");
				}else{
					model.addAttribute("isBindWX", "0");
				}
			}else{
				model.addAttribute("isBindWX", "1");
			}
			model.addAttribute("bindRst", bindRst);
		}
		
		SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
		model.addAttribute("userAccount", accountService.getUserAccount(loginUserDetails.getUserId()));
		model.addAttribute("orderCount", orderService.getUserOrderCount(loginUserDetails.getUserId()));
		model.addAttribute("memberType", sellerService.getMemberTypeByMemberId(loginUserDetails.getUserId()).getKey());
		model.addAttribute("memberId",loginUserDetails.getUserId());
		model.addAttribute("phoneNumber", BeanUtils.shortPhoneNum(loginUserDetails.getUserName()));
		fillShopInfo(model);
		return "usercenter/admin";
	}

	/**
	 * B端嵌入C端个人中心首页
	 */
	@RequestMapping("/wfx-app/usercenter")
	public String adminForApp(ModelMap model, @RequestParam(name = "shopId", required = false) String shopId,
			@RequestParam(name = "loginName", required = false) String loginName, 
			@RequestParam(name = "loginPassword", required = false) String loginPassword,
			@RequestParam(name = "unionId", required = false) String unionId) {
		//设置当前菜单
		SessionUtils.setCurrentMenuID(MenuEnum.MENU_MINE.getKey());
		//注册账号登录
		if(!StringUtils.isEmpty(loginName)){
			// 直接登录
			UserLoginVo loginVo = new UserLoginVo();
			loginVo.setLoginName(loginName);
			loginVo.setLoginPassword(loginPassword);
			loginVo.setAutoLogin(false);
			loginVo.setSaveLogin(false);
			UserLoginAccountVo userLoginAccount = loginService.doLogin(loginVo);
			// 更新session中的用户信息
			loginService.updateSessionUserDetails(userLoginAccount).getData();
		}else{
			//不考虑没有微信注册的情况
			WXUserInfoResponse userInfo = new WXUserInfoResponse();
			userInfo.setUnionid(unionId);
			loginService.doWithWXUserInfo(userInfo, weiXinProperties.getAppId(), weiXinProperties.getAppSecret());
		}
	
		if (!Strings.isNullOrEmpty(shopId)) {
			ShopVo shop = shopService.getShopModelById(shopId);
			if (shop != null) {
				SessionUtils.setCurrentShopId(shopId);
			}
		}
		SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
		model.addAttribute("userAccount", accountService.getUserAccount(loginUserDetails.getUserId()));
		model.addAttribute("orderCount", orderService.getUserOrderCount(loginUserDetails.getUserId()));
		model.addAttribute("phoneNumberShow", loginUserDetails.getPhoneNumber());
		model.addAttribute("isOldVersion", SessionUtils.getRequest().getSession().getAttribute("isOldVersion"));
		fillShopInfo(model);
		return "usercenter/admin";
	}

	/**
	 * 用户信息页面
	 *
	 * @since 1.0 Created by lipangeng on 16/4/4 下午1:46. Email:lipg@outlook.com.
	 */
	@RequestMapping(value = "/usercenter/userinfo", method = RequestMethod.GET)
	@LoginValidate
	public String userInfo(Model model) {
		SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
		UserInfoVo userInfo = accountService.getUserInfo(loginUserDetails.getUserId());
		model.addAttribute("userAccount", accountService.getUserAccount(loginUserDetails.getUserId()));
		model.addAttribute("userInfo", userInfo);
		model.addAttribute("wxConfig", wxService.getWXConfig(weiXinProperties.getAppId()));
		return "usercenter/account-information";
	}

	/**
	 * 更新用户信息
	 *
	 * @param birthday
	 *            出生日期
	 * @param sex
	 *            性别
	 * @param updateType
	 *            更新类型
	 *
	 * @since 1.0 Created by lipangeng on 16/4/5 上午11:07.
	 *        Email:lipg@outlook.com.
	 */
	@RequestMapping(value = "/usercenter/userinfo", method = RequestMethod.POST, produces = "application/json")
	@LoginValidate
	@ResponseBody
	public Result updateUserInfo(@RequestParam(value = "birthday", required = false) String birthday, @RequestParam(value = "sex", required = false) Integer sex, @RequestParam("updateType") String updateType) {
		SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
		if (updateType.equals("birthday")) {
			Date birthdayDate = DateUtils.parseDate(birthday);
			if (birthdayDate == null) {
				return Result.create(false, "出生日期不正确");
			}
			if (birthdayDate.getTime() > System.currentTimeMillis()) {
				return Result.create(false, "出生日期不能大于今天");
			}
			Result result = accountService.updateUserBirthday(loginUserDetails.getUserId(), birthdayDate);
			if (result.hasError()) {
				return Result.create(false, "更新出生日期发生错误.错误:" + result.getMessage());
			}
		} else if (updateType.equals("sex")) {
			Result result = accountService.updateUserSex(loginUserDetails.getUserId(), sex);
			if (result.hasError()) {
				return Result.create(false, "更新性别信息发生错误.错误:" + result.getMessage());
			}
		}
		return Result.create(true, "成功更新用户信息", accountService.getUserInfo(loginUserDetails.getUserId()));
	}

	/**
	 * 创建店铺成功页
	 *
	 * @return
	 */
	@RequestMapping("/create_success")
	public String createSuccess() {
		return "usercenter/create-success";
	}

	/**
	 * 创建店铺
	 *
	 * @return
	 */
	@LoginValidate
	@RequestMapping(value = "/{shopId}/create", method = RequestMethod.GET)
	public String create(ModelMap modelMap, @PathVariable("shopId") String shopId) {
		modelMap.addAttribute("shopId", shopId);
		Map<String, String> imgMap = shopService.getShopDefaultImagesUrl();
		modelMap.addAttribute("imgurl", imgMap.get(WfxConstant.WFX_SHOP_LOGO_URL_DEFALUT));
		return "usercenter/shop/create-shop";
	}

	@RequestMapping(value = "/{shopId}/create", method = RequestMethod.POST)
	@LoginValidate
	public String doCreate(@Valid ShopVo shopVo, BindingResult result, ModelMap modelMap, @PathVariable("shopId") String parentShopId) {
		if (result.hasErrors()) {
			for (FieldError error : result.getFieldErrors()) {
				modelMap.addAttribute("errorMsg_" + error.getField().toString(), error.getDefaultMessage());
			}
			return "usercenter/shop/create-shop";
		}
		Result<String> resultData = sellerService.createShop(shopVo, parentShopId);
		if (resultData.hasError() && resultData.getCode() != 2) {
			modelMap.addAttribute("errorMsg_name", resultData.getMessage());
			return "usercenter/shop/create-shop";
		}
		modelMap.addAttribute("result", resultData);
		modelMap.addAttribute("shopId", parentShopId);
		if (resultData.hasError()) {
			return "usercenter/shop/create-fail";
		}
		return "usercenter/shop/create-success";
	}

	@RequestMapping("usercenter/userHeadUrl")
	@ResponseBody
	@LoginValidate
	public String userHeadUrl() {
		SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
		return accountService.getUserInfo(loginUserDetails.getUserId()).getUserHeadUrl();
	}

	@RequestMapping(value = "/{shopId}/create_guide")
	@LoginValidate
	public String createGuide(ModelMap modelMap, @PathVariable("shopId") String shopId) {
		modelMap.addAttribute("shopId", shopId);
		return "usercenter/shop/create-guide";
	}

	/**
	 * 优粉
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("usercenter/ufans")
	@LoginValidate
	public String ufans(ModelMap modelMap, HttpServletRequest request) {
		String flag = request.getParameter("flag");
		String userId = SessionUtils.getLoginUserDetails().getUserId();
		if(StringUtils.isEmpty(flag)){
			String type = sellerService.getMemberTypeByMemberId(userId).getKey();
			if(type.equals("0_1")||type.equals("1_1")){
				return "redirect:/weixin-authorisation.sc?from=ufans";
			}
		}
		ShopVo shopVo = shopService.getShopModelByUserId(userId);
		modelMap.addAttribute("memberId", userId);
		if (shopVo == null) {
			return "usercenter/shop/ufansing";
		}
		modelMap.addAttribute("shop", shopVo);
		return "usercenter/shop/ufansed";
	}
	
	/**
	 * 进入绑定手机页面
	 *
	 * @zheng.qq
	 */
	@RequestMapping(value = "/usercenter/bind-phone", method = RequestMethod.GET)
	public String bindPhone(Model model) {

		UserTypeEnum userTypeEnum = sellerService.getMemberTypeByMemberId(SessionUtils.getLoginUserDetails().getUserId());
		// 绑定过手机
		if (!UserTypeEnum.MEMBER_NO_AGENT_0_2.equals(userTypeEnum) && !UserTypeEnum.MEMBER_AND_AGENT_1_2.equals(userTypeEnum)) {
			model.addAttribute("phoneNumber", SessionUtils.getLoginUserDetails().getUserName());
			return "usercenter/mobile-phone-update";
		}

		return "usercenter/mobile-phone-bind";
	}
	
	/**
	 * 执行绑定手机
	 *
	 * @zheng.qq
	 */
	@RequestMapping(value = "/usercenter/bind-phone-action", method = RequestMethod.POST)
	public String bindPhoneAction(@Valid PhoneBindVo phoneBindVo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		String regRedirect = "redirect:" + WfxConstant.WFX_PHONE_BIND_URL;
		// 1.验证手机号
		if (accountService.hasPhoneNumber(phoneBindVo.getPhoneNum())) {
			redirectAttributes.addFlashAttribute("errorMsg", "错误提示:该手机号码已被注册!");
			return regRedirect;
		}
		// 2.验证短信验证码
		Result<Boolean> result = accountService.checkSMSCode(phoneBindVo.getPhoneNum(), phoneBindVo.getSmsCode());
		if (result.hasError()) {
			redirectAttributes.addFlashAttribute("errorMsg", "错误提示:" + result.getMessage());
			return regRedirect;
		}
		if(SessionUtils.getWXUserInfo()!=null){
			String unionId = SessionUtils.getWXUserInfo().getUnionid();
			loginService.phoneSellerBind(unionId, phoneBindVo.getPhoneNum(), phoneBindVo.getSmsCode(), phoneBindVo.getPassWord());
		}else{
			logger.error("获取微信用户信息失败！");
		}
		
		return "redirect:/usercenter.sc";
	}
	
	
	/**
     * 微信授权登录
     *
     * @since 1.0 Created by lipangeng on 16/3/23 下午1:50. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "/usercenter/bind-weixin")
    public String login_wx(HttpServletRequest request) {
    	if(SessionUtils.getWXUserInfo()==null){
    		Result<String> authorizeBaseUrl = wxService.getAuthorizeBaseUrl(weiXinProperties.getWxAuthorizeUrl(),
                    weiXinProperties.getAppId(),
                    weiXinProperties.getIndexUrl()+"/usercenter/bind-weixin-action.sc");
        	
        	if (authorizeBaseUrl.hasError()) {
        		return "redirect:" + WfxConstant.WFX_LOGIN_URL;
            }else{
            	return "redirect:"+authorizeBaseUrl.getData();
            }
    	}else{
    		//绑定微信
    		if(SessionUtils.getWXUserInfo()!=null){
    			SessionWXUserInfo userInfo = SessionUtils.getWXUserInfo();
            	String memberId = SessionUtils.getLoginUserDetails().getUserId();
            	loginService.wxSellerBind(userInfo.getUnionid(), userInfo.getNickname(),
            			Integer.parseInt(userInfo.getSex()), userInfo.getHeadimgurl(), memberId);
    		}else{
    			logger.error("获取微信用户信息失败！");
    		}
            return "redirect:/usercenter.sc";
    	}
    	
        
    }
	
	
	/**
     * 微信授权登录
     *
     * @since 1.0 Created by lipangeng on 16/3/23 下午1:50. Email:lipg@outlook.com.
     */
	@RequestMapping(value = "/usercenter/bind-weixin-action")
	public String bindWXAction(String code) {
    	Result<WXAccessTokenResponse> accessToken = wxService.getAccessTokenByCode(weiXinProperties.getAppId(), 
    			weiXinProperties.getAppSecret(), code);
    	if(accessToken.hasError()){
    		 logger.error(accessToken.getMessage());
    		 return "redirect:/error.sc";
    	}
    	Result<WXAccessTokenResponse> refreshToken = wxService.getRefreshToken(weiXinProperties.getAppId(), accessToken.getData().getRefreshToken());
    	if(refreshToken.hasError()){
	   		 logger.error(refreshToken.getMessage());
	   		 return "redirect:/error.sc";
    	}
    	
    	Result<WXUserInfoResponse> useInfo = wxService.getUserInfo(accessToken.getData().getOpenId(), refreshToken.getData().getAccessToken());
    	if(useInfo.hasError()){
	   		 logger.error(useInfo.getMessage());
	   		 return "redirect:/error.sc";
    	}
    	//绑定微信
    	String memberId = SessionUtils.getLoginUserDetails().getUserId();
    	Result result = loginService.wxSellerBind(useInfo.getData().getUnionid(), useInfo.getData().getNickname(),
    			Integer.parseInt(useInfo.getData().getSex()), useInfo.getData().getHeadimgurl(), memberId);
    	if(result.isSuccess()){
    		return "redirect:/usercenter.sc?unionId=" + useInfo.getData().getUnionid() + "&bindRst=true";
    	}else{
    		return "redirect:/usercenter.sc?unionId=" + useInfo.getData().getUnionid() + "&bindRst=false";
    	}
        
    }
	
	/**
	 * 跳转到授权绑定微信账号 
	 *
	 * @return
	 */
	@LoginValidate
	@RequestMapping("/weixin-authorisation")
	public String weixinAuthorisation(HttpServletRequest request, ModelMap modelMap) {
		String from = request.getParameter("from");
		modelMap.addAttribute("from", from);
		return "usercenter/weixin-authorisation";
	}
	
	
	
	/**
	 * 正品保证
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/usercenter/quality-assurance")
	public String quality_assurance(HttpServletRequest request, ModelMap modelMap) {
		return "usercenter/quality-assurance";
	}
	
	

	private void fillShopInfo(ModelMap modelMap) {
		String shopId = SessionUtils.getCurrentShopIdOrDefault();
		ShopVo shopVo = shopService.getShopModelById(shopId);
		Assert.notNull(shopVo, "获取店铺信息异常!");
		modelMap.addAttribute("shop", shopVo);
	}
}
