package com.yougou.wfx.customer.service.order.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.yougou.wfx.aftersale.api.front.IAfterSaleFrontApi;
import com.yougou.wfx.aftersale.dto.input.OrderRefundInputDto;
import com.yougou.wfx.aftersale.dto.output.OrderAfterSaleDto;
import com.yougou.wfx.aftersale.dto.output.OrderAfterSaleSearchDto;
import com.yougou.wfx.aftersale.dto.output.OrderRefundOutputDto;
import com.yougou.wfx.aftersale.dto.output.SupplierAddressOutputDto;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.utils.JacksonUtils;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.order.refund.OrderRefundAddressVo;
import com.yougou.wfx.customer.model.order.refund.OrderRefundExpressVo;
import com.yougou.wfx.customer.model.order.refund.OrderRefundSearchVo;
import com.yougou.wfx.customer.model.order.refund.OrderRefundVo;
import com.yougou.wfx.customer.service.order.IOrderRefundService;
import com.yougou.wfx.dto.base.PageModel;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.order.api.front.IOrderFrontApi;
import com.yougou.wfx.order.dto.output.OrderOutputDto;

/**
 * 订单退货的Service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/13 下午6:39
 * @since 1.0 Created by lipangeng on 16/4/13 下午6:39. Email:lipg@outlook.com.
 */
@Service
public class OrderRefundService implements IOrderRefundService {
    private static final Logger logger = LoggerFactory.getLogger(OrderRefundService.class);
    @Autowired
    private IAfterSaleFrontApi afterSaleFrontApi;

    @Autowired
    private IOrderFrontApi orderFrontApi;
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
    @Override
    public Page<OrderRefundVo> getUserOrderRefund(String userId, Page page, OrderRefundSearchVo search) {
        // 创建分页数据
        PageModel pageModel = page.toPageModelDto();
        // 创建搜索数据
        OrderAfterSaleSearchDto searchDto = new OrderAfterSaleSearchDto();
        searchDto.setRefundStatus(search.getStatus());
        PageModel<OrderAfterSaleDto> afterSalePage = afterSaleFrontApi.queryOrderAfterSalePage(userId, pageModel, searchDto);
        // 注入分页信息
        // 创建返回值
        Page<OrderRefundVo> orderRefunds = Page.valueOf(afterSalePage);
        if (afterSalePage != null && afterSalePage.getItems() != null) {
            for (OrderAfterSaleDto orderAfterSaleDto : afterSalePage.getItems()) {
                orderRefunds.getItems().add(OrderRefundVo.valueOf(orderAfterSaleDto));
            }
        }
        return orderRefunds;
    }

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
    @Override
    public OrderRefundAddressVo getBackAddress(String orderDetailId, String supplierCode) {
        SupplierAddressOutputDto backAddress = afterSaleFrontApi.getBackAddress(orderDetailId, supplierCode);
        if (backAddress == null) {
            return null;
        }
        OrderRefundAddressVo refundAddress = new OrderRefundAddressVo();
        refundAddress.setName(backAddress.getContact());
        refundAddress.setPhone(backAddress.getPhone());
        refundAddress.setAddress(String.format("%s %s %s %s",
                                               backAddress.getProvince(),
                                               backAddress.getCity(),
                                               backAddress.getArea(),
                                               backAddress.getAddress()));
        return refundAddress;
    }

