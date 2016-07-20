package com.yougou.wfx.customer.interceptor;

import com.google.common.base.Objects;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.constant.SessionConstant;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.enums.OperatingEnvironmentEnum;
import com.yougou.wfx.customer.common.utils.JacksonUtils;
import com.yougou.wfx.customer.model.common.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * Created by zhang.sj on 2016/3/22.
 */
public class EnvironmentHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession httpSession = request.getSession();
        
       
        Object context = httpSession.getAttribute(SessionConstant.WEB_ROOT);
        if(StringUtils.isEmpty(context)){
        	String webRoot = getWebRootUrl(request);
        	httpSession.setAttribute(SessionConstant.WEB_ROOT, webRoot);
        }
        
        //设置运行环境
        Object runEnvironment = httpSession.getAttribute(SessionConstant.RUNNING_ENVIRONMENT);
        if(StringUtils.isEmpty(runEnvironment)){
        	String ua = checkOperatingEnvironment(request);
        	httpSession.setAttribute(SessionConstant.RUNNING_ENVIRONMENT, ua);
        }
        runEnvironment = httpSession.getAttribute(SessionConstant.RUNNING_ENVIRONMENT);
        if(runEnvironment.toString().equals(OperatingEnvironmentEnum.IOS_APP_PLATFORM.getKey())){
        	Object version = httpSession.getAttribute(SessionConstant.IOS_APP_VERSION);
        	Object o1 = httpSession.getAttribute("isOldVersion");
        	if(ObjectUtils.isEmpty(o1) && !ObjectUtils.isEmpty(version)){
        		if(version.toString().compareTo("1.0.2") < 0){
    				httpSession.setAttribute("isOldVersion", "1");
    			}else{
    				httpSession.setAttribute("isOldVersion", "0");
    			}
        	}else if(ObjectUtils.isEmpty(version)){
        		String userAgent = request.getHeader("User-Agent");
        		logger.error("IOS=========：" + userAgent);
        		if(userAgent.indexOf("WFXIOS") + 7 < userAgent.length()){
        			String s = userAgent.substring(userAgent.indexOf("WFXIOS") + 7);
        			if(s.compareTo("1.0.2") < 0){
        				httpSession.setAttribute("isOldVersion", "1");
        			}else{
        				httpSession.setAttribute("isOldVersion", "0");
        			}
        			httpSession.setAttribute(SessionConstant.IOS_APP_VERSION, s);
        		}
        		logger.error("IOS=========版本号："+httpSession.getAttribute(SessionConstant.IOS_APP_VERSION));
        		logger.error("IOS=========是否是旧版本："+httpSession.getAttribute("isOldVersion"));
        	}
        }
        
        if(runEnvironment.toString().equals(OperatingEnvironmentEnum.ANDROID_APP_PLATFORM.getKey())){
        	Object version = httpSession.getAttribute(SessionConstant.ANDROID_APP_VERSION);
        	Object o1 = httpSession.getAttribute("isOldVersion");
        	if(ObjectUtils.isEmpty(o1) && !ObjectUtils.isEmpty(version)){
        		if(version.toString().compareTo("1.2.0") < 0){
    				httpSession.setAttribute("isOldVersion", "1");
    			}else{
    				httpSession.setAttribute("isOldVersion", "0");
    			}
        	}else if(ObjectUtils.isEmpty(version)){
        		String userAgent = request.getHeader("User-Agent");
        		logger.error("ANDROID=========：" + userAgent);
        		if(userAgent.indexOf("WFXANDROID") + 11 < userAgent.length()){
        			String s = userAgent.substring(userAgent.indexOf("WFXANDROID") + 11);
        			if(s.compareTo("1.2.0") < 0){
        				httpSession.setAttribute("isOldVersion", "1");
        			}else{
        				httpSession.setAttribute("isOldVersion", "0");
        			}
        			httpSession.setAttribute(SessionConstant.ANDROID_APP_VERSION, s);
        		}else{
        			httpSession.setAttribute("isOldVersion", "1");
        			httpSession.setAttribute(SessionConstant.ANDROID_APP_VERSION, "1.1.0");
        		}
        		logger.error("ANDROID=========版本号："+httpSession.getAttribute(SessionConstant.ANDROID_APP_VERSION));
        		logger.error("ANDROID=========是否是旧版本："+httpSession.getAttribute("isOldVersion"));
        	}
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
    /**
     * 获取服务根路径
     *
     * zheng.qq
     */
    private String getWebRootUrl(HttpServletRequest request) {
    	StringBuffer url = request.getRequestURL();  
    	String webRootUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();  
        return webRootUrl;
    }
    /**
     * 检测运行环境
     *
     * zheng.qq
     */
    public String checkOperatingEnvironment(HttpServletRequest request) {
    	String rs = OperatingEnvironmentEnum.BROWSER_PLATFORM.getKey();
    	String ua = request.getHeader("User-Agent");
    	if(!StringUtils.isEmpty(ua)){
    		if (ua.contains("WFXIOS")) {
        		rs = OperatingEnvironmentEnum.IOS_APP_PLATFORM.getKey();
        	}else if(ua.contains("WFXANDROID")){
        		rs = OperatingEnvironmentEnum.ANDROID_APP_PLATFORM.getKey();
        	}else if(ua.indexOf("MicroMessenger") > 0){
        		rs = OperatingEnvironmentEnum.WX_PLATFORM.getKey();
        	}else{
        		rs = OperatingEnvironmentEnum.BROWSER_PLATFORM.getKey();
        	}
    	}
    	return rs;
    }
    
}
