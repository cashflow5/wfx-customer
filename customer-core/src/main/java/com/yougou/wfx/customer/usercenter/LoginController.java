package com.yougou.wfx.customer.usercenter;

import com.google.common.base.Strings;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.crypto.CryptoUtils;
import com.yougou.wfx.customer.common.crypto.PasswordCryptoUtils;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.session.SessionWXUserInfo;
import com.yougou.wfx.customer.common.spring.BindingUtils;
import com.yougou.wfx.customer.common.utils.LogUtils;
import com.yougou.wfx.customer.configurations.weixin.WeiXinProperties;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.customer.model.usercenter.UserLoginAccountVo;
import com.yougou.wfx.customer.model.usercenter.UserLoginVo;
import com.yougou.wfx.customer.model.weixin.base.WXAccessTokenResponse;
import com.yougou.wfx.customer.model.weixin.base.WXUserInfoResponse;
import com.yougou.wfx.customer.service.shop.IShopService;
import com.yougou.wfx.customer.service.shoppingcart.IShoppingCartService;
import com.yougou.wfx.customer.service.usercenter.ILoginService;
import com.yougou.wfx.customer.service.weixin.IWXService;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.ResultCodeEnum;
import com.yougou.wfx.member.api.front.IMemberAccountFrontApi;
import com.yougou.wfx.member.dto.output.MemberAccountOutputDto;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.Map;

/**
 * 登陆用的Controller
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/23 上午11:46
 * @since 1.0 Created by lipangeng on 16/3/23 上午11:46. Email:lipg@outlook.com.
 */
