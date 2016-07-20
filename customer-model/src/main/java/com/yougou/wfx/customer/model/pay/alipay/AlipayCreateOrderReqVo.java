package com.yougou.wfx.customer.model.pay.alipay;

import com.yougou.pay.constant.TradeTypeConstant;
import com.yougou.pay.vo.PayReqVo;

/**
 * 支付宝支付的订单创建
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/17 下午4:31
 * @since 1.0 Created by lipangeng on 16/5/17 下午4:31. Email:lipg@outlook.com.
 */
public class AlipayCreateOrderReqVo {
    private String orderNo;
    private String payment;
    private String bankNo;
    private String tradeNo;
    private String userId;
    private String userName;
    private String clientAddress;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }

    /**
     * 构建支付请求的dto
     *
     * @since 1.0 Created by lipangeng on 16/5/17 下午6:35. Email:lipg@outlook.com.
     */
    public PayReqVo toPayReqDto() {
        PayReqVo payReqVo = new PayReqVo();
        payReqVo.setBankNo(this.getBankNo());//支付方式（内部定义)
        payReqVo.setTradeOutTime("");//交易失效时间
        payReqVo.setTradeNo(this.getTradeNo());//创建交易，返回的交易号
        payReqVo.setTradeAmount(this.getPayment());//交易金额
        payReqVo.setBizNo(this.getOrderNo());//订单号
        payReqVo.setBizType(TradeTypeConstant.TRADE_BIZ_TYPE_WFX_ORDER);
        payReqVo.setCustomerNo(this.getUserId());
        payReqVo.setCustomerName(this.getUserName());
        payReqVo.setClientAddress(this.getClientAddress());
        return payReqVo;
    }
}
