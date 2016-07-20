package com.yougou.wfx.customer.model.order.refund;

import com.google.common.base.Objects;
import com.yougou.wfx.aftersale.dto.input.OrderRefundInputDto;
import com.yougou.wfx.aftersale.dto.output.OrderAfterSaleDto;
import com.yougou.wfx.aftersale.dto.output.OrderRefundOutputDto;
import com.yougou.wfx.customer.model.order.OrderDetailVo;
import com.yougou.wfx.customer.model.order.OrderVo;
import com.yougou.wfx.customer.model.order.refund.form.OrderRefundFormVo;
import com.yougou.wfx.customer.model.order.refund.form.OrderReturnGoodsFormVo;
import com.yougou.wfx.enums.RefundStatusEnum;
import com.yougou.wfx.enums.RefundTypeEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/13 下午6:43
 * @since 1.0 Created by lipangeng on 16/4/13 下午6:43. Email:lipg@outlook.com.
 */
public class OrderRefundVo {
    /** 主键 */
    private String id;
    /** 退款单号 */
    private String refundNo;
    /** 微分销订单号 */
    private String wfxOrderNo;
    /** 订单号 */
    private String orderId;
    /** 子订单号 */
    private String orderDetailId;
    /** 商品编号 */
    private String prodId;
    /** 商品名称 */
    private String prodName;
    /** 买家的注册账号 */
    private String buyerLoginName;
    /** 买家的账户id */
    private String buyerLoginId;
    /** 分销商id */
    private String sellerId;
    /** 店铺ID */
    private String shopId;
    /** 店铺名称 */
    private String shopName;
    /** 店铺编码 */
	private String shopCode ;
    /** 交易总金额。 */
    private Double totalFee;
    /** 退还金额(退还给买家的金额)。 */
    private Double refundFee;
    /** 支付给卖家的金额(交易总金额-退还给买家的金额)。 */
    private Double payment;
    /** 退款状态 */
    private String status;
    /** 退款状态的中文说明 */
    private String statusDesc;
    /** 货物状态 */
    private String goodStatus;
    /** 物流公司名称 */
    private String companyName;
    /** 退货运单号 */
    private String sid;
    /** 退款原因 */
    private String reason;
    /** 退款说明 */
    private String description;
    /** 买家是否需要退货。 */
    private String hasGoodReturn;
    /** 退款对应的订单交易状态 */
    private String orderStatus;
    /** 退款单同步状态 */
    private String syncStatus;
    /** 同步描述’,用于保存返回退款单保存失败原因 */
    private String syncRemark;
    /** 纪录创建时间 */
    private Date createTime;
    /** 纪录修改时间 */
    private Date updateTime;
    /** 是否存在超时可选值:true(是)’,false(否) */
    private String existTimeout;
    /** 超时时间 */
    private Date timeout;
    /** 退款阶段(onsale,aftersale) */
    private String refundPhase;
    /** 退款版本号 */
    private String refundVersion;
    /** 退款扩展属性 */
    private String attribute;
    /** 退款类型 */
    private String refundType;
    /** 退款类型的中文说明 */
    private String refundTypeDesc;
    /** 退货数量 */
    private Integer proNum;
    /** 订单对应的发货供货商名称 */
    private String supplierName;
    /** 退款单退款的时间 */
    private Date payTime;
    /** 售中/售后 */
    private String isAfterReceived;
    /** 尺码编码 */
    private String sizeNo;
    /** 尺码 */
    private String sizeName;
    /** 商品小图url */
    private String picSmall;
    /** 商品名称 */
    private String commodityName;
    /** 颜色 */
    private String specName;
    /** 商品编码（微分销） */
    private String wfxCommodityNo;
    /** 购买数量 */
    private Integer buyNum;
    /** 分销价 */
    private Double wfxPrice;
    /** 商家（定义的货品）编码 */
    private String thirdPartyCode;
    /** 拒绝退款原因 */
    private String denyReason;
    /** 订单是否已经发货 */
    private boolean delivered= false;
    /** 可退商品数量 */
    private Integer canReturnNum;
    /** 可退金额 */
    private Double canReturnFee;
    /** 能否取消 */
    private boolean canCancel = false;
    /** 能否修改 */
    private boolean canEdit = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getBuyerLoginName() {
        return buyerLoginName;
    }

