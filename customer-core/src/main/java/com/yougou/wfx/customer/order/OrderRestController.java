package com.yougou.wfx.customer.order;

import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.service.order.IOrderService;
import com.yougou.wfx.customer.service.usercenter.IUserActionLogService;
import com.yougou.wfx.enums.OrderLogTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单的相关ajax接口
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/11 下午5:20
 * @since 1.0 Created by lipangeng on 16/4/11 下午5:20. Email:lipg@outlook.com.
 */
@RestController
@RequestMapping("rest/order")
public class OrderRestController {
    private static final Logger logger = LoggerFactory.getLogger(OrderRestController.class);
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IUserActionLogService userActionLogService;

    /**
     * 取消订单接口
     *
     * @since 1.0 Created by lipangeng on 16/4/12 下午4:35. Email:lipg@outlook.com.
     */
    @RequestMapping("{orderId}/cancel")
    @LoginValidate
    public Result cancelOrder(@PathVariable("orderId") String orderId) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        Result cancelOrderResult = orderService.cancelOrder(loginUserDetails.getUserId(), orderId);
        if (cancelOrderResult.isSuccess()) {
            userActionLogService.addUserActionLog(OrderLogTypeEnum.ORDER_OPT_LOG.getKey() + "", "取消订单");
        }
        return cancelOrderResult;
    }

    /**
     * 确认收货
     *
     * @since 1.0 Created by lipangeng on 16/4/13 上午9:42. Email:lipg@outlook.com.
     */
    @RequestMapping("{orderId}/confirmGoods")
    @LoginValidate
    public Result confirmOrderGoods(@PathVariable("orderId") String orderId) {
        SessionUserDetails loginUserDetails = SessionUtils.getLoginUserDetails();
        Result confirmOrderGoodsResult = orderService.confirmOrderGoods(loginUserDetails.getUserId(), orderId);
        if (confirmOrderGoodsResult.isSuccess()) {
            userActionLogService.addUserActionLog(OrderLogTypeEnum.ORDER_TURN_LOG.getKey() + "", "订单已确认收货");
        }
        return confirmOrderGoodsResult;
    }
}
