<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="orderRefunds" type="com.yougou.wfx.customer.model.common.Page<com.yougou.wfx.customer.model.order.refund.OrderRefundVo>" -->
<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl">
    <title>优购微零售_退款_售后</title>
<#include "../../layouts/style.ftl">
    <link rel="stylesheet" type="text/css" href="${context}/static/css/plugins/dropload/dropload.css?v=${static_version}" />
    <style>
    .yg-my-order .my-order-collapse li{padding-left:15px;}
    .mui-table-view .iconfont{float:right;display:none;}
    .mui-table-view .active .iconfont{display:block;}
    .mui-table-view .mui-tab-item{float:left;}
    .mui-backdrop{display:none;}
    </style>
</head>
<body>
<div class="viewport yg-refund-after-sale">
    <header class="mui-bar yg-header header-ext yg-my-order">
        	    <a class="mui-icon mui-icon-left-nav mui-pull-left" href="${context!''}/usercenter.sc"></a>
				<h1 class="mui-title">
					<a id="orderSelect"><span><i class="iconfont f14">&#xe60c;</i></span></a>
				</h1>
				<div class="my-order-collapse" id="middlePopover">
					<ul class="mui-table-view bg-gray">
					    <li class="mui-table-view-cell"><a class="mui-tab-item" href="javascript:void(0)">全部</a><i class="iconfont">&#xe652;</i></li>
						<li class="mui-table-view-cell"><a class="mui-tab-item" href="javascript:void(0)">退款申请中</a><i class="iconfont">&#xe652;</i></li>
                        <li class="mui-table-view-cell"><a class="mui-tab-item" href="javascript:void(0)">退款成功</a><i class="iconfont">&#xe652;</i></li>
                        <li class="mui-table-view-cell"><a class="mui-tab-item" href="javascript:void(0)">待商家确认收货</a><i class="iconfont">&#xe652;</i></li>
                        <li class="mui-table-view-cell"><a class="mui-tab-item" href="javascript:void(0)">卖家拒绝退款</a><i class="iconfont">&#xe652;</i></li>
                        <li class="mui-table-view-cell"><a class="mui-tab-item" href="javascript:void(0)">退款关闭</a><i class="iconfont">&#xe652;</i></li>
					</ul>
				</div>
    </header>
    <div class="mui-content">
        <div id="myListData" class="mui-control-content mui-active">
        <#if orderRefunds??&&(orderRefunds.items)??&&(orderRefunds.items)?size gt 0>
        <#list orderRefunds.items as orderRefund>
            <section class="yg-floor yg-shopping-cart-floor">
                <div class="mui-row shopping-cart-title border-top">
                    <div class="mui-col-sm-6 mui-col-xs-6">
                        <a href="${context}/${(orderRefund.shopId)!''}.sc?flag=1">
                            <i class="iconfont Red">&#xe60e;</i> ${(orderRefund.shopCode)!''} <i class="iconfont Gray">&#xe624;</i>
                        </a>
                    </div>
                    <div class="mui-col-sm-6 mui-col-xs-6 tright">
                        <#if ((orderRefund.status)!'')=='SUCCESS_REFUND'>
                            <a href="${context}/order/refund/${(orderRefund.refundNo)!''}.sc"
                               class="Green">${(orderRefund.statusDesc)!''}</a>
                        <#else >
                            <a href="${context}/order/refund/${(orderRefund.refundNo)!''}.sc"
                               class="Red">${(orderRefund.statusDesc)!''}</a>
                        </#if>
                    </div>
                </div>
                <a href="${context}/order/refund/${(orderRefund.refundNo)!''}.sc"
                   class="shopping-cart-list">
                    <div class="shopping-cart-list-item yg-layout-column-left-80">
                        <div class="yg-layout-left">
                            <img src="${(orderRefund.picSmall)!''}" alt="" />
                        </div>
                        <div class="yg-layout-main">
                            <div class="yg-layout-container">
                                <div class="goods-title">${(orderRefund.prodName)!''} ${(orderRefund.specName)!''}</span></div>
                            </div>
                        </div>
                    </div>
                </a>
                <a class="shopping-cart-title border-top">
                    <div class="mui-row">
                        <div class="mui-col-sm-6 mui-col-xs-6">
                            交易金额：￥${((orderRefund.totalFee)!0)?string('0.00')}
                        </div>
                        <div class="mui-col-sm-6 mui-col-xs-6 tright">
                            退款金额：<span class="Red">￥${((orderRefund.refundFee)!0)?string('0.00')}</span>
                        </div>
                    </div>
                </a>
            </section>
        </#list>
        <#else>
            <div class="yg-pro-detail-nomore pt20 pb20" style="text-align:center;"><img src="${context}/static/images/prodetail/nothing.png" style="width:30px"><br/>没有相关信息</div>
        </#if>
        </div>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="${context}/static/js/yg-order-refunds.js?v=${static_version}" charset="UTF-8"></script>
<script type="text/javascript">
    var errorMsg = '${errorMsg!''}';
</script>
</body>
</html>