    public void setBuyerLoginName(String buyerLoginName) {
        this.buyerLoginName = buyerLoginName;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Double getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Double refundFee) {
        this.refundFee = refundFee;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGoodStatus() {
        return goodStatus;
    }

    public void setGoodStatus(String goodStatus) {
        this.goodStatus = goodStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHasGoodReturn() {
        return hasGoodReturn;
    }

    public void setHasGoodReturn(String hasGoodReturn) {
        this.hasGoodReturn = hasGoodReturn;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getSyncRemark() {
        return syncRemark;
    }

    public void setSyncRemark(String syncRemark) {
        this.syncRemark = syncRemark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getExistTimeout() {
        return existTimeout;
    }

    public void setExistTimeout(String existTimeout) {
        this.existTimeout = existTimeout;
    }

    public Date getTimeout() {
        return timeout;
    }

    public void setTimeout(Date timeout) {
        this.timeout = timeout;
    }

    public String getRefundPhase() {
        return refundPhase;
    }

    public void setRefundPhase(String refundPhase) {
        this.refundPhase = refundPhase;
    }

    public String getRefundVersion() {
        return refundVersion;
    }

    public void setRefundVersion(String refundVersion) {
        this.refundVersion = refundVersion;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public Integer getProNum() {
        return proNum;
    }

    public void setProNum(Integer proNum) {
        this.proNum = proNum;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getIsAfterReceived() {
        return isAfterReceived;
    }

    public void setIsAfterReceived(String isAfterReceived) {
        this.isAfterReceived = isAfterReceived;
    }

    public String getSizeNo() {
        return sizeNo;
    }

    public void setSizeNo(String sizeNo) {
        this.sizeNo = sizeNo;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getPicSmall() {
        return picSmall;
    }

    public void setPicSmall(String picSmall) {
        this.picSmall = picSmall;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getWfxCommodityNo() {
        return wfxCommodityNo;
    }

    public void setWfxCommodityNo(String wfxCommodityNo) {
        this.wfxCommodityNo = wfxCommodityNo;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Double getWfxPrice() {
        return wfxPrice;
    }

    public void setWfxPrice(Double wfxPrice) {
        this.wfxPrice = wfxPrice;
    }

    public String getThirdPartyCode() {
        return thirdPartyCode;
    }

    public void setThirdPartyCode(String thirdPartyCode) {
        this.thirdPartyCode = thirdPartyCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getRefundTypeDesc() {
        return refundTypeDesc;
    }

    public void setRefundTypeDesc(String refundTypeDesc) {
        this.refundTypeDesc = refundTypeDesc;
    }

    public String getWfxOrderNo() {
        return wfxOrderNo;
    }

    public void setWfxOrderNo(String wfxOrderNo) {
        this.wfxOrderNo = wfxOrderNo;
    }

    public String getDenyReason() {
        return denyReason;
    }

    public void setDenyReason(String denyReason) {
        this.denyReason = denyReason;
    }

    public Integer getCanReturnNum() {
        return canReturnNum;
    }

    public void setCanReturnNum(Integer canReturnNum) {
        this.canReturnNum = canReturnNum;
    }

    public Double getCanReturnFee() {
        return canReturnFee;
    }

    public void setCanReturnFee(Double canReturnFee) {
        this.canReturnFee = canReturnFee;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getBuyerLoginId() {
        return buyerLoginId;
    }

    public void setBuyerLoginId(String buyerLoginId) {
        this.buyerLoginId = buyerLoginId;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    /**
     * OrderAfterSaleDto转换为本地vo
     *
     * @since 1.0 Created by lipangeng on 16/4/29 下午5:09. Email:lipg@outlook.com.
     */
    public static OrderRefundVo valueOf(OrderAfterSaleDto dto) {
        if (dto == null) {
            return null;
        }
        OrderRefundVo orderRefund = new OrderRefundVo();
        orderRefund.setId(dto.getRefundId());
        orderRefund.setOrderId(dto.getOrderId());
        orderRefund.setOrderDetailId(dto.getOrderDetailId());
        orderRefund.setShopId(dto.getShopId());
        orderRefund.setShopName(dto.getShopName());
        orderRefund.setStatus(dto.getStatus());
        orderRefund.setStatusDesc(RefundStatusEnum.getDescByKey(dto.getStatus()));
        orderRefund.setProdId(dto.getProdId());
        orderRefund.setProdName(dto.getProdName());
        orderRefund.setSpecName(dto.getProdSpec());
        orderRefund.setPicSmall(dto.getSmallPic());
        orderRefund.setTotalFee(dto.getTradeFee());
        orderRefund.setRefundFee(dto.getRefundFee());
        orderRefund.setRefundNo(dto.getRefundNo());
        orderRefund.setShopCode(dto.getShopCode());
        return orderRefund;
    }

    /**
     * OrderInfoDto转换为本地vo
     *
     * @since 1.0 Created by lipangeng on 16/4/29 下午6:00. Email:lipg@outlook.com.
     */
    public static OrderRefundVo valueOf(OrderRefundOutputDto dto) {
        if (dto == null) {
            return null;
        }
        OrderRefundVo orderRefund = new OrderRefundVo();
        orderRefund.setId(dto.getId());
        orderRefund.setRefundNo(dto.getRefundNo());
        orderRefund.setOrderId(dto.getOrderId());
        orderRefund.setOrderDetailId(dto.getOrderDetailId());
        orderRefund.setProdId(dto.getProdId());
        orderRefund.setProdName(dto.getProdName());
        orderRefund.setBuyerLoginName(dto.getBuyerLoginName());
        orderRefund.setSellerId(dto.getSellerId());
        orderRefund.setShopId(dto.getShopId());
        orderRefund.setShopName(dto.getShopName());
        orderRefund.setTotalFee(dto.getTotalFee());
        orderRefund.setRefundFee(dto.getRefundFee());
        orderRefund.setPayment(dto.getPayment());
        orderRefund.setStatus(dto.getStatus());
        orderRefund.setStatusDesc(RefundStatusEnum.getDescByKey(dto.getStatus()));
        orderRefund.setGoodStatus(dto.getGoodStatus());
        orderRefund.setCompanyName(dto.getCompanyName());
        orderRefund.setSid(dto.getSid());
        orderRefund.setReason(dto.getReason());
        orderRefund.setDescription(dto.getDescription());
        orderRefund.setHasGoodReturn(dto.getHasGoodReturn());
        orderRefund.setOrderStatus(dto.getOrderStatus());
        orderRefund.setSyncStatus(dto.getSyncStatus());
        orderRefund.setSyncRemark(dto.getSyncRemark());
        orderRefund.setCreateTime(dto.getCreateTime());
        orderRefund.setUpdateTime(dto.getUpdateTime());
        orderRefund.setExistTimeout(dto.getExistTimeout());
        orderRefund.setTimeout(dto.getTimeout());
        orderRefund.setRefundPhase(dto.getRefundPhase());
        orderRefund.setRefundVersion(dto.getRefundVersion());
        orderRefund.setAttribute(dto.getAttribute());
        orderRefund.setRefundType(dto.getRefundType());
        orderRefund.setRefundTypeDesc(RefundTypeEnum.getDescByKey(dto.getRefundType()));
        orderRefund.setProNum(dto.getProNum());
        orderRefund.setSupplierName(dto.getSupplierName());
        orderRefund.setPayTime(dto.getPayTime());
        orderRefund.setIsAfterReceived(dto.getIsAfterReceived());
        orderRefund.setSizeNo(dto.getSizeNo());
        orderRefund.setSizeName(dto.getSizeName());
        orderRefund.setPicSmall(dto.getPicSmall());
        orderRefund.setCommodityName(dto.getCommodityName());
        orderRefund.setSpecName(dto.getSpecName());
        orderRefund.setWfxCommodityNo(dto.getWfxOrderNo());
        orderRefund.setBuyNum(dto.getBuyNum());
        orderRefund.setWfxPrice(dto.getWfxPrice());
        orderRefund.setThirdPartyCode(dto.getThirdPartyCode());
        orderRefund.setDenyReason(dto.getDenyReason());
        orderRefund.setDelivered(dto.isDelivered());
        orderRefund.setCanReturnNum(dto.getCanReturnNum());
        orderRefund.setCanReturnFee(dto.getCanReturnFee());
        // 状态处理
        // 如果是正在申请退款和等待买家确认收货和买家拒绝退款的,则显示取消退款按钮
        if (Objects.equal(orderRefund.getStatus(), RefundStatusEnum.APPLYING.getKey()) ||
            Objects.equal(orderRefund.getStatus(), RefundStatusEnum.PENDING_DELIVERD.getKey()) ||
            Objects.equal(orderRefund.getStatus(), RefundStatusEnum.REJECT_REFUND.getKey())) {
            orderRefund.setCanCancel(true);
            // 如果是已经发货的订单,则显示修改订单按钮
            if (! Objects.equal(orderRefund.getRefundType(), RefundTypeEnum.ONLY_REFUND.getKey())) {
                orderRefund.setCanEdit(true);
            }
        }
        orderRefund.setShopCode(dto.getShopCode());
        return orderRefund;
    }

    /**
     * 更新orderRefund信息
     *
     * @since 1.0 Created by lipangeng on 16/5/3 下午9:19. Email:lipg@outlook.com.
     */
    public OrderRefundVo update(OrderReturnGoodsFormVo form) {
        if (form == null) {
            return this;
        }
        this.setRefundFee(form.getRefundFee());
        this.setPayment(new BigDecimal(this.getTotalFee()).subtract(new BigDecimal(this.getRefundFee())).doubleValue());
        this.setReason(form.getReason());
        this.setDescription(form.getDescription());
        this.setProNum(form.getProNum());
        this.setRefundType(form.getRefundType());
        this.setCompanyName(form.getExpress());
        this.setSid(form.getExpressNo());
        this.setHasGoodReturn("true");
        //orderRefund.setSupplierName();
        return this;
    }

    /**
     * 更新orderRefund信息
     *
     * @since 1.0 Created by lipangeng on 16/5/3 下午9:19. Email:lipg@outlook.com.
     */
    public OrderRefundVo update(OrderRefundFormVo form) {
        if (form == null) {
            return this;
        }
        this.setRefundFee(form.getRefundFee());
        this.setPayment(0d);
        this.setReason(form.getReason());
        this.setDescription(form.getDescription());
        this.setRefundType(form.getRefundType());
        this.setHasGoodReturn("false");
        //orderRefund.setSupplierName();
        return this;
    }

    /**
     * 创建退款单的dto,默认是创建
     *
     * @since 1.0 Created by lipangeng on 16/5/3 下午10:33. Email:lipg@outlook.com.
     */
    public OrderRefundInputDto toOrderRefundInputDto(Boolean create) {
        create = create == null ? true : create;
        OrderRefundInputDto input = new OrderRefundInputDto();
        if (create) {
            input.setProdId(this.getProdId());
            input.setProdName(this.getProdName());
            input.setSellerId(this.getSellerId());
            input.setShopId(this.getShopId());
            input.setShopName(this.getShopName());
            input.setOrderStatus(this.getOrderStatus());
            input.setCreateTime(this.getCreateTime());
            input.setRefundPhase(this.getRefundPhase());
            input.setRefundVersion(this.getRefundVersion());
            input.setSupplierName(this.getSupplierName());
            input.setWfxOrderNo(this.getWfxOrderNo());
            input.setOrderDetailId(this.getOrderDetailId());
        }
        input.setId(this.getId()); //更新必填
        input.setOrderId(this.getOrderId()); //任何情况必填
        input.setBuyerLoginName(this.getBuyerLoginName()); //任何情况必填
        input.setTotalFee(this.getTotalFee());
        input.setRefundFee(this.getRefundFee());
        input.setPayment(this.getPayment());
        input.setStatus(this.getStatus());
        input.setReason(this.getReason());
        input.setDescription(this.getDescription());
        input.setCompanyName(this.getCompanyName());
        input.setSid(this.getSid());
        input.setProNum(this.getProNum());
        input.setRefundType(this.getRefundType());
        return input;
    }

    /**
     * 根据订单信息构建退货订单信息
     *
     * @since 1.0 Created by lipangeng on 16/5/3 下午10:13. Email:lipg@outlook.com.
     */
    public static OrderRefundVo valueOf(OrderVo order, OrderDetailVo orderDetail) {
        if (order == null || orderDetail == null) {
            return null;
        }
        OrderRefundVo orderRefund = new OrderRefundVo();
        orderRefund.setOrderId(order.getId());
        orderRefund.setOrderDetailId(orderDetail.getId());
        orderRefund.setProdId(orderDetail.getProdId());
        orderRefund.setProdName(orderDetail.getProdName());
        orderRefund.setBuyerLoginName(order.getBuyerAccount());
        orderRefund.setBuyerLoginId(order.getBuyerId());
        orderRefund.setSellerId(order.getSellerId());
        orderRefund.setShopId(order.getShopId());
        orderRefund.setShopName(order.getShopName());
        orderRefund.setTotalFee(orderDetail.getPayment());
        orderRefund.setStatus(RefundStatusEnum.APPLYING.getKey());
        orderRefund.setOrderStatus(order.getStatus());
        orderRefund.setCreateTime(new Date());
        orderRefund.setRefundPhase("aftersale");
        orderRefund.setRefundVersion("1");
        orderRefund.setProNum(0);
        orderRefund.setWfxOrderNo(order.getWfxOrderNo());
        return orderRefund;
    }

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
}
