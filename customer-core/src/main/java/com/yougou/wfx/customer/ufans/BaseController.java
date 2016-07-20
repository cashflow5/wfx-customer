package com.yougou.wfx.customer.ufans;
//package com.yougou.wfx.customer.shop;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import com.alibaba.fastjson.JSON;
//import com.yougou.wfx.appserver.constant.WfxConstant;
//import com.yougou.wfx.appserver.serivce.AppVersionService;
//import com.yougou.wfx.appserver.serivce.DiscoverService;
//import com.yougou.wfx.appserver.serivce.IFileUploadService;
//import com.yougou.wfx.appserver.serivce.IFinanceService;
//import com.yougou.wfx.appserver.serivce.IMemberAccountService;
//import com.yougou.wfx.appserver.serivce.IOrderService;
//import com.yougou.wfx.appserver.serivce.IProductService;
//import com.yougou.wfx.appserver.serivce.ISellerService;
//import com.yougou.wfx.appserver.serivce.IBShopService;
//import com.yougou.wfx.appserver.serivce.ISupplyService;
//import com.yougou.wfx.appserver.vo.IUserContext;
//import com.yougou.wfx.appserver.vo.login.UserInfo;
//
///**
// * Created by lizhw on 2016/4/8.
// */
//
//@EnableAutoConfiguration
//public class BaseController {
//
//	/**
//	 * 用户账户接口
//	 */
//	@Autowired
//	protected IMemberAccountService memberAccountService;
//
//	/**
//	 * 财务接口
//	 */
//	@Autowired
//	protected IFinanceService financeService;
//
//	/**
//	 * 店铺接口
//	 */
//	@Autowired
//	protected IBShopService shopService;
//
//	/**
//	 * 分销商信息接口
//	 */
//	@Autowired
//	protected IBSellerService sellerService;
//
//	/**
//	 * 订单接口
//	 */
//	@Autowired
//	protected IOrderService orderService;
//
//	/**
//	 * 商品接口
//	 */
//	@Autowired
//	protected IProductService productService;
//
//	/**
//	 * 货源市场接口
//	 */
//	@Autowired
//	protected ISupplyService supplyService;
//
//	/**
//	 * 上传图片接口
//	 */
//	@Autowired
//	protected IFileUploadService fileUploadService;
//
//	/**
//	 * App版本接口
//	 */
//	@Autowired
//	protected AppVersionService appVersionService;
//
//	/**
//	 * 发现接口
//	 */
//	@Autowired
//	protected DiscoverService discoverService;
//
//	public HttpServletRequest getRequest() {
//		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//		return attrs.getRequest();
//	}
//
//	public HttpSession getSession() {
//		HttpSession session = getRequest().getSession();
//		return session;
//	}
//
//	public String getSessionStr(String key) {
//		Object attribute = getSession().getAttribute(key);
//		if (null == attribute) {
//			return "";
//		}
//		String s = (String) attribute;
//		return s;
//	}
//
//	public String getSessionId() {
//		String sid = getSession().getId();
//		return sid;
//	}
//
//	public String getIp() {
//		return getIpAddr(getRequest());
//	}
//
//	public String getReferer() {
//		return getRequest().getHeader("referer");
//	}
//
//	public String getUserAgent() {
//		return getRequest().getHeader("user-agent");
//	}
//
//	public void setUserInfo(UserInfo userInfo) {
//		HttpSession session = getSession();
//		String json = null == userInfo ? "" : JSON.toJSONString(userInfo);
//		session.setAttribute(WfxConstant.SESSION_USER_KEY, json);
//	}
//
//	public UserInfo getUserInfo() {
//		HttpSession session = getSession();
//		Object o = session.getAttribute(WfxConstant.SESSION_USER_KEY);
//		if (null == o) {
//			return null;
//		}
//		String json = (String) o;
//		if (StringUtils.isBlank(json)) {
//			return null;
//		}
//		UserInfo userInfo = JSON.parseObject(json, UserInfo.class);
//		return userInfo;
//
//	}
//
//	protected void setUserContext(IUserContext userContext) {
//		String ip = getIp();
//		String referer = getReferer();
//		String userAgent = getUserAgent();
//		String sid = getSessionId();
//		userContext.setIp(ip);
//		userContext.setReferer(referer);
//		userContext.setUserAgent(userAgent);
//		userContext.setSid(sid);
//	}
//
//	public static String getIpAddr(HttpServletRequest request) {
//		String ip = request.getHeader("x-forwarded-for");
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getRemoteAddr();
//		}
//		if (StringUtils.isNotEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
//			String[] strIps = StringUtils.stripToEmpty(ip).split(",");
//			if (strIps != null && strIps.length > 1) {
//				return StringUtils.stripToEmpty(strIps[1]);
//			}
//		}
//		return ip;
//	}
//}
