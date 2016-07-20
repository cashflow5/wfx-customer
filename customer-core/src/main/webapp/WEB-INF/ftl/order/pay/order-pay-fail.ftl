<#-- @ftlvariable name="order" type="com.yougou.wfx.customer.model.order.OrderVo" -->
<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl">
    <title>优购微零售_订单支付失败提示</title>
<#include "../../layouts/style.ftl">
</head>
<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
    	<a class="mui-icon mui-icon-left-nav mui-pull-left" href="${context!''}/order/myorder.sc?status=WAIT_PAY&tabType=1"></a>
        <h1 class="mui-title">订单支付失败</h1>
    </header>
    <div class="mui-content">
        <section class="yg-floor pt10 mt20">
            <p class="f16 mt10 pb20">您的订单暂未支付成功，系统将会为您保留1个小时，1小时后系统将会自动取消订单，请尽快完成支付。</p>
        </section>
        <section class="yg-floor pt10" id="orderDetail">
            <div class="mui-row">
                <div class="mui-col-sm-10 mui-col-xs-10">
                    <p class="f16">支付金额：<span class="Red">${((order.payment)!'0')?string('0.00')} 元</span></p>
                    <p class="f16">支付方式：${(order.payTypeDesc)!''}</p>
                </div>
                <div class="mui-col-sm-2 mui-col-xs-2 tright pt20">
                    <span class="iconfont">&#xe60d;</span>
                </div>
            </div>
        </section>
        <section class="plr10 mt20 pt5">
            <div class="mui-row pr10">
                <div class="mui-col-sm-6 mui-col-xs-6">
                    <a href="${context!''}/order/${(order.id)!''}/pay.sc" class="mui-btn mui-btn-danger w100p">去支付</a>
                </div>
                <div class="mui-col-sm-6 mui-col-xs-6">
                    <a href="${context!''}/index.sc" class="mui-btn w100p ml10">继续购买</a>
                </div>
            </div>
        </section>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
<script src="${context}/static/js/yg-pay-fail.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var orderId = '${(order.id)!''}';
</script>
</body>
</html>