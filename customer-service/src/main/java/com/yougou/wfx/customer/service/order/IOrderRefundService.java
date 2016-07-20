package com.yougou.wfx.customer.service.order;

import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.order.refund.*;

import java.util.List;

/**
 * 订单退货的Service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/13 下午6:25
 * @since 1.0 Created by lipangeng on 16/4/13 下午6:25. Email:lipg@outlook.com.
 */
public interface IOrderRefundService {

    /**
     * 获取用户的退款退货订单列表
     *
     * @param userId
     *         用户Id
     * @param page
     *         分页参数
     * @param search
     *         查询参数
     *
     * @since 1.0 Created by lipangeng on 16/4/14 下午4:25. Email:lipg@outlook.com.
     */
    Page<OrderRefundVo> getUserOrderRefund(String userId, Page page, OrderRefundSearchVo search);

    /**
     * 获取订单的退货地址
     *
     * @param orderDetailId
     *         子订单id
     * @param supplierCode
     *         供应商编码
     *
     * @since 1.0 Created by lipangeng on 16/4/13 下午6:37. Email:lipg@outlook.com.
     */
    OrderRefundAddressVo getBackAddress(String orderDetailId, String supplierCode);

    /**
     * 获取退货退款单信息
     *
     * @param userName
     *         用户名
     * @param orderId
     *         订单id
     * @param orderDetailId
     *         商品id
     *
     * @since 1.0 Created by lipangeng on 16/4/13 下午6:46. Email:lipg@outlook.com.
     */
    OrderRefundVo getRefundItem(SessionUserDetails loginUserDetails, String orderId, String orderDetailId);

    /**
     * 获取退货退款单信息
     *
     * @param userName
     *         用户名
     * @param refundNo
     *         退款单id
     *
     * @since 1.0 Created by lipangeng on 16/4/13 下午6:46. Email:lipg@outlook.com.
     */
    OrderRefundVo getRefundItem(SessionUserDetails loginUserDetails, String refundNo);


    /**
     * 创建退款退货订单信息
     *
     * @param userId
     *         用户Id
     * @param orderRefund
     *         创建订单参数
     *
     * @since 1.0 Created by lipangeng on 16/4/14 下午2:16. Email:lipg@outlook.com.
     */
    Result<OrderRefundVo> createRefundItem(String userId, OrderRefundVo orderRefund);

    /**
     * 更新退款退货订单信息
     *
     * @param userName
     *         用户名
     * @param OrderRefund
     *         更新订单参数
     *
     * @since 1.0 Created by lipangeng on 16/4/14 下午2:16. Email:lipg@outlook.com.
     */
    Result<OrderRefundVo> updateRefundItem(String userName, OrderRefundVo OrderRefund);

    /**
     * 取消退款退货订单信息
     *
     * @param userName
     *         用户名
     * @param refundNo
     *         退款单编号
     *
     * @since 1.0 Created by lipangeng on 16/4/14 下午2:16. Email:lipg@outlook.com.
     */
    Result cancelRefundItem( SessionUserDetails loginUserDetails, String refundNo);

    /**
     * 获取退货地址可选的物流公司
     *
     * @since 1.0 Created by lipangeng on 16/4/14 下午5:48. Email:lipg@outlook.com.
     */
    List<OrderRefundExpressVo> getExpress();

}
