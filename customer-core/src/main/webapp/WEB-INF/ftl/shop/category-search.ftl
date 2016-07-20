<!DOCTYPE html>
<html>

<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_商品列表</title>
<#include "../layouts/style.ftl">
    <link rel="stylesheet" type="text/css" href="${context}/static/css/plugins/dropload/dropload.css?v=${static_version}"/>
</head>

<body>
<div class="viewport">
<#include "../shoppingcart/shopping-cart-float.ftl">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <div class="header-ext-box">
            <div class="header-nav-box-content">
                <div class="yg-nav-bar">
                    <a href="#goodsDetail" class="yg-nav-bar-item">浅口鞋</a></div>
            </div>
        </div>
        <a id="btnMenu" class="mui-pull-right header-button"><span class="iconfont">&#xe637;</span></a>
    </header>

<#include "../layouts/menu.ftl">
    <input type="hidden" id="categoryId" value="${categoryId!''}">
    <input type="hidden" id="shopId" value="${(shop.id)!''}">
    <div class="mui-content">
        <section class="yg-floor">
            <div class="yg-piclist normal">
            <#if (page.items)??>
                <#list page.items as commodity>
                    <a class="yg-piclist-item" href="${context}/${(shop.id)!''}/item/${(commodity.no)!''}.sc">
                        <div class="content">
                            <div>
                                <img class="yg-pic-commodity"
                                     data-lazyload="${context}/static/images/imglist/img-0001-g.jpg"/>
                            </div>
                            <p class="item-title">${(commodity.commodityName)!''}</p>
                            <p class="item-price">&yen;${((commodity.wfxPrice!0)?string("0.00"))!''} <span
                                    class="item-price-source">&yen;${(commodity.publicPrice!0)?string("0.00")!''}</span></p>
                            <#if commodity.isOnsale==0>
                                <div class="yg-goods-off-sale"></div>
                            <#elseif commodity.stock lt 1 >
                                <div class="yg-goods-sold-out"></div>
                            </#if>
                        </div>
                    </a>
                </#list>
            </#if>
            </div>
        </section>
    </div>
</div>

<#include "../layouts/index-menu.ftl">
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/plugins/mui.lazyload.js?v=${static_version}"></script>
<script src="${context}/static/js/plugins/mui.lazyload.img.js?v=${static_version}"></script>
<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script src="${context}/static/js/yg-list-category.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>

</html>