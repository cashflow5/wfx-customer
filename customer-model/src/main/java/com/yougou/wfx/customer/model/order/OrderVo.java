package com.yougou.wfx.customer.model.order;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.yougou.wfx.enums.OrderStatusEnum;
import com.yougou.wfx.order.dto.output.OrderDetailCommDto;
import com.yougou.wfx.order.dto.output.OrderDetailDto;
import com.yougou.wfx.order.dto.output.OrderInfoDto;
import com.yougou.wfx.order.dto.output.OrderOutputDto;

import java.util.Date;
import java.util.List;

/**
 * Created by zhang.sj on 2016/3/22.
 */
public class OrderVo {
    /** 主键id */
    private String id;
    /** 订单号 */
    private String wfxOrderNo;
    /** 分销商id */
    private String sellerId;
    /** 店铺id */
    private String shopId;
    /** 店铺名称 */
    private String shopName;
    /** 店铺编码 */
    private String shopCode;
    /** supplierId */
    private String supplierId;
    /** 支付方式 */
    private String payType;
    /** 付款方式的中文说明 */
    private String payTypeDesc;
    /** 付款时间 */
    private Date payTime;
    /** 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分 */
    private Double payment;
    /** 商品购买数量。取值范围：大于零的整数 */
    private Integer num;
    /** 商品金额 */
    private Double totalFee;
    /** 系统优惠金额 */
    private Double discountFee;
    /** 邮费。 */
    private Double postFee;
    /** 订单状态。 */
    private String status;
    /** 订单状态的中文名 */
    private String statusDesc;
    /** 买家id */
    private String buyerId;
    /** 买家账户 */
    private String buyerAccount;
    /** 买家留言 */
    private String buyerMessage;
    /** 买家昵称 */
    private String buyerNick;
    /** 收货人的姓名 */
    private String receiverName;
    /** 收货人的手机号码 */
    private String receiverMobile;
    /** 收货人的手机号码 */
    private String receiverMobileEncode;
    /** 收货人电话 */
    private String receiverPhone;
    /** 收货人身份证号码 */
    private String idCardNo;
    /** 收货人所在地址 */
    private String receiverState;
    /** 收货人的所在城市 */
    private String receiverCity;
    /** 收货人的所在地区 */
    private String receiverDistrict;
    /** 收货人的详细地址 */
    private String receiverAddress;
    /** 收货人的邮编 */
    private String receiverZip;
    /** 创建交易时的物流方式（交易完成前，物流方式有可能改变，但系统里的这个字段一直不变）。可选值：ems, express, post, free, virtual。 */
    private String shippingType;
    /** 收货时间段 */
    private Integer receiveTimeRange;
    /** 订单创建时间。 */
    private Date createdTime;
    /** 订单修改时间。 */
    private Date updateTime;
    /** 确认收货时间 */
    private Date confirmTime;
    /** 订单关闭时间 */
    private Date closeTime;
    /** 人工退款标志 */
    private Integer manualRefundFlag;
    /** 是否可以退款 */
    private boolean canRefund = false;
    /** 是否可以取消订单 */
    private boolean canCancel = false;
    /** 是否可以支付 */
    private boolean canPay = false;
    /** 是否可以确认收货 */
    private boolean canConfirm = false;
    /**是否可以重新购买*/
    private boolean canBuyAgin = false;
    /** 是否可以查看物流信息 */
    private boolean canShowLogistics = false;
    /** 订单中的商品列表 */
    private List<OrderDetailVo> orderDetails = Lists.newArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWfxOrderNo() {
        return wfxOrderNo;
    }

    public void setWfxOrderNo(String wfxOrderNo) {
        this.wfxOrderNo = wfxOrderNo;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
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

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Double getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Double discountFee) {
        this.discountFee = discountFee;
    }

    public Double getPostFee() {
        return postFee;
    }

