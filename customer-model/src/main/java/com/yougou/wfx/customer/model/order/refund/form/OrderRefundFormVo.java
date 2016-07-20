package com.yougou.wfx.customer.model.order.refund.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 订单退款的Vo
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/12 上午11:20
 * @since 1.0 Created by lipangeng on 16/4/12 上午11:20. Email:lipg@outlook.com.
 */
public class OrderRefundFormVo implements Serializable{
    /** 退款类型 */
    @NotBlank(message = "退款类型不能为空")
    private String refundType;
    /** 退款原因 */
    @NotBlank(message = "退款原因不能为空")
    private String reason;
    /** 退款金额 */
    @NotNull(message = "退款金额不能为空")
    private Double refundFee;
    /** 退款说明 */
    private String description;

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Double refundFee) {
        this.refundFee = refundFee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
