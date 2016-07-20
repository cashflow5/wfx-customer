<#-- @ftlvariable name="shopId" type="java.lang.String" -->
<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_下单失败</title>
<#include "../layouts/style.ftl">
</head>
<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
    	<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">下单失败</h1>
    </header>
    <div class="mui-content">
        <section class="yg-floor mt20">
            <div class="yg-fail-success">
                <i class="iconfont Red">&#xe64d;</i>
            </div>
            <p class="Red tcenter f14 pb20">抱歉，下单失败！</p>
        </section>
        <section class="plr10">
            <p class="f12 pb10">因某种原因，导致您的订单无法成功提交请返回购物车重新下单，如果多次失败请稍后再试。</p>
            <p class="f12 pb10">参考信息:${errorMsg!''}</p>
            <a class="mui-btn mui-btn-danger mui-btn-block" href="${context}/shoppingcart.sc">返回购物车</a>
        </section>
    </div>
</div>
<#include "../layouts/corejs.ftl">
</body>
</html>