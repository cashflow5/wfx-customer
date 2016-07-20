<#if voPage.items??>
    <#list voPage.items as commodity >
    <li class="mui-table-view-cell mui-media">
    <a href="${context}/${(shop.id)!''}/item/${(commodity.no)!''}.sc">
        <img data-lazyload="${commodity.pictureMb!(context+'/static/images/thum.png')}" class="mui-media-object mui-pull-left"  src="${commodity.pictureMb!(context+'/static/images/thum.png')}">
        <div class="mui-media-body">
            <p class="mui-ellipsis">${(commodity.commodityName)!''}</p>
            <span class="pro-detail-price f12"><em class="f14">&yen;${((commodity.wfxPrice)!0)?string("0.00")}</em><del>&yen;${((commodity.publicPrice)!0)?string("0.00")}</del></span>
        </div>
    </a></li>
    </#list>
</#if>