    /**
     * 获取退货退款单信息
     *
     * @param userName
     *         暂时传入的为userName
     * @param orderId
     *         订单id
     * @param orderDetailId
     *         商品id
     *
     * @since 1.0 Created by lipangeng on 16/4/13 下午6:46. Email:lipg@outlook.com.
     */
    @Override
    public OrderRefundVo getRefundItem(SessionUserDetails loginUserDetails, String orderId, String orderDetailId) {
    	String userName = loginUserDetails.getUserName();
    	String userId = loginUserDetails.getUserId();
        WFXResult<OrderRefundOutputDto> refundOutput = afterSaleFrontApi.getRefundItem(orderDetailId);
        
        // 如果为空,code为3,结果集为空,或者成功找到,但是不是他自己的退款单时报错
        if (refundOutput == null || refundOutput.getResultCode() == 3 || refundOutput.getResult() == null ){
//        || (refundOutput.getResultCode() == 1 && ! Objects.equal(refundOutput.getResult().getBuyerLoginName(), userName))) {
            logger.error("找不到退款单信息,或无权访问退款信息.用户:{},订单:{},子订单:{}", userName, orderId, orderDetailId);
            return null;
        }
        
        //用户校验
        OrderRefundOutputDto refund = refundOutput.getResult();
        String wfxOrderNo = refund.getWfxOrderNo();
        OrderOutputDto order = orderFrontApi.getOrderByWFXOrderNo(wfxOrderNo);
        if( refundOutput.getResultCode() == 1 &&(order==null|| !order.getBuyerId().equals(userId)) ){
        	 logger.error("无权访问退款信息.用户:{},订单:{},子订单:{}", userName, orderId, orderDetailId);
             return null;
        }
        
        if (refundOutput.getResultCode() == 1 && (! Objects.equal(refundOutput.getResult().getOrderId(), orderId) ||
                                                  ! Objects.equal(orderDetailId, refundOutput.getResult().getOrderDetailId()))) {
            logger.warn("用户:{},意图查看他人订单:{},子订单:{}的退货信息", userName, orderId, orderDetailId);
            return null;
        }
        return OrderRefundVo.valueOf(refundOutput.getResult());
    }

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
    @Override
    public OrderRefundVo getRefundItem(SessionUserDetails loginUserDetails, String refundNo) {
    	String userName = loginUserDetails.getUserName();
    	String userId = loginUserDetails.getUserId();
        if (Strings.isNullOrEmpty(userName) || Strings.isNullOrEmpty(refundNo)) {
            logger.error("找不到退款单信息,参数不完整。userName:" + userName + ",refundNo:" + refundNo);
            return null;
        }
        OrderRefundOutputDto refundOutput = afterSaleFrontApi.getDetailByRefundNo(refundNo);
        //用户校验
        String wfxOrderNo = refundOutput.getWfxOrderNo();
        OrderOutputDto order = orderFrontApi.getOrderByWFXOrderNo(wfxOrderNo);
        if( refundOutput == null || order==null|| !order.getBuyerId().equals(userId) ){
        	 logger.error("找不到退款单信息,或无权访问退款信息.用户:{},退款单:{}", userName, refundNo);
             return null;
        }
        
        if (Strings.isNullOrEmpty(refundOutput.getOrderId()) || Strings.isNullOrEmpty(refundOutput.getOrderDetailId())) {
            logger.error("退款单信息不完整:" + JacksonUtils.Convert(refundOutput));
            return null;
        }
        // 更新可退款可退货数量信息以及发货状态
        WFXResult<OrderRefundOutputDto> refundItem = afterSaleFrontApi.getRefundItem(refundOutput.getOrderDetailId());
        if (refundItem != null && refundItem.getResultCode() != 3 && refundItem.getResult() != null) {
            refundOutput.setCanReturnFee(refundItem.getResult().getCanReturnFee());
            refundOutput.setCanReturnNum(refundItem.getResult().getCanReturnNum());
            refundOutput.setDelivered(refundItem.getResult().isDelivered());
        }
        return OrderRefundVo.valueOf(refundOutput);
    }

