package com.yougou.wfx.customer.service.bapp.impl;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.yougou.wfx.aftersale.api.front.IAfterSaleFrontApi;
import com.yougou.wfx.appserver.vo.BooleanResult;
import com.yougou.wfx.appserver.vo.IUserContext;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.cms.api.front.ICommoditySaleCatFrontApi;
import com.yougou.wfx.cms.api.front.IGoodsMarketFrontApi;
import com.yougou.wfx.commodity.api.front.ICommodityStyleFrontApi;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.dto.base.UserContext;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.finance.api.front.ICommissionDetailFrontApi;
import com.yougou.wfx.finance.api.front.IFinSellerAccountWithdrawFrontApi;
import com.yougou.wfx.finance.api.front.IFinSellerInfoDetailFrontApi;
import com.yougou.wfx.finance.api.front.IFinSellerInfoFrontApi;
import com.yougou.wfx.member.api.front.IMemberAccountFrontApi;
import com.yougou.wfx.member.dto.output.MemberAccountOutputDto;
import com.yougou.wfx.order.api.front.IOrderFrontApi;
import com.yougou.wfx.seller.api.front.ISellerInfoFrontApi;
import com.yougou.wfx.seller.dto.output.SellerInfoOutputDto;
import com.yougou.wfx.shop.api.front.IShopFrontApi;
import com.yougou.wfx.system.api.IFileUploadApi;
import com.yougou.wfx.system.api.IWFXSystemApi;

/**
 * Created by lizhw on 2016/4/11.
 */
public class BaseServiceImpl {

	/**
	 * 会员帐号接口
	 */
	@Autowired
	protected IMemberAccountFrontApi memberAccountFrontApi;

	/**
	 * 店铺信息接口
	 */
	@Autowired
	protected IShopFrontApi shopFrontApi;

	/**
	 * 分销商信息接口
	 */
	@Autowired
	protected ISellerInfoFrontApi sellerInfoFrontApi;

	/**
	 * 订单信息接口
	 */
	@Autowired
	protected IOrderFrontApi orderFrontApi;

	/**
	 * 商品信息接口
	 */
	@Autowired
	protected ICommodityStyleFrontApi commodityStyleFrontApi;

	/**
	 * 财务信息接口
	 */
	@Autowired
	protected IFinSellerInfoFrontApi finSellerInfoFrontApi;

	/**
	 * 财务信息 分销商详情
	 */
	@Autowired
	protected IFinSellerInfoDetailFrontApi finSellerInfoDetailFrontApi;

	/**
	 * 佣金接口
	 */
	@Autowired
	protected ICommissionDetailFrontApi commissionDetailFrontApi;

	/**
	 * 货源市场接口
	 */
	@Autowired
	protected IGoodsMarketFrontApi goodsMarketFrontApi;

	/**
	 * 文件上传接口
	 */
	@Autowired
	protected IFileUploadApi fileUploadApi;

	/**
	 * 申请提现接口
	 */
	@Autowired
	protected IFinSellerAccountWithdrawFrontApi finSellerAccountWithdrawFrontApi;

	/**
	 * 销售分类接口
	 */
	@Autowired
	protected ICommoditySaleCatFrontApi commoditySaleCatFrontApi;
	
	/**
	 * 销售分类接口
	 */
	@Autowired
	protected IAfterSaleFrontApi afterSaleFrontApi;

	@Autowired
	protected IWFXSystemApi systemApi;

	private static String IMG_BASE_URL = "";
	public static Object locker = new Object();

	protected String getImgBaseUrl() {
		if (isBlank(IMG_BASE_URL)) {
			IMG_BASE_URL = systemApi.obtainImgBaseUrl();
		}
		return IMG_BASE_URL;
	}

	protected static final String PRODUCT_IMG_BASE_URL = "http://i1.ygimg.cn/pics";

	protected boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}

	protected UserInfo convertMember2UserInfo(MemberAccountOutputDto member) {
		UserInfo userInfo = new UserInfo();
		if (null == member) {
			return null;
		}
		userInfo.setId(member.getId());
		if (null != member.getSellerInfo()) {
			userInfo.setSellerId(member.getSellerInfo().getId());
		}
		if (null != member.getShopInfo()) {
			userInfo.setShopId(member.getShopInfo().getId());
			userInfo.setShopCode(member.getShopInfo().getShopCode());
		}
		userInfo.setLoginName(member.getLoginName());
		userInfo.setMemberType(member.getMemberType());
		userInfo.setOpenId(member.getOpenId());
		return userInfo;
	}

	protected String getShopId(UserInfo userInfo) {
		if (null == userInfo) {
			return "";
		}
		return userInfo.getShopId();
	}

	// 请保证userInfo的userId和sellerId至少有一个不为空
	protected String getSellerId(UserInfo userInfo) {
		if (null == userInfo ) {
			return "";
		}else{
			if(StringUtils.isEmpty(userInfo.getSellerId())){
				SellerInfoOutputDto seller = sellerInfoFrontApi.getSellerByMemberId( userInfo.getId());
				return seller.getId();
			}else{
				return userInfo.getSellerId();
			}
		}
	}

	private HttpServletRequest getRequest() {
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}

	private HttpSession getSession() {
		HttpSession session = getRequest().getSession();
		return session;
	}

	@SuppressWarnings("unused")
	private void setUserInfo(UserInfo userInfo) {
		HttpSession session = getSession();
		String json = null == userInfo ? "" : JSON.toJSONString(userInfo);
		session.setAttribute(WfxConstant.SESSION_USER_KEY, json);
	}

	protected void setBoolResult(BooleanResult result, WFXResult<Boolean> wfxResult) {
		if (wfxResult == null) {
			return;
		}
		result.setResult(wfxResult.getResult());
		if (wfxResult.getResult()) {
			result.setMsg(wfxResult.getResultMsg());
		} else {
			result.setError(wfxResult.getResultMsg());
		}
	}

	protected UserContext getUserContext(IUserContext context, String method) {
		UserContext uc = new UserContext();
		uc.setSessionId(context.getSid());
		uc.setUserAgent(context.getUserAgent());
		uc.setReferer(context.getReferer());
		uc.setClientIp(context.getIp());
		uc.setRequestMethod(method);
		return uc;
	}

	protected double toDouble(Double d) {
		if (d == null) {
			return 0;
		}
		return d.doubleValue();
	}

	protected int toInt(Integer d) {
		if (d == null) {
			return 0;
		}
		return d.intValue();
	}

	protected double addDouble(double x, double y) {
		BigDecimal add1 = new BigDecimal(Double.toString(x));
		BigDecimal add2 = new BigDecimal(Double.toString(y));
		return add1.add(add2).doubleValue();
	}
	protected double mulDouble(double x, double y) {
		BigDecimal add1 = new BigDecimal(Double.toString(x));
		BigDecimal add2 = new BigDecimal(Double.toString(y));
		return add1.multiply(add2).doubleValue();
	}

}
