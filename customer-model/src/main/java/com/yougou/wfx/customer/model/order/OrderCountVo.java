package com.yougou.wfx.customer.model.order;

import java.io.Serializable;

/**
 * 订单数量vo
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/30 下午5:27
 * @since 1.0 Created by lipangeng on 16/3/30 下午5:27. Email:lipg@outlook.com.
 */
public class OrderCountVo implements Serializable {
    /** 全部订单数量 */
    private Integer allOrder = 0;
    /** 已发货订单数量 */
    private Integer shipedOrder = 0;
    /** 待付款订单数量 */
    private Integer waitPay = 0;
    /** 待退货订单数量 */
    private Integer waitRefund = 0;
    /** 退货/退款订单 */
    private Integer refundOrder = 0;
    
    /** 待发货 */
    private Integer waitDeliver = 0;
    
    /** 待收货*/
    private Integer waitReceive = 0;

    public Integer getAllOrder() {
        return allOrder;
    }

    public void setAllOrder(Integer allOrder) {
        this.allOrder = allOrder;
    }

    public Integer getShipedOrder() {
        return shipedOrder;
    }

    public void setShipedOrder(Integer shipedOrder) {
        this.shipedOrder = shipedOrder;
    }

    public Integer getWaitPay() {
        return waitPay;
    }

    public void setWaitPay(Integer waitPay) {
        this.waitPay = waitPay;
    }

    public Integer getWaitRefund() {
        return waitRefund;
    }

    public void setWaitRefund(Integer waitRefund) {
        this.waitRefund = waitRefund;
    }

    public Integer getRefundOrder() {
        return refundOrder;
    }

    public void setRefundOrder(Integer refundOrder) {
        this.refundOrder = refundOrder;
    }

	public Integer getWaitDeliver() {
		return waitDeliver;
	}

	public void setWaitDeliver(Integer waitDeliver) {
		this.waitDeliver = waitDeliver;
	}

	public Integer getWaitReceive() {
		return waitReceive;
	}

	public void setWaitReceive(Integer waitReceive) {
		this.waitReceive = waitReceive;
	}
    
}
