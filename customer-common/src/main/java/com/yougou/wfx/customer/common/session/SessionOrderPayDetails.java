package com.yougou.wfx.customer.common.session;

import java.io.Serializable;

/**
 * 订单支付信息
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/22 下午2:32
 * @since 1.0 Created by lipangeng on 16/4/22 下午2:32. Email:lipg@outlook.com.
 */
public class SessionOrderPayDetails implements Serializable {
    private static final long serialVersionUID = - 5524915062722015205L;
    private String bankNo;
    private String orderId;
    private String tradeNo;

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
