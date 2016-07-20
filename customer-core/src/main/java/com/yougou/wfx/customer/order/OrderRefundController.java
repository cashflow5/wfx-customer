package com.yougou.wfx.customer.order;

import com.google.common.base.Objects;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.spring.BindingUtils;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.order.OrderDetailVo;
import com.yougou.wfx.customer.model.order.OrderVo;
import com.yougou.wfx.customer.model.order.refund.OrderRefundSearchVo;
import com.yougou.wfx.customer.model.order.refund.OrderRefundVo;
import com.yougou.wfx.customer.model.order.refund.form.OrderRefundFormVo;
import com.yougou.wfx.customer.model.order.refund.form.OrderReturnGoodsFormVo;
import com.yougou.wfx.customer.service.order.IOrderRefundService;
import com.yougou.wfx.customer.service.order.IOrderService;
import com.yougou.wfx.customer.service.usercenter.IUserActionLogService;
import com.yougou.wfx.enums.OrderLogTypeEnum;
import com.yougou.wfx.enums.RefundTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * 订单退款
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/12 上午11:02
 * @since 1.0 Created by lipangeng on 16/4/12 上午11:02. Email:lipg@outlook.com.
 */
@Controller
@RequestMapping("order")
public class OrderRefundController {
    private static final Logger logger = LoggerFactory.getLogger(OrderRefundController.class);
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOrderRefundService orderRefundService;
    @Autowired
    private IUserActionLogService userActionLogService;

