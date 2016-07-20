package com.yougou.wfx.customer.service.bapp;


import com.yougou.wfx.appserver.vo.PageSearchResult;
import com.yougou.wfx.appserver.vo.order.OrderDetailInfo;
import com.yougou.wfx.appserver.vo.order.OrderIdentity;
import com.yougou.wfx.appserver.vo.order.OrderInfo;
import com.yougou.wfx.appserver.vo.order.OrderSearcher;

/**
 * Created by lizhw on 2016/4/11.
 */
public interface IOrderService extends IBaseService {

    PageSearchResult<OrderSearcher, OrderInfo> getOrderInfoList(OrderSearcher searcher);

    OrderDetailInfo getOrderDetail(OrderIdentity identity);

	PageSearchResult<OrderSearcher, OrderInfo> getShopOrderInfoList(
			OrderSearcher searcher);
}
