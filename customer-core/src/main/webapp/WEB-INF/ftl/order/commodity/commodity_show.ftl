<!DOCTYPE html>
<html>

<head>
<#include "../../layouts/meta.ftl">
	<title>优购微零售_商品清单</title>
<#include "../../layouts/style.ftl">
</head>

<body class="yg-pull-refresh">
<div id="pageList" class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">购买的商品</h1>
        <a class="mui-pull-right header-button" href="javascript:;">共${total!0}件</a>
    </header>
    <div class="mui-content">
	    <section class="yg-floor yg-shopping-cart-floor">
        	<#if commoditys?? && commoditys?size gt 0>
                <#list commoditys as commodity>
                <div class="shopping-cart-list">
					<div class="shopping-cart-list-item yg-layout-column-left-80">
						<div class="yg-layout-left">
							<img src="${(commodity.imageUrl)!''}" alt="">
						</div>
						<div class="yg-layout-main">
							<div class="yg-layout-container">
								<div class="mui-row">
									<div class="mui-col-sm-9 mui-col-xs-9">
										<div class="goods-title line-one">${(commodity.name)!''}</div>
                                        <div class="goods-title line-one"><span class="Gray">颜色：${(commodity.specName)!''}  尺码：${(commodity.size)!''}</span></div>
									</div>
									<div class="mui-col-sm-3 mui-col-xs-3 pl5">
										<span class="Red">&yen;${((commodity.wfxPrice)!0)?string("0.00")}</span>
									</div>
								</div>
								<p class="Gray">&yen;${((commodity.wfxPrice)!0)?string("0.00")} * ${(commodity.count)!0}</p>
							</div>
						</div>
					</div>
				</div>
                </#list>
            </#if>
		</section>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
</body>
</html>