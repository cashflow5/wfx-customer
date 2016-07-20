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
                            <#if commodity.isOnsale!=1>
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