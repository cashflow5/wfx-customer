<#-- @ftlvariable name="orderAutoConfirmDays" type="java.lang.String" -->
<#-- @ftlvariable name="commodityNos" type="java.util.Map<java.lang.String,java.lang.String>" -->
<#-- @ftlvariable name="orderLeftTime" type="long" -->
<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="isConfirmGoods" type="java.lang.Boolean" -->
<#-- @ftlvariable name="isPay" type="java.lang.Boolean" -->
<#-- @ftlvariable name="order" type="com.yougou.wfx.customer.model.order.OrderVo" -->
<!DOCTYPE html>
<html>

<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_订单详情</title>
<#include "../layouts/style.ftl">
</head>

<body>
<div class="viewport yg-order-detail">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">${(order.statusDesc)!''}</h1>
    </header>
    <nav class="mui-bar mui-bar-tab yg-btn-group">
        <div class="mui-tab-item">
        <#if (order.canPay)!false>
            <div class="yg-text-info">
                <div class="text-info-body">
                    <span class="f12">付款剩余时间：</span>
                    <div class="Black residualTime"></div>
                </div>
            </div>
            <button type="button" id="payNow" class="mui-btn mui-btn-danger fr mr10" data-id="${(order.id)!''}">去付款</button>
        </#if>
        <#if (order.canCancel)!false>
            <button type="button" id="cancelOrder" data-id="${(order.id)!''}" class="mui-btn fr mr10 off-order-btn">取消订单</button>
        </#if>
        <#if (order.canConfirm)!false>
            <div class="yg-text-info">
                <div class="text-info-body">
                    <span class="f12">发货${orderAutoConfirmDays!'7'}天后系统将自动确认收货</span>
                </div>
            <#--<div class="Black residualTime"></div>-->
            </div>
            <a id="confirmOrderGoods" class="mui-btn mui-btn-danger fr mr10" data-id="${(order.id)!''}" href="javascript:;">确认收货</a>
        </#if>
        <#if (order.canBuyAgin)!false>
            <a id="buyAgin" class="mui-btn mui-btn-danger fr mr10" data-id="${(order.id)!''}">重新购买</a>
        </#if>
        </div>
    </nav>
    <div class="mui-content">
        <section class="yg-box <#if (order.canShowLogistics)!false>mb0 no-border</#if>">
            <div class="Gray tright"><i class="iconfont Blue">&#xe600;</i> 担保交易</div>
            <span class="fl letter-spacing-order">订单号:${(order.wfxOrderNo)!''}</span>
            <span class="fr Gray f12 letter-spacing-time">下单时间:${((order.createdTime)!'')?datetime}</span>
        </section>
    <#if (order.canShowLogistics)!false>
        <a href="${context}/order/${order.id}/logistics.sc" class="yg-box link-box">
            <i class="iconfont Gray"></i> 查看物流跟踪
        </a>
    </#if>
        <section class="yg-box address-box mt10">
            <div class="box-content-wrap">
                <div class="box-title"><i class="iconfont Pink">&#xe620;</i>${(order.receiverName)!''} <i class="iconfont Pink ml50">
                    &#xe648;
                </i>${(order.receiverMobileEncode)!''}</div>
                <div class="box-info-content">${(order.receiverState)!''} ${(order.receiverCity)!''} ${(order.receiverDistrict)!''} ${(order
                .receiverAddress)!''}</div>
            </div>
        </section>
        <section class="yg-floor yg-shopping-cart-floor">
            <a class="shopping-cart-title border-top">
                <div class="mui-row">
                    <div class="mui-col-sm-7 mui-col-xs-7">
                      	商品信息
                    </div>
                    <div class="mui-col-sm-5 mui-col-xs-5 tright">
                    </div>
                </div>
            </a>
            <div class="shopping-cart-list">
            <#list order.orderDetails as orderDetail>
                <div class="shopping-cart-list-item yg-layout-column-left-80"
                     data-shopid="${(order.shopId)!''}"
                     data-commodityid="${(commodityNos[((orderDetail.commodityId)!'')])!''}">
                    <div class="yg-layout-left">
                        <img src="${(orderDetail.picSmall)!''}" alt="">
                    </div>
                    <div class="yg-layout-main">
                        <div class="yg-layout-container mui-row">
                            <div class="mui-col-sm-8 mui-col-xs-8">
                                <div class="goods-title">${(orderDetail.prodName)!''} ${(orderDetail.prodSpec)!''}</div>
                                <#if ((orderDetail.canRefund)!false)>
                                    <div class="pt10">
                                        <a class="mui-btn"
                                           href="${context}/order/${order.id}/${orderDetail.id}/refund.sc">退款/退货</a>
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
                                <p class="Gray">x${(orderDetail.num)!''}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
            </div>
        </section>
        <section class="pb10">
            <ul class="mui-table-view">
                <li class="mui-table-view-cell">
                    <span class="Gray">支付方式</span> <span class="fr">在线支付（${(order.payTypeDesc)!''}）</span>
                </li>
                <li class="mui-table-view-cell">
                    <span class="Gray">配送信息</span> <span class="fr">${(order.shippingType)!''}</span>
                </li>
            </ul>
        </section>
        <section class="yg-box mt10 pb0">
					<span class="box-content-wrap">
						<p class="Gray">商品总金额 <span class="Red fr">&yen; ${((order.totalFee)!0)?string('0.00')}</span></p>
					    <div class="Gray">+运费<span class="Red fr">&yen; ${((order.postFee)!0)?string('0.00')}</span></div>
					    <div class="bd-top-gray tright mt10 lh-44">
						    <span class="f12">实付款：</span> <span class="f18 Red">&yen; ${((order.payment)!0)?string('0.00')}</span>
					    </div>
					</span>
        </section>
    </div>
</div>
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/yg-order-detail.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var errorMsg = '${errorMsg!''}';
    var orderLeftTime =${orderLeftTime!0};
</script>
</body>
</html>