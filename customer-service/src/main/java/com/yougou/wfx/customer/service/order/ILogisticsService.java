package com.yougou.wfx.customer.service.order;

import com.yougou.wfx.customer.model.order.LogisticsVo;

import java.util.List;

/**
 * 物流信息
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/11 下午3:33
 * @since 1.0 Created by lipangeng on 16/4/11 下午3:33. Email:lipg@outlook.com.
 */
public interface ILogisticsService {
    /**
     * 更具订单编号获取订单的物流信息
     *
     * @since 1.0 Created by lipangeng on 16/4/11 下午3:37. Email:lipg@outlook.com.
     */
    List<LogisticsVo> getLogisticsByOrder(String orderId);
}
