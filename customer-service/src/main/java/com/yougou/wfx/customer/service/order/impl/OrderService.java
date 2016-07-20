package com.yougou.wfx.customer.service.order.impl;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yougou.pay.api.IPayApi;
import com.yougou.pay.constant.TradeTypeConstant;
import com.yougou.pay.model.PayTradeInfo;
import com.yougou.wfx.commodity.api.front.ICommodityStyleFrontApi;
import com.yougou.wfx.commodity.dto.output.CommodityProductOutputDto;
import com.yougou.wfx.commodity.dto.output.CommodityStyleOutputDto;
import com.yougou.wfx.customer.common.bean.BeanUtils;
import com.yougou.wfx.customer.common.constant.WeiXinConstant;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.utils.JacksonUtils;
import com.yougou.wfx.customer.common.utils.LogUtils;
import com.yougou.wfx.customer.model.commodity.CommodityShoppingCartVo;
import com.yougou.wfx.customer.model.commodity.CommodityStyleVo;
import com.yougou.wfx.customer.model.commodity.PictureVo;
import com.yougou.wfx.customer.model.commodity.ProductVo;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.order.*;
import com.yougou.wfx.customer.model.seller.SellerinfoVo;
import com.yougou.wfx.customer.model.shoopingcart.ShoppingCartChangeVo;
import com.yougou.wfx.customer.model.shop.SelectItemVo;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.customer.model.usercenter.myaddress.AccountAddressVo;
import com.yougou.wfx.customer.model.weixin.custom.request.TextMessageRequest;
import com.yougou.wfx.customer.model.weixin.custom.response.MessageResponse;
import com.yougou.wfx.customer.service.commodity.ICommodityService;
import com.yougou.wfx.customer.service.order.IOrderService;
import com.yougou.wfx.customer.service.shop.ISellerService;
import com.yougou.wfx.customer.service.shop.IShopService;
import com.yougou.wfx.customer.service.shoppingcart.IShoppingCartService;
import com.yougou.wfx.customer.service.system.ISystemService;
import com.yougou.wfx.customer.service.usercenter.IAccountAddressService;
import com.yougou.wfx.customer.service.weixin.IWXService;
import com.yougou.wfx.dto.base.PageModel;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.OrderStatusEnum;
import com.yougou.wfx.member.api.front.IMemberAccountFrontApi;
import com.yougou.wfx.member.dto.output.MemberAccountOutputDto;
import com.yougou.wfx.order.api.front.IOrderFrontApi;
import com.yougou.wfx.order.dto.input.OrderSearchDto;
import com.yougou.wfx.order.dto.output.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 获取订单信息
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/30 下午6:08
 * @since 1.0 Created by lipangeng on 16/3/30 下午6:08. Email:lipg@outlook.com.
 */
