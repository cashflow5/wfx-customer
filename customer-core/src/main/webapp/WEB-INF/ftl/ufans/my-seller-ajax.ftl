<#if result??&&(result.items)??&&(result.items)?size gt 0 >
 <#list result.items as item>
	<li class="mui-table-view-cell vr-after-pl0">
		<div class="yg-layout-column-left-80 fxs-colum-afterdiaplay">
			<div class="yg-layout-left">
					<a class="fxs-photo" href="${context}/ufans/mySeller/getSubDetail.sc?level=2&subSellerId=${(item.id)!''}">
					<img width="80" height="80" src="${(item.logoUrl)!(context+'/static/images/user-photo.png')}" alt="" /></a>
			</div>
			<div class="yg-layout-main">
				<div class="yg-layout-container mui-row">
					<div class="mui-col-sm-11 mui-col-xs-11">
						<div>No.${(item.shopCode)!''}</div>
						<div class="f12 mt5">时间：<#if (item.regTime)??>${(item.regTime)?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
						<div class="f12 mt5">佣金：<span class="Red"><#if (item.commissionTotalAmountForParent)??>￥${((item.commissionTotalAmountForParent)!0)?string('0.00')}<#else>0</#if></span>&nbsp;&nbsp;分销商：<span class="Red">${(item.subSellerCount)!'0'}</span></div>
					</div>
					<a href="${context}/ufans/mySeller/getLevel3SubList.sc?sellerId=${(item.id)!''}" class="mui-col-sm-1 mui-col-xs-1 fxs-lh80">
						<span class="iconfont Blue f18">&#xe636;</span>
					</a>
				</div>
			</div>
		</div>
	</li>
	
</#list>
</#if>