<!DOCTYPE html>
<html>

<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售-<#if (shop.shopCode)=='优购微零售总店'>${(shop.shopCode)!''}<#else>No.${(shop.shopCode)!''}</#if></title>
<#include "../layouts/style.ftl">

</head>

<body>
<div id="pageIndex" class="viewport">
    <div class="mui-content">
    	<section class="yg-floor yg-shop-infos yg-new-style">
			<img class="shop-title-img" src="${context}/static/images/index-bg.png"/>
			<div class="yg-version-info">beta v1.0.0(内部测试)</div>
			<div class="yg-shop-title"><span><#if (shop.shopCode)=='优购微零售总店'>${(shop.shopCode)!''}<#else>No.${(shop.shopCode)!''}</#if></span> <span class="yg-badge-primary">加盟</span>
 		</section>
        <section class="yg-floor">
            <header class="title-wrap">
                <h2 class="title">热卖商品</h2>
            </header>
        <div class="yg-piclist normal">
        <#if hotCommoditys??>
            <#list hotCommoditys as commodity>
                <a class="yg-piclist-item" href="${context}/preview/${(shop.id)!''}/commodity/${(commodity.no)!''}.sc">
                    <div class="content">
                        <div>
                            <img data-lazyload="${commodity.pictureMb!''}"/>
                        </div>
                        <p class="item-title">${(commodity.commodityName)!''}</p>
                        <p class="item-price">&yen;${(commodity.wfxPrice)!0?string("0.00")} <span
                                class="item-price-source">&yen;${(commodity.publicPrice)!0?string("0.00")}</span></p>
                        <#if commodity.isOnsale!=1>
                            <div class="yg-goods-off-sale"></div>
                        <#elseif commodity.stock lt 1 >
                            <div class="yg-goods-sold-out"></div>
                        </#if>
                    </div>

                </a>
            </#list>

        </div>
            <div class="see-all">
                <a class="mui-btn" href="${context}/preview/shop/list/${(shop.id)!''}.sc"> 查看全部商品 </a>
            </div>
        <#else>
            <div class="mui-text-center ">
                <div>该店铺下暂无商品！</div>
            </div>
        </#if>
        </section>
    </div>
</div>
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/plugins/mui.lazyload.js?v=${static_version}"></script>
<script src="${context}/static/js/plugins/mui.lazyload.img.js?v=${static_version}"></script>
<script src="${context}/static/js/yg-index.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>

</body>

</html>