@Service
public class OrderService implements IOrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	@Autowired
	private IOrderFrontApi orderFrontApi;
	@Autowired
	private IShoppingCartService shoppingCartService;
	@Autowired
	private IShopService shopService;
	@Autowired
	private ISellerService sellerService;
	@Autowired
	private IAccountAddressService accountAddressService;
	@Autowired
	private IPayApi payApi;
	@Autowired
	private ICommodityService commodityService;
	@Autowired
	private ISystemService systemService;
	@Autowired
	private ICommodityStyleFrontApi commodityStyleFrontApi;
	@Autowired
	private IMemberAccountFrontApi memberAccountFrontApi;
	@Autowired
	private IWXService wXService;

	/**
	 * 获取用户的订单信息
	 *
	 * @param userId
	 *            用户id
	 *
	 * @since 1.0 Created by lipangeng on 16/3/30 下午6:00.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public OrderCountVo getUserOrderCount(String userId) {
		OrderCountVo orderCount = new OrderCountVo();
		OrderCountOutputDto memberOrderCount = orderFrontApi.getMemberOrderCount(userId);
		if (memberOrderCount != null) {
			orderCount.setAllOrder(memberOrderCount.getAllOrderCount());
			orderCount.setWaitPay(memberOrderCount.getNotPayOrderCount());
			orderCount.setShipedOrder(memberOrderCount.getShipedOrderCount());
			orderCount.setRefundOrder(memberOrderCount.getRefundOrderCount());
			orderCount.setWaitRefund(memberOrderCount.getRefundOrderCount());
			// 待发货
			orderCount.setWaitDeliver(memberOrderCount.getWaitDeliverOrderCount());
			// 待收货
			orderCount.setWaitReceive(memberOrderCount.getShipedOrderCount());

		}
		return orderCount;
	}

	/**
	 * 判断用户是否有成功购买行为 检测当前登录用户是否有已完成或者确认收货的订单(不要求查询数量，存在即可)
	 *
	 * @return
	 */
	@Override
	public boolean existsValidOrders() {
		SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
		if (loginUserDetails == null || Strings.isNullOrEmpty(loginUserDetails.getUserId())) {
			return false;
		}
		PageModel pageModel = new PageModel();
		pageModel.setLimit(1);
		OrderSearchDto orderSearchDto = new OrderSearchDto();
		// 判断是否有已确认收货订单
		orderSearchDto.setStatus(OrderStatusEnum.TRADE_SUCCESS.getKey());
		PageModel<OrderOutputDto> orders = orderFrontApi.getOrders(loginUserDetails.getUserId(), pageModel, orderSearchDto);
		if (orders != null && orders.getItems() != null && orders.getItems().size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取用户的订单
	 *
	 * @param userId
	 *            用户id
	 * @param page
	 *            分页对象
	 * @param searchVo
	 *            查询对象
	 *
	 * @since 1.0 Created by lipangeng on 16/4/7 下午5:20. Email:lipg@outlook.com.
	 */
	@Override
	public Page<OrderVo> getUserOrders(String userId, Page page, OrderSearchVo searchVo) {
		Page<OrderVo> orderPage = new Page<>();
		orderPage.setItems(Lists.<OrderVo> newArrayList());
		// 创建分页请求
		PageModel pageModel = null;
		if (page != null) {
			pageModel = page.toPageModelDto();
		} else {
			pageModel = new PageModel();
		}
		// 创建搜索请求
		OrderSearchDto search = null;
		if (searchVo != null) {
			search = searchVo.toOrderSearchDto();
		} else {
			search = new OrderSearchDto();
		}
		// 请求订单
		PageModel<OrderOutputDto> orders = orderFrontApi.getOrders(userId, pageModel, search);
		// 填充订单数据
		if (orders != null && orders.getItems() != null) {
			for (OrderOutputDto orderOutputDto : orders.getItems()) {
				orderPage.getItems().add(OrderVo.valueOf(orderOutputDto));
			}
		}
		return orderPage;
	}

	/**
	 * 获取用户订单信息
	 *
	 * @param userId
	 *            用户id
	 * @param orderId
	 *            订单id
	 *
	 * @since 1.0 Created by lipangeng on 16/4/11 下午3:54.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public OrderVo getUserOrder(String userId, String orderId) {
		// 填充各个订单的商品数据
		OrderInfoDto orderInfo = orderFrontApi.getOrderById(orderId);
		if (orderInfo == null || orderInfo.getBuyerId() == null || !orderInfo.getBuyerId().equals(userId)) {
			return null;
		}
		return OrderVo.valueOf(orderInfo);
	}

	/**
	 * 取消订单
	 *
	 * @param userId
	 *            用户id
	 * @param orderId
	 *            订单id
	 *
	 * @since 1.0 Created by lipangeng on 16/4/12 下午4:28.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public Result cancelOrder(String userId, String orderId) {
		OrderInfoDto orderInfo = orderFrontApi.getOrderById(orderId);
		if (userId == null || !userId.equals(orderInfo.getBuyerId())) {
			return Result.create(false, 400, "无权取消该订单");
		}
		WFXResult<Boolean> result = orderFrontApi.cancelOrder(orderId);
		if (result == null) {
			return Result.create(false, 500, "调用远程Api返回NUll失败,无法取消订单");
		}
		if (!result.getResult()) {
			return Result.create(false, 500, "取消订单失败.错误:" + result.getResultCode() + "::" + result.getResultMsg());
		}
		return Result.create();
	}

	/**
	 * 确认订单
	 *
	 * @param userId
	 *            用户id
	 * @param orderId
	 *            订单id
	 *
	 * @since 1.0 Created by lipangeng on 16/4/13 上午9:43.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public Result confirmOrderGoods(String userId, String orderId) {
		OrderInfoDto orderInfo = orderFrontApi.getOrderById(orderId);
		if (userId == null || !userId.equals(orderInfo.getBuyerId())) {
			return Result.create(false, 400, "无权对订单进行确认收货操作");
		}
		WFXResult<Boolean> result = orderFrontApi.conformOrder(orderId);
		if (result == null) {
			return Result.create(false, 500, "调用远程Api返回NUll失败,无法对此订单进行确认收货操作");
		}
		if (!result.getResult()) {
			return Result.create(false, 500, "订单确认收货失败.错误:" + result.getResultCode() + "::" + result.getResultMsg());
		}
		return Result.create();
	}

	/**
	 * 创建订单接口
	 *
	 * @param orderCreateVo
	 *            订单数据
	 *
	 * @since 1.0 Created by lipangeng on 16/4/15 上午9:39.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public Result<OrderVo> createOrder(OrderCreateVo orderCreateVo) {
		if (orderCreateVo == null || StringUtils.isEmpty(orderCreateVo.getAddressId()) || StringUtils.isEmpty(orderCreateVo.getPayType())) {
			logger.error(LogUtils.requestInfo("创建订单参数异常,参考信息:{}"), JacksonUtils.Convert(orderCreateVo));
			return Result.create(Boolean.FALSE, "创建订单参数异常。请返回购物车重新下单。");
		}
		SessionUserDetails userDetails = SessionUtils.getLoginUserDetails();
		if (userDetails == null) {
			logger.error(LogUtils.requestInfo("无法创建订单,用户尚未登录"));
			return Result.create(Boolean.FALSE, "用户未登录");
		}
		// 购物车
		List<CommodityShoppingCartVo> cartVos = shoppingCartService.getCheckedCommodityShoppingCartVos();
		if (CollectionUtils.isEmpty(cartVos)) {
			logger.error(LogUtils.requestInfo("购物车数据为空，无法提交订单"));
			return Result.create(Boolean.FALSE, "购物车数据为空，无法提交订单");
		}
		
		// 地址信息
		AccountAddressVo accountAddressVo = accountAddressService.getAddress(userDetails.getUserId(), orderCreateVo.getAddressId());
		if (accountAddressVo == null) {
			logger.error(LogUtils.requestInfo("收货地址异常,收货地址id:{}"), orderCreateVo.getAddressId());
			return Result.create(Boolean.FALSE, "收货地址异常");
		}
		// 检查是否超过下单数量限制
		int orderDayNumber = orderFrontApi.getOrderDayNumberByMemberId(userDetails.getUserId());
		int oneDayNum = systemService.getUserOneDayOrderNum();
		if (orderDayNumber > oneDayNum) {
			logger.error(LogUtils.requestInfo("超过每日下单数量,每日最大可下单数量:{}"), orderDayNumber);
			return Result.create(false, "超过每日下单数量,每日最大可下单数量:" + oneDayNum);
		}
		// 批量获取商品信息并检查是否下单店铺商品
		Set<String> commodityNos = Sets.newHashSet();
		for (CommodityShoppingCartVo cartVo : cartVos) {
			commodityNos.add(cartVo.getCommodityNo());
		}
		Map<String, CommodityStyleVo> commoditys = commodityService.getCommodityByNos(Lists.newArrayList(commodityNos), Boolean.TRUE, Boolean.TRUE);
		if (CollectionUtils.isEmpty(commoditys)) {
			logger.error(LogUtils.requestInfo("无法获取商品信息,商品nos:{}"), JacksonUtils.Convert(commodityNos));
			return Result.create(Boolean.FALSE, "获取商品信息异常");
		}
		// 构建单个订单
		StringBuilder errorMsg = new StringBuilder();
		OrderCreateDto createDto = new OrderCreateDto();
		createDto.setPayType(orderCreateVo.getPayType());
		createDto.setDiscountFee(0d);
		createDto.setPostFee(0d);
		createDto.setStatus(OrderStatusEnum.WAIT_PAY.getKey());
		createDto.setBuyerAccount(userDetails.getUserName());
		createDto.setBuyerId(userDetails.getUserId());
		createDto.setOrderDetailCreateDto(Lists.<OrderDetailCreateDto> newArrayList());
		createDto.setReceiverName(accountAddressVo.getName());
		createDto.setReceiverAddress(accountAddressVo.getAddress());
		createDto.setReceiverCity(accountAddressVo.getCity());
		createDto.setReceiverMobile(accountAddressVo.getPhone());
		createDto.setReceiverPhone(accountAddressVo.getPhone());
		createDto.setReceiverDistrict(accountAddressVo.getDistrict());
		createDto.setReceiverState(accountAddressVo.getProvince());
		// 计算商品总数量
		int orderNum = 0;
		// 计算总付款
		BigDecimal orderPayment = new BigDecimal(0);
		for (CommodityShoppingCartVo cartVo : cartVos) {
			// 检查商品信息
			CommodityStyleVo commodity = commoditys.get(cartVo.getCommodityNo());
			if (commodity == null) {
				errorMsg.append(String.format("商品%s %s 数据异常;", cartVo.getName(), cartVo.getSpecName()));
				continue;
			}
			if (commodity.getIsOnsale() == 0) {
				errorMsg.append(String.format("商品%s %s 已下架;", cartVo.getName(), cartVo.getSpecName()));
				continue;
			}
			// 检查Product信息
			ProductVo product = getCandidateProduct(cartVo.getSkuId(), commodity.getProducts());
			if (product == null) {
				errorMsg.append(String.format("商品%s %s 异常;", cartVo.getName(), cartVo.getSpecName()));
				continue;
			}
			if (product.getInventoryNum() <= 0) {
				errorMsg.append(String.format("商品%s %s 已售罄;", cartVo.getName(), cartVo.getSpecName()));
				continue;
			}
			if (product.getInventoryNum() < cartVo.getCount()) {
				errorMsg.append(String.format("商品%s %s 库存不足;", cartVo.getName(), cartVo.getSpecName()));
				continue;
			}
			// 如果订单供应商id没有注入,则注入供应商id
			if (Strings.isNullOrEmpty(createDto.getSupplierId())) {
				createDto.setSupplierId(commodity.getSupplierId());
			}
			// 创建子订单信息
			OrderDetailCreateDto dto = new OrderDetailCreateDto();
			dto.setNum(cartVo.getCount());
			dto.setCommodityId(cartVo.getCommodityId());
			dto.setPrice(cartVo.getWfxPrice());
			dto.setProdId(product.getId());
			dto.setProdName(cartVo.getName());
			dto.setProdSpec(cartVo.getSpecName() + " , " + cartVo.getSize());
			dto.setDiscountFee(0d);
			dto.setProDiscountFee(0d);
			// 计算金额,尚未加入运费等做计算.
			if (dto.getPrice() == null || dto.getNum() == null) {
				errorMsg.append("商品:").append(cartVo.getName()).append("的价格或购买数量异常.");
			} else {
				BigDecimal fee = new BigDecimal(dto.getPrice());
				fee = fee.multiply(new BigDecimal(dto.getNum()));
				dto.setTotalFee(fee.doubleValue());
				dto.setPayment(fee.doubleValue());
				orderPayment = orderPayment.add(fee);
			}
			// 计算商品数量
			if (dto.getNum() != null) {
				orderNum += dto.getNum();
			}
			createDto.getOrderDetailCreateDto().add(dto);
		}
		// 注入数量,金额信息
		createDto.setNum(orderNum);
		createDto.setPayment(orderPayment.doubleValue());
		createDto.setTotalFee(orderPayment.doubleValue());
		if (errorMsg.length() > 0) {
			logger.error(LogUtils.requestInfo("创建订单时,商品信息效验失败,参考信息:{}"), errorMsg);
			return Result.create(Boolean.FALSE, errorMsg.toString());
		}
		OrderResultDto orderCreateResult = null;
		try {
			orderCreateResult = orderFrontApi.createOrder(createDto);
		} catch (Exception e) {
			logger.error(LogUtils.requestInfo("创建订单异常,参考信息:" + e.getMessage()), e);
			return Result.create(false, "创建订单发生未知异常");
		}
		if (orderCreateResult == null || orderCreateResult.getCode() != 1 || orderCreateResult.getOrderInfoDto() == null) {
			logger.error(LogUtils.requestInfo("创建订单失败,参考信息:{}"), JacksonUtils.Convert(orderCreateResult));
			if (orderCreateResult != null) {
				return Result.create(false, orderCreateResult.getMessage());
			} else {
				return Result.create(false, "创建订单发生未知异常");
			}
		}
		// 清空购物车信息
		shoppingCartService.clearShoppingcartByItems(cartVos);
		// 填充成功订单信息
		logger.info(LogUtils.requestInfo("创建订单成功,订单id:{}"), orderCreateResult.getOrderInfoDto().getId());
		return Result.create(Boolean.TRUE, "创建订单成功!", OrderVo.valueOf(orderCreateResult.getOrderInfoDto()));
	}

	private ProductVo getCandidateProduct(String productNo, List<ProductVo> products) {
		if (CollectionUtils.isEmpty(products)) {
			return null;
		}
		for (ProductVo product : products) {
			if (product.getProductNo().equals(productNo)) {
				return product;
			}
		}
		return null;
	}

	/**
	 * 获取当前确认订单页面数据模型
	 *
	 * @return
	 */
	@Override
	public OrderPayVo getOrderPayInfo(String addressId) {
		SessionUserDetails user = SessionUtils.getLoginUserDetails();
		OrderPayVo orderPayVo = new OrderPayVo();
		List<String> payTypes = orderFrontApi.getPayInfo();
		// 设置支付方式
		orderPayVo.setPayTypes(payTypes);
		AccountAddressVo defaultAddress = null;
		if (!StringUtils.isEmpty(addressId)) {
			AccountAddressVo accountAddressVo = accountAddressService.getAddress(user.getUserId(), addressId);
			if (accountAddressVo != null) {
				defaultAddress = accountAddressVo;
			}
		}
		if (defaultAddress == null || StringUtils.isEmpty(addressId)) {
			List<AccountAddressVo> addressVos = accountAddressService.getAllAddress(user.getUserId());

			if (!CollectionUtils.isEmpty(addressVos)) {
				for (AccountAddressVo accountAddressVo : addressVos) {
					if (accountAddressVo.isDefaultAddress()) {
						defaultAddress = accountAddressVo;
					}
				}
				if (defaultAddress == null) {
					defaultAddress = addressVos.get(0);
				}
			}
		}
		if (defaultAddress != null) {
			defaultAddress.setPhone(BeanUtils.shortPhoneNum(defaultAddress.getPhone()));
		}
		// 设置收获地址
		orderPayVo.setAddress(defaultAddress);
		List<CommodityShoppingCartVo> cartVos = shoppingCartService.getCommodityShoppingCartVos(0);
		double totalPrice = 0;
		int totalCount = 0;
		double freight = 0;
		List<OrderCommodityVo> commoditys = Lists.newArrayList();
		if (!CollectionUtils.isEmpty(cartVos)) {
			for (CommodityShoppingCartVo cartVo : cartVos) {
				if (cartVo.isChecked()) {
					OrderCommodityVo orderCommodityVo = new OrderCommodityVo();
					orderCommodityVo.setCommodityNo(cartVo.getCommodityNo());
					orderCommodityVo.setSkuId(cartVo.getSkuId());
					orderCommodityVo.setCount(cartVo.getCount());
					orderCommodityVo.setWfxPrice(cartVo.getWfxPrice());
					orderCommodityVo.setImageUrl(cartVo.getImageUrl());
					totalPrice += cartVo.getWfxPrice() * cartVo.getCount();
					totalCount += cartVo.getCount();
					commoditys.add(orderCommodityVo);
				}
			}
		}
		orderPayVo.setTotalCount(totalCount);
		orderPayVo.setCommoditys(commoditys);
		orderPayVo.setTotalPrice(totalPrice);
		orderPayVo.setFreight(freight);
		return orderPayVo;
	}

	/**
	 * 重新购买功能生成订单模型 return OrderPayVo
	 */
	@Override
	public OrderPayVo getOrderPayInfo(OrderVo order) {
		String addressId = null;
		SessionUserDetails user = SessionUtils.getLoginUserDetails();
		OrderPayVo orderPayVo = new OrderPayVo();
		List<String> payTypes = orderFrontApi.getPayInfo();
		// 设置支付方式
		orderPayVo.setPayTypes(payTypes);

		// 设置收货地址
		AccountAddressVo defaultAddress = null;
		if (!StringUtils.isEmpty(addressId)) {
			AccountAddressVo accountAddressVo = accountAddressService.getAddress(user.getUserId(), addressId);
			if (accountAddressVo != null) {
				defaultAddress = accountAddressVo;
			}
		}
		if (defaultAddress == null || StringUtils.isEmpty(addressId)) {
			List<AccountAddressVo> addressVos = accountAddressService.getAllAddress(user.getUserId());
			if (!CollectionUtils.isEmpty(addressVos)) {
				for (AccountAddressVo accountAddressVo : addressVos) {
					if (accountAddressVo.isDefaultAddress()) {
						defaultAddress = accountAddressVo;
					}
				}
				if (defaultAddress == null) {
					defaultAddress = addressVos.get(0);
				}
			}
		}
		if (defaultAddress != null) {
			defaultAddress.setPhone(BeanUtils.shortPhoneNum(defaultAddress.getPhone()));
		}
		// 设置收获地址
		orderPayVo.setAddress(defaultAddress);
		// 获取订单的商品数据
		List<CommodityShoppingCartVo> cartVos = this.getCommodityShoppingCartVos(order, order.getOrderDetails());
		// 加入购物车（现在的订单逻辑，只有加入购物车才可以生成订单）
		if (!CollectionUtils.isEmpty(cartVos)) {
			//加入购物车之前，将选择的购物车给清空掉
			List<CommodityShoppingCartVo> oldCartVos = shoppingCartService.getCommodityShoppingCartVos(0);
			if(!CollectionUtils.isEmpty(oldCartVos)){
				for(CommodityShoppingCartVo oldCart:oldCartVos){
					oldCart.setChecked(false);
					oldCart.setHasModify(true);
				}
				shoppingCartService.upDateShoppingCartData(oldCartVos);
			}
			//勾选新增加的商品
			for (CommodityShoppingCartVo cartVo : cartVos) {
				ShoppingCartChangeVo cartChange = new ShoppingCartChangeVo();
				cartChange.setCommodityNo(cartVo.getCommodityNo());
				cartChange.setShopId(cartVo.getShopId());
				cartChange.setSkuCount(cartVo.getCount());
				cartChange.setSkuId(cartVo.getSkuId());
				shoppingCartService.addCart(cartChange);
			}
			shoppingCartService.upDateShoppingCartData(cartVos);
		}

		double totalPrice = 0;
		int totalCount = 0;
		double freight = 0;
		List<OrderCommodityVo> commoditys = Lists.newArrayList();
		if (!CollectionUtils.isEmpty(cartVos)) {
			for (CommodityShoppingCartVo cartVo : cartVos) {
				if (cartVo.isChecked()) {
					totalPrice += cartVo.getWfxPrice() * cartVo.getCount();
					totalCount += cartVo.getCount();
					OrderCommodityVo orderCommodityVo = new OrderCommodityVo();
					orderCommodityVo.setCommodityNo(cartVo.getCommodityNo());
					orderCommodityVo.setSkuId(cartVo.getSkuId());
					orderCommodityVo.setCount(cartVo.getCount());
					orderCommodityVo.setWfxPrice(cartVo.getWfxPrice());
					orderCommodityVo.setImageUrl(cartVo.getImageUrl());
					commoditys.add(orderCommodityVo);
				}
			}
		}
		orderPayVo.setTotalCount(totalCount);
		orderPayVo.setCommoditys(commoditys);
		orderPayVo.setTotalPrice(totalPrice);
		orderPayVo.setFreight(freight);
		return orderPayVo;
	}
	
	/**
	 * 立即购买-获取订单支付信息
	 *
	 * @return
	 */
	@Override
	public OrderPayVo getOrderPayInfo(SelectItemVo vo) {
		SessionUserDetails user = SessionUtils.getLoginUserDetails();
		OrderPayVo orderPayVo = new OrderPayVo();
		List<String> payTypes = orderFrontApi.getPayInfo();
		// 设置支付方式
		orderPayVo.setPayTypes(payTypes);
		
		AccountAddressVo defaultAddress = null;
		List<AccountAddressVo> addressVos = accountAddressService.getAllAddress(user.getUserId());

		if (!CollectionUtils.isEmpty(addressVos)) {
			for (AccountAddressVo accountAddressVo : addressVos) {
				if (accountAddressVo.isDefaultAddress()) {
					defaultAddress = accountAddressVo;
				}
			}
			if (defaultAddress == null) {
				defaultAddress = addressVos.get(0);
			}
		}
		if (defaultAddress != null) {
			defaultAddress.setPhone(BeanUtils.shortPhoneNum(defaultAddress.getPhone()));
		}
		// 设置收获地址
		orderPayVo.setAddress(defaultAddress);

		//设置支付金额等信息
		double totalPrice = vo.getPrice() * vo.getCount();
		int totalCount = vo.getCount();
		double freight = 0;
		orderPayVo.setTotalCount(totalCount);
		orderPayVo.setTotalPrice(totalPrice);
		orderPayVo.setFreight(freight);

		//设置订单中商品信息
		List<OrderCommodityVo> commoditys = Lists.newArrayList();
		OrderCommodityVo orderCommodityVo = new OrderCommodityVo();
		orderCommodityVo.setCommodityNo(vo.getCommodityNo());
		orderCommodityVo.setSkuId(vo.getSkuId());
		orderCommodityVo.setCount(vo.getCount());
		orderCommodityVo.setWfxPrice(vo.getPrice());
		List<PictureVo> pictureVos = commodityService.getCommodityPictures(vo.getCommodityNo(), WfxConstant.WFX_COMMODITY_PICTURE_TYPE_LIST);
		if (!CollectionUtils.isEmpty(pictureVos)) {
			orderCommodityVo.setImageUrl(pictureVos.get(0).getUrl());
		}
		commoditys.add(orderCommodityVo);
		orderPayVo.setCommoditys(commoditys);
		
		return orderPayVo;
	}

	@Override
	public List<CommodityShoppingCartVo> getCommodityShoppingCartVos(OrderVo order, List<OrderDetailVo> orderDetailList) {
		List<CommodityShoppingCartVo> cartList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(orderDetailList)) {
			return cartList;
		}
		CommodityShoppingCartVo cart = null;
		for (OrderDetailVo orderDetail : orderDetailList) {
			cart = new CommodityShoppingCartVo();
			CommodityStyleOutputDto style = commodityStyleFrontApi.getById(orderDetail.getCommodityId());
			CommodityProductOutputDto product = commodityStyleFrontApi.getProductById(orderDetail.getProdId());

			if (style == null || product == null) {
				continue;
			}

			cart.setId(null);
			cart.setName(style.getCommodityName());
			cart.setCommodityNo(style.getNo());
			cart.setCommodityId(style.getId());
			cart.setCount(orderDetail.getNum());
			cart.setChecked(true);
			String shopId = SessionUtils.getCurrentShopIdOrDefault();
			ShopVo shopVo = shopService.getShopModelById(shopId);
			cart.setShopId(shopId);
			cart.setShopCode(shopVo.getShopCode());
			cart.setShopName(shopVo.getName());
			cart.setStock(product.getInventoryNum());
			cart.setSize(product.getSizeName());
			cart.setSpecName(style.getSpecName());
			cart.setWfxPrice(style.getWfxPrice());
			cart.setSkuId(product.getProductNo());
			cart.setIsOnsale(style.getIsOnsale());

			List<PictureVo> pictureVos = commodityService.getCommodityPictures(style.getNo(), WfxConstant.WFX_COMMODITY_PICTURE_TYPE_LIST);
			if (!CollectionUtils.isEmpty(pictureVos)) {
				cart.setImageUrl(pictureVos.get(0).getUrl());
			}
			cartList.add(cart);
		}
		if (CollectionUtils.isEmpty(cartList)) {
			return cartList;
		}
		List<CommodityShoppingCartVo> removeList = Lists.newArrayList();
		for (CommodityShoppingCartVo cartVo : cartList) {
			if (cartVo.getIsOnsale() != 1 || cartVo.getStock() <= 0) {
				removeList.add(cartVo);
			}
		}
		cartList.removeAll(removeList);
		return cartList;
	}

	/**
	 * 创建交易流水,创建交易（提交订单号成功，转入支付收银台触发）
	 *
	 * @param order
	 *
	 * @since 1.0 Created by lipangeng on 16/4/22 下午1:58.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public Result<String> createOrderPayTrade(OrderVo order) {
		PayTradeInfo payTradeInfo = new PayTradeInfo();
		payTradeInfo.setBizNo(order.getWfxOrderNo());// 订单号-必须
		payTradeInfo.setBizType(TradeTypeConstant.TRADE_BIZ_TYPE_ORDER);// 业务类型-必须
		payTradeInfo.setTradeType(TradeTypeConstant.TRADE_TYPE_PAY);// 交易类型-必须
		payTradeInfo.setAmount(String.valueOf(order.getPayment()));// 交易金额-必须
		try {
			String tradeNo = payApi.createTrade(payTradeInfo);
			if (Strings.isNullOrEmpty(tradeNo)) {
				logger.error("无法获取交易流水号");
				return Result.create(false, "创建交易流水号失败");
			}
			return Result.create(true, "创建交易流水号成功.", tradeNo);
		} catch (Exception e) {
			logger.error("创建交易流水号失败.错误:" + e.getMessage());
			return Result.create(false, "创建交易流水号失败.错误:" + e.getMessage());
		}
	}

	@Override
	public Boolean queryIfAutoTransfer(String buyerId, String wfxOrderNo) {
		Map<String, String> map = orderFrontApi.queryIfAutoTransfer(buyerId, wfxOrderNo);
		if (map != null && map.get("shopId") != null && map.get("shopCode") != null) {
			return true;
		}
		return false;
	}

	@Override
	public void sendFansOrderMessage(OrderVo order, String appId, String secret) {
		// TODO Auto-generated method stub
		try {
			if (order == null) {
				logger.error("订单对象信息不能为null");
				return;
			}
			OrderOutputDto orderInfo = orderFrontApi.getCommiOrderById(order.getId());
			if (orderInfo == null) {
				logger.error("订单数据失败！");
				return;
			}
			MemberAccountOutputDto member1 = memberAccountFrontApi.getMemberAccountById(order.getBuyerId());
			if (member1 != null) {
				if (!StringUtils.isEmpty(member1.getH5OpenId())) {
					// 成为优粉微信通知
					TextMessageRequest request1 = new TextMessageRequest();
					request1.setToUser(member1.getH5OpenId());
					request1.getText().setContent(WeiXinConstant.TO_BE_FANS);
					Result<MessageResponse> r1 = wXService.sendTextMessage(wXService.getAccessToken(appId, secret), request1);
					if (r1.isSuccess()) {
						logger.error("发送成为优粉微信通知成功！");
					} else {
						logger.error("发送成为优粉微信通知失败！失败原因：" + r1.getMessage());
					}
				}
				String memberId2 = memberAccountFrontApi.getParentMemberId(member1.getId());
				if (!StringUtils.isEmpty(memberId2)) {
					MemberAccountOutputDto member2 = memberAccountFrontApi.getMemberAccountById(memberId2);
					if (member2 != null) {
						if (!StringUtils.isEmpty(member2.getH5OpenId())) {
							// 新增二级分销商
							TextMessageRequest request2 = new TextMessageRequest();
							request2.setToUser(member2.getH5OpenId());
							String message2 = MessageFormat.format(WeiXinConstant.NEW_TWO_LEVEL_AGENT_MESSAGE, member1.getPlatformUsername());
							request2.getText().setContent(message2);
							Result<MessageResponse> r2 = wXService.sendTextMessage(wXService.getAccessToken(appId, secret), request2);
							if (r2.isSuccess()) {
								logger.error("发送新增二级分销商微信通知成功！");
							} else {
								logger.error("发送新增二级分销商微信通知失败！失败原因：" + r2.getMessage());
							}
							// 粉丝下单通知
							message2 = MessageFormat.format(WeiXinConstant.NEW_FANS_ORDER_MESSAGE, member1.getPlatformUsername(), order.getPayTime(), order.getWfxOrderNo(), order.getPayment(), orderInfo.getCommissionMap().get(1).getCommission());
							request2.getText().setContent(message2);
							r2 = wXService.sendTextMessage(wXService.getAccessToken(appId, secret), request2);
							if (r2.isSuccess()) {
								logger.error("发送粉丝下单通知成功！");
							} else {
								logger.error("发送粉丝下单微信通知失败！失败原因：" + r2.getMessage());
							}
						}
						String memberId3 = memberAccountFrontApi.getParentMemberId(member2.getId());
						if (!StringUtils.isEmpty(memberId3)) {
							MemberAccountOutputDto member3 = memberAccountFrontApi.getMemberAccountById(memberId3);
							if (member3 != null && !StringUtils.isEmpty(member3.getH5OpenId())) {
								// 新增三级分销商
								TextMessageRequest request3 = new TextMessageRequest();
								request3.setToUser(member3.getH5OpenId());
								String message3 = MessageFormat.format(WeiXinConstant.NEW_THREE_LEVEL_AGENT_MESSAGE, member1.getPlatformUsername());
								request3.getText().setContent(message3);
								Result<MessageResponse> r3 = wXService.sendTextMessage(wXService.getAccessToken(appId, secret), request3);
								if (r3.isSuccess()) {
									logger.error("发送新增三级分销商微通知成功！");
								} else {
									logger.error("发送新增三级分销商微信通知失败！失败原因：" + r3.getMessage());
								}
								// 二级分销商的粉丝成功下单通知
								message3 = MessageFormat.format(WeiXinConstant.NEW_TWO_LEVEL_AGENT_AND_FANS_ORDER_MESSAGE, member2.getPlatformUsername(), order.getPayTime(), order.getWfxOrderNo(), order.getPayment(), orderInfo.getCommissionMap().get(2).getCommission());
								request3.getText().setContent(message3);
								r3 = wXService.sendTextMessage(wXService.getAccessToken(appId, secret), request3);
								if (r3.isSuccess()) {
									logger.error("发送二级分销商的粉丝成功下单通知成功！");
								} else {
									logger.error("发送二级分销商的粉丝成功下单通知失败！失败原因：" + r3.getMessage());
								}
							}
							String memberId4 = memberAccountFrontApi.getParentMemberId(member3.getId());
							if (!StringUtils.isEmpty(memberId4)) {
								MemberAccountOutputDto member4 = memberAccountFrontApi.getMemberAccountById(memberId4);
								if (member4 != null && !StringUtils.isEmpty(member4.getH5OpenId())) {
									// 三级分销商的粉丝成功下单通知
									TextMessageRequest request4 = new TextMessageRequest();
									String message4 = MessageFormat.format(WeiXinConstant.NEW_THREE_LEVEL_AGENT_AND_FANS_ORDER_MESSAGE, member3.getPlatformUsername(), order.getPayTime(), order.getWfxOrderNo(), order.getPayment(), orderInfo.getCommissionMap().get(3).getCommission());
									request4.getText().setContent(message4);
									Result<MessageResponse> r4 = wXService.sendTextMessage(wXService.getAccessToken(appId, secret), request4);
									if (r4.isSuccess()) {
										logger.error("发送三级分销商的粉丝成功下单通知成功！");
									} else {
										logger.error("发送三级分销商的粉丝成功下单通知失败！失败原因：" + r4.getMessage());
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("粉丝下单微信通知失败：" + e);
		}
	}

	@Override
	public void sendAgentOrderMessage(OrderVo order, String appId, String secret) {
		// TODO Auto-generated method stub
		try {
			if (order == null) {
				logger.error("订单对象信息不能为null");
				return;
			}
			OrderOutputDto orderInfo = orderFrontApi.getCommiOrderById(order.getId());
			if (orderInfo == null) {
				logger.error("订单数据失败！");
				return;
			}
			MemberAccountOutputDto member1 = memberAccountFrontApi.getMemberAccountById(order.getBuyerId());
			if (member1 != null) {
				if (!StringUtils.isEmpty(member1.getH5OpenId())) {
					// 自己下单通知
					TextMessageRequest request1 = new TextMessageRequest();
					request1.setToUser(member1.getH5OpenId());
					String message1 = MessageFormat.format(WeiXinConstant.NEW_MY_ORDER_MESSAGE, order.getPayTime(), order.getWfxOrderNo(), order.getPayment(), orderInfo.getCommissionMap().get(1).getCommission());
					request1.getText().setContent(message1);
					Result<MessageResponse> r1 = wXService.sendTextMessage(wXService.getAccessToken(appId, secret), request1);
					if (r1.isSuccess()) {
						logger.error("发送自己下单通知成功！");
					} else {
						logger.error("发送自己下单通知失败！失败原因：" + r1.getMessage());
					}
				}
				String memberId2 = memberAccountFrontApi.getParentMemberId(member1.getId());
				if (!StringUtils.isEmpty(memberId2)) {
					MemberAccountOutputDto member2 = memberAccountFrontApi.getMemberAccountById(memberId2);
					if (member2 != null) {
						if (!StringUtils.isEmpty(member2.getH5OpenId())) {
							// 二级分销商成功下单
							TextMessageRequest request2 = new TextMessageRequest();
							request2.setToUser(member2.getH5OpenId());
							String message2 = MessageFormat.format(WeiXinConstant.NEW_TWO_LEVEL_AGENT_AND_FANS_ORDER_MESSAGE, member1.getPlatformUsername(), order.getPayTime(), order.getWfxOrderNo(), order.getPayment(), orderInfo.getCommissionMap().get(2).getCommission());
							request2.getText().setContent(message2);
							Result<MessageResponse> r2 = wXService.sendTextMessage(wXService.getAccessToken(appId, secret), request2);
							if (r2.isSuccess()) {
								logger.error("发送二级分销商成功下单通知成功！");
							} else {
								logger.error("发送二级分销商成功下单通知失败！失败原因：" + r2.getMessage());
							}
						}
						String memberId3 = memberAccountFrontApi.getParentMemberId(member2.getId());
						if (!StringUtils.isEmpty(memberId3)) {
							MemberAccountOutputDto member3 = memberAccountFrontApi.getMemberAccountById(memberId3);
							if (!StringUtils.isEmpty(member3.getH5OpenId())) {
								// 三级级分销商成功下单
								TextMessageRequest request3 = new TextMessageRequest();
								request3.setToUser(member3.getH5OpenId());
								String message3 = MessageFormat.format(WeiXinConstant.NEW_THREE_LEVEL_AGENT_AND_FANS_ORDER_MESSAGE, member2.getPlatformUsername(), order.getPayTime(), order.getWfxOrderNo(), order.getPayment(), orderInfo.getCommissionMap().get(3).getCommission());
								request3.getText().setContent(message3);
								Result<MessageResponse> r3 = wXService.sendTextMessage(wXService.getAccessToken(appId, secret), request3);
								if (r3.isSuccess()) {
									logger.error("发送三级级分销商成功下单通知成功！");
								} else {
									logger.error("发送三级级分销商成功下单通知失败！失败原因：" + r3.getMessage());
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("分销商下单微信通知失败：" + e);
		}
	}

}