    public void setPostFee(Double postFee) {
        this.postFee = postFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverDistrict() {
        return receiverDistrict;
    }

    public void setReceiverDistrict(String receiverDistrict) {
        this.receiverDistrict = receiverDistrict;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public Integer getReceiveTimeRange() {
        return receiveTimeRange;
    }

    public void setReceiveTimeRange(Integer receiveTimeRange) {
        this.receiveTimeRange = receiveTimeRange;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerAccount() {
        return buyerAccount;
    }

    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public Integer getManualRefundFlag() {
        return manualRefundFlag;
    }

    public void setManualRefundFlag(Integer manualRefundFlag) {
        this.manualRefundFlag = manualRefundFlag;
    }

    public List<OrderDetailVo> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailVo> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getReceiverMobileEncode() {
        return receiverMobileEncode;
    }

    public void setReceiverMobileEncode(String receiverMobileEncode) {
        this.receiverMobileEncode = receiverMobileEncode;
    }

    public String getReceiverState() {
        return receiverState;
    }

    public void setReceiverState(String receiverState) {
        this.receiverState = receiverState;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public boolean isCanRefund() {
        return canRefund;
    }

    public void setCanRefund(boolean canRefund) {
        this.canRefund = canRefund;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    public boolean isCanConfirm() {
        return canConfirm;
    }

    public void setCanConfirm(boolean canConfirm) {
        this.canConfirm = canConfirm;
    }

    public boolean isCanBuyAgin() {
		return canBuyAgin;
	}

	public void setCanBuyAgin(boolean canBuyAgin) {
		this.canBuyAgin = canBuyAgin;
	}

	public boolean isCanShowLogistics() {
        return canShowLogistics;
    }

    public void setCanShowLogistics(boolean canShowLogistics) {
        this.canShowLogistics = canShowLogistics;
    }

    public boolean isCanPay() {
        return canPay;
    }

    public void setCanPay(boolean canPay) {
        this.canPay = canPay;
    }

    public String getPayTypeDesc() {
        return payTypeDesc;
    }

    public void setPayTypeDesc(String payTypeDesc) {
        this.payTypeDesc = payTypeDesc;
    }

    /**
     * 从OrderOutputDto转换为本地vo
     *
     * @since 1.0 Created by lipangeng on 16/4/29 上午11:46. Email:lipg@outlook.com.
     */
    public static OrderVo valueOf(OrderOutputDto dto) {
        if (dto == null) {
            return null;
        }
        OrderVo order = new OrderVo();
        order.setId(dto.getId());
        order.setWfxOrderNo(dto.getWfxOrderNo());
        order.setSellerId(dto.getSellerId());
        order.setShopId(dto.getShopId());
        order.setShopName(dto.getShopName());
        order.setSupplierId(dto.getSupplierId());
        order.setPayType(dto.getPayType());
        order.setPayTime(dto.getPayTime());
        order.setPayment(dto.getPayment());
        order.setNum(dto.getNum());
        order.setTotalFee(dto.getTotalFee());
        order.setDiscountFee(dto.getDiscountFee());
        order.setPostFee(dto.getPostFee());
        order.setStatus(dto.getStatus());
        order.setStatusDesc(OrderStatusEnum.getDescByKey(dto.getStatus()));
        order.setBuyerId(dto.getBuyerId());
        order.setBuyerAccount(dto.getBuyerAccount());
        order.setBuyerMessage(dto.getBuyerMessage());
        order.setBuyerNick(dto.getBuyerNick());
        order.setReceiverName(dto.getReceiverName());
        order.setReceiverMobile(dto.getReceiverMobile());
        order.setReceiverMobileEncode(shortPhoneNum(dto.getReceiverPhone()));
        order.setReceiverPhone(dto.getReceiverPhone());
        order.setIdCardNo(dto.getIdCardNo());
        order.setReceiverState(dto.getReceiverState());
        order.setReceiverCity(dto.getReceiverCity());
        order.setReceiverDistrict(dto.getReceiverDistrict());
        order.setReceiverAddress(dto.getReceiverAddress());
        order.setReceiverZip(dto.getReceiverZip());
        order.setShippingType(dto.getShippingType());
        order.setReceiveTimeRange(dto.getReceiveTimeRange());
        order.setCreatedTime(dto.getCreatedTime());
        order.setUpdateTime(dto.getUpdateTime());
        order.setConfirmTime(dto.getConfirmTime());
        order.setCloseTime(dto.getCloseTime());
        order.setManualRefundFlag(dto.getManualRefundFlag());
        order.setShopCode(dto.getShopCode());
        if (dto.getCommList() != null) {
            for (OrderDetailCommDto orderDetailCommDto : dto.getCommList()) {
                order.getOrderDetails().add(OrderDetailVo.valueOf(orderDetailCommDto));
            }
        }
        configOrderStatus(order);
        configPayTypeDesc(order);
        return order;
    }

    public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	/**
     * 从OrderInfoDto转换为本地vo
     *
     * @since 1.0 Created by lipangeng on 16/4/29 下午1:53. Email:lipg@outlook.com.
     */
    public static OrderVo valueOf(OrderInfoDto dto) {
        if (dto == null) {
            return null;
        }
        OrderVo order = new OrderVo();
        order.setId(dto.getId());
        order.setWfxOrderNo(dto.getWfxOrderNo());
        order.setSellerId(dto.getSellerId());
        order.setShopId(dto.getShopId());
        order.setShopName(dto.getShopName());
        order.setSupplierId(dto.getSupplierId());
        order.setPayType(dto.getPayType());
        order.setPayTime(dto.getPayTime());
        order.setPayment(dto.getPayment());
        order.setNum(dto.getNum());
        order.setTotalFee(dto.getTotalFee());
        order.setDiscountFee(dto.getDiscountFee());
        order.setPostFee(dto.getPostFee());
        order.setStatus(dto.getStatus());
        order.setStatusDesc(OrderStatusEnum.getDescByKey(dto.getStatus()));
        order.setBuyerId(dto.getBuyerId());
        order.setBuyerAccount(dto.getBuyerAccount());
        order.setBuyerMessage(dto.getBuyerMessage());
        order.setBuyerNick(dto.getBuyerNick());
        order.setReceiverName(dto.getReceiverName());
        order.setReceiverMobile(dto.getReceiverMobile());
        order.setReceiverMobileEncode(shortPhoneNum(dto.getReceiverPhone()));
        order.setReceiverPhone(dto.getReceiverPhone());
        order.setIdCardNo(dto.getIdCardNo());
        order.setReceiverState(dto.getReceiverState());
        order.setReceiverCity(dto.getReceiverCity());
        order.setReceiverDistrict(dto.getReceiverDistrict());
        order.setReceiverAddress(dto.getReceiverAddress());
        order.setReceiverZip(dto.getReceiverZip());
        order.setShippingType(dto.getShippingType());
        order.setReceiveTimeRange(dto.getReceiveTimeRange());
        order.setCreatedTime(dto.getCreatedTime());
        order.setUpdateTime(dto.getUpdateTime());
        order.setConfirmTime(dto.getConfirmTime());
        order.setCloseTime(dto.getCloseTime());
        order.setManualRefundFlag(dto.getManualRefundFlag());
        order.setShopCode(dto.getShopCode());
        if (dto.getOrderDetailList() != null) {
            for (OrderDetailDto orderDetailDto : dto.getOrderDetailList()) {
                order.getOrderDetails().add(OrderDetailVo.valueOf(orderDetailDto));
            }
        }
        configOrderStatus(order);
        configPayTypeDesc(order);
        return order;
    }

    /**
     * 配置订单状态及可用操作
     *
     * @since 1.0 Created by lipangeng on 16/4/29 下午2:32. Email:lipg@outlook.com.
     */
    private static void configOrderStatus(OrderVo order) {
        if (order == null) {
            return;
        }
        OrderStatusEnum orderStatus = OrderStatusEnum.valueOf(order.getStatus());
        switch (orderStatus) {
            // 等待付款情况下可以取消订单,可以支付订单
            case WAIT_PAY:
                order.setCanCancel(true);
                order.setCanPay(true);
                break;
            // 等待发货的时候仅显示退货退款按钮
            case WAIT_DELIVER:
                break;
            // 部分发货的时候可以退款,跟踪物流
            case PART_DELIVERED:
                order.setCanShowLogistics(true);
                break;
            // 已发货商品可以退款,物流跟踪,确认收货
            case DELIVERED:
                order.setCanShowLogistics(true);
                order.setCanConfirm(true);
                break;
            // 交易成功的订单大于7天不可退款
            case TRADE_SUCCESS:
                break;
            // 交易关闭的订单可以点击重新购买
            case TRADE_CLOSED:
            	order.setCanBuyAgin(true);
                break;
            default:
        }
    }

    /**
     * 设置退款方式中文说明
     *
     * @since 1.0 Created by lipangeng on 16/5/3 上午10:14. Email:lipg@outlook.com.
     */
    private static void configPayTypeDesc(OrderVo order) {
        if (! Strings.isNullOrEmpty(order.getPayType())) {
            switch (order.getPayType()) {
                case "wechatpay":
                    order.setPayTypeDesc("微信支付");
                    break;
                case "alipay":
                    order.setPayTypeDesc("支付宝支付");
                    break;
                default:
            }
        }
    }

    /**
     * 手机号加密
     *
     * @since 1.0 Created by lipangeng on 16/4/29 上午11:55. Email:lipg@outlook.com.
     */
    private static String shortPhoneNum(String phoneNum) {
        if (Strings.isNullOrEmpty(phoneNum) || phoneNum.length() < 7) {
            return phoneNum;
        }
        return phoneNum.substring(0, 3) + "****" + phoneNum.substring(phoneNum.length() - 4);
    }
}
