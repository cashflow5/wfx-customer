<!DOCTYPE html>
<html>

<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_分类列表</title>
<#include "../layouts/style.ftl">
</head>

<body>
<div class="viewport yg-category-page">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <div class="header-ext-box">
            <div class="header-search-box-content">
                <input type="text" placeholder="搜索店铺内商品"/>
            </div>
        </div>
    </header>
    <div class="mui-content">
        <input type="hidden" id="shopId" value="${shop.id!''}">
        <section class="yg-floor mb0 mt20">
            <div class="yg-layout-column-left-76">
                <div class="yg-layout-left yg-category-tabs">
                <#if shopCats??>
                    <#list shopCats as shopcat>
                        <#if shopcat_index==0>
                            <a class="yg-category-tab active" href="#${shopcat.id}">${shopcat.name!''}</a>
                        <#else>
                            <a class="yg-category-tab" href="#${shopcat.id}">${shopcat.name!''}</a>
                        </#if>
                    </#list>
                </#if>
                </div>
                <div class="yg-layout-main">
                    <div class="yg-layout-container yg-category-list-items">
                    <#if shopCats??>
                        <#list shopCats as shopcat>
                            <#if shopcat_index==0>
                            <div id="${shopcat.id!''}" class="yg-category-item active">
                            <#else>
                            <div id="${shopcat.id!''}" class="yg-category-item">
                            </#if>
                            <#if shopcat.childs??>
                                <#list shopcat.childs as child>
                                    <a href="javascript:;" data-id="${child.id!''}">${child.name!''}</a>
                                </#list>
                            </#if>
                        </div>
                        </#list>
                    <#else>
                        该店铺没有设置分类!
                    </#if>
                    </div>
                    </div>
                </div>
        </section>
    </div>
</div>
<#include "../layouts/index-menu.ftl">
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/yg-category-list.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>

</html>