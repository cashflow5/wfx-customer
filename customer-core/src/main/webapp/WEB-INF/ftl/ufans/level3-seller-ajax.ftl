<#if result??&&(result.items)??&&(result.items)?size gt 0 >
<#list result.items as item>
<li class="mui-table-view-cell vr-after-pl0">
	<input type='hidden' id='subSellerId' value='${(subSellerId)!''}'></input>
	<div class="yg-layout-column-left-80 fxs-colum-afterdiaplay">
		<div class="yg-layout-left">
			<div class="fxs-photo"><a href="${context}/ufans/mySeller/getSubDetail.sc?level=3&subSellerId=${(item.id)!''}" >
			<img width="80" height="80" src="${(item.logoUrl)!(context+'/static/images/user-photo.png')}" 
			alt="" /></a>
			</div>
		</div>
		<div class="yg-layout-main">
			<div class="yg-layout-container mui-row">
				<div class="mui-col-sm-11 mui-col-xs-11">
					<div>No.${(item.shopCode)!''}</div>
					<div class="f12 mt5">时间：<#if (item.regTime)??>${(item.regTime)?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
					<div class="f12 mt5">佣金：<span class="Red"><#if (item.commissionTotalAmountForParent)??>￥${((item.commissionTotalAmountForParent)!0)?string('0.00')}<#else>0</#if></span></div>
				</div>
				
			</div>
		</div>

	</div>
</li>

</#list>
 
</#if>
						  