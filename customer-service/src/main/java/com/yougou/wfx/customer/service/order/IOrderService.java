package com.yougou.wfx.customer.service.order;

import java.util.List;

import com.yougou.wfx.customer.model.commodity.CommodityShoppingCartVo;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.order.*;
import com.yougou.wfx.customer.model.shoopingcart.ShoppingCartChangeVo;
import com.yougou.wfx.customer.model.shop.SelectItemVo;

/**
 * 订单service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/30 下午5:56
 * @since 1.0 Created by lipangeng on 16/3/30 下午5:56. Email:lipg@outlook.com.
 */
public interface IOrderService {

    /**
     * 获取用户的订单信息
     *
     * @since 1.0 Created by lipangeng on 16/3/30 下午6:00. Email:lipg@outlook.com.
     */
    OrderCountVo getUserOrderCount(String userId);

    /**
     * 判断用户是否有成功购买行为 检测当前登录用户是否有已完成或者确认收货的订单(不要求查询数量，存在即可)
     *
     * @return
     */
    boolean existsValidOrders();

    /**
     * 获取用户的订单列表
     *
     * @param userId
     *         用户id
     * @param page
     *         分页排序请求
     * @param searchVo
     *         搜索条件
     *
     * @since 1.0 Created by lipangeng on 16/4/7 下午5:20. Email:lipg@outlook.com.
     */
    Page<OrderVo> getUserOrders(String userId, Page page, OrderSearchVo searchVo);

    /**
     * 获取用户订单信息
     *
     * @param userId
     *         用户id
     * @param orderId
     *         订单id
     *
     * @since 1.0 Created by lipangeng on 16/4/11 下午3:54. Email:lipg@outlook.com.
     */
    OrderVo getUserOrder(String userId, String orderId);

    /**
     * 取消订单
     *
     * @param userId
     *         用户id
     * @param orderId
     *         订单id
     *
     * @since 1.0 Created by lipangeng on 16/4/12 下午4:28. Email:lipg@outlook.com.
     */
    Result cancelOrder(String userId, String orderId);

    /**
     * 确认订单
     *
     * @param userId
     *         用户id
     * @param orderId
     *         订单id
     *
     * @since 1.0 Created by lipangeng on 16/4/13 上午9:43. Email:lipg@outlook.com.
     */
    Result confirmOrderGoods(String userId, String orderId);

    /**
     * 创建订单接口
     *
     * @param orderCreateVo
     *         订单数据
     *
     * @since 1.0 Created by lipangeng on 16/4/15 上午9:39. Email:lipg@outlook.com.
     */
    Result<OrderVo> createOrder(OrderCreateVo orderCreateVo);

    /**
     * 获取当前确认订单页面数据模型
     *
     * @param addressId
     *         收获地址ID
     *
     * @return
     */
    OrderPayVo getOrderPayInfo(String addressId);
    
    /**
     * 
     * @param order
     * @return
     */
    OrderPayVo getOrderPayInfo(SelectItemVo vo);
    
    /**
     * 能过订单获取当前确认订单页面数据模型
     * @param order
     * @return
     */
    OrderPayVo getOrderPayInfo(OrderVo order);

    /**
     * 创建交易流水,创建交易（提交订单号成功，转入支付收银台触发）
     *
     * @since 1.0 Created by lipangeng on 16/4/22 下午1:58. Email:lipg@outlook.com.
     */
    Result<String> createOrderPayTrade(OrderVo order);

	/**
	 * 是否成为优粉
	 * @param buyerId
	 * @param wfxOrderNo
	 * @return
	 */
	Boolean queryIfAutoTransfer(String buyerId, String wfxOrderNo);
	
	/**
	 * 粉丝下单通知
	 * @param order
	 * @return
	 */
	void sendFansOrderMessage(OrderVo order, String appId, String secret);
	
	/**
	 * 分销商下单通知
	 * @param order
	 * @return
	 */
	void sendAgentOrderMessage(OrderVo order, String appId, String secret);
	
	/**
	 * 通过订单和订单详情，获取在售状态并且有库存的商品列表
	 * @param order
	 * @param orderDetailList
	 * @return
	 */
	List<CommodityShoppingCartVo> getCommodityShoppingCartVos(OrderVo order,List<OrderDetailVo> orderDetailList);
}
