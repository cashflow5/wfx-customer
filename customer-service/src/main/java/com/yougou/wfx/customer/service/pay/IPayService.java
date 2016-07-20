package com.yougou.wfx.customer.service.pay;

import com.yougou.pay.vo.PayReqVo;
import com.yougou.pay.vo.PayResVo;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.order.dto.output.AfterPayedCallBackDto;

/**
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/17 下午5:37
 * @since 1.0 Created by lipangeng on 16/5/17 下午5:37. Email:lipg@outlook.com.
 */
public interface IPayService {
    /**
     * 支付回调信息
     *
     * @since 1.0 Created by lipangeng on 16/4/22 下午3:55. Email:lipg@outlook.com.
     */
    public Result callbackAfterPayed();


    /**
     * 创建支付订单,获取支付信息
     *
     * @param req
     *         支付请求信息
     *
     * @since 1.0 Created by lipangeng on 16/5/17 下午6:00. Email:lipg@outlook.com.
     */
    public Result<PayResVo> createOrder(PayReqVo req);

    /**
     * 支付的同步通知
     *
     * @since 1.0 Created by lipangeng on 16/5/18 下午5:09. Email:lipg@outlook.com.
     */
    Result<AfterPayedCallBackDto> syncNotify();
}
