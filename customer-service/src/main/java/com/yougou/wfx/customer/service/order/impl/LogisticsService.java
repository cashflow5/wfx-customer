package com.yougou.wfx.customer.service.order.impl;

import com.google.common.collect.Lists;
import com.yougou.wfx.customer.model.order.LogisticsVo;
import com.yougou.wfx.customer.service.order.ILogisticsService;
import com.yougou.wfx.order.api.front.IOrderFrontApi;
import com.yougou.wfx.order.dto.output.ConsignInfosOutPutDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物流信息的实现类
 *
 * @author lipangeng, Email:lipg@outlook.comw
 * @version 1.0 on 16/4/11 下午3:37
 * @since 1.0 Created by lipangeng on 16/4/11 下午3:37. Email:lipg@outlook.com.
 */
@Service
public class LogisticsService implements ILogisticsService {
    private static final Logger logger = LoggerFactory.getLogger(LogisticsService.class);
    @Autowired
    private IOrderFrontApi orderFrontApi;

    /**
     * 更具订单ID获取订单的物流信息
     *
     * @param orderId
     *         订单编号
     *
     * @since 1.0 Created by lipangeng on 16/4/11 下午3:37. Email:lipg@outlook.com.
     */
    @Override
    public List<LogisticsVo> getLogisticsByOrder(String orderId) {
        List<ConsignInfosOutPutDto> consignInfos = orderFrontApi.getConsignInfosByOrderId(orderId);
        List<LogisticsVo> logisticsVos = Lists.newArrayList();
        if (consignInfos != null) {
            for (ConsignInfosOutPutDto consignInfo : consignInfos) {
                logisticsVos.add(LogisticsVo.valueOf(consignInfo));
            }
        }
        return logisticsVos;
    }
}
