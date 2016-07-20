package com.yougou.wfx.customer.service.pay;

import com.yougou.pay.vo.PayResVo;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.pay.alipay.AlipayCreateOrderReqVo;

/**
 * 支付宝支付
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/17 下午4:29
 * @since 1.0 Created by lipangeng on 16/5/17 下午4:29. Email:lipg@outlook.com.
 */
public interface IAlipayService {
    /**
     * 创建支付宝订单
     *
     * @param req
     *         创建订单的参数
     *
     * @since 1.0 Created by lipangeng on 16/5/17 下午4:32. Email:lipg@outlook.com.
     */
    Result<PayResVo> createOrder(AlipayCreateOrderReqVo req);

}
