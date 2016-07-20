package com.yougou.wfx.customer.model.order.refund;

/**
 * 退款详情
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/14 上午9:56
 * @since 1.0 Created by lipangeng on 16/4/14 上午9:56. Email:lipg@outlook.com.
 */
public class OrderRefundDetailVo {
    /** 退款状态 */
    private String status;
    /** 退款类型 */
    private String type;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