@Controller
@RequestMapping
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ILoginService loginService;
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private WeiXinProperties weiXinProperties;
    @Autowired
    private IWXService wxService;
    @Autowired
    private IShopService shopService;

    /**
     * 用户登陆页面
     *
     * @since 1.0 Created by lipangeng on 16/3/23 下午1:50. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {
        logger.debug(LogUtils.requestInfo("访问登陆页面"));
        Map<String, Object> modelMap = model.asMap();
        Object errorMsg = modelMap.get("errorMsg");
        if (errorMsg == null || Strings.isNullOrEmpty(errorMsg.toString())) {
            SessionUtils.setLoginReferer();
        }
        UserLoginVo rememberMeValue = SessionUtils.getRememberMeValue();
        if (rememberMeValue != null) {
            model.addAttribute("userName", rememberMeValue.getLoginName());
            model.addAttribute("passWord", "********");
            model.addAttribute("isAutoLogin", true);
            logger.debug(LogUtils.requestInfo("UserName:{},进行自动登陆"), rememberMeValue.getLoginName());
        }
        return "usercenter/login";
    }

    /**
     * 登陆的处理过程
     *
     * @since 1.0 Created by lipangeng on 16/3/23 下午2:39. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String doLogin(@Valid UserLoginVo loginVo, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        logger.debug(LogUtils.requestInfo("UserName:{},进行登陆验证"), loginVo.getLoginName());
        // 自动登录的数据填充
        UserLoginVo rememberMeValue = SessionUtils.getRememberMeValue();
        if (rememberMeValue != null) {
            loginVo.setUserId(rememberMeValue.getUserId());
            loginVo.setLoginName(rememberMeValue.getLoginName());
            loginVo.setLoginPasswordEncode(rememberMeValue.getLoginPasswordEncode());
            loginVo.setSaveLogin(false);
            loginVo.setAutoLogin(true);
            logger.debug(LogUtils.requestInfo("UserName:{},自动登陆数据已填充"), loginVo.getLoginName());
        }
        // 如果不是自动登录则验证用户名密码
        if (bindingResult.hasErrors() && !loginVo.isAutoLogin()) {
            redirectAttributes.addFlashAttribute("errorMsg", "错误提示:手机号或密码不能为空!");
            // 如果自动登录认证失败,
            if (loginVo.isAutoLogin()) {
                SessionUtils.removeRememberMe();
            }
            logger.info(LogUtils.requestInfo("自动登陆失败,登陆数据效验错误。参考信息{}"), BindingUtils.errors(bindingResult));
            return "redirect:" + WfxConstant.WFX_LOGIN_URL;
        }
        // 进行登陆操作
        UserLoginAccountVo userLoginAccount = loginService.doLogin(loginVo);
        if (!userLoginAccount.isLogined()) {
            if (loginVo.isAutoLogin()) {
                redirectAttributes.addFlashAttribute("errorMsg", "错误提示:自动登录失效,请重新登录!");
                SessionUtils.removeRememberMe();
                logger.info(LogUtils.requestInfo("UserName:{},自动登录失效"), loginVo.getLoginName());
            } else {
                redirectAttributes.addFlashAttribute("errorMsg", "错误提示:用户名或密码错误!");
                logger.info(LogUtils.requestInfo("UserName:{},登陆失败,用户名或密码错误。"), loginVo.getLoginName());
            }
            return "redirect:" + WfxConstant.WFX_LOGIN_URL;
        }

        //更新session中的用户信息
        SessionUserDetails userDetails = loginService.updateSessionUserDetails(userLoginAccount).getData();
        // 记住我功能
        if (userLoginAccount.isLogined()) {
            // 记住我功能
            if (loginVo.isSaveLogin()) {
                loginService.updateRememberMe(userDetails);
            } else if (!loginVo.isAutoLogin()) {
                SessionUtils.removeRememberMe();
            }
        }
        // 登陆成功,返回页面
        String redirectUrl = SessionUtils.getLoginReferer();
        SessionUtils.removeLoginReferer();
        boolean isPay = redirectUrl.contains("isPay=true");
        // 合并购物车
        shoppingCartService.mergeShoppingCartData(isPay);
        logger.info(LogUtils.requestInfo("UserName:{},登陆成功"), loginVo.getLoginName());
        return "redirect:" + redirectUrl;
    }
    
    
    /**
     * 微信授权登录
     *
     * @since 1.0 Created by lipangeng on 16/3/23 下午1:50. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "login-wx", method = RequestMethod.GET)
    public String login_wx(HttpServletRequest request) {
    	SessionUtils.setLoginReferer();
    	Result<String> authorizeBaseUrl = wxService.getAuthorizeBaseUrl(weiXinProperties.getWxAuthorizeUrl(),
                weiXinProperties.getAppId(),
                weiXinProperties.getGrantBackUrl());
    	
    	if (authorizeBaseUrl.hasError()) {
    		return "redirect:" + WfxConstant.WFX_LOGIN_URL;
        }else{
        	return "redirect:"+authorizeBaseUrl.getData();
        }
    }
    
    /**
     * 微信授权登录
     *
     * @since 1.0 Created by lipangeng on 16/3/23 下午1:50. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "get-wx-userinfo", method = RequestMethod.GET)
    public String getWxUserInfo(String code) {
    	Result<WXAccessTokenResponse> accessToken = wxService.getAccessTokenByCode(weiXinProperties.getAppId(), 
    			weiXinProperties.getAppSecret(), code);
    	if(accessToken.hasError()){
    		 logger.error(accessToken.getMessage());
    		 return "error";
    	}
    	Result<WXAccessTokenResponse> refreshToken = wxService.getRefreshToken(weiXinProperties.getAppId(), accessToken.getData().getRefreshToken());
    	if(refreshToken.hasError()){
	   		 logger.error(refreshToken.getMessage());
	   		 return "error";
    	}
    	
    	Result<WXUserInfoResponse> userInfo = wxService.getUserInfo(accessToken.getData().getOpenId(), refreshToken.getData().getAccessToken());
    	if(userInfo.hasError()){
	   		 logger.error(userInfo.getMessage());
	   		 return "error";
    	}
    	loginService.doWithWXUserInfo(userInfo.getData(), weiXinProperties.getAppId(), weiXinProperties.getAppSecret());
    	
        if(ObjectUtils.isEmpty(SessionUtils.getLoginReferer())){
        	return "redirect:" + WfxConstant.WFX_INDEX_URL;
        }
        return "redirect:" + SessionUtils.getLoginReferer();
    }
    
    
}
