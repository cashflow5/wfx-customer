package com.yougou.wfx.customer.order;

import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.order.OrderSearchVo;
import com.yougou.wfx.customer.service.order.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/7 下午4:01
 * @since 1.0 Created by lipangeng on 16/4/7 下午4:01. Email:lipg@outlook.com.
 */
@Controller
@RequestMapping(value = "order/myorder")
public class MyOrderController {
    private static final Logger logger = LoggerFactory.getLogger(MyOrderController.class);
    @Autowired
    private IOrderService orderService;

    /**
     * 我的订单列表
     *
     * @since 1.0 Created by lipangeng on 16/4/7 下午4:04. Email:lipg@outlook.com.
     */
    @RequestMapping("")
    @LoginValidate
    public String myorder(Model model, Page page, OrderSearchVo orderSearchVo) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        model.addAttribute("orders", orderService.getUserOrders(loginUserDetails.getUserId(), page, orderSearchVo));
        model.addAttribute("orderSearchVo", orderSearchVo);
        return "order/myorder/my-order";
    }

    /**
     * ajax获取订单信息
     *
     * @since 1.0 Created by lipangeng on 16/4/11 上午9:38. Email:lipg@outlook.com.
     */
    @RequestMapping("ajax")
    @LoginValidate
    public String orderAjax(Model model, Page page, OrderSearchVo orderSearchVo) {
        myorder(model, page, orderSearchVo);
        return "order/myorder/my-order-ajax";
    }
}
