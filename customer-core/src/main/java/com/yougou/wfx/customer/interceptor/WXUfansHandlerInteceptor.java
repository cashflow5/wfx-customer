package com.yougou.wfx.customer.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yougou.wfx.customer.common.configurations.WFXProperties;
import com.yougou.wfx.customer.common.constant.SessionConstant;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.configurations.weixin.WeiXinProperties;
import com.yougou.wfx.customer.service.system.ISystemService;
import com.yougou.wfx.customer.service.weixin.IWXService;

/**
 * 微信锁粉拦截器
 * 
 * @author li.lq
 *
 */
public class WXUfansHandlerInteceptor implements HandlerInterceptor {
	private WeiXinProperties weiXinProperties;
	private WFXProperties wfxProperties;
	private IWXService wXService;
	private ISystemService systemService;

	public WXUfansHandlerInteceptor(WeiXinProperties weiXinProperties, IWXService wXService, WFXProperties wfxProperties, ISystemService systemService) {
		this.weiXinProperties = weiXinProperties;
		this.wXService = wXService;
		this.wfxProperties = wfxProperties;
		this.systemService = systemService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		HttpSession httpSession = request.getSession();

        Object config = httpSession.getAttribute(SessionConstant.UFAN_ARTICLE_URL);
        if(ObjectUtils.isEmpty(config)){
        	String ufan_article_url = systemService.getConfigByKey(WfxConstant.UFANS_ARTICLE_URL_KEY);
        	if(!StringUtils.isEmpty(ufan_article_url)){
        		httpSession.setAttribute(SessionConstant.UFAN_ARTICLE_URL, ufan_article_url);
        	}else{
        		httpSession.setAttribute(SessionConstant.UFAN_ARTICLE_URL, "http://m.yougou.net/discover/view/8bfa535b2a1a4679a7d5da5002142523.sc");
        	}
        	
        }
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			//System.out.println(request.getServletPath()+"------");
			modelAndView.addObject("wxConfig", wXService.getWXConfig(weiXinProperties.getAppId()));
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub

	}

}
