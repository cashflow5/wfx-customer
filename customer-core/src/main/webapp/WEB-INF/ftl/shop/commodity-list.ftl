<!DOCTYPE html>
<html>

<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_商品列表</title>
<#include "../layouts/style.ftl">
<link rel="stylesheet" type="text/css" href="${context}/static/css/productListAndDetail.css?v=${static_version}" />
<script>
	var commStyle = '${search.commStyle!''}';
</script>
</head>
    <body>
        <div id="offCanvasWrapper" class="mui-off-canvas-wrap mui-draggable viewport pro-detail-viewport yg-product-list-new">
        <!--菜单部分-->
        <#--<#include "../shoppingcart/shopping-cart-float.ftl">-->
        <aside id="offCanvasSide" class="mui-off-canvas-right yg-fiter-aside">
            <form id="yg-list-filter" action="${context}/${shop.id!''}/list.sc" method="post">
            <!-- <input type="hidden" name="saleQuantityOrder" id="saleQuantityOrder" value="">
            <input type="hidden" name="updateOrder" id="updateOrder" value="">
            <input type="hidden" name="priceOrder" id="priceOrder" value=""> -->
            <div class="yg-aside-title yg-aside-title-one">
                <span id="mui-btn-outlined" class="mui-btn mui-btn-outlined" >清空筛选</span>
                <span id="mui-btn-comfig-form" class="mui-btn mui-btn-comfig" >确认</span>
                <span id="offCanvasHide" class="yg-aside-close f16" >X</span>
            </div>
            <div class="yg-aside-title yg-aside-title-two">
                <p id="showCat">分类</p>
                <span id="mui-btn-comfig-classify" class="mui-btn mui-btn-comfig" >确认</span>
                <span id="yg-aside-filter-back" class="yg-aside-close iconfont" >&#xe61d;</span>
            </div>
            <div id="offCanvasSideScroll" class="mui-scroll-wrapper yg-aside-scroll-wrapper">
                <div class="mui-scroll">
                    <div class="content">
                        <ul class="mui-table-view yg-filter-aside-ul">
                            <li class="mui-table-view-cell">
                                <a class="mui-navigate-right" date-id="catgory" data-name="分类">分类<em>全部</em></a>
                                <input type="hidden" class="currentInput" data-true="${search.catgory!''}" value="${search.catgory!''}" name="catgory" >
                                <input type="hidden" class="currentInputName" data-true="${search.catgoryName!''}" value="${search.catgoryName!''}" name="catgoryName" >
                                <input type="hidden" data-true="${search.catId!''}" value="${search.catId!''}" name="catId" >
            					<input type="hidden" data-true="${search.commStyle!''}" value="${search.commStyle!''}" class="commStyle" name="commStyle" >
                            <#if search.brandNo?? && search.brandNo != ''>
                            	<input type="hidden" data-true="${search.brandNo!''}" value="${search.brandNo!''}" name="brandNo" >
                            </li>
                            <#else>
                            </li>
                            <li class="mui-table-view-cell">
                                <a class="mui-navigate-right" date-id="brand" data-name="品牌">品牌<em>全部</em></a>
                                <input type="hidden" class="currentInput" data-true="${search.brand!''}" value="${search.brand!''}" name="brand" >
                                <input type="hidden" class="currentInputName" data-true="${search.brandName!''}" value="${search.brandName!''}" name="brandName" >
                            </li>
                            </#if>
                            <li class="mui-table-view-cell">
                                <a class="mui-navigate-right" date-id="sex" data-name="性别">性别<em>全部</em></a>
                                <input type="hidden" class="currentInput" data-true="${search.sex!''}" value="${search.sex!''}" name="sex" >
                                 <input type="hidden" class="currentInputName" data-true="${search.sexName!''}" value="${search.sexName!''}" name="sexName" >
                            </li>
                            <li class="mui-table-view-cell">
                                <a class="mui-navigate-right" date-id="price" data-name="价格">价格<em>全部</em></a>
                                <input type="hidden" class="currentInput" data-true="${search.price!''}" value="${search.price!''}" name="price" >
                                <input type="hidden" class="currentInputName" data-true="${search.priceName!''}" value="${search.priceName!''}" name="priceName" >
                            </li>
                            <li class="mui-table-view-cell">
                                <a class="mui-navigate-right" date-id="size" data-name="尺码">尺码<em>全部</em></a>
                                <input type="hidden" class="currentInput" data-true="${search.size!''}" value="${search.size!''}" name="size" >
                                <input type="hidden" class="currentInputName" data-true="${search.sizeName!''}" value="${search.sizeName!''}" name="sizeName" >
                            </li>
                            <li class="mui-table-view-cell">
                                <a class="mui-navigate-right" date-id="color" data-name="颜色">颜色<em>全部</em></a>
                                <input type="hidden" class="currentInput" data-true="${search.color!''}" value="${search.color!''}" name="color" >
                                <input type="hidden" class="currentInputName" data-true="${search.colorName!''}" value="${search.colorName!''}" name="colorName" >
                            </li>
                            <#if styleCount &gt; 0>
                            <li class="mui-table-view-cell">
                                <a class="mui-navigate-right" date-id="style" data-name="款式">款式<em>全部</em></a>
                                <input type="hidden" class="currentInput" data-true="${search.style!''}" value="${search.style!''}" name="style" >
                                <input type="hidden" class="currentInputName" data-true="${search.styleName!''}" value="${search.styleName!''}" name="styleName" >
                            </li>
                            </#if>
                            <#if mannerCount &gt; 0>
                            <li class="mui-table-view-cell">
                                <a class="mui-navigate-right" date-id="manner" data-name="风格">风格<em>全部</em></a>
                                <input type="hidden" class="currentInput" data-true="${search.manner!''}" value="${search.manner!''}" name="manner" >
                                <input type="hidden" class="currentInputName" data-true="${search.mannerName!''}" value="${search.mannerName!''}" name="mannerName" >
                                <input type="hidden" value="1" name="page" >
                            </li>
                            </#if>
                        </ul>
                        <div class="yg-filter-aside-ul-detail-wrap">
                            <ul class="mui-table-view yg-filter-aside-ul-detail" date-more="true">
                                
                            </ul>
                            <#if search.brandNo?? && search.brandNo != ''>
                            <ul class="mui-table-view yg-filter-aside-ul-detail" date-more="true">
                                
                            </ul>
                            </#if>
                            <ul class="mui-table-view yg-filter-aside-ul-detail" date-more="true">
                                
                            </ul>
                            <ul class="mui-table-view yg-filter-aside-ul-detail" date-more="true">
                                
                            </ul>
                            <ul class="mui-table-view yg-filter-aside-ul-detail" date-more="true">
                                
                            </ul>
                            <ul class="mui-table-view yg-filter-aside-ul-detail" date-more="true">
                                
                            </ul>
                            <ul class="mui-table-view yg-filter-aside-ul-detail" date-more="true">
                                
                            </ul>
                            <#if styleCount &gt; 0>
                            <ul class="mui-table-view yg-filter-aside-ul-detail" date-more="true">
                                
                            </ul>
                            </#if>
                            <#if mannerCount &gt; 0>
                            <ul class="mui-table-view yg-filter-aside-ul-detail" date-more="true">
                                
                            </ul>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
            </form>
        </aside>

        <div class="mui-inner-wrap" >
            <header class="mui-bar mui-bar-nav yg-pro-detail-bar-nav">
                <!-- <a class="mui-action-back mui-btn mui-pull-left"></a> -->
                <#if running_environment != '4'>
                <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
                </#if>
                <h1 class="mui-title f16">商品列表</h1>
                <a id="yg-img-sb-tab" class="mui-btn mui-pull-right f12 yg-img-sb-tab-big-img" href="javascript:;"></a>
                <a class="iconfont header-button jumpLink"  href="/index.sc"></a>
            </header>
            <div class="yg-pro-detail-nav-w">
                <div class="mui-segmented-control yg-pro-detail-nav">
                    <a class="mui-control-item f12 <#if search.saleQuantityOrder?? && (search.saleQuantityOrder == 'desc')>mui-activeNew</#if>" href="#">销量</a>
                    <a class="mui-control-item f12 <#if search.updateOrder?? && (search.updateOrder == 'desc')>mui-activeNew</#if>" href="#">新品</a>
                    <a class="mui-control-item f12 <#if search.popularity?? && (search.popularity == 'desc')>mui-activeNew</#if>" href="#">人气</a>
                    <#if search.priceOrder?? && (search.priceOrder == 'asc' || search.priceOrder == 'desc')>
                        <#if search.priceOrder == 'desc'>
                            <a class="mui-control-item yg-pro-detail-price f12 mui-activeNew" href="#">价格<img src="${context}/static/images/prodetail/yg-price-down.png" /></a>
                        <#else>
                            <a class="mui-control-item yg-pro-detail-price f12 mui-activeNew" href="#">价格<img src="${context}/static/images/prodetail/yg-price-up.png" /></a>
                        </#if>
                    <#else>
                    	<a class="mui-control-item yg-pro-detail-price f12" href="#">价格<img src="${context}/static/images/prodetail/yg-price.png" /></a>
                    </#if>
                    <a id="offCanvasShow" class="mui-control-item mui-control-item-filter f12" href="#">筛选<img src="${context}/static/images/prodetail/yg-filter.png" /><em></em></a>
                </div>
                <div class="yg-pro-detail-filter" id="yg-pro-detail-filter">
                    <div class="mui-scroll-wrapper yg-pro-detail-filter-w" id="yg-pro-detail-filter-w">
                        <div class="mui-scroll"></div>
                    </div>
                    <span class="f12 yg-pro-detail-filter-clear">清除</span>
                </div>
            </div>
            <input type="hidden" id="shopId_" value="${(shop.id)!''}">
            <div id="offCanvasContentScroll" class="mui-content yg-pro-detail-content mui-scroll-wrapper">
                <#if voPage.items?? && voPage.items?size &gt; 0>
                <div class="mui-scroll">
                    <div class="mui-content yg-pro-detail-list">
                        <ul class="mui-table-view yg-pro-detail-view yg-pro-detail-view-two" id="yg-pro-detail-view">
                                <#list voPage.items as commodity >
                                <li class="mui-table-view-cell mui-media">
                                <a href="${context}/${(shop.id)!''}/item/${(commodity.no)!''}.sc">
                                    <img data-lazyload="${commodity.pictureMb!(context+'/static/images/thum.png')}" class="mui-media-object mui-pull-left"  src="">
                                    <div class="mui-media-body">
                                        <p class="mui-ellipsis">${(commodity.commodityName)!''}</p>
                                        <span class="pro-detail-price f12"><em class="f14">&yen;${((commodity.wfxPrice)!0)?string("0.00")}</em><del>&yen;${((commodity.publicPrice)!0)?string("0.00")}</del></span>
                                    </div>
                                </a></li>
                                </#list>
                        </ul>
                        <div id="morePageDivId"></div>
                        <div class="mui-pull-bottom-pocket mui-block mui-visibility loading-hide"><div class="mui-pull"><div class="mui-pull-loading mui-icon mui-spinner mui-visibility"></div><div class="mui-pull-caption mui-pull-caption-refresh">加载中...</div></div></div>
                    </div>
                </div>
                <#else>
                    <div class="yg-pro-detail-nomore"><img src="${context}/static/images/prodetail/nothing.png" />抱歉，没有找到相关商品</br>请更换筛选条件</div> 
                </#if>

            </div>
            <!-- off-canvas backdrop -->
            <div class="mui-off-canvas-backdrop"></div>
        </div>
        </div>
        <form id="yg-list-filter-true" action="${context}/${shop.id!''}/list.sc" method="post">
            <input type="hidden" value="${search.catgory!''}" name="catgory" >
            <input type="hidden" value="${search.catgoryName!''}" name="catgoryName" >
            <input type="hidden" value="${search.catId!''}" id="catId" name="catId" >
            <#if search.brandNo?? && search.brandNo != ''>
                <input type="hidden" value="${search.brandNo!''}" id="brandNo" name="brandNo" >
            <#else>
                <input type="hidden" value="${search.brand!''}" name="brand" >
                <input type="hidden" value="${search.brandName!''}" name="brandName" >
            </#if>
            <input type="hidden" value="${search.sex!''}" name="sex" >
            <input type="hidden" value="${search.sexName!''}" name="sexName" >
            <input type="hidden" value="${search.price!''}" name="price" >
            <input type="hidden" value="${search.priceName!''}" name="priceName" >
            <input type="hidden" value="${search.size!''}" name="size" >
            <input type="hidden" value="${search.sizeName!''}" name="sizeName" >
            <input type="hidden" value="${search.color!''}" name="color" >
            <input type="hidden" value="${search.colorName!''}" name="colorName" >
            <input type="hidden" value="${search.style!''}" name="style" >
            <input type="hidden" value="${search.styleName!''}" name="styleName" >
            <input type="hidden" value="${search.manner!''}" name="manner" >
            <input type="hidden" value="${search.mannerName!''}" name="mannerName" >
            <input type="hidden" value="${search.commStyle!''}" class="commStyle" name="commStyle" >
        </form>
        <#include "../layouts/corejs.ftl">
<script>
 var shareobj={  
	   title:"", // 分享标题
	   desc:'', // 分享描述
       link:'', // 分享链接
       imgUrl:'', // 分享图标
       type:'link',
       success: function () {},
       cancel: function () {}
	 }; 
wx.ready(function () {
	    //分享到朋友圈
	    wx.onMenuShareTimeline(shareobj); 
	     //分享给朋友
         wx.onMenuShareAppMessage(shareobj);
	     //分享qq
	     wx.onMenuShareQQ(shareobj);
	     //分享到腾讯微博
	     wx.onMenuShareWeibo(shareobj);
	     //分享到QQ空间
	     wx.onMenuShareQZone(shareobj);
	     wx.showOptionMenu();
	   }); 
</script>
        <script src="${context}/static/js/plugins/mui.lazyload.js?v=${static_version}"></script>
        <script src="${context}/static/js/plugins/mui.lazyload.img.js?v=${static_version}"></script>
        <script src="${context}/static/js/yg-product-list.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
    </body>

</html>