    /**
     * 退款列表页面
     *
     * @since 1.0 Created by lipangeng on 16/4/12 上午11:12. Email:lipg@outlook.com.
     */
    @RequestMapping("myrefunds")
    @LoginValidate
    public String list(Model model, Page page, OrderRefundSearchVo search) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        Page<OrderRefundVo> orderRefunds = orderRefundService.getUserOrderRefund(loginUserDetails.getUserId(), page, search);
        model.addAttribute("orderRefunds", orderRefunds);
        return "order/refund/order-refunds";
    }

    /**
     * 退款列表页面
     *
     * @since 1.0 Created by lipangeng on 16/4/12 上午11:12. Email:lipg@outlook.com.
     */
    @RequestMapping("myrefunds/ajax")
    @LoginValidate
    public String listAjax(Model model, Page page, OrderRefundSearchVo search) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        Page<OrderRefundVo> orderRefunds = orderRefundService.getUserOrderRefund(loginUserDetails.getUserId(), page, search);
        model.addAttribute("orderRefunds", orderRefunds);
        return "order/refund/order-refunds-ajax";
    }

    /**
     * 退货退款
     *
     * @since 1.0 Created by lipangeng on 16/4/14 上午8:09. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "{orderId}/{orderDetailId}/refundDetail", method = RequestMethod.GET)
    @LoginValidate
    public String detail(Model model,
                         @PathVariable("orderId") String orderId,
                         @PathVariable("orderDetailId") String orderDetailId,
                         RedirectAttributes redirectAttributes) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        OrderRefundVo refundItem = orderRefundService.getRefundItem(loginUserDetails, orderId, orderDetailId);
        if (refundItem == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "退货信息不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_REFUNDS_URL;
        }
        model.addAttribute("orderRefund", refundItem);
        return "order/refund/order-refund-detail";
    }

    /**
     * 退货退款
     *
     * @since 1.0 Created by lipangeng on 16/4/14 上午8:09. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "refund/{refundNo}", method = RequestMethod.GET)
    @LoginValidate
    public String detail(Model model, @PathVariable("refundNo") String refundNo, RedirectAttributes redirectAttributes) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        OrderRefundVo refundItem = orderRefundService.getRefundItem(loginUserDetails, refundNo);
        if (refundItem == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "退货信息不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_REFUNDS_URL;
        }
        model.addAttribute("orderRefund", refundItem);
        return "order/refund/order-refund-detail";
    }

    /**
     * 请求退款页面
     *
     * @since 1.0 Created by lipangeng on 16/4/12 上午11:13. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "{orderId}/{orderDetailId}/refund", method = RequestMethod.GET)
    @LoginValidate
    public String refund(@PathVariable("orderId") String orderId,
                         @PathVariable("orderDetailId") String orderDetailId,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        // 获取订单信息以及子订单信息
        OrderVo userOrder = orderService.getUserOrder(loginUserDetails.getUserId(), orderId);
        OrderDetailVo orderDetail = findOrderDetail(userOrder, orderDetailId);
        // 如果订单或者子订单信息为空,则返回退款退货列表
        if (userOrder == null || orderDetail == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "订单不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        // 如果子订单不能退款
        if (! orderDetail.isCanRefund()) {
            redirectAttributes.addFlashAttribute("errorMsg", "该商品暂时无法退款,提示:" + orderDetail.getRefundStatusDesc());
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        model.addAttribute("order", userOrder);
        model.addAttribute("orderDetail", orderDetail);
        OrderRefundVo orderRefund = orderRefundService.getRefundItem(loginUserDetails, orderId, orderDetailId);
        if (! model.containsAttribute("refundForm")) {
            OrderRefundFormVo orderRefundForm = new OrderRefundFormVo();
            if (orderRefund != null) {
                orderRefundForm.setRefundFee(orderRefund.getCanReturnFee());
            } else {
                orderRefundForm.setRefundFee(0d);
            }
            model.addAttribute("refundForm", orderRefundForm);
        }
        model.addAttribute("orderRefund", orderRefund);
        return "order/refund/order-refund";
    }

    /**
     * 处理退款请求
     *
     * @since 1.0 Created by lipangeng on 16/4/12 上午11:14. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "{orderId}/{orderDetailId}/refund", method = RequestMethod.POST)
    @LoginValidate
    public String doRefund(@Valid OrderRefundFormVo orderRefundForm,
                           BindingResult bindingResult,
                           @PathVariable("orderId") String orderId,
                           @PathVariable("orderDetailId") String orderDetailId,
                           RedirectAttributes redirectAttributes) {
        // 验证订单数据
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMsg", BindingUtils.errors(bindingResult));
            return "redirect:/order/" + orderId + "/" + orderDetailId + "/refund.sc";
        }
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        // 获取订单信息以及子订单信息
        OrderVo userOrder = orderService.getUserOrder(loginUserDetails.getUserId(), orderId);
        OrderDetailVo orderDetail = findOrderDetail(userOrder, orderDetailId);
        // 如果订单或者子订单信息为空,则返回退款退货列表
        if (userOrder == null || orderDetail == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "订单不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        // 判断子订单是否可以退款
        if (! orderDetail.isCanRefund()) {
            redirectAttributes.addFlashAttribute("errorMsg", "该商品暂时无法退款,提示:" + orderDetail.getRefundStatusDesc());
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        // 判断退款金额的有效性,未发货仅退款时,金额必须一致,已发货仅退款时,金额不能超过已付金额
        Result checkRefundFee = checkRefundFee(orderDetail,
                                               orderRefundService.getRefundItem(loginUserDetails, orderId, orderDetailId),
                                               orderRefundForm.getRefundFee(),
                                               orderDetail.getNum(),
                                               orderRefundForm.getRefundType());
        if (checkRefundFee.hasError()) {
            redirectAttributes.addFlashAttribute("refundForm", orderRefundForm);
            redirectAttributes.addFlashAttribute("errorMsg", checkRefundFee.getMessage());
            return "redirect:/order/" + orderId + "/" + orderDetailId + "/refund.sc";
        }
        // 构建创建退款单数据
        OrderRefundVo orderRefund = OrderRefundVo.valueOf(userOrder, orderDetail);
        if (orderRefund != null) {
            orderRefund.update(orderRefundForm);
        }
        Result<OrderRefundVo> refundItem = orderRefundService.createRefundItem(loginUserDetails.getUserId(), orderRefund);
        if (refundItem.hasError()) {
            redirectAttributes.addFlashAttribute("errorMsg", "创建退款单失败:" + refundItem.getMessage());
            redirectAttributes.addFlashAttribute(orderRefundForm);
            return "redirect:/order/" + orderId + "/" + orderDetailId + "/refund.sc";
        }
        // 退款日志
        if (refundItem.getData() != null) {
            userActionLogService.addUserActionLog(OrderLogTypeEnum.ORDER_OPT_LOG.getKey() + "",
                                                  String.format("退款类型：%s，退款单：%s，退款原因：%s，商家编码：%s，退款金额：%s",
                                                                refundItem.getData().getRefundTypeDesc(),
                                                                refundItem.getData().getId(),
                                                                refundItem.getData().getReason(),
                                                                refundItem.getData().getShopId(),
                                                                refundItem.getData().getRefundFee()));
        }
        return "redirect:/order/" + orderId + "/" + orderDetailId + "/refundDetail.sc";
    }

    /**
     * 修改退款请求页面
     *
     * @since 1.0 Created by lipangeng on 16/4/12 上午11:13. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "refund/{refundNo}/editRefund", method = RequestMethod.GET)
    @LoginValidate
    public String editRefund(Model model, @PathVariable("refundNo") String refundNo, RedirectAttributes redirectAttributes) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        // 处理退款单数据
        OrderRefundVo orderRefund = orderRefundService.getRefundItem(loginUserDetails, refundNo);
        if (orderRefund == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "退款信息不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_REFUNDS_URL;
        }
        // 获取订单信息以及子订单信息
        OrderVo userOrder = orderService.getUserOrder(loginUserDetails.getUserId(), orderRefund.getOrderId());
        OrderDetailVo orderDetail = findOrderDetail(userOrder, orderRefund.getOrderDetailId());
        // 如果订单或者子订单信息为空,则返回退款退货列表
        if (userOrder == null || orderDetail == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "订单不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        // 处理退款单可退金额和可退数量
        configEditReturnFeeAndNum(orderRefund);
        // 判断该退款单是否可修改
        if (! orderRefund.isCanEdit()) {
            redirectAttributes.addFlashAttribute("errorMsg", "该退款单不可修改");
            return "redirect:/order/refund/" + refundNo + ".sc";
        }
        if (! model.containsAttribute("refundForm")) {
            OrderRefundFormVo orderRefundForm = new OrderRefundFormVo();
            orderRefundForm.setDescription(orderRefund.getDescription());
            orderRefundForm.setReason(orderRefund.getReason());
            orderRefundForm.setRefundType(orderRefund.getRefundType());
            orderRefundForm.setRefundFee(orderRefund.getRefundFee());
            model.addAttribute("refundForm", orderRefundForm);
        }
        model.addAttribute("orderRefund", orderRefund);
        model.addAttribute("order", userOrder);
        model.addAttribute("orderDetail", orderDetail);
        return "order/refund/order-refund-edit";
    }

    /**
     * 更新退款请求页面
     *
     * @since 1.0 Created by lipangeng on 16/4/12 上午11:14. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "refund/{refundNo}/editRefund", method = RequestMethod.POST)
    @LoginValidate
    public String doEditRefund(@Valid OrderRefundFormVo orderRefundForm,
                               BindingResult bindingResult,
                               @PathVariable("refundNo") String refundNo,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMsg", BindingUtils.errors(bindingResult));
            redirectAttributes.addFlashAttribute("refundForm", orderRefundForm);
            return "redirect:/order/refund/" + refundNo + "/editRefund.sc";
        }
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        OrderRefundVo orderRefund = orderRefundService.getRefundItem(loginUserDetails, refundNo);
        // 判断该退款单是否存在
        if (orderRefund == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "退款信息不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_REFUNDS_URL;
        }
        // 获取订单信息以及子订单信息
        OrderVo userOrder = orderService.getUserOrder(loginUserDetails.getUserId(), orderRefund.getOrderId());
        OrderDetailVo orderDetail = findOrderDetail(userOrder, orderRefund.getOrderDetailId());
        // 如果订单或者子订单信息为空,则返回退款退货列表
        if (userOrder == null || orderDetail == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "订单不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        // 处理退款单可退金额和可退数量
        configEditReturnFeeAndNum(orderRefund);
        // 判断该退款单是否可修改
        if (! orderRefund.isCanEdit()) {
            redirectAttributes.addFlashAttribute("errorMsg", "退款单不可修改");
            redirectAttributes.addFlashAttribute("refundForm", orderRefundForm);
            return "redirect:/order/refund/" + refundNo + "/editRefund.sc";
        }
        // 判断退款金额的有效性,未发货仅退款时,金额必须一致,已发货仅退款时,金额不能超过已付金额
        Result checkRefundFee = checkRefundFee(orderDetail,
                                               orderRefund,
                                               orderRefundForm.getRefundFee(),
                                               orderDetail.getNum(),
                                               orderRefundForm.getRefundType());
        if (checkRefundFee.hasError()) {
            redirectAttributes.addFlashAttribute("refundForm", orderRefundForm);
            redirectAttributes.addFlashAttribute("errorMsg", checkRefundFee.getMessage());
            return "redirect:/order/refund/" + refundNo + "/editRefund.sc";
        }
        // 更新退款单信息
        orderRefund.update(orderRefundForm);
        Result<OrderRefundVo> refundItem = orderRefundService.updateRefundItem(loginUserDetails.getUserId(), orderRefund);
        if (refundItem.hasError()) {
            redirectAttributes.addFlashAttribute("errorMsg", "更新退款单失败:" + refundItem.getMessage());
            redirectAttributes.addFlashAttribute("refundForm", orderRefundForm);
            return "redirect:/order/refund/" + refundNo + "/editRefund.sc";
        }
        // 退款日志
        if (refundItem.getData() != null) {
            userActionLogService.addUserActionLog(OrderLogTypeEnum.ORDER_OPT_LOG.getKey() + "",
                                                  String.format("退款类型：%s，退款单：%s，退款原因：%s，商家编码：%s，退款金额：%s, 修改退款原因:%s",
                                                                refundItem.getData().getRefundTypeDesc(),
                                                                refundItem.getData().getId(),
                                                                orderRefund.getReason(),
                                                                refundItem.getData().getShopId(),
                                                                refundItem.getData().getRefundFee(),
                                                                refundItem.getData().getReason()));
        }
        return "redirect:/order/refund/" + refundNo + ".sc";
    }

    /**
     * 退款退货页面
     *
     * @since 1.0 Created by lipangeng on 16/4/14 上午8:09. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "{orderId}/{orderDetailId}/returnGoods", method = RequestMethod.GET)
    @LoginValidate
    public String ruturnGoods(Model model,
                              @PathVariable("orderId") String orderId,
                              @PathVariable("orderDetailId") String orderDetailId,
                              RedirectAttributes redirectAttributes) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        // 获取订单信息以及子订单信息
        OrderVo userOrder = orderService.getUserOrder(loginUserDetails.getUserId(), orderId);
        OrderDetailVo orderDetail = findOrderDetail(userOrder, orderDetailId);
        // 如果订单或者子订单信息为空,则返回退款退货列表
        if (userOrder == null || orderDetail == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "订单不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_REFUNDS_URL;
        }
        // 如果该订单不能申请退款,则返回商品列表页面
        if (! orderDetail.isCanRefund()) {
            redirectAttributes.addFlashAttribute("errorMsg", "该商品无法申请退货." + orderDetail.getRefundStatusDesc());
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        OrderRefundVo refundItem = orderRefundService.getRefundItem(loginUserDetails, orderId, orderDetailId);
        if (! refundItem.isDelivered()) {
            redirectAttributes.addFlashAttribute("errorMsg", "该商品未发货,无法申请退货.");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        model.addAttribute("order", userOrder);
        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("backAddress", orderRefundService.getBackAddress(orderDetail.getId(), userOrder.getSupplierId()));
        model.addAttribute("expresses", orderRefundService.getExpress());
        model.addAttribute("orderRefund", refundItem);
        return "order/refund/order-refund-goods";
    }

    /**
     * 退货退款执行
     *
     * @since 1.0 Created by lipangeng on 16/4/14 上午8:09. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "{orderId}/{orderDetailId}/returnGoods", method = RequestMethod.POST)
    @LoginValidate
    public String doReturnGoods(@PathVariable("orderId") String orderId,
                                @PathVariable("orderDetailId") String orderDetailId,
                                @Valid OrderReturnGoodsFormVo orderReturnGoodsForm,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMsg", BindingUtils.errors(bindingResult));
            return "redirect:/order/" + orderId + "/" + orderDetailId + "/returnGoods.sc";
        }
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        // 获取订单信息以及子订单信息
        OrderVo userOrder = orderService.getUserOrder(loginUserDetails.getUserId(), orderId);
        OrderDetailVo orderDetail = findOrderDetail(userOrder, orderDetailId);
        // 如果订单或者子订单信息为空,则返回退款退货列表
        if (userOrder == null || orderDetail == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "订单不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_REFUNDS_URL;
        }
        // 如果该订单不能申请退款,则返回商品列表页面
        if (! orderDetail.isCanRefund()) {
            redirectAttributes.addFlashAttribute("errorMsg", "该商品无法申请退款." + orderDetail.getRefundStatusDesc());
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        OrderRefundVo refundItemVo = orderRefundService.getRefundItem(loginUserDetails, orderId, orderDetailId);
        if (! refundItemVo.isDelivered()) {
            redirectAttributes.addFlashAttribute("errorMsg", "该商品未发货,无法申请退货.");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        // 判断退款单数据
        Result checkRefundFee = checkRefundFee(orderDetail,
                                               refundItemVo,
                                               orderReturnGoodsForm.getRefundFee(),
                                               orderReturnGoodsForm.getProNum(),
                                               orderReturnGoodsForm.getRefundType());
        if (checkRefundFee.hasError()) {
            orderReturnGoodsForm.setRefundFee(orderDetail.getPayment());
            orderReturnGoodsForm.setProNum(orderDetail.getNum());
            redirectAttributes.addFlashAttribute("errorMsg", checkRefundFee.getMessage());
            return "redirect:/order/" + orderId + "/" + orderDetailId + "/returnGoods.sc";
        }
        // 构建退款单
        OrderRefundVo orderRefund = OrderRefundVo.valueOf(userOrder, orderDetail);
        if (orderRefund != null) {
            orderRefund.update(orderReturnGoodsForm);
        }
        Result<OrderRefundVo> refundItem = orderRefundService.createRefundItem(loginUserDetails.getUserId(), orderRefund);
        if (refundItem.hasError()) {
            redirectAttributes.addFlashAttribute("errorMsg", "更新退款单失败:" + refundItem.getMessage());
            redirectAttributes.addFlashAttribute(orderReturnGoodsForm);
            return "redirect:/order/" + orderId + "/" + orderDetailId + "/returnGoods.sc";
        }
        // 退货退款日志
        if (refundItem.getData() != null) {
            userActionLogService.addUserActionLog(OrderLogTypeEnum.ORDER_OPT_LOG.getKey() + "",
                                                  String.format("退款类型：%s，退款单：%s，退款原因：%s，商家编码：%s，退款金额：%s，快递公司：%s，快递单号：%s”",
                                                                refundItem.getData().getRefundTypeDesc(),
                                                                refundItem.getData().getId(),
                                                                refundItem.getData().getReason(),
                                                                refundItem.getData().getShopId(),
                                                                refundItem.getData().getRefundFee(),
                                                                refundItem.getData().getCompanyName(),
                                                                refundItem.getData().getSid()));
        }
        return "redirect:/order/" + orderId + "/" + orderDetailId + "/refundDetail.sc";
    }

    /**
     * 退款退货页面
     *
     * @since 1.0 Created by lipangeng on 16/4/14 上午8:09. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "refund/{refundNo}/editReturnGoods", method = RequestMethod.GET)
    @LoginValidate
    public String editRuturnGoods(Model model, @PathVariable("refundNo") String refundNo, RedirectAttributes redirectAttributes) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        // 获取退款单信息
        OrderRefundVo orderRefund = orderRefundService.getRefundItem(loginUserDetails, refundNo);
        if (orderRefund == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "退款信息不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_REFUNDS_URL;
        }
        if (! orderRefund.isDelivered()) {
            redirectAttributes.addFlashAttribute("errorMsg", "该商品未发货,无法申请退货.");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        // 获取订单信息以及子订单信息
        OrderVo userOrder = orderService.getUserOrder(loginUserDetails.getUserId(), orderRefund.getOrderId());
        OrderDetailVo orderDetail = findOrderDetail(userOrder, orderRefund.getOrderDetailId());
        // 如果订单或者子订单信息为空,则返回退款退货列表
        if (userOrder == null || orderDetail == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "订单不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_REFUNDS_URL;
        }
        // 处理退款单可退金额和可退数量
        configEditReturnFeeAndNum(orderRefund);
        // 判断该退款单是否可修改
        if (! orderRefund.isCanEdit()) {
            redirectAttributes.addFlashAttribute("errorMsg", "退款单不可修改");
            return "redirect:/order/refund/" + refundNo + ".sc";
        }
        if (! model.containsAttribute("orderReturnGoodsForm")) {
            OrderReturnGoodsFormVo orderReturnGoodsForm = new OrderReturnGoodsFormVo();
            orderReturnGoodsForm.setDescription(orderRefund.getDescription());
            orderReturnGoodsForm.setReason(orderRefund.getReason());
            orderReturnGoodsForm.setRefundType(orderRefund.getRefundType());
            orderReturnGoodsForm.setRefundFee(orderRefund.getRefundFee());
            orderReturnGoodsForm.setExpress(orderRefund.getCompanyName());
            orderReturnGoodsForm.setExpressNo(orderRefund.getSid());
            model.addAttribute("orderReturnGoodsForm", orderReturnGoodsForm);
        }
        model.addAttribute("order", userOrder);
        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("orderRefund", orderRefund);
        model.addAttribute("backAddress", orderRefundService.getBackAddress(orderDetail.getId(), userOrder.getSupplierId()));
        model.addAttribute("expresses", orderRefundService.getExpress());
        return "order/refund/order-refund-goods-edit";
    }

    /**
     * 退货退款修改
     *
     * @since 1.0 Created by lipangeng on 16/4/14 上午8:09. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "refund/{refundNo}/editReturnGoods", method = RequestMethod.POST)
    @LoginValidate
    public String doEditReturnGoods(@PathVariable("refundNo") String refundNo,
                                    @Valid OrderReturnGoodsFormVo orderReturnGoodsForm,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMsg", BindingUtils.errors(bindingResult));
            redirectAttributes.addFlashAttribute(orderReturnGoodsForm);
            return "redirect:/order/refund/" + refundNo + "/returnGoods.sc";
        }
        OrderRefundVo orderRefund = orderRefundService.getRefundItem(loginUserDetails, refundNo);
        // 判断该退款单是否存在
        if (orderRefund == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "退款信息不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_REFUNDS_URL;
        }
        if (! orderRefund.isDelivered()) {
            redirectAttributes.addFlashAttribute("errorMsg", "该商品未发货,无法申请退货.");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_ORDERS_URL;
        }
        // 获取订单信息以及子订单信息
        OrderVo userOrder = orderService.getUserOrder(loginUserDetails.getUserId(), orderRefund.getOrderId());
        OrderDetailVo orderDetail = findOrderDetail(userOrder, orderRefund.getOrderDetailId());
        // 如果订单或者子订单信息为空,则返回退款退货列表
        if (userOrder == null || orderDetail == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "订单不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_REFUNDS_URL;
        }

        // 处理退款单可退金额和可退数量
        configEditReturnFeeAndNum(orderRefund);
        // 判断该退款单是否可修改
        if (! orderRefund.isCanEdit()) {
            redirectAttributes.addFlashAttribute("errorMsg", "退款单不可修改");
            return "redirect:/order/refund/" + refundNo + ".sc";
        }
        // 判断退款金额
        Result checkRefundFee = checkRefundFee(orderDetail,
                                               orderRefund,
                                               orderReturnGoodsForm.getRefundFee(),
                                               orderReturnGoodsForm.getProNum(),
                                               orderReturnGoodsForm.getRefundType());
        if (checkRefundFee.hasError()) {
            orderReturnGoodsForm.setRefundFee(orderDetail.getPayment());
            orderReturnGoodsForm.setProNum(orderDetail.getNum());
            redirectAttributes.addFlashAttribute("orderReturnGoodsForm", orderReturnGoodsForm);
            redirectAttributes.addFlashAttribute("errorMsg", checkRefundFee.getMessage());
            return "redirect:/order/refund/" + refundNo + "/editReturnGoods.sc";
        }
        // 更新退款信息
        orderRefund.update(orderReturnGoodsForm);
        Result<OrderRefundVo> refundItem = orderRefundService.updateRefundItem(loginUserDetails.getUserId(), orderRefund);
        if (refundItem.hasError()) {
            redirectAttributes.addFlashAttribute("errorMsg", "更新退款单失败:" + refundItem.getMessage());
            redirectAttributes.addFlashAttribute(orderReturnGoodsForm);
            return "redirect:/order/refund" + refundNo + "/refund.sc";
        }
        if (refundItem.getData() != null) {
            userActionLogService.addUserActionLog(OrderLogTypeEnum.ORDER_OPT_LOG.getKey() + "",
                                                  String.format("退款类型：%s，退款单：%s，退款原因：%s，商家编码：%s，退款金额：%s，快递公司：%s，快递单号：%s ,修改退款原因:%s”",
                                                                refundItem.getData().getRefundTypeDesc(),
                                                                refundItem.getData().getId(),
                                                                orderRefund.getReason(),
                                                                refundItem.getData().getShopId(),
                                                                refundItem.getData().getRefundFee(),
                                                                refundItem.getData().getCompanyName(),
                                                                refundItem.getData().getSid(),
                                                                refundItem.getData().getReason()));
        }
        return "redirect:/order/refund/" + refundNo + ".sc";
    }

    /**
     * 取消退款
     *
     * @since 1.0 Created by lipangeng on 16/4/14 上午8:09. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "refund/{refundNo}/cancelRefund", method = RequestMethod.GET)
    @LoginValidate
    public String cancelRefund(@PathVariable("refundNo") String refundNo, RedirectAttributes redirectAttributes) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        OrderRefundVo orderRefund = orderRefundService.getRefundItem(loginUserDetails, refundNo);
        // 判断该退款单是否存在
        if (orderRefund == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "退款信息不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_REFUNDS_URL;
        }
        // 获取订单信息以及子订单信息
        OrderVo userOrder = orderService.getUserOrder(loginUserDetails.getUserId(), orderRefund.getOrderId());
        OrderDetailVo orderDetail = findOrderDetail(userOrder, orderRefund.getOrderDetailId());
        // 如果订单或者子订单信息为空,则返回退款退货列表
        if (userOrder == null || orderDetail == null) {
            redirectAttributes.addFlashAttribute("errorMsg", "订单不存在");
            return "redirect:" + WfxConstant.WFX_ORDER_MY_REFUNDS_URL;
        }

        // 判断该退款单是否可取消
        if (! orderRefund.isCanCancel()) {
            redirectAttributes.addFlashAttribute("errorMsg", "退款单不可取消");
            return "redirect:/order/refund/" + refundNo + ".sc";
        }
        Result cancelRefund = orderRefundService.cancelRefundItem(loginUserDetails, refundNo);
        if (cancelRefund.hasError()) {
            redirectAttributes.addFlashAttribute("errorMsg", "取消退款单失败:" + cancelRefund.getMessage());
        }
        return "redirect:/order/refund/" + refundNo + ".sc";
    }

    /**
     * 检查退款金额是否正确
     *
     * @since 1.0 Created by lipangeng on 16/5/4 上午11:40. Email:lipg@outlook.com.
     */
    private Result checkRefundFee(OrderDetailVo orderDetail, OrderRefundVo orderRefund, Double refundFee, Integer num, String refundType) {
        if (orderDetail == null || orderRefund == null || orderDetail.getPayment() == null || refundFee == null || num == null ||
            refundType == null) {
            return Result.create(false, "退款金额有误,缺少必要参数.");
        }
        try {
            switch (RefundTypeEnum.valueOf(refundType)) {
                case ONLY_REFUND:
                    if (! Objects.equal(orderRefund.getCanReturnFee(), refundFee)) {
                        return Result.create(false, "退款金额与付款金额不符");
                    }
                    break;
                case DELIVERD_REFUND:
                    if (orderRefund.getCanReturnFee() < refundFee) {
                        return Result.create(false, "退款金额不能超过付款金额");
                    }
                    break;
                // 退货退款,能设置商品数量,这个最大可退金额怎么计算的
                case REJECTED_REFUND:
                    BigDecimal fee = new BigDecimal(orderDetail.getWfxPrice());
                    fee = fee.multiply(new BigDecimal(num));
                    if (! Objects.equal(fee.doubleValue(), refundFee) || orderRefund.getCanReturnFee() < refundFee ||
                        orderRefund.getCanReturnNum() < num) {
                        return Result.create(false, "退款金额或数量无效,最大可退数量为:" + orderRefund.getCanReturnNum());
                    }
                    break;
                default:
            }
        } catch (Exception e) {
            logger.error(refundType + "不是有效的退款类型", e);
            return Result.create(false, refundType + "不是有效的退款类型");
        }
        return Result.create();
    }

    /**
     * 从订单中查找子订单信息
     *
     * @since 1.0 Created by lipangeng on 16/5/4 下午12:07. Email:lipg@outlook.com.
     */
    private OrderDetailVo findOrderDetail(OrderVo order, String orderDetailId) {
        OrderDetailVo orderDetail = null;
        if (order != null && order.getOrderDetails() != null) {
            for (OrderDetailVo orderDetailVo : order.getOrderDetails()) {
                if (Objects.equal(orderDetailId, orderDetailVo.getId())) {
                    orderDetail = orderDetailVo;
                    break;
                }
            }
        }
        return orderDetail;
    }

    /**
     * 修改退款单时处理可退金额和可退数量
     *
     * @since 1.0 Created by lipangeng on 16/5/5 上午11:02. Email:lipg@outlook.com.
     */
    private void configEditReturnFeeAndNum(OrderRefundVo orderRefund) {
        if (orderRefund.getRefundFee() == null || orderRefund.getProNum() == null) {
            return;
        }
        Double fee = orderRefund.getCanReturnFee() == null ? 0d : orderRefund.getCanReturnFee();
        Integer num = orderRefund.getCanReturnNum() == null ? 0 : orderRefund.getCanReturnNum();
        orderRefund.setCanReturnNum(num + orderRefund.getProNum());
        orderRefund.setCanReturnFee(new BigDecimal(fee).add(new BigDecimal(orderRefund.getRefundFee())).doubleValue());
    }
}
