<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_购物车（空）</title>
<#include "../layouts/style.ftl">
</head>
<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
    	<#if (running_environment != '3' && running_environment != '4')>
			<#if from?? && from == 'bar'>
			<#else>
	        	<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	        </#if>
		</#if>
        <h1 class="mui-title">购物车</h1>
    </header>
    <#if (running_environment != '3' && running_environment != '4')>
		<#if from?? && from=='bar'>
			<#include "../layouts/index-menu.ftl">
		</#if>
	</#if>
    <div class="mui-content">
        <section class="yg-floor pt10 mt20">
            <div class="yg-fail-success">
                <div class="shopp-cart-circle"><i class="iconfont White">&#xe627;</i></div>
            </div>
            <p class="tcenter f18 mt10 pb20">购物车还是空的，去挑选中意的商品吧</p>
            <a href="${context}/${shopId}.sc?flag=1" class="mui-btn mui-btn-danger mui-btn-block mt10">去逛逛</a>
        </section>
    </div>
</div>
<#include "../layouts/corejs.ftl"/>
</body>
</html>