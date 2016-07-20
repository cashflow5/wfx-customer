<#-- @ftlvariable name="orders" type="com.yougou.wfx.customer.model.common.Page<com.yougou.wfx.customer.model.order.OrderVo>" -->
<#--ajax获取我的订单列表信息,可以直接用my-order.ftl相关内容覆盖-->
<#list orders.items as order>
<section class="yg-floor yg-shopping-cart-floor">
    <a href="${context}/order/${order.id}.sc" class="shopping-cart-title border-top">
        <div class="mui-row">
            <div class="mui-col-sm-7 mui-col-xs-7">
                <i class="iconfont Red"></i> <#if (order.shopCode)??&&(order.shopCode)=='优购微零售总店'>${(order.shopCode)}<#else>No.${(order.shopCode)!''}</#if> <i class="iconfont Gray"></i>
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
                            	<div class="goods-title line-one">
                                    <a href="${context}/order/${(order.id)!''}.sc">${(orderDetail.prodName)!''}</a>
                                </div>
                                <div class="goods-title line-one"><span class="Gray"> ${(orderDetail.prodSpec)!''}</span></div>
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
            </div>
        </div>
    </div>
</section>
</#list>