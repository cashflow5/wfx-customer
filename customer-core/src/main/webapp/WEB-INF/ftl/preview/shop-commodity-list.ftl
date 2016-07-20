<!DOCTYPE html>
<html>

<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_商品列表</title>
<#include "../layouts/style.ftl">
<link rel="stylesheet" type="text/css" href="${context}/static/css/plugins/dropload/dropload.css?v=${static_version}" />
</head>

<body>
<div class="viewport">
<#include "../shoppingcart/shopping-cart-float.ftl">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
    <#--<div class="header-ext-box">-->
    <#--<div class="header-search-box-content">-->
    <#--<input type="text" placeholder="搜索店铺内商品"/>-->
    <#--</div>-->
    <#--</div>-->
        <div class="mui-text-center header-ext-box" style="height: 100%;line-height: 44px;">全部商品</div>
        <a id="btnMenu" class="mui-pull-right header-button"><span class="iconfont">&#xe637;</span></a>
    </header>
<#include "../layouts/menu.ftl">
<input type="hidden" id="shopId_" value="${(shop.id)!''}">
    <div class="mui-content ">
     	<div id="listData" class="mui-control-content mui-active">
        <section class="yg-floor">
            <div class="yg-piclist normal">
            <#if voPage.items??>
                <#list voPage.items as commodity >
                    <a class="yg-piclist-item" href="${context}/${(shop.id)!''}/item/${(commodity.no)!''}.sc">
                        <div class="content">
                            <div>
                                <img data-lazyload="${commodity.pictureMb!(context+'/static/images/thum.png')}"/>
                            </div>
                            <p class="item-title">${(commodity.commodityName)!''}</p>
                            <p class="item-price">&yen;${((commodity.wfxPrice)!0)?string("0.00")} <span
                                    class="item-price-source">&yen;${((commodity.publicPrice)!0)?string("0.00")}</span>
                            </p>
                            <#--<#if commodity.isOnsale!=1>-->
                                <div class="yg-goods-off-sale"></div>
                            <#--<#elseif commodity.stock lt 1 >-->
                            <#--<div class="yg-goods-sold-out"></div>-->
                            <#--</#if>-->
                        </div>
                    </a>
                </#list>
            <#else>
                <section class="yg-floor">
                    <div class="title-wrap mui-text-center">
                        <div class="title">该店铺下暂无商品！</div>
                    </div>
                </section>
            </#if>
            </div>
        </section>
        </div>
    </div>
</div>
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/plugins/mui.lazyload.js?v=${static_version}"></script>
<script src="${context}/static/js/plugins/mui.lazyload.img.js?v=${static_version}"></script>
<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script src="${context}/static/js/yg-list-preview.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>