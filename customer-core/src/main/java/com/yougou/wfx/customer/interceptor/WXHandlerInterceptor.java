package com.yougou.wfx.customer.interceptor;

import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.HandlerMethodSelector;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.annotations.WXLoginValidate;
import com.yougou.wfx.customer.common.constant.SessionConstant;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.enums.OperatingEnvironmentEnum;
import com.yougou.wfx.customer.common.session.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Created by zhang.sj on 2016/3/22.
 */
public class WXHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WXHandlerInterceptor.class);
    private List<HandlerMethodArgumentResolver> argumentResolvers;
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    
    private final Map<MethodParameter, HandlerMethodArgumentResolver> argumentResolverCache =
			new ConcurrentHashMap<MethodParameter, HandlerMethodArgumentResolver>(256);
    
    private final Map<Class<?>, Set<Method>> initBinderCache = new ConcurrentHashMap<Class<?>, Set<Method>>(64);

    @Autowired
	public WXHandlerInterceptor(RequestMappingHandlerAdapter adapter){
		argumentResolvers = adapter.getArgumentResolvers();
		requestMappingHandlerAdapter = adapter;
	}
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	if (! (handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        WXLoginValidate validate = handlerMethod.getMethodAnnotation(WXLoginValidate.class);
        if (validate == null || ! validate.isValidate()) {
            return true;
        }
    	HttpSession httpSession = request.getSession();
        //设置运行环境
        Object runEnvironment = httpSession.getAttribute(SessionConstant.RUNNING_ENVIRONMENT);
        if(runEnvironment.toString().equals(OperatingEnvironmentEnum.WX_PLATFORM.getKey())){
        	Object wxUserInfo = httpSession.getAttribute(SessionConstant.WX_USER_INFO);
        	if (ObjectUtils.isEmpty(wxUserInfo)) {
        		MethodParameter[] parameters = handlerMethod.getMethodParameters();
        		if(parameters.length>0){
        			ServletWebRequest webRequest = new ServletWebRequest(request, response);
                	MethodParameter parameter = parameters[0];//shopId必须为方法的第一个参数 ， 要是参数必须设置name属性。
            		HandlerMethodArgumentResolver resolver = getArgumentResolver(parameter);
            	
            		ModelAndViewContainer mavContainer = new ModelAndViewContainer();
            		mavContainer.addAllAttributes(RequestContextUtils.getInputFlashMap(request));
            		WebDataBinderFactory webDataBinderFactory = getDataBinderFactory(handlerMethod);
            		Object value = resolver.resolveArgument(parameter, mavContainer, webRequest, webDataBinderFactory);
            		if(value!=null){
            			//设置当前访问的店铺ID，目的是为了锁粉用的。
                		SessionUtils.setCurrentShopId(value.toString());
                		String url = WfxConstant.WFX_WX_GRANT_URL;
                		String redirectURL = getRedirectURL(request);
                        if (StringUtils.hasText(redirectURL)) {
                        	url += "?redirectURL=" + URLEncoder.encode(redirectURL,"utf-8");
                        }
                		response.sendRedirect(url);
                		return false;
            		}
        		}
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

    private String getRedirectURL(HttpServletRequest request) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(request.getContextPath());
        urlBuilder.append(request.getServletPath());
        if (StringUtils.hasText(request.getQueryString())) {
            urlBuilder.append("?").append(request.getQueryString().trim());
        }
        return urlBuilder.toString();
    }
    
    private WebDataBinderFactory getDataBinderFactory(HandlerMethod handlerMethod) throws Exception {
		Class<?> handlerType = handlerMethod.getBeanType();
		Set<Method> methods = this.initBinderCache.get(handlerType);
		if (methods == null) {
			methods = HandlerMethodSelector.selectMethods(handlerType, RequestMappingHandlerAdapter.INIT_BINDER_METHODS);
			this.initBinderCache.put(handlerType, methods);
		}
		List<InvocableHandlerMethod> initBinderMethods = new ArrayList<InvocableHandlerMethod>();
		for (Method method : methods) {
			Object bean = handlerMethod.getBean();
			initBinderMethods.add(new InvocableHandlerMethod(bean, method));
		}
		return new ServletRequestDataBinderFactory(initBinderMethods, requestMappingHandlerAdapter.getWebBindingInitializer());
	}

    
    private HandlerMethodArgumentResolver getArgumentResolver(
			MethodParameter parameter) {
		HandlerMethodArgumentResolver result = this.argumentResolverCache.get(parameter);
		if (result == null) {
			for (HandlerMethodArgumentResolver methodArgumentResolver : this.argumentResolvers) {
				if (logger.isTraceEnabled()) {
					logger.trace("Testing if argument resolver [" + methodArgumentResolver + "] supports [" +
							parameter.getGenericParameterType() + "]");
				}
				if (methodArgumentResolver.supportsParameter(parameter)) {
					result = methodArgumentResolver;
					this.argumentResolverCache.put(parameter, result);
					break;
				}
			}
		}
		return result;
	}
}
