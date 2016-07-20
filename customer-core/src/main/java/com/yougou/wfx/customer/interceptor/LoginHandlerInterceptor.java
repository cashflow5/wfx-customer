package com.yougou.wfx.customer.interceptor;

import com.google.common.base.Objects;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.constant.SessionConstant;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.enums.OperatingEnvironmentEnum;
import com.yougou.wfx.customer.common.session.SessionUtils;
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
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(LoginHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (! (handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginValidate loginValidate = handlerMethod.getMethodAnnotation(LoginValidate.class);
        if (loginValidate == null || ! loginValidate.isValidate()) {
            return true;
        }
        HttpSession httpSession = request.getSession();
        Object account = httpSession.getAttribute(WfxConstant.WFX_LOGIN_CUSTOMER);
        if (ObjectUtils.isEmpty(account)) {
        	Object running_environment = httpSession.getAttribute(SessionConstant.RUNNING_ENVIRONMENT);
        	String loginURL = WfxConstant.WFX_LOGIN_URL;
        	if(running_environment!=null && 
        			running_environment.toString().equals(OperatingEnvironmentEnum.WX_PLATFORM.getKey())){
        		loginURL = WfxConstant.WFX_WX_GRANT_URL;
        	}
        	String redirectURL = getRedirectURL(request);
            if (StringUtils.hasText(redirectURL)) {
                loginURL += "?redirectURL=" + URLEncoder.encode(redirectURL,"utf-8");
            }
            if (isJsonPostRequest(request)) {
                response.setContentType("application/json; charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                try (PrintWriter writer = response.getWriter()) {
                    writer.append(JacksonUtils.Convert(Result.create(false, 400, "登陆失效或尚未登陆,请重新登陆.")));
                } catch (IOException e) {
                    logger.error("处理未登陆json请求失败,无法返回json给客户端", e);
                }
            } else {
                response.sendRedirect(loginURL);
            }
            return false;
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
    
    /**
     * 检测是不是请求json数据的post请求
     *
     * @since 1.0 Created by lipangeng on 16/4/26 下午3:42. Email:lipg@outlook.com.
     */
    private boolean isJsonPostRequest(HttpServletRequest request) {
        String method = request.getMethod();
        String contentType = request.getHeader("Accept");
        contentType = contentType == null ? null : contentType.toLowerCase();
        if (HttpMethod.resolve(method) == HttpMethod.POST &&
            (Objects.equal(contentType, "application/json") || Objects.equal(contentType, "application/javascript"))) {
            return true;
        } else {
            return false;
        }
    }

}
