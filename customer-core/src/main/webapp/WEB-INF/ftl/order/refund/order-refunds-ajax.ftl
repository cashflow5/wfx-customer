<#-- @ftlvariable name="orderRefunds" type="com.yougou.wfx.customer.model.common.Page<com.yougou.wfx.customer.model.order.refund.OrderRefundVo>" -->
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
