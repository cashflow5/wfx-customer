package com.yougou.wfx.customer.weixin;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yougou.tools.common.utils.StringUtil;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.session.SessionWXUserInfo;
import com.yougou.wfx.customer.common.utils.JacksonUtils;
import com.yougou.wfx.customer.configurations.weixin.WeiXinProperties;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.weixin.base.WXAccessTokenResponse;
import com.yougou.wfx.customer.model.weixin.base.WXUserInfoResponse;
import com.yougou.wfx.customer.model.weixin.custom.request.MessageRequest;
import com.yougou.wfx.customer.model.weixin.custom.request.TextMessageRequest;
import com.yougou.wfx.customer.model.weixin.custom.response.MessageResponse;
import com.yougou.wfx.customer.model.weixin.user.response.UserSearchResponse;
import com.yougou.wfx.customer.service.weixin.IWXService;
import com.yougou.wfx.customer.usercenter.LoginController;
import com.yougou.wfx.member.api.front.IMemberAccountFrontApi;
import com.yougou.wfx.member.dto.output.MemberAccountOutputDto;

/**
 * <p>Title: WeiXinController</p>
 * <p>Description: </p>
 * @author: zheng.qq
 * @date: 2016年6月28日
 */
@Controller
@RequestMapping("weixin")
public class WeiXinController {
	private static final Logger logger = LoggerFactory.getLogger(WeiXinController.class);
	@Autowired
    private WeiXinProperties weiXinProperties;
    @Autowired
    private IWXService wxService;
    @Autowired
	private IMemberAccountFrontApi memberAccountFrontApi;
	/**
	 * 微信的页面授权
	 * @param mode   1：用户手动授权  2：默认
	 * @return
	 */
    @RequestMapping(value = "page-authorization", method = RequestMethod.GET)
    public String login_wx(String mode) {
    	//设置授权时的页面地址，授权完之后，跳回该页面。
    	SessionUtils.setLoginReferer();
    	Result<String> result = null;
    	if(mode.equals("1")){
    		result = wxService.getAuthorizeBaseUrl(weiXinProperties.getWxAuthorizeUrl(),
                    weiXinProperties.getAppId(),
                    weiXinProperties.getGrantBackUrl());
    	}else{
    		result = wxService.getAuthorizeBaseUrl(weiXinProperties.getWxAuthorizeUrl(),
                    weiXinProperties.getAppId(),
                    weiXinProperties.getGrantBackUrl());
    	}
    	if (result.hasError()) {
    		return "redirect:" + WfxConstant.WFX_LOGIN_URL;
        }else{
        	return "redirect:"+result.getData();
        }
    }
    
    /**
     * 微信授权回调地址
     * @param code
     * @return
     */
    @RequestMapping(value = "page-authorization-after-url", method = RequestMethod.GET)
    public String getWxUserInfo(String code) {
    	WXAccessTokenResponse accessToken = wxService.getAccessTokenByCode(weiXinProperties.getAppId(), 
    			weiXinProperties.getAppSecret(), code).getData();
    	WXAccessTokenResponse refreshToken = wxService.getRefreshToken(weiXinProperties.getAppId(), 
    			accessToken.getRefreshToken()).getData();
    	WXUserInfoResponse userInfo = wxService.getUserInfo(accessToken.getOpenId(),
    			refreshToken.getAccessToken()).getData();
    	//将微信信息保存到session中
    	SessionWXUserInfo sUserInfo = new SessionWXUserInfo();
		sUserInfo.setOpenid(userInfo.getOpenid());
		sUserInfo.setNickname(userInfo.getNickname());
		sUserInfo.setSex(userInfo.getSex());
		sUserInfo.setHeadimgurl(userInfo.getHeadimgurl());
		sUserInfo.setCity(userInfo.getCity());
		sUserInfo.setCountry(userInfo.getCountry());
		sUserInfo.setPrivilege(userInfo.getPrivilege());
		sUserInfo.setProvince(userInfo.getProvince());
		sUserInfo.setUnionid(userInfo.getUnionid());
		SessionUtils.setWXUserInfo(sUserInfo);
		
		if(ObjectUtils.isEmpty(SessionUtils.getLoginReferer())){
        	return "redirect:" + WfxConstant.WFX_INDEX_URL;
        }
        return "redirect:" + SessionUtils.getLoginReferer();
    }
    
