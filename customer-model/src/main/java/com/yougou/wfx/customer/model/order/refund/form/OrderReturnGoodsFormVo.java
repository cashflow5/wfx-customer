package com.yougou.wfx.customer.model.order.refund.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/12 下午2:25
 * @since 1.0 Created by lipangeng on 16/4/12 下午2:25. Email:lipg@outlook.com.
 */
public class OrderReturnGoodsFormVo implements Serializable{
    /** 退款类型 */
    @NotBlank(message = "退款类型不能为空")
    private String refundType;
    /** 退款原因 */
    @NotBlank(message = "退款原因不能为空")
    private String reason;
    /** 退款金额 */
    @NotNull(message = "退款金额不能为空")
    private Double refundFee;
    /** 物流公司 */
    @NotBlank(message = "物流公司不能为空")
    private String express;
    /** 物流编号 */
    @NotBlank(message = "物流编号不能为空")
    private String expressNo;
    /** 退款说明 */
    private String description;
    /** 退款商品数量 */
    private Integer proNum;

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

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public Integer getProNum() {
        return proNum;
    }

    public void setProNum(Integer proNum) {
        this.proNum = proNum;
    }
}
