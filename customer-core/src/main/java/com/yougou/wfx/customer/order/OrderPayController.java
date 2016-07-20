package com.yougou.wfx.customer.order;

import java.text.MessageFormat;

import com.google.common.base.Objects;
import com.yougou.pay.constant.BankNoConstant;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.constant.WeiXinConstant;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.session.SessionOrderPayDetails;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.configurations.weixin.WeiXinProperties;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.order.OrderVo;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.customer.model.weixin.custom.request.TextMessageRequest;
import com.yougou.wfx.customer.model.weixin.custom.response.MessageResponse;
import com.yougou.wfx.customer.service.order.IOrderService;
import com.yougou.wfx.customer.service.shop.ISellerService;
import com.yougou.wfx.customer.service.shop.IShopService;
import com.yougou.wfx.customer.service.usercenter.IUserActionLogService;
import com.yougou.wfx.customer.service.weixin.IWXService;
import com.yougou.wfx.enums.OrderLogTypeEnum;
import com.yougou.wfx.member.dto.output.MemberAccountOutputDto;
import com.yougou.wfx.seller.api.front.ISellerInfoFrontApi;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 订单支付相关的Controller
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/14 下午3:11
 * @since 1.0 Created by lipangeng on 16/4/14 下午3:11. Email:lipg@outlook.com.
 */
@Controller
@RequestMapping("order")
public class OrderPayController {
	private static final Logger logger = LoggerFactory.getLogger(OrderPayController.class);
	@Autowired
	private IOrderService orderService;
	@Autowired
	private WeiXinProperties weiXinProperties;
	@Autowired
	private IWXService wxService;
	@Autowired
	private IUserActionLogService userActionLogService;
	@Autowired
	private ISellerService sellerService;
	@Autowired
    private IShopService shopService;

	/**
	 * 订单的支付信息
	 *
	 * @since 1.0 Created by lipangeng on 16/4/14 下午3:13.
	 *        Email:lipg@outlook.com.
	 */
	@RequestMapping("{orderId}/payDetail")
	@LoginValidate
	public String payDetail(Model model, @PathVariable("orderId") String orderId, RedirectAttributes redirectAttributes) {
		SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
		OrderVo userOrder = orderService.getUserOrder(loginUserDetails.getUserId(), orderId);
		if (userOrder == null) {
			redirectAttributes.addFlashAttribute("errorMsg", "订单不存在或无权访问该订单");
			return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
		}
		model.addAttribute("order", userOrder);
		if (Objects.equal(userOrder.getStatus(), "WAIT_PAY") || Objects.equal(userOrder.getStatus(), "TRADE_CLOSED")) {
			return "order/pay/order-pay-fail";
		} else if (!Objects.equal(userOrder.getStatus(), "WAIT_PAY")) {
			// 支付成功日志
			userActionLogService.addUserActionLog(OrderLogTypeEnum.ORDER_TURN_LOG.getKey() + "", "订单支付成功");
			
			// 查询会员订单是否为首单支付自动升级优粉的信息
			Boolean isFirstUfans = orderService.queryIfAutoTransfer(userOrder.getBuyerId(), userOrder.getWfxOrderNo());
			if(isFirstUfans){
				orderService.sendFansOrderMessage(userOrder, weiXinProperties.getAppId(), weiXinProperties.getAppSecret());
				//修改当前店铺为自己的店铺
				ShopVo shopVo = shopService.getShopModelByUserId(userOrder.getBuyerId());
				if(shopVo!=null){
					SessionUtils.setCurrentShopId(shopVo.getId());
				}
			}else{
				orderService.sendAgentOrderMessage(userOrder, weiXinProperties.getAppId(), weiXinProperties.getAppSecret());
			}
			model.addAttribute("isFirstUfans", isFirstUfans);
			model.addAttribute("memberType", sellerService.getMemberTypeByMemberId(loginUserDetails.getUserId()).getKey());
			return "order/pay/order-pay-success";
		}
		return "redirect:/order/" + orderId + ".sc";
	}

	/**
	 * 支付订单
	 *
	 * @since 1.0 Created by lipangeng on 16/4/14 下午3:13.
	 *        Email:lipg@outlook.com.
	 */
	@RequestMapping("{orderId}/pay")
	@LoginValidate
	public String doPay(@PathVariable("orderId") String orderId, Model model, RedirectAttributes redirectAttributes) {
		SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
		SessionOrderPayDetails orderPayDetails = loginUserDetails.getOrderPayDetails();
		// 验证订单信息
		OrderVo userOrder = orderService.getUserOrder(loginUserDetails.getUserId(), orderId);
		if (userOrder == null) {
			redirectAttributes.addFlashAttribute("errorMsg", "订单不存在或无权访问该订单");
			return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
		}
		if (!Objects.equal(userOrder.getStatus(), "WAIT_PAY")) {
			redirectAttributes.addFlashAttribute("errorMsg", "该订单无法支付,订单状态:" + userOrder.getStatusDesc());
			return "redirect:/order/" + orderId + ".sc";
		}
		// 注入选择的支付方式信息,默认微信支付
		String bankNo = "";
		if (userOrder.getPayType() != null) {
			switch (userOrder.getPayType()) {
			case "wechatpay":
				bankNo = BankNoConstant.WFX_H5_BANK_NO_WEIXIN;
				break;
			case "alipay":
				bankNo = BankNoConstant.WFX_H5_BANK_NO_ALIPAY;
				break;
			}
		}
		orderPayDetails.setBankNo(bankNo);
		// 订单验证通过,注入订单号到session中
		orderPayDetails.setOrderId(orderId);
		// 创建交易（提交订单号成功，转入支付收银台触发）
		Result<String> orderPayTrade = orderService.createOrderPayTrade(userOrder);
		if (orderPayTrade.hasError()) {
			redirectAttributes.addFlashAttribute("errorMsg", "该订单无法支付,创建交易流水失败,请重试");
			return "redirect:/order/" + orderId + ".sc";
		}
		// 在session中注入交易信息
		orderPayDetails.setTradeNo(orderPayTrade.getData());
		// 微信H5支付代码是8001
		// 微信支付
		switch (bankNo) {
		case BankNoConstant.WFX_H5_BANK_NO_WEIXIN:
			Result<String> authorizeBaseUrl = wxService.getAuthorizeBaseUrl(weiXinProperties.getWxAuthorizeUrl(), weiXinProperties.getAppId(), weiXinProperties.getOrderPayUrl());
			if (authorizeBaseUrl.hasError()) {
				redirectAttributes.addFlashAttribute("errorMsg", "该订单无法支付,无法获取微信授权地址.");
				return "redirect:/order/" + orderId + ".sc";
			}
			// 所有信息验证成功,保存session中的信息,并跳转支付页面
			loginUserDetails.save();
			model.addAttribute("authorizeBaseUrl", authorizeBaseUrl.getData());
			return "pay/wxcheck";
		case BankNoConstant.WFX_H5_BANK_NO_ALIPAY:
			// 所有信息验证成功,保存session中的信息,并跳转支付页面
			loginUserDetails.save();
			return "redirect:/pay/alipay.sc";
		}
		// 通用支付地址
		return "redirect:/pay/otherPay.sc";
	}
}
