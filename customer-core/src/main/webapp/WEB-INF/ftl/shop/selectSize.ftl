<div class="yg-add-shopcart-box">
	<section class="yg-floor mb0 relative mui-clearfix">
		<div class="img-goods-100">
			<img style="width:70%;" src="${commodity.defaultPic!''}" />
		</div>
		<p class="detail-price">&yen;${(commodity.wfxPrice!0)?string("0.00")}</p>
		<div id="formExt1" class="yg-layout-column-left mt10 mui-clearfix">
			<div class="yg-layout-left" style="width:35px;">已选:</div>
			<div class="yg-layout-main">
				<div class="yg-layout-container">
				<span id="pdColor1" data-id="${commodity.no!''}">${commodity.specName!''}</span>,
				<span id="pdSize1" data-id=""></span><span id="pdNumber1" style="margin-left:5px;">1</span>件
			</div>
		</div>
		<span id="close" class="iconfont mui-pull-right f18">&#xe64c;</span>
	</section>
	<section class="yg-floor yg-form-floor yg-form-floor1 pd0">
		<form id="formBuy1">
			<div class="yg-layout-column-left border-bottom1 mb10 ml10 mr10 mt10" style="margin-top:5px;">
				<div class="yg-layout-left">颜色</div>
				<div class="yg-layout-main">
					<div class="yg-layout-container">
					<input type="hidden" name="specName" />
					<#if styleCommoditys??>
                        <#list styleCommoditys as styleCommodity>
                        	<#if styleCommodity.stock?? && styleCommodity.stock &gt; 0>
                            <#if styleCommodity.id ==commodity.id>
                                <a class="mui-btn spec-btn active" style="margin-bottom:5px;"
                                   data-value="${styleCommodity.specName}"
                                   data-id="${styleCommodity.no!''}">${styleCommodity.specName}</a>
                            <#else>
                                <a class="mui-btn spec-btn"
                                   data-value="${styleCommodity.specName}" style="margin-bottom:5px;"
                                   data-id="${styleCommodity.no!''}">${styleCommodity.specName}</a>
                            </#if>
                            </#if>
                        </#list>
                    </#if>
                    </div>
				</div>
			</div>
			<div class="yg-layout-column-left border-bottom1 mb10 ml10 mr10">
				<div class="yg-layout-left">尺码</div>
				<div class="yg-layout-main">
					<div class="yg-layout-container">
						<input type="hidden" name="pdSize" />
						<#if (commodity.products)??>
                            <#list commodity.products as product>
                                <#if commodity.isOnsale==1 && product.sellStatus==1 && product.inventoryNum gt 0>
                                    <a class="mui-btn" style="margin-bottom:5px;"
                                       data-value="${product.sizeNo}"
                                       data-id="${product.productNo!''}">${product.sizeName}</a>
                                <#else>
                                    <!--<a class="mui-btn mui-btn-grey mui-disabled"
                                       data-value="${product.sizeNo}"
                                       data-id="${product.productNo!''}">${product.sizeName}</a>-->
                                </#if>
                            </#list>
                        </#if>
					</div>
				</div>
			</div>
			<div class="yg-layout-column-left ml10 mr10 pb10" style="padding-bottom:5px !important;">
				<div class="yg-layout-left">数量</div>
				<div class="yg-layout-main">
					<div class="yg-layout-container">
						<div class="mui-numbox" data-numbox-min="0">
							<button class="mui-btn mui-btn-numbox-minus mui-btn-numbox-minus1" type="button">-</button>
							<input name="pdNumber1" class="mui-input-numbox" type="number" value="1" disabled="disabled"/>
							<button class="mui-btn mui-btn-numbox-plus mui-btn-numbox-plus1" type="button" data-maxCount="${skuMaxCount!'3'}">+</button>
						</div>
					</div>
				</div>
			</div>
			<nav class="mui-bar-tab yg-nav">
				<a id="add-shopcart-sure" class="mui-tab-item yg-nav-btn-buy-now w37p">
					<span class="mui-tab-label">确定</span>
				</a>
			</nav>
		</form>
	</section>
</div>