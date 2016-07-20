<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_详细信息</title>
<#include "../layouts/style.ftl">
</head>
<body>
<div class="viewport">
<#if isPreview>
    <header class="mui-bar yg-header header-ext">
        <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <div class="header-ext-box">
            <div class="header-nav-box-content">
                <div class="yg-nav-bar"><a href="#goodsPhoto" class="yg-nav-bar-item active">商品</a><a
                        href="#goodsDetail" class="yg-nav-bar-item">详情</a></div>
            </div>
        </div>
    </header>
</#if>
    <div class="mui-content">
        <div id="goodsPhoto" class="mui-slider">
            <div class="mui-slider-group">
            <#if (commodity.pictures)??>
                <#list commodity.pictures as picture>
                    <div class="mui-slider-item">
                        <a href="#"><img src="${picture.url}" alt=""/></a>
                    </div>
                </#list>
            </#if>
            </div>
            <div class="mui-slider-indicator">
            <#if (commodity.pictures)??>
                <#list commodity.pictures as picture>
                    <#if picture_index==0>
                        <div class="mui-indicator mui-active"></div>
                    <#else >
                        <div class="mui-indicator"></div>
                    </#if>
                </#list>
            </#if>
            </div>
        <#if commodity.isOnsale!=1>
            <div class="yg-goods-off-sale"></div>
        <#elseif commodity.stock lt 1 >
            <div class="yg-goods-sold-out"></div>
        </#if>

        </div>
        <section class="yg-floor mb0">
            <input type="hidden" id="shopId" value="${shop.id!''}">
            <input type="hidden" id="commodityId" value="${commodity.id!''}">
            <h3>${(commodity.commodityName)!''}</h3>
            <p class="detail-price">&yen;${(commodity.wfxPrice)!0?string("0.00")}</p>
            <p class="detail-price-source">市场价：&yen;${(commodity.publicPrice)!0?string("0.00")}</p>
        </section>
        <section class="yg-shop-certificate-floor mui-row">
            <div class="mui-col-sm-3 mui-col-xs-3"><i class="iconfont Yellow">&#xe603;</i><span>官方认证</span></div>
            <div class="mui-col-sm-3 mui-col-xs-3"><i class="iconfont Blue">&#xe600;</i><span>担保交易</span></div>
            <div class="mui-col-sm-3 mui-col-xs-3"><i class="iconfont Red">&#xe601;</i><span>正品保证</span></div>
            <div class="mui-col-sm-3 mui-col-xs-3"><i class="iconfont Green">&#xe603;</i><span>七天退货</span></div>
        </section>
        <section class="yg-floor yg-form-floor">
            <div id="formExt" class="yg-layout-column-left mt10">
                <div class="yg-layout-left">已选</div>
                <div class="yg-layout-main">
                    <div class="yg-layout-container">
                        <span id="pdColor" data-id="${commodity.no!''}">${commodity.specName!''}</span>
                        <span id="pdSize" data-id=""></span>
                        <span id="pdNumber">1</span>件 <i class="iconfont formExt">&#xe61e;</i></div>
                </div>
            </div>
            <form id="formBuy">
                <div class="yg-layout-column-left mt10">
                    <div class="yg-layout-left">颜色</div>
                    <div class="yg-layout-main">
                        <div class="yg-layout-container">
                            <input type="hidden" name="specName"/>
                        <#if styleCommoditys??>
                            <#list styleCommoditys as styleCommodity>
                                <#if styleCommodity.id ==commodity.id>
                                    <a class="mui-btn spec-btn active"
                                       data-value="${styleCommodity.specName}"
                                       data-id="${styleCommodity.no!''}">${styleCommodity.specName}</a>
                                <#else>
                                    <a class="mui-btn spec-btn"
                                       href="${context}/preview/${shop.id!''}/commodity/${styleCommodity.no}.sc"
                                       data-value="${styleCommodity.specName}"
                                       data-id="${styleCommodity.no!''}">${styleCommodity.specName}</a>
                                </#if>
                            </#list>
                        </#if>
                        </div>
                    </div>
                </div>
                <div class="yg-layout-column-left">
                    <div class="yg-layout-left">尺寸</div>
                    <div class="yg-layout-main">
                        <div class="yg-layout-container">
                            <input type="hidden" name="pdSize"/>
                        <#if (commodity.products)??>
                            <#list commodity.products as product>
                                <#if product.sellStatus==1 && product.inventoryNum gt 0>
                                    <a class="mui-btn"
                                       data-value="${product.sizeNo}"
                                       data-id="${product.productNo!''}">${product.sizeName}</a>
                                <#elseif product.sellStatus==0 || product.inventoryNum lte 0>
                                    <a class="mui-btn mui-btn-grey mui-disabled"
                                       data-value="${product.sizeNo}"
                                       data-id="${product.productNo!''}">${product.sizeName}</a>
                                </#if>
                            </#list>
                        </#if>
                        </div>
                    </div>
                </div>
                <div class="yg-layout-column-left">
                    <div class="yg-layout-left">数量</div>
                    <div class="yg-layout-main">
                        <div class="yg-layout-container">
                            <div class="mui-numbox" data-numbox-min="1" data-numbox-max="1">
                                <button class="mui-btn mui-btn-numbox-minus" type="button" disabled>-</button>
                                <input name="pdNumber" class="mui-input-numbox" type="number" disabled/>
                                <button class="mui-btn mui-btn-numbox-plus" type="button" disabled>+</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </section>
        <section class="yg-floor yg-service-info-floor">
            <#--<p><span class="Gray">运费</span>店铺单笔订单满99元免运费</p>-->
            <p><span class="Gray">服务</span>由优购发货并提供售后</p>
            <p><span class="Gray">提示</span>支持7天无理由退货</p>
        </section>
        <section class="yg-floor yg-shop-detial-floor">
			<div class="yg-layout-column-left-80">
				<div class="yg-layout-left">
					<div class="user-photo">
						<img src="${(shop.logoUrl)!''}" alt="" style="height: 80px;width: 80px;"/>
					</div>
				</div>
				<div class="yg-layout-main">
					<div class="yg-layout-container">
						<a href="<#if isPreview>javascript:void(0);<#else>${context}/preview/shop/${(shop.id)!''}.sc</#if>" class="shop-title">
							<span><#if (shop.shopCode)=='优购微零售总店'>${(shop.shopCode)!''}<#else>No.${(shop.shopCode)!''}</#if> <span class="yg-badge-primary">加盟</span>
							<i class="iconfont Gray"></i>
						</a>
					</div>
				</div>
			</div>
			<!--<div class="mui-row mt10">
				<div class="mui-col-sm-6 mui-col-xs-6">
					<a href="category-list.shtml" class="mui-btn"><i class="iconfont">&#xe622;</i> 查看商品分类</a>
				</div>
				<div class="mui-col-sm-6 mui-col-xs-6">
					<a href="index.shtml" class="mui-btn"><i class="iconfont Red">&#xe60e;</i> 进入店铺</a>
				</div>
			</div>-->
		</section>
        <section class="yg-floor yg-shop-detial-new">
			<img src="${context}/static/images/banner.png"/>
		</section>
        <section id="goodsDetail" class="yg-floor yg-detail-img-floor">
        	${(commodity.prodDesc)!''}
        </section>
    </div>
</div>
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/plugins/mui.lazyload.js?v=${static_version}"></script>
<script src="${context}/static/js/plugins/mui.lazyload.img.js?v=${static_version}"></script>
</body>

</html>