package com.yougou.wfx.customer.common.cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/25
 */
public class CookieUtils {

    private static final Logger logger = LoggerFactory.getLogger(CookieUtils.class);

    /**
     * 移除cookie
     *
     * @param name
     * @return
     */
    public static String getCookie(String name) {
        if (!StringUtils.hasText(name))
            return null;
        Cookie[] cookies = getCookies();
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    /**
     * 移除cookie
     *
     * @param name
     */
    public static void removeCookie(String name) {
        if (!StringUtils.hasText(name)) {
            return;
        }
        HttpServletResponse response = getResponse();
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 设置cookie
     *
     * @param name
     * @param value
     */
    public static void setCookie(String name, String value, int maxAge) {
        if (!StringUtils.hasText(name)) {
            return;
        }
        HttpServletResponse response = getResponse();
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 获取所有的cookie
     *
     * @return
     */
    public static Cookie[] getCookies() {
        HttpServletRequest request = getRequest();
        if (request == null)
            return null;
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0)
            return null;
        return cookies;
    }


    /**
     * 获取请求中的{@link HttpServletRequest}
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午2:56. Email:lipg@outlook.com.
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取{@link HttpServletResponse}
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午4:27. Email:lipg@outlook.com.
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
}
