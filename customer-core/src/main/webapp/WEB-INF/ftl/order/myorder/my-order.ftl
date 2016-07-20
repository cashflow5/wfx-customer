<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="OrderStatusEnum" type="com.yougou.wfx.enums.OrderStatusEnum" -->
<#-- @ftlvariable name="orders" type="com.yougou.wfx.customer.model.common.Page<com.yougou.wfx.customer.model.order.OrderVo>" -->
<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl">
    <title>优购微零售_订单列表</title>
<#include "../../layouts/style.ftl">
    <link rel="stylesheet" type="text/css" href="${context}/static/css/plugins/dropload/dropload.css?v=${static_version}" />
</head>
<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
    	<a class="mui-icon mui-icon-left-nav mui-pull-left" href="${context!''}/usercenter.sc"></a>
		<h1 class="mui-title">我的订单</h1>
	</header>
    <div class="mui-content">
        <div id="myListData" class="mui-control-content mui-active">
					<div class="yg-shopping-top w100p pd10 mui-clearfix">
                        <a class="w20p tcenter block fl" href="${context}/order/myorder.sc?tabType=0">全部</a>
						<a class="w20p tcenter block fl" href="${context}/order/myorder.sc?status=WAIT_PAY&tabType=1">待付款</a>
						<a class="w20p tcenter block fl" href="${context}/order/myorder.sc?status=WAIT_DELIVER&tabType=2">待发货</a>
						<a class="w20p tcenter block fl" href="${context}/order/myorder.sc?mergeSearchFlag=1&tabType=3">待收货</a>
						<a class="w20p tcenter block fl" href="${context}/order/myorder.sc?status=TRADE_SUCCESS&tabType=4">交易成功</a>
		</div>
        <#list orders.items as order>
            <section class="yg-floor yg-shopping-cart-floor mt10">
                <a href="${context}/order/${order.id}.sc" class="shopping-cart-title border-top">
                    <div class="mui-row">
                        <div class="mui-col-sm-7 mui-col-xs-7">
                            	订单号：${(order.wfxOrderNo)!''}
                        </div>
                        <div class="mui-col-sm-5 mui-col-xs-5 tright">
                        <#--订单状态-->
                            <#if ((order.status)!'')=='WAIT_PAY'>
                                <span class="Red">${order.statusDesc}</span>
                            <#else >
                            ${order.statusDesc}
                            </#if>
                        </div>
                    </div>
                </a>
                <#list order.orderDetails as orderDetail>
                <#--默认最大显示三条数据-->
                    <#if orderDetail_index lt ((myOrderListCommoditySize?number)!'3') >
                        <div class="shopping-cart-list">
                            <div class="shopping-cart-list-item yg-layout-column-left-80">
                                <a class="yg-layout-left" href="${context}/order/${order.id}.sc">
                                    <img src="${(orderDetail.picSmall)!''}" alt="">
                                </a>
                                <div class="yg-layout-main">
                                    <div class="yg-layout-container mui-row">
                                        <div class="mui-col-sm-8 mui-col-xs-8">
                                        	<div class="goods-title">
                                                <a href="${context}/order/${(order.id)!''}.sc">${(orderDetail.prodName)!''}</a>
                                            </div>
                                            <div class="goods-title line-one"><span class="Gray"> ${(orderDetail.prodSpec)!''}</span></div>
                                            <#if ((orderDetail.canRefund)!false)>
                                                <div class="pt10">
                                                    <a class="mui-btn"
                                                       href="${context}/order/${(order.id)!''}/${(orderDetail.id)!''}/refund.sc">退款/退货</a>
                                                </div>
                                            <#elseif ((orderDetail.refundStatusDesc)!'')!=''>
                                                <a href="${context}/order/${order.id}/${(orderDetail.id)!''}/refundDetail.sc">
                                                    <div class="Yellow pt10"><i class="iconfont"></i> ${(orderDetail.refundStatusDesc)!''}
                                                    </div>
                                                </a>
                                            </#if>
                                        </div>
                                        <div class="mui-col-sm-4 mui-col-xs-4 tright">
                                            <p>&yen;${((orderDetail.wfxPrice)!0)?string('0.00')}</p>
                                            <p class="Gray">x${((orderDetail.num)!0)?number}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </#if>
                </#list >
                <div class="shopping-cart-title border-top confirm-receipt-wrap">
                    <div class="mui-row">
                        <div class="mui-col-sm-5 mui-col-xs-5 lh-33">
                            实付款：&yen;${((order.payment)!0)?string('0.00')}
                        </div>
                        <div class="mui-col-sm-7 mui-col-xs-7 tright">
                        <#--按钮处理-->
                            <#if (order.canCancel)!false>
                                <a class="mui-btn" href="javascript:;" data-id="${(order.id)!''}" id="cancelOrder">取消订单</a>
                            </#if>
                            <#if (order.canPay)!false>
                                <a class="mui-btn mui-btn-danger" href="${context}/order/${order.id}/pay.sc">去支付</a>
                            </#if>
                            <#if (order.canShowLogistics)!false>
                                <a class="mui-btn" href="${context}/order/${order.id}/logistics.sc">物流跟踪</a>
                            </#if>
                            <#if (order.canConfirm)!false>
                                <a class="mui-btn mui-btn-danger btn-confirm"
                                   href="javascript:;"
                                   id="confirmOrderGoods"
                                   data-id="${(order.id)!''}">确认收货</a>
                            </#if>
                            <#if (order.canBuyAgin)!false>
                                <a class="mui-btn mui-btn-danger btn-confirm"
                                   id="buyAgin" data-id="${(order.id)!''}">重新购买</a>
                            </#if>
                        </div>
                    </div>
                </div>
            </section>
        </#list>
        </div>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script src="${context}/static/js/yg-orders.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var errorMsg = '${errorMsg!''}';
    var tabType='${orderSearchVo.tabType}';
    if(tabType==undefined){tabType=0}
    $("#myListData a").eq(tabType).addClass("Red");
</script>
</body>
</html>