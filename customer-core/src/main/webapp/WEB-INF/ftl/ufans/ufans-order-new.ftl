<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="OrderStatusEnum" type="com.yougou.wfx.enums.OrderStatusEnum" -->
<#-- @ftlvariable name="pageSearchResult" type="com.yougou.wfx.appserver.vo.PageSearchResult<com.yougou.wfx.appserver.vo.order.OrderSearcher, com.yougou.wfx.appserver.vo.order.OrderInfo>" -->
<!DOCTYPE html>
<html>
	<head>
		<#include "../layouts/meta.ftl">
		<#include "../layouts/style.ftl">
		<title>优购微零售_优粉_我的订单</title>
		<link rel="stylesheet" type="text/css" href="${context}/static/css/plugins/dropload/dropload.css?v=${static_version}" />
	</head>
	<input type="hidden" id="orderLevel" value="${(searcher.level)!1}"/>
	<input type="hidden" id="status" value="${(searcher.status)!''}"/>

	<body class="bg-gray">
		<div class="viewport yg-my-order">
			<header class="mui-bar yg-header header-ext">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">
					<a id="orderSelect"><span><#if (searcher.level)??&&(searcher.level)==2>二级佣金订单
					 <#elseif (searcher.level)??&&(searcher.level)==3>三级佣金订单
					 <#else>一级佣金订单 </#if><i class="iconfont f14">&#xe60c;</i></span></a>
				</h1>
				<div class="my-order-collapse">
					<ul class="mui-table-view bg-gray">
						<li class="mui-table-view-cell active" data-value="1">
							<span>一级佣金订单</span>
							<i class="iconfont">&#xe652;</i>
						</li>
						<li class="mui-table-view-cell" data-value = "2">
							<span>二级佣金订单</span>
						</li>
						<li class="mui-table-view-cell" data-value = "3">
							<span>三级佣金订单</span>
						</li>
					</ul>
				</div>
				<input type="hidden" id="orderLevel" value="${(searcher.level)!1}"/>
			</header>

			<div class="mui-content">
				<div id="myListData" class="mui-control-content mui-active">
					<div class="order-type-list">
						<a href="javascript:;" data-value="1" class="active">全部</a>
						<a href="javascript:;" data-value="2">待付款</a>
						<a href="javascript:;" data-value="3">待发货</a>
						<a href="javascript:;" data-value="5">待收货</a>
						<a href="javascript:;" data-value="6">交易成功</a>
					</div>
			    <div id="myListDatahtml">
						<#if (pageSearchResult.items)??&&(pageSearchResult.items)?size gt 0 >
					<#list pageSearchResult.items as order>
					<section class="yg-floor yg-shopping-cart-floor mt10 no-border-bottom">
 					<a href="${context}/ufans/order/detail.sc?orderId=${(order.id)!''}&level=${(searcher.level)!1}" class="shopping-cart-title">
                    <div class="mui-row">
                        <div class="mui-col-sm-7 mui-col-xs-7">
                            <#if (order.shopCode)??&&(order.shopCode)=='优购微零售总店'>${(order.shopCode)}<#else>No.${(order.shopCode)!''}</#if> 
                        </div>
                        <div class="mui-col-sm-5 mui-col-xs-5 tright">
                        <#--订单状态-->
                            <#if ((order.state_flag)!'')=='待付款'>
                                <span class="Red">${(order.state_flag)!''}</span>
                            <#else>
                            ${(order.state_flag)!''}
                            </#if>
                        </div>
                    </div>
                </a>
                <#if (order.products)??&&(order.products)?size gt 0>
                <div class="shopping-cart-list">
                <#list order.products as product>
                <#--默认最大显示三条数据-->
                    <#if  (product_index lt 3) >
                            <div class="shopping-cart-list-item yg-layout-column-left-80 <#if (order.products)?size != (product_index+1) > my-orderlist-after relative</#if>">
                                <a class="yg-layout-left" href="${context}/ufans/order/detail.sc?orderId=${(order.id)!''}&level=${(searcher.level)!1}" >
                                    <img src="${(product.url)!''}" alt="">
                                </a>
                                <div class="yg-layout-main">
                                    <div class="yg-layout-container mui-row">
                                        <div class="mui-col-sm-8 mui-col-xs-8">
                                            <a class="goods-title" href="${context}/ufans/order/detail.sc?orderId=${(order.id)!''}&level=${(searcher.level)!1}">${(product.describe)!''}</a>
                                           
                                        </div>
                                        <div class="mui-col-sm-4 mui-col-xs-4 tright">
                                            <p>&yen;<#if (product.price)??>${(product.price)}<#else>0</#if></p>
                                            <p class="Gray">x<#if (product.num)??>${(product.num)}<#else>0</#if></p>
                                            
                                        </div>
                                        <#if (product.refundShowStatus)??&&(product.refundShowStatus)=='3'>
                                        	<div class="Yellow pt10"><i class="iconfont"></i> 退款完成
                                            </div>
                                        </#if>
                                        <#if (product.refundShowStatus)??&&(product.refundShowStatus)=='2'>
                                        	<div class="Yellow pt10"><i class="iconfont"></i> 退款中
                                            </div>
                                        </#if>
                                        
                                        <div class="tright">
                                        <#if ((order.commissionStatus)!0)!=3><span class="Red">预计佣金：￥<#if (product.commission)??>${(product.commission)}<#else>0</#if></span>
                                       								      <#else><span class="Red">佣金：￥<#if (product.commission)??>${(product.commission)}<#else>0</#if></span></#if>
                                       	</div>
                                    </div>
                                </div>
                            </div>
                    </#if>
                </#list >
				</div>
                </#if>
                <div class="shopping-cart-title border-top confirm-receipt-wrap no-border-bottom">
                    <div class="mui-row">
                        <div class="mui-col-sm-5 mui-col-xs-5 lh-33">
                            实付款：&yen;<#if (order.paying)??>${(order.paying)}<#else>0</#if>
                        </div>
                        <div class="mui-col-sm-7 mui-col-xs-7 tright lh-33">
							<span><#if ((order.commissionStatus)!0)!=3>预计佣金：<#else>佣金：</#if><span class="Red">￥<#if (order.brokerage)??>${(order.brokerage)}<#else>0</#if></span></span>
						</div>
                    </div>
                </div>
                </a>
					</section>
        </#list>
		</#if>
                  </div>
				</div>
			</div>
		</div>
		<#include "../layouts/corejs.ftl">
		<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
		<script src="${context}/static/js/ufans/ufans-orders-new.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript">
		    var errorMsg = '${errorMsg!''}';
		</script>
	</body>

</html>