    /**
     * 创建退货订单信息
     *
     * @param userId
     *         用户Id
     * @param orderRefund
     *         创建订单参数
     *
     * @since 1.0 Created by lipangeng on 16/4/14 下午2:16. Email:lipg@outlook.com.
     */
    @Override
    public Result<OrderRefundVo> createRefundItem(String userId, OrderRefundVo orderRefund) {
        if (userId == null || orderRefund == null) {
            return Result.create(false, "参数空,创建退货单失败");
        }
        if (! Objects.equal(userId, orderRefund.getBuyerLoginId())) {
            logger.warn("用户ID:{},意图创建他人订单:{},子订单:{}的退货信息", userId, orderRefund.getOrderId(), orderRefund.getOrderDetailId());
            return Result.create(false, 400, "无权创建他人订单的退款信息");
        }
        WFXResult<OrderRefundOutputDto> refundItem = afterSaleFrontApi.createRefundItem(orderRefund.toOrderRefundInputDto(true));
        if (refundItem.getResultCode() != 200) {
            return Result.create(false, refundItem.getResultCode(), refundItem.getResultMsg());
        }
        return Result.create(true, "", OrderRefundVo.valueOf(refundItem.getResult()));
    }

    /**
     * 更新退款退货订单信息
     *
     * @param userId
     *         用户名
     * @param orderRefund
     *         更新订单参数
     *
     * @since 1.0 Created by lipangeng on 16/4/14 下午2:16. Email:lipg@outlook.com.
     */
    @Override
    public Result<OrderRefundVo> updateRefundItem(String userId, OrderRefundVo orderRefund) {
        if (userId == null || orderRefund == null) {
            return Result.create(false, "参数空,更新退货单失败");
        }
        // 校验买家是否一致
        String wfxOrderNo = orderRefund.getWfxOrderNo();
        OrderOutputDto order= orderFrontApi.getOrderByWFXOrderNo(wfxOrderNo);
        if (! Objects.equal(userId, order.getBuyerId())) {
            logger.warn("用户:{},意图创建他人订单:{},子订单:{}的退货信息", userId, orderRefund.getOrderId(), orderRefund.getOrderDetailId());
            return Result.create(false, 400, "无权创建他人订单的退款信息");
        }
        WFXResult<OrderRefundOutputDto> refundItem = afterSaleFrontApi.updateRefundItem(orderRefund.toOrderRefundInputDto(false));
        if (refundItem.getResultCode() != 200) {
            return Result.create(false, refundItem.getResultCode(), refundItem.getResultMsg());
        }
        return Result.create(true, "", OrderRefundVo.valueOf(refundItem.getResult()));
    }

    /**
     * 取消退款退货订单信息
     *
     * @param userName
     *         用户Id
     * @param refundNo
     *         退款单号
     *
     * @since 1.0 Created by lipangeng on 16/4/14 下午2:16. Email:lipg@outlook.com.
     */
    @Override
    public Result cancelRefundItem(SessionUserDetails loginUserDetails, String refundNo) {
        OrderRefundVo refundItem = getRefundItem(loginUserDetails, refundNo);
        if (refundItem == null) {
            return Result.create(false, "无法找到退款退货信息");
        }
        OrderRefundInputDto input = new OrderRefundInputDto();
        input.setId(refundItem.getId());
        input.setRefundNo(refundItem.getRefundNo());
        input.setBuyerLoginName( loginUserDetails.getUserName() );
        WFXResult<Boolean> result = afterSaleFrontApi.cancelRefund(input);
        return Result.valueOf(result);
    }

    /**
     * 获取退货地址可选的物流公司
     *
     * @since 1.0 Created by lipangeng on 16/4/14 下午5:48. Email:lipg@outlook.com.
     */
    @Override
    public List<OrderRefundExpressVo> getExpress() {
        List<OrderRefundExpressVo> expresses = Lists.newArrayList();
        Map<String, String> emsList = afterSaleFrontApi.getEMSList();
        if (emsList != null) {
            for (Map.Entry<String, String> ems : emsList.entrySet()) {
                OrderRefundExpressVo express = new OrderRefundExpressVo();
                express.setName(ems.getValue());
                express.setNo(ems.getKey());
                expresses.add(express);
            }
        }
        return expresses;
    }
}
