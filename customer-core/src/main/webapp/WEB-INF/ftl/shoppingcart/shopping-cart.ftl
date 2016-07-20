<!DOCTYPE html>
<html>

<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_购物车</title>
<#include "../layouts/style.ftl">
</head>

<body class="yg-pull-refresh">
<div id="pageList" class="viewport">
    <header class="mui-bar yg-header header-ext">
    	<h1 class="mui-title">购物车</h1>
    	<#if (running_environment != '3' && running_environment != '4')>
			<#if from?? && from == 'bar'>
			<#else>
	        	<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
	        </#if>
		</#if>
    </header>
    <#if (running_environment != '3' && running_environment != '4')>
		<#if from?? && from == 'bar'>
			<#include "../layouts/index-menu.ftl">
		</#if>
	</#if>
<#include "../layouts/menu.ftl">
    <nav class="mui-bar mui-bar-tab yg-nav">
        <div class="mui-tab-item yg-nav-text yg-shoppingcar-select">
        <span class="mui-checkbox">
        <input type="checkbox" id="selectAll" name="checkbox"
        <#if cartResultVo.selectAll>checked="checked"</#if>/><label for="selectAll">全选</label>
        </span>
            <span class="mui-tab-label" id="btn-total-money">合计：¥${(cartResultVo.totalPrice!'0')?string('0.00')}</span>
        </div>
        <a class="mui-tab-item yg-nav-btn-buy-now w37p" href="${context}/confirm_order.sc">
            <span class="mui-tab-label" id="btn-total-count">去结算（${cartResultVo.totalCount}）</span>
        </a>
    </nav>
    <div class="mui-content">
    	<input type="hidden" id="shopId" value="${shopId!''}">
    	<section class="yg-floor yg-shopping-cart-floor">
    		<div class="shopping-cart-list">
		    <#if cartCommoditys??&&cartCommoditys?size gt 0>
		    	<#list cartCommoditys as cart>
		    		<div class="shopping-cart-list-item yg-layout-column-left-120" data-sku-id="${cart.skuId!''}" data-id="${cart.id}">
		                <#if cart.isOnsale=1&&cart.stock gte 1>
		                    <div class="yg-layout-left">
								<span class="mui-checkbox">
									<input type="checkbox" name="checkbox" class="sku-checkbox" <#if cart.checked>checked="checked"</#if>/>
								</span><a href="${context}/${shopId!''}/item/${cart.commodityNo}.sc" style="display:inline-block;"><img src="${cart.imageUrl!''}" alt=""/></a>
		                    </div>
		                    <div class="yg-layout-main">
		                        <div class="yg-layout-container">
		                            <div class="goods-title line-one">
		                                <a href="${context}/${shopId!''}/item/${cart.commodityNo}.sc">${cart.name!''}</a>
		                            </div>
		                            <div class="goods-title line-one"><span class="Gray">颜色：${cart.specName!''}  尺码：${cart.size!''}</span></div>
		                            <div class="goods-price">&yen;${(cart.wfxPrice!0)?string("0.00")}</div>
		                            <div>
		                                <div class="mui-numbox" data-numbox-min='1'
		                                     data-numbox-max='${skuMaxCount}'>
		                                    <button class="mui-btn mui-numbox-btn-minus" type="button">-
		                                    </button>
		                                    <input class="mui-numbox-input sku-number" type="number" value="${cart.count}" disabled/>
		                                    <button class="mui-btn mui-numbox-btn-plus" type="button">+</button>
		                                </div>
		                                <a class="iconfont goods-delete">&#xe61a;</a>
		                            </div>
		                        </div>
		                    </div>
		                <#else>
		                    <div class="yg-layout-left">
		                        <span class="mui-checkbox">
		                            <input type="checkbox" name="checkbox" class="sku-checkbox" disabled/>
		                        </span><a href="${context}/${shopId!''}/item/${cart.commodityNo}.sc" style="display:inline-block;"><img src="${cart.imageUrl!''}" alt=""/></a>
		                        <#if cart.isOnsale!=1>
		                            <span class="sold-out"></span>
		                        <#elseif cart.stock lt 1 >
		                            <span class="sold-out2"></span>
		                        </#if>
		                    </div>
		                    <div class="yg-layout-main">
		                        <div class="yg-layout-container">
		                        	<div class="goods-title line-one">
		                                <a href="${context}/${shopId!''}/item/${cart.commodityNo}.sc">${cart.name!''}</a>
		                            </div>
		                            <div class="goods-title line-one"><span class="Gray">颜色：${cart.specName!''}  尺码：${cart.size!''}</span></div>
		                            <div class="goods-price">&yen;${(cart.wfxPrice!0)?string("0.00")}</div>
		                            <div>
		                                <div class="mui-numbox" data-numbox-min='1'>
		                                    <button class="mui-btn mui-numbox-btn-minus btn-disabled" type="button" disabled>
		                                    	-
		                                    </button>
		                                    <input class="mui-numbox-input sku-number btn-disabled" type="number" value="${cart.count}" disabled/>
		                                    <button class="mui-btn mui-numbox-btn-plus btn-disabled" type="button" disabled>
		                                    	+
		                                    </button>
		                                </div>
		                                <a class="iconfont goods-delete">&#xe61a;</a>
		                            </div>
		                        </div>
		                    </div>
		                </#if>
		            </div>
		        </#list>
			</#if>
			</div>
		</section>
    </div>
</div>
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/plugins/mui.lazyload.js?v=${static_version}"></script>
<script src="${context}/static/js/plugins/mui.lazyload.img.js?v=${static_version}"></script>
<script src="${context}/static/js/yg-shopping-cart.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>

</html>