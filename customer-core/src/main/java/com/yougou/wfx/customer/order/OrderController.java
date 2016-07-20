package com.yougou.wfx.customer.order;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.configurations.WFXProperties;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.spring.BindingUtils;
import com.yougou.wfx.customer.common.utils.JacksonUtils;
import com.yougou.wfx.customer.common.utils.LogUtils;
import com.yougou.wfx.customer.model.commodity.CommodityShoppingCartVo;
import com.yougou.wfx.customer.model.commodity.CommodityStyleVo;
import com.yougou.wfx.customer.model.commodity.PictureVo;
import com.yougou.wfx.customer.model.commodity.ProductVo;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.order.OrderCreateVo;
import com.yougou.wfx.customer.model.order.OrderDetailVo;
import com.yougou.wfx.customer.model.order.OrderPayVo;
import com.yougou.wfx.customer.model.order.OrderVo;
import com.yougou.wfx.customer.model.shop.SelectItemVo;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.customer.service.commodity.ICommodityService;
import com.yougou.wfx.customer.service.order.IOrderService;
import com.yougou.wfx.customer.service.shop.IShopService;
import com.yougou.wfx.customer.service.shoppingcart.IShoppingCartService;
import com.yougou.wfx.customer.service.system.ISystemService;
import com.yougou.wfx.customer.service.usercenter.IUserActionLogService;
import com.yougou.wfx.enums.OrderLogTypeEnum;
import com.yougou.wfx.enums.OrderStatusEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang.sj on 2016/3/22.
 */

