<div class="yg-info-notes-box">
    <section class="yg-floor yg-form-floor pd0">
		<#if cortext??>
		<div class="info-notes ml10 mr10">
			<p class="border-bottom f14">${cortext.name!'抱歉，未查到该皮质的名称'}</p>
			<p class="Gray f12">${cortext.description!'抱歉，未查到该皮质的描述'}</p>
			<!--<p><img src="${context}/static/images/niupi.png" /></p>-->
		</div>
		<#else> 
		<div class="info-notes ml10 mr10">
			<p class="border-bottom f14">报歉，未查到该皮质的说明信息</p>
		</div>
		</#if>
		<#--<nav class="mui-bar-tab yg-nav">
			<a id="add-shopcart-sure" class="mui-tab-item yg-nav-btn-buy-now w37p">
				<span class="mui-tab-label">关闭</span>
			</a>
		</nav>-->
	</section>
</div>