    /**
     * 判断用户是否关注微信公共号
     * @param code
     * @return
     */
    @RequestMapping(value = "checkUserIsSubscribe", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkUserIsSubscribe(String memberId) {
    	boolean rs = false;
    	if(!StringUtils.isEmpty(memberId)){
    		MemberAccountOutputDto memberAccountOutputDto = memberAccountFrontApi.getMemberAccountById(memberId);
    		if(memberAccountOutputDto!=null && !StringUtils.isEmpty(memberAccountOutputDto.getH5OpenId())){
    			String openId = memberAccountOutputDto.getH5OpenId();
    			String accessToken = wxService.getAccessToken(weiXinProperties.getAppId(), 
    	    			weiXinProperties.getAppSecret());
    			Result<WXUserInfoResponse> result = wxService.getUserInfoInSubscribe(openId, accessToken);
    			if(result.isSuccess() && result.getData().getSubscribe().equals("1")){
    				rs = true;
    			}else{
    				if(result.getData()!=null && !result.getData().getSubscribe().equals("1")){
    					logger.error("用户未关注微信公共号！");
    				}else{
    					logger.error("判断用户是否关注微信公共号失败：" + result.getMessage());
    				}
    				
    			}
    		}
    	}
    	return rs;
    }
    
    /**
     * 微信授权回调地址
     * @param code
     * @return
     */
    @RequestMapping(value = "test1", method = RequestMethod.GET)
    @ResponseBody
    public Result<MessageResponse> test1(String openId, String msg) {
    	TextMessageRequest request = new TextMessageRequest();
    	request.setToUser(openId);
    	request.getText().setContent(msg);
    	Result<MessageResponse> result = wxService.sendTextMessage(null, request);
    	return result;
    }
    
    @RequestMapping(value = "test2", method = RequestMethod.GET)
    @ResponseBody
    public Result<UserSearchResponse> test2(String openId, String msg) {
    	String accessToken = wxService.getAccessToken();
		if(StringUtil.isStrEmpty(accessToken)){
			logger.error("获取调用接口凭证失败！");
			return Result.create(false, "获取调用接口凭证失败！", null);
		}
		Result<UserSearchResponse> result = wxService.searchUser(accessToken, openId);
    	
    	return result;
    }
    
    @RequestMapping(value = "test3", method = RequestMethod.GET)
    @ResponseBody
    public Result<WXUserInfoResponse> test3(String openId) {
    	String accessToken = wxService.getAccessToken();
		if(StringUtil.isStrEmpty(accessToken)){
			logger.error("获取调用接口凭证失败！");
			return Result.create(false, "获取调用接口凭证失败！", null);
		}
		Result<WXUserInfoResponse> result = wxService.getUserInfoInSubscribe(openId, accessToken);
    	
    	return result;
    }
    
    @RequestMapping(value = "test4", method = RequestMethod.GET)
    @ResponseBody
    public int test4() {
    	Result flushAccessToken = wxService.flushAccessToken(weiXinProperties.getBeforeRefreshTime(),
		                true,
		                weiXinProperties.getAppId(),
		                weiXinProperties.getAppSecret());
		if (flushAccessToken.isSuccess()) {
			logger.info("刷新AccessToken成功,信息:" + JacksonUtils.Convert(flushAccessToken));
			Result flushJSTicket = wxService.flushJSTicket(weiXinProperties.getBeforeRefreshTime(), false);
			if (flushJSTicket.hasError()) {
				logger.error("刷新JSTocket失败,错误信息:" + JacksonUtils.Convert(flushJSTicket));
				return 2;
			} else {
				logger.info("刷新JSTocket成功,信息:" + JacksonUtils.Convert(flushJSTicket));
				return 1;
			}
		} else {
			logger.error("刷新AccessToken失败.错误信息:" + JacksonUtils.Convert(flushAccessToken));
			return 0;
		}
    }
    
    @RequestMapping(value = "test5", method = RequestMethod.GET)
    @ResponseBody
    public String test5() {
    	return wxService.getAccessToken();
    }
}
