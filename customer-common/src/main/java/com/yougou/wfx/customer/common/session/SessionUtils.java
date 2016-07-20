package com.yougou.wfx.customer.common.session;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.yougou.wfx.customer.common.configurations.WFXProperties;
import com.yougou.wfx.customer.common.constant.SessionConstant;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.crypto.CryptoUtils;
import com.yougou.wfx.customer.model.usercenter.UserLoginVo;
import com.yougou.wfx.customer.model.weixin.base.WXUserInfoResponse;
import com.yougou.wfx.dto.base.UserContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Session工具
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/24 上午9:52
 * @since 1.0 Created by lipangeng on 16/3/24 上午9:52. Email:lipg@outlook.com.
 */
public class SessionUtils {
    private static final Logger logger = LoggerFactory.getLogger(SessionUtils.class);

    private static Cipher DECRYPT_COOKIE_VALUE = CryptoUtils.AES(SessionConstant.COOKIE_REMEMBER_ME_AES_KEY, Cipher.DECRYPT_MODE);
    private static Cipher ENCRYPT_COOKIE_VALUE = CryptoUtils.AES(SessionConstant.COOKIE_REMEMBER_ME_AES_KEY, Cipher.ENCRYPT_MODE);

    private static WFXProperties wfxProperties;

    public static void setWfxProperties(WFXProperties wfxProperties) {
        SessionUtils.wfxProperties = wfxProperties;
    }

    /**
     * 更新登录来源,以供登陆后返回原页面. 如果request的header中没有referer,则判断是否存在redirectURL参数,redirectURL参数只处理/开头的链接.
     *
     * @since 1.0 Created by lipangeng on 16/3/24 上午10:13. Email:lipg@outlook.com.
     */
    public static void setLoginReferer() {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        String referer = request.getParameter("redirectURL");
        if (Strings.isNullOrEmpty(referer)) {
            referer = request.getHeader(SessionConstant.HEADER_REFERER);
            if (! Strings.isNullOrEmpty(referer)) {
                try {
                    URL url = new URL(referer);
                    referer = url.getPath();
                } catch (MalformedURLException e) {
                    logger.warn("登录页面来源无效", e);
                }
            }
        }
        if (referer == null || ! referer.startsWith("/") || referer.startsWith(WfxConstant.WFX_LOGIN_URL) ||
            referer.startsWith(WfxConstant.WFX_LOGOUT_URL) || referer.startsWith(WfxConstant.WFX_USERCENTER_MODITY_PASSWORD_SUCCESS_URL) ||
            referer.startsWith(WfxConstant.WFX_RESET_PASSWORD_SECCESS_URL) ||
            referer.startsWith(WfxConstant.WFX_USERCENTER_MODITY_PASSWORD_SET_PASSWORD_URL) ||
            referer.startsWith(WfxConstant.WFX_RESET_PASSWORD_PASSWORD_URL)) {
        } else {
            session.setAttribute(SessionConstant.LOGIN_REFERER, referer);
        }
    }

