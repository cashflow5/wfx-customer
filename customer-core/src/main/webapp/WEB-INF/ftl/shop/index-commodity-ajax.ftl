 <#if hotCommoditys??>
            <#list hotCommoditys as commodity>
                <a class="yg-piclist-item" href="${context}/${(shopId)!''}/item/${(commodity.no)!''}.sc">
                    <div class="content">
                        <div>
                            <img src="${commodity.pictureMb!(context+'/static/images/thum.png')}"/>
	                        <p class="item-title">${(commodity.commodityName)!''}</p>
	                        <p class="item-price">&yen;${(commodity.wfxPrice!0)?string('0.00')}
	                        	<span class="item-price-source">&yen;${(commodity.publicPrice!0)?string('0.00')}</span>
	                        </p>
                        </div>
                            <div class="yg-goods-off-sale"></div>
                    </div>
                </a>
            </#list>
</#if>