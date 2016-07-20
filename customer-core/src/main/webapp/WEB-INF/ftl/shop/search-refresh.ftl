<div class="yg-piclist normal">
<#if (page.items)??>
    <#list page.items as commodity>
        <a class="yg-piclist-item" href="${context}/${(shop.id)!''}/item/${(commodity.no)!''}.sc">
            <div class="content">
                <img class="yg-pic-commodity" data-lazyload="${context}/static/images/imglist/img-0001-g.jpg"/>
                <p class="item-title">${(commodity.commodityName)!''}</p>
                <p class="item-price">&yen;${(commodity.wfxPrice?string)!''} <span
                        class="item-price-source">&yen;${(commodity.publicPrice?string)!''}</span></p>
            </div>
        </a>
    </#list>
</#if>
</div>