    /**
     * 获取用户在session中存储的来源
     *
     * @since 1.0 Created by lipangeng on 16/3/24 上午11:54. Email:lipg@outlook.com.
     */
    public static String getLoginReferer() {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object referer = session.getAttribute(SessionConstant.LOGIN_REFERER);
            if (referer != null) {
                return referer.toString();
            }
        }
        return WfxConstant.WFX_INDEX_URL;
    }

    public static void removeLoginReferer() {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(SessionConstant.LOGIN_REFERER);
        }
    }

    /**
     * 用户退出
     *
     * @since 1.0 Created by lipangeng on 16/3/25 上午12:35. Email:lipg@outlook.com.
     */
    public static void logout() {
        HttpServletRequest request = getRequest();
        request.getSession().removeAttribute(WfxConstant.WFX_LOGIN_CUSTOMER);
        request.getSession().removeAttribute(SessionConstant.LOGIN_REFERER);
        request.getSession().removeAttribute(SessionConstant.WX_USER_INFO);
        request.getSession().removeAttribute(SessionConstant.IMAGE_CODE);
        request.getSession().removeAttribute(SessionConstant.CURRENT_SHOP_ID);
        request.getSession().removeAttribute(SessionConstant.CURRENT_MENU_ID);
        request.getSession().removeAttribute(SessionConstant.BUYING_SHOP_ID);
        removeRememberMe();
    }

    /**
     * 获取session中用户的登陆信息
     *
     * @return 如果用户已经登录, 则返回用户信息, 如果用户尚未登录, 则返回null
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午2:46. Email:lipg@outlook.com.
     */
    public static SessionUserDetails getLoginUserDetails() {
        HttpServletRequest request = getRequest();
        if(request==null){
        	return null;
        }
        HttpSession session = request.getSession();
        Object userDetails = session.getAttribute(WfxConstant.WFX_LOGIN_CUSTOMER);
        if (userDetails != null && userDetails instanceof SessionUserDetails) {
            return (SessionUserDetails) userDetails;
        }
        return null;
    }

    /**
     * 在session中注入用户信息
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午2:30. Email:lipg@outlook.com.
     */
    public static void setLoginUserDetails(SessionUserDetails sessionUserDetails) {
        HttpServletRequest request = getRequest();
        if(request==null){
        	return;
        }
        HttpSession session = request.getSession();
        session.setAttribute(WfxConstant.WFX_LOGIN_CUSTOMER, sessionUserDetails);
    }

    /**
     * 判断用户是否已经登录
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午4:35. Email:lipg@outlook.com.
     */
    public static boolean isLogined() {
        return getLoginUserDetails() != null;
    }

    /**
     * 注入记住我的cookie
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午5:25. Email:lipg@outlook.com.
     */
    public static void setRememberMe(SessionUserDetails userDetails) {
        HttpServletResponse response = getResponse();
        try {
            String value = userDetails.getUserId() + SessionConstant.COOKIE_REMEMBER_ME_SALT +
                           userDetails.getUserName() + SessionConstant.COOKIE_REMEMBER_ME_SALT + userDetails.getPasswordEncode();
            byte[] result = ENCRYPT_COOKIE_VALUE.doFinal(value.getBytes(Charset.forName("utf-8")));
            Cookie cookie = new Cookie(SessionConstant.COOKIE_REMEMBER_ME, CryptoUtils.bytes2Hex(result));
            cookie.setMaxAge(60 * 60 * 24 * 30);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            logger.error("无法加密cookie内容", e);
        }
    }

    /**
     * 注入记住我的cookie
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午5:25. Email:lipg@outlook.com.
     */
    public static UserLoginVo getRememberMeValue() {
        HttpServletRequest request = getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (SessionConstant.COOKIE_REMEMBER_ME.equals(cookie.getName())) {
                byte[] result = CryptoUtils.hex2Bytes(cookie.getValue());
                try {
                    if (result == null) {
                        return null;
                    }
                    String resultS = new String(DECRYPT_COOKIE_VALUE.doFinal(result), Charset.forName("utf-8"));
                    List<String> strings = Splitter.on(SessionConstant.COOKIE_REMEMBER_ME_SALT).splitToList(resultS);
                    if (strings.size() == 3) {
                        UserLoginVo loginVo = new UserLoginVo();
                        loginVo.setUserId(strings.get(0));
                        loginVo.setLoginName(strings.get(1));
                        loginVo.setLoginPasswordEncode(strings.get(2));
                        return loginVo;
                    }
                } catch (IllegalBlockSizeException | BadPaddingException e) {
                    logger.error("无法解密cookie内容", e);
                }
            }
        }
        return null;
    }

    /**
     * 删除记住我的cookie
     *
     * @since 1.0 Created by lipangeng on 16/3/25 上午10:27. Email:lipg@outlook.com.
     */
    public static void removeRememberMe() {
        HttpServletResponse response = getResponse();
        Cookie cookie = new Cookie(SessionConstant.COOKIE_REMEMBER_ME, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 获取session中存储的图片验证码
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午11:34. Email:lipg@outlook.com.
     */
    public static String getImageCode() {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(SessionConstant.IMAGE_CODE);
        if (attribute != null && attribute instanceof String) {
            return attribute.toString();
        }
        return null;
    }

    /**
     * 将图片验证码写入session
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午11:33. Email:lipg@outlook.com.
     */
    public static void setImageCode(String imageCode) {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(SessionConstant.IMAGE_CODE, imageCode);
    }

    /**
     * 获取session中存储的图片验证码
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午11:34. Email:lipg@outlook.com.
     */
    public static void removeImageCode() {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        session.removeAttribute(SessionConstant.IMAGE_CODE);
    }

    /**
     * 获取请求相关信息
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午3:45. Email:lipg@outlook.com.
     */
    public static UserContext getUserContext() {
        HttpServletRequest request = getRequest();
        UserContext userContext = new UserContext();
        userContext.setClientIp(getRemoteIP());
        userContext.setReferer(request.getHeader(SessionConstant.HEADER_REFERER));
        userContext.setRequestMethod(request.getMethod());
        userContext.setSessionId(request.getSession().getId());
        userContext.setUserAgent(request.getHeader(SessionConstant.HEADER_USER_AGENT));
        return userContext;
    }

    /**
     * 获取用户的ip地址,并且在代理的情况下获取真正的ip
     *
     * @since 1.0 Created by lipangeng on 16/3/28 下午3:48. Email:lipg@outlook.com.
     */
    public static String getRemoteIP() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        // 如果x-forwarded-for头中带有多个ip,则注入第一个ip地址,其他ip为负载均衡器ip。
        if (! Strings.isNullOrEmpty(ip) && ! "unknown".equalsIgnoreCase(ip)) {
            List<String> ips = Splitter.on(",").splitToList(ip);
            if (! CollectionUtils.isEmpty(ips)) {
                ip = ips.get(0).trim();
            }
        }
        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (Strings.isNullOrEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取客户端请求的网址
     *
     * @since 1.0 Created by lipangeng on 16/4/20 下午1:52. Email:lipg@outlook.com.
     */
    public static String getRequrestServer() {
        HttpServletRequest request = getRequest();
        StringBuffer url = request.getRequestURL();
        return request.getRequestURL().delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
    }

    /**
     * 获取请求中的{@link HttpServletRequest}
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午2:56. Email:lipg@outlook.com.
     */
    public static HttpServletRequest getRequest() {
    	HttpServletRequest request = null;
    	ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	if(servletRequestAttributes!=null){
    		request = servletRequestAttributes.getRequest();
    	}
        return request;
    }

    /**
     * 获取{@link HttpServletResponse}
     *
     * @since 1.0 Created by lipangeng on 16/3/24 下午4:27. Email:lipg@outlook.com.
     */
    public static HttpServletResponse getResponse() {
    	HttpServletResponse response = null;
    	ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	if(servletRequestAttributes!=null){
    		response = servletRequestAttributes.getResponse();
    	}
        return response;
    }

    /**
     * 获取当前店铺id
     *
     * @since 1.0 Created by lipangeng on 16/5/6 下午5:43. Email:lipg@outlook.com.
     */
    public static String getCurrentShopId() {
        HttpSession session = getRequest().getSession();
        if (session != null) {
            Object currentShopId = session.getAttribute(SessionConstant.CURRENT_SHOP_ID);
            if (currentShopId != null && currentShopId instanceof String) {
                return (String) currentShopId;
            }
        }
        return null;
    }

    /**
     * 更新当前店铺id
     *
     * @since 1.0 Created by lipangeng on 16/5/6 下午5:43. Email:lipg@outlook.com.
     */
    public static void setCurrentShopId(String shopId) {
        HttpSession session = getRequest().getSession();
        if (session != null && ! Strings.isNullOrEmpty(shopId)) {
            session.setAttribute(SessionConstant.CURRENT_SHOP_ID, shopId);
        }
    }

    /**
     * 获取当前店铺id,如果不存在则返回默认总店铺id
     *
     * @since 1.0 Created by lipangeng on 16/5/6 下午5:43. Email:lipg@outlook.com.
     */
    public static String getCurrentShopIdOrDefault() {
        String currentShopId = getCurrentShopId();
        if (Strings.isNullOrEmpty(currentShopId)) {
            currentShopId = wfxProperties.getIndexShopId();
        }
        return currentShopId;
    }

    /**
     * 获取想要购买的商品的shopId的地址
     *
     * @since 1.0 Created by lipangeng on 16/5/17 下午3:43. Email:lipg@outlook.com.
     */
    public static String getBuyingShopId() {
        HttpSession session = getRequest().getSession();
        if (session != null) {
            Object attribute = session.getAttribute(SessionConstant.BUYING_SHOP_ID);
            if (attribute instanceof String) {
                return (String) attribute;
            }
        }
        return null;
    }

    /**
     * 获取想要购买的商品的shopId的地址
     *
     * @since 1.0 Created by lipangeng on 16/5/17 下午3:43. Email:lipg@outlook.com.
     */
    public static void setBuyingShopId(String buyingShopId) {
        HttpSession session = getRequest().getSession();
        if (session != null && ! Strings.isNullOrEmpty(buyingShopId)) {
            session.setAttribute(SessionConstant.BUYING_SHOP_ID, buyingShopId);
        }
    }
    
    /**
     * 获取微信授权信息
     *
     * zheng.qq
     */
    public static SessionWXUserInfo getWXUserInfo() {
        HttpSession session = getRequest().getSession();
        if (session != null) {
            Object attribute = session.getAttribute(SessionConstant.WX_USER_INFO);
            if (attribute instanceof SessionWXUserInfo) {
                return (SessionWXUserInfo) attribute;
            }
        }
        return null;
    }
    
    /**
     * 更新微信授权信息
     *
     * zheng.qq
     */
    public static void setWXUserInfo(SessionWXUserInfo userInfo) {
        HttpSession session = getRequest().getSession();
        if (session != null && ! ObjectUtils.isEmpty(userInfo)) {
            session.setAttribute(SessionConstant.WX_USER_INFO, userInfo);
        }
    }
    
    /**
     * 获取当前菜单
     */
    public static String getCurrentMenuID() {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        Object attribute = session.getAttribute(SessionConstant.CURRENT_MENU_ID);
        if (attribute != null && attribute instanceof String) {
            return attribute.toString();
        }
        return null;
    }

    /**
     * 设置当前菜单
     *
     */
    public static void setCurrentMenuID(String menuId) {
        HttpServletRequest request = getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(SessionConstant.CURRENT_MENU_ID, menuId);
    }
}
