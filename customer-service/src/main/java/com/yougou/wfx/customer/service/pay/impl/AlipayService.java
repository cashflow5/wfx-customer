package com.yougou.wfx.customer.service.pay.impl;

import com.yougou.pay.api.IPayApi;
import com.yougou.pay.util.PayClientUtil;
import com.yougou.pay.vo.PayReqVo;
import com.yougou.pay.vo.PayResVo;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.utils.JacksonUtils;
import com.yougou.wfx.customer.common.utils.LogUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.pay.alipay.AlipayCreateOrderReqVo;
import com.yougou.wfx.customer.service.pay.IAlipayService;
import com.yougou.wfx.customer.service.pay.IPayService;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.ResultCodeEnum;
import com.yougou.wfx.order.api.front.IOrderFrontApi;
import com.yougou.wfx.order.dto.output.AfterPayedCallBackDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 支付宝支付
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/17 下午4:30
 * @since 1.0 Created by lipangeng on 16/5/17 下午4:30. Email:lipg@outlook.com.
 */
@Service
public class AlipayService implements IAlipayService {
    private static final Logger logger = LoggerFactory.getLogger(AlipayService.class);
    @Autowired
    private IPayApi payApi;
    @Autowired
    private IPayService payService;
    @Autowired
    private IOrderFrontApi orderFrontApi;

    /**
     * 创建支付宝订单
     *
     * @param req
     *         创建订单的参数
     *
     * @since 1.0 Created by lipangeng on 16/5/17 下午4:32. Email:lipg@outlook.com.
     */
    @Override
    public Result<PayResVo> createOrder(AlipayCreateOrderReqVo req) {
        if (req == null) {
            return Result.create(false, 500, "无法创建订单,参数无效.");
        }
        PayReqVo payReqVo = req.toPayReqDto();
        Result<PayResVo> createResult = payService.createOrder(payReqVo);
        // 判断创建订单是否有误
        if (createResult.hasError()) {
            return Result.create(false, createResult.getCode(), createResult.getMessage());
        }
        return Result.create(true, "创建支付宝订单成功", createResult.getData());
    }
}