@Controller
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private WFXProperties wfxProperties;
    @Autowired
    private IUserActionLogService userActionLogService;
    @Autowired
    private IShopService shopService;
    @Autowired
    private ICommodityService commodityService;
    @Autowired
    private ISystemService systemService;

    @RequestMapping("/confirm_order")
    @LoginValidate
    public String payOrder(ModelMap modelMap, String addressId) {
        OrderPayVo orderPayVo = orderService.getOrderPayInfo(addressId);
        if (! CollectionUtils.isEmpty(orderPayVo.getCommoditys())) {
            if (orderPayVo.getCommoditys().size() > 3) {
                orderPayVo.setCommoditys(orderPayVo.getCommoditys().subList(0, 3));
            }
        }
        modelMap.addAttribute("orderPay", orderPayVo);
        //用户所在店铺
        String currentid = SessionUtils.getCurrentShopIdOrDefault();
        ShopVo shopVo = shopService.getShopModelById(currentid);
        Assert.notNull(shopVo, "获取店铺信息异常");
        modelMap.addAttribute("shop", shopVo);
        SessionUserDetails sessionUserDetails = SessionUtils.getLoginUserDetails();
        sessionUserDetails.setAddressPayRedirect(Boolean.FALSE);
        sessionUserDetails.save();
        modelMap.addAttribute("memberId",sessionUserDetails.getUserId());
        return "order/pay-order";
    }
    
    @RequestMapping("/order/buyAgin")
    @LoginValidate
    public String buyAginOrder(String orderId,ModelMap modelMap) {
    	//获取当前登陆用户信息
    	SessionUserDetails sessionUserDetails = SessionUtils.getLoginUserDetails();
    	//首选获取订单
    	OrderVo order = orderService.getUserOrder(sessionUserDetails.getUserId(),orderId);
    	Assert.notNull(order, "获取订单信息异常");
    	
    	OrderPayVo orderPayVo = orderService.getOrderPayInfo(order);
        if (! CollectionUtils.isEmpty(orderPayVo.getCommoditys())) {
            if (orderPayVo.getCommoditys().size() > 3) {
                orderPayVo.setCommoditys(orderPayVo.getCommoditys().subList(0, 3));
            }
        }
    	
        //用户所在店铺
        String currentid = SessionUtils.getCurrentShopIdOrDefault();
        ShopVo shopVo = shopService.getShopModelById(currentid);
        Assert.notNull(shopVo, "获取店铺信息异常");
        sessionUserDetails.setAddressPayRedirect(Boolean.FALSE);
        sessionUserDetails.save();
        modelMap.addAttribute("shopId", currentid);
        modelMap.addAttribute("orderPay", orderPayVo);
        modelMap.addAttribute("shop", shopVo);
        modelMap.addAttribute("memberId",sessionUserDetails.getUserId());
        return "order/pay-order";
    }
    
    @RequestMapping("/order/buyAginCheck")
    @ResponseBody
    public String buyAginCheck(String orderId) {
    	Map resultMap = new HashMap<String, String>();
    	String code = "success";
    	//获取当前登陆用户信息
    	SessionUserDetails sessionUserDetails = SessionUtils.getLoginUserDetails();
    	//首选获取订单
    	OrderVo order = orderService.getUserOrder(sessionUserDetails.getUserId(),orderId);
    	
    	//获取订单的商品数据
		List<CommodityShoppingCartVo> cartVos = orderService.getCommodityShoppingCartVos(order,order.getOrderDetails());
		if(CollectionUtils.isEmpty(cartVos)){
			code= "fail";
		}
		resultMap.put("code", code);
        return JSON.toJSONString(resultMap);
    }

    /**
     * 订单付款
     *
     * @since 1.0 Created by lipangeng on 16/4/11 下午5:18. Email:lipg@outlook.com.
     */
    @RequestMapping("order/{orderId}")
    @LoginValidate
    public String orderPay(@PathVariable("orderId") String orderId, Model model, RedirectAttributes redirectAttributes) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        OrderVo userOrder = orderService.getUserOrder(loginUserDetails.getUserId(), orderId);
        if (userOrder == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "订单不存在或无权访问");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        // 判断是否显示自动确认收货时间
        if (userOrder.isCanConfirm()) {
            model.addAttribute("orderAutoConfirmDays", systemService.getConfigByKey(WfxConstant.WFX_ORDER_AUTO_CONFIRM_ORDER_DAYS));
        }
        model.addAttribute("order", userOrder);
        if (Objects.equal(OrderStatusEnum.WAIT_PAY.getKey(), userOrder.getStatus()) && userOrder.getCreatedTime() != null) {
            Calendar calendar = Calendar.getInstance();
            long nowMililis = calendar.getTimeInMillis();
            calendar.setTime(userOrder.getCreatedTime());
            // 未支付订单,有效时间1个小时
            calendar.add(Calendar.HOUR_OF_DAY, wfxProperties.getWaitPayOrderLeftTimeHour());
            model.addAttribute("orderLeftTime", (calendar.getTimeInMillis() - nowMililis) / 1000);
        }
        // 注入商品的no
        List<String> commodityIds = Lists.newArrayList();
        Map<String, String> commodityNos = Maps.newHashMap();
        for (OrderDetailVo orderDetailVo : userOrder.getOrderDetails()) {
            commodityIds.add(orderDetailVo.getCommodityId());
        }
        List<CommodityStyleVo> commodityList = commodityService.getCommodityListByIds(commodityIds, false, false);
        if (commodityList != null) {
            for (CommodityStyleVo commodityStyleVo : commodityList) {
                commodityNos.put(commodityStyleVo.getId(), commodityStyleVo.getNo());
            }
        }
        model.addAttribute("commodityNos", commodityNos);
        return "order/order-detail";
    }

    @RequestMapping(value = "/confirm_order", method = RequestMethod.POST)
    @LoginValidate
    public String orderCreate(@Valid OrderCreateVo orderCreateVo, BindingResult bindingResult, Model model) {
        logger.debug(LogUtils.requestInfo("申请创建订单,参考信息:{}"), JacksonUtils.Convert(orderCreateVo));
        Result<OrderVo> createResult = null;
        // 判断传入的参数是否有误
        if (bindingResult.hasErrors()) {
            logger.error(LogUtils.requestInfo("创建订单参数异常,参考信息:{}"), BindingUtils.errors(bindingResult));
            createResult = Result.create(false, BindingUtils.errors(bindingResult));
        }
        // 如果传入的参数无误的话,则创建订单
        if (createResult == null) {
            createResult = orderService.createOrder(orderCreateVo);
        }
        // 订单创建失败处理
        if (createResult.hasError() || createResult.getData() == null) {
            model.addAttribute("errorMsg", createResult.getMessage());
            logger.error(LogUtils.requestInfo("创建订单失败,参考信息:{}"), createResult.getMessage());
            return "order/order-create-fail";
        }
        // 创建订单成功日志
        userActionLogService.addUserActionLog(OrderLogTypeEnum.ORDER_TURN_LOG.getKey() + "", "提交订单成功");
        logger.info(LogUtils.requestInfo("创建订单成功,订单号:{}"), createResult.getData().getId());
        return "redirect:/order/" + createResult.getData().getId() + "/pay.sc";
    }

    @RequestMapping(value = "/order/commodity/show")
    @LoginValidate
    public String orderCommodityShow(ModelMap modelMap) {
		List<CommodityShoppingCartVo> chechedCartVos = shoppingCartService.getCheckedCommodityShoppingCartVos();
		int total = 0;
        if (! CollectionUtils.isEmpty(chechedCartVos)) {
            for (CommodityShoppingCartVo cartVo : chechedCartVos) {
                total += cartVo.getCount();
            }
        }

        modelMap.addAttribute("total", total);
        modelMap.addAttribute("commoditys", chechedCartVos);
        return "order/commodity/commodity_show";
    }
    
}
