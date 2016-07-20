<#if result??&&(result.items)??&&(result.items?size>0)>
	<#list result.items as item>
		<li class="mui-table-view-cell vr-after-pl0">
			<a class="">
				<div class="mui-row">
					<div class="mui-col-sm-2 mui-col-xs-2 mf-pt3">
						<span class="user-head-portrait mr20 lh-44">
							<img src="${(item.headShowImg)!'${context}/static/images/admin-user-photo.png'}"/>
						</span>
					</div>
					<div class="mui-col-sm-10 mui-col-xs-10 lh-44"><span>${(item.visitorName)!''}</span></div>					
				</div>
			</a>
		</li>
	</#list>
</#if>
<#if loginOut??>
${loginOut}	
</#if>
