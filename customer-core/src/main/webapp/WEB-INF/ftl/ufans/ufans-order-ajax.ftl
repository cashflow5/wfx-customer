<#-- @ftlvariable name="pageSearchResult" type="com.yougou.wfx.appserver.vo.PageSearchResult<com.yougou.wfx.appserver.vo.order.OrderSearcher, com.yougou.wfx.appserver.vo.order.OrderInfo>" --><#--ajax获取我的订单列表信息,可以直接用ufans-order.ftl相关内容覆盖-->
<#if (pageSearchResult.items)??&&(pageSearchResult.items)?size gt 0 >
					<#list pageSearchResult.items as order>
                	<section class="yg-floor yg-shopping-cart-floor mt10 no-border-bottom">
 					<a href="${context}/ufans/order/detail.sc?orderId=${(order.id)!''}&level=${(searcher.level)!1}" class="shopping-cart-title">
                    <div class="mui-row">
                        <div class="mui-col-sm-7 mui-col-xs-7">
                            <#if (order.shopCode)??&&(order.shopCode)=='优购微零售总店'>${(order.shopCode)}<#else>No.${(order.shopCode)!''}</#if> 
                        </div>
                        <div class="mui-col-sm-5 mui-col-xs-5 tright">
                        <#--订单状态-->
                            <#if ((order.state_flag)!'')=='待付款'>
                                <span class="Red">${(order.state_flag)!''}</span>
                            <#else>
                            ${(order.state_flag)!''}
                            </#if>
                        </div>
                    </div>
                </a>
                <#if (order.products)??&&(order.products)?size gt 0>
                <div class="shopping-cart-list">
                <#list order.products as product>
                <#--默认最大显示三条数据-->
                    <#if  (product_index lt 3) >
                            <div class="shopping-cart-list-item yg-layout-column-left-80 <#if (order.products)?size != (product_index+1) > my-orderlist-after relative</#if>">
                                <a class="yg-layout-left" href="${context}/ufans/order/detail.sc?orderId=${(order.id)!''}&level=${(searcher.level)!1}" >
                                    <img src="${(product.url)!''}" alt="">
                                </a>
                                <div class="yg-layout-main">
                                    <div class="yg-layout-container mui-row">
                                        <div class="mui-col-sm-8 mui-col-xs-8">
                                            <a class="goods-title" href="${context}/ufans/order/detail.sc?orderId=${(order.id)!''}&level=${(searcher.level)!1}">${(product.describe)!''}</a>
                                           
                                        </div>
                                        <div class="mui-col-sm-4 mui-col-xs-4 tright">
                                            <p>&yen;<#if (product.price)??>${(product.price)}<#else>0</#if></p>
                                            <p class="Gray">x<#if (product.num)??>${(product.num)}<#else>0</#if></p>
                                            
                                        </div>
                                       <#if (product.refundShowStatus)??&&(product.refundShowStatus)=='3'>
                                        	<div class="Yellow pt10"><i class="iconfont"></i> 退款完成
                                            </div>
                                        </#if>
                                        <#if (product.refundShowStatus)??&&(product.refundShowStatus)=='2'>
                                        	<div class="Yellow pt10"><i class="iconfont"></i> 退款中
                                            </div>
                                        </#if>
                                        <div class="tright">
                                        <#if ((order.commissionStatus)!0)!=3><span class="Red">预计佣金：￥<#if (product.commission)??>${(product.commission)}<#else>0</#if></span>
                                       								      <#else><span class="Red">佣金：￥<#if (product.commission)??>${(product.commission)}<#else>0</#if></span></#if>
                                       	</div>
                                    </div>
                                </div>
                            </div>
                    </#if>
                </#list >
				</div>
                </#if>
                <div class="shopping-cart-title border-top confirm-receipt-wrap no-border-bottom">
                    <div class="mui-row">
                        <div class="mui-col-sm-5 mui-col-xs-5 lh-33">
                            实付款：&yen;<#if (order.paying)??>${(order.paying)}<#else>0</#if>
                        </div>
                        <div class="mui-col-sm-7 mui-col-xs-7 tright lh-33">
							<span><#if ((order.commissionStatus)!0)!=3>预计佣金：<#else>佣金：</#if><span class="Red">￥<#if (order.brokerage)??>${(order.brokerage)}<#else>0</#if></span></span>
						</div>
                    </div>
                </div>
                </a>
            </section>
        </#list>
		</#if>