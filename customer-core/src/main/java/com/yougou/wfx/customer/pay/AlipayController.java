package com.yougou.wfx.customer.pay;

import com.google.common.base.Strings;
import com.yougou.pay.vo.PayResVo;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.session.SessionOrderPayDetails;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.utils.JacksonUtils;
import com.yougou.wfx.customer.common.utils.LogUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.order.OrderVo;
import com.yougou.wfx.customer.model.pay.alipay.AlipayCreateOrderReqVo;
import com.yougou.wfx.customer.service.order.IOrderService;
import com.yougou.wfx.customer.service.pay.IAlipayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;

/**
 * 支付宝支付
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/17 下午12:01
 * @since 1.0 Created by lipangeng on 16/5/17 下午12:01. Email:lipg@outlook.com.
 */
@Controller
public class AlipayController {
    private static final Logger logger = LoggerFactory.getLogger(AlipayController.class);
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IAlipayService alipayService;

    /**
     * 支付宝支付页面
     *
     * @since 1.0 Created by lipangeng on 16/5/17 下午2:15. Email:lipg@outlook.com.
     */
    @RequestMapping("pay/alipay")
    @LoginValidate
    public String paying(Model model, RedirectAttributes redirectAttributes) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        SessionOrderPayDetails orderPayDetails = loginUserDetails.getOrderPayDetails();
        // 验证session中的支付信息是否完整
        if (orderPayDetails == null || Strings.isNullOrEmpty(orderPayDetails.getOrderId()) ||
            Strings.isNullOrEmpty(orderPayDetails.getBankNo()) || Strings.isNullOrEmpty(orderPayDetails.getTradeNo())) {
            logger.error(LogUtils.requestInfo("支付参数验证失败,支付信息:{}"), JacksonUtils.Convert(orderPayDetails));
            redirectAttributes.addFlashAttribute("errorMsg", "支付信息不完整,请重试.");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        OrderVo userOrder = orderService.getUserOrder(loginUserDetails.getUserId(), orderPayDetails.getOrderId());
        if (userOrder == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "订单不存在或无权访问该订单");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        if (! Objects.equals(userOrder.getStatus(), "WAIT_PAY")) {
            redirectAttributes.addFlashAttribute("errorMsg", "该订单无法支付,订单状态:" + userOrder.getStatusDesc());
            return "redirect:/order/" + orderPayDetails.getOrderId() + ".sc";
        }
        // 创建支付宝支付
        AlipayCreateOrderReqVo createOrderReq = new AlipayCreateOrderReqVo();
        createOrderReq.setOrderNo(userOrder.getWfxOrderNo());
        createOrderReq.setPayment(String.valueOf(userOrder.getPayment()));
        createOrderReq.setBankNo(orderPayDetails.getBankNo());
        createOrderReq.setTradeNo(orderPayDetails.getTradeNo());
        createOrderReq.setUserId(loginUserDetails.getUserId());
        createOrderReq.setUserName(loginUserDetails.getUserName());
        Result<PayResVo> createOrderResult = alipayService.createOrder(createOrderReq);
        if (createOrderResult.hasError()) {
            redirectAttributes.addFlashAttribute("errorMsg", createOrderResult.getMessage());
            logger.error(LogUtils.requestInfo("获取支付信息失败,参考信息:{}"), createOrderResult.getMessage());
            return "redirect:/order/" + orderPayDetails.getOrderId() + ".sc";
        }
        PayResVo payResVo = createOrderResult.getData();
        if (payResVo.getReqParamMap() == null && Objects.equals("get", payResVo.getActionMethod())) {
            return payResVo.getActionForm();
        }
        model.addAttribute("payResVo", payResVo);
        return "pay/alipaying";
    }
}
