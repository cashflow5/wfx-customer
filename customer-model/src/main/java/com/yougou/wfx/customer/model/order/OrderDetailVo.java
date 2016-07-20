package com.yougou.wfx.customer.model.order;

import com.google.common.base.Strings;
import com.yougou.wfx.enums.RefundStatusEnum;
import com.yougou.wfx.order.dto.output.OrderDetailCommDto;
import com.yougou.wfx.order.dto.output.OrderDetailDto;

/**
 * 订单中商品信息
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/7 下午4:22
 * @since 1.0 Created by lipangeng on 16/4/7 下午4:22. Email:lipg@outlook.com.
 */
public class OrderDetailVo {
    /** 主键 */
    private String id;
    /** 子订单编号 */
    private String wfxOrderDetailNo;
    /** 商品id */
    private String commodityId;
    /** 商品图片 */
    private String picSmall;
    /** 唯一编号,货品id */
    private String prodId;
    /** 商品名称 */
    private String prodName;
    /** 商品规格 颜色,尺码等 */
    private String prodSpec;
    /** 分销价 */
    private Double wfxPrice;
    /** 子订单实付金额 */
    private Double payment;
    /** 购买数量 */
    private Integer num;
    /** 是否显示退货退款按钮 */
    private boolean canRefund = false;
    /** 退款状态,冗余,暂未使用 */
    private String refundStatus;
    /** 退款状态的中文说明 */
    private String refundStatusDesc;

    public boolean isCanRefund() {
        return canRefund;
    }

    public void setCanRefund(boolean canRefund) {
        this.canRefund = canRefund;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdSpec() {
        return prodSpec;
    }

    public void setProdSpec(String prodSpec) {
        this.prodSpec = prodSpec;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getRefundStatusDesc() {
        return refundStatusDesc;
    }

    public void setRefundStatusDesc(String refundStatusDesc) {
        this.refundStatusDesc = refundStatusDesc;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getWfxOrderDetailNo() {
        return wfxOrderDetailNo;
    }

    public void setWfxOrderDetailNo(String wfxOrderDetailNo) {
        this.wfxOrderDetailNo = wfxOrderDetailNo;
    }

    public String getPicSmall() {
        return picSmall;
    }

    public void setPicSmall(String picSmall) {
        this.picSmall = picSmall;
    }

    public Double getWfxPrice() {
        return wfxPrice;
    }

    public void setWfxPrice(Double wfxPrice) {
        this.wfxPrice = wfxPrice;
    }

    /**
     * 通过接口的dto数据,构建本地vo对象
     *
     * @since 1.0 Created by lipangeng on 16/4/29 上午11:41. Email:lipg@outlook.com.
     */
    public static OrderDetailVo valueOf(OrderDetailCommDto dto) {
        if (dto == null) {
            return null;
        }
        OrderDetailVo orderDetail = new OrderDetailVo();
        orderDetail.setId(dto.getOrderDetailId());
        orderDetail.setWfxOrderDetailNo(dto.getWfxOrderDetailNo());
        orderDetail.setProdId(dto.getProdId());
        orderDetail.setCommodityId(dto.getCommodityId());
        orderDetail.setPicSmall(dto.getPicSmall());
        orderDetail.setProdName(dto.getProdName());
        orderDetail.setProdSpec(dto.getProdSpec());
        orderDetail.setWfxPrice(dto.getWfxPrice());
        orderDetail.setNum(dto.getNum());
        configRefundStatus(dto.getRefundShowStatus(), orderDetail);
        return orderDetail;
    }

    /**
     * 通过接口的dto数据,构建本地vo对象
     *
     * @since 1.0 Created by lipangeng on 16/4/29 上午11:41. Email:lipg@outlook.com.
     */
    public static OrderDetailVo valueOf(OrderDetailDto dto) {
        if (dto == null) {
            return null;
        }
        OrderDetailVo orderDetail = new OrderDetailVo();
        orderDetail.setId(dto.getId());
        orderDetail.setWfxOrderDetailNo(dto.getWfxOrderDetailNo());
        orderDetail.setProdId(dto.getProdId());
        orderDetail.setCommodityId(dto.getCommodityId());
        orderDetail.setPicSmall(dto.getPicSmall());
        orderDetail.setProdName(dto.getProdName());
        orderDetail.setProdSpec(dto.getProdSpec());
        orderDetail.setWfxPrice(dto.getPrice());
        orderDetail.setPayment(dto.getPayment());
        orderDetail.setNum(dto.getNum());
        orderDetail.setRefundStatus(dto.getRefundStatus());
        orderDetail.setRefundStatusDesc(RefundStatusEnum.getDescByKey(dto.getRefundStatus()));
        configRefundStatus(dto.getRefundShowStatus(), orderDetail);
        return orderDetail;
    }

    /**
     * 配置退款显示状态
     *
     * @since 1.0 Created by lipangeng on 16/5/3 上午8:17. Email:lipg@outlook.com.
     */
    private static void configRefundStatus(String showStatus, OrderDetailVo orderDetail) {
        if (! Strings.isNullOrEmpty(showStatus)) {
            switch (showStatus) {
                case "1":
                    orderDetail.setCanRefund(true);
                    break;
                case "2":
                    orderDetail.setCanRefund(false);
                    orderDetail.setRefundStatusDesc("退款中");
                    break;
                case "3":
                    orderDetail.setCanRefund(false);
                    orderDetail.setRefundStatusDesc("已退款");
                    break;
                case "4":
                    orderDetail.setCanRefund(false);
                default:
            }
        }
    }
}
