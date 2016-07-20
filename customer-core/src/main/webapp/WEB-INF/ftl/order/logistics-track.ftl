<#-- @ftlvariable name="commodityNos" type="java.util.Map<java.lang.String,java.lang.String>" -->
<#-- @ftlvariable name="page" type="java.lang.Integer" -->
<#-- @ftlvariable name="order" type="com.yougou.wfx.customer.model.order.OrderVo" -->
<#-- @ftlvariable name="logistics" type="com.yougou.wfx.customer.model.order.LogisticsVo" -->
<#-- @ftlvariable name="logisticses" type="java.util.List<com.yougou.wfx.customer.model.order.LogisticsVo>" -->
<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_物流跟踪</title>
<#include "../layouts/style.ftl">
</head>
<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">物流跟踪</h1>
    </header>
    <div class="mui-content">
        <section class="yg-nav-tab">
            <div class="nav-tags">
            <#list logisticses as log>
                <a class="nav-tag mui-btn mui-btn-gray <#if (page??)&&(page==(log_index+1))>active</#if>"
                   href="${context}/order/${(order.id)!''}/logistics.sc?page=${log_index+1}">包裹${log_index+1}</a>
            </#list>
            </div>
        </section>
        <section class="yg-box">
            <p><span class="Gray">发货时间：</span><#if (logistics.shipTime)??>${((logistics.shipTime)!'')?string('yyyy-MM-dd HH:mm:ss')}</#if>
            </p>
            <p><span class="Gray">配送公司：</span>${(logistics.logisticsName)!''}（${(logistics.logisticsOrderNo)!''}）</p>
        </section>
    <#if logistics??>
        <section class="border-bottom border-top yg-floor yg-shopping-cart-floor mt10">
            <div class="shopping-cart-list">
                <#list (logistics.styles)![] as style>
                    <div class="shopping-cart-list-item yg-layout-column-left-80"
                         data-shopid="${(order.shopId)!''}"
                         data-commodityid="${(commodityNos[((style.commodityId)!'')])!''}">
                        <div class="yg-layout-left">
                            <img src="${(style.picUrl)!''}" alt="" />
                        </div>
                        <div class="yg-layout-main">
                            <div class="yg-layout-container">
                                <div class="goods-title">${(style.prodName)!''} ${(style.prodSpec)!''}</div>
                                <p class="Gray">X${(style.num)!''}</p>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </section>
        <section class="yg-box">
            <ul class="yg-logistics-track-list ml10">
                <#if logistics.logs?? && (logistics.logs?size>0)>
                    <#list logistics.logs as log>
                        <li class="yg-logistics-track-item selected">
                            <span class="cr"></span>
                            <div class="yg-logistics-track-message">${(log.context)!''}</div>
                            <p class="Gray">${(log.time)!''}</p>
                        </li>
                    </#list>
                <#else >
                    <li class="yg-logistics-track-item selected">
                        <span class="cr"></span>
                        <div class="yg-logistics-track-message">暂无物流信息</div>
                        <p class="Gray">${.now}</p>
                    </li>
                </#if>
            </ul>
        </section>
    </#if>
    </div>
</div>
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/yg-order-logistics.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>