<#if commissionList??&&(commissionList.items1)??&&(commissionList.items1?size>0)>
<ul class="mui-table-view" name="notSettled" >
	<#list commissionList.items1 as notSettledCommission>
	<li class="mui-table-view-cell">
		<a class="mui-navigate-right" href="#" onclick="location.href='/ufans/order/detail.sc?orderId=${notSettledCommission.id}&level=${notSettledCommission.level}'">
			<span class="mui-badge cor-094">
			<#if (notSettledCommission.amount)?number lt 0>
				-
			<#else>
				+
			</#if>
			￥${(((notSettledCommission.amount)?number)?abs)?string('0.00')}</span>
			<div>${notSettledCommission.orderNo}</div>
			<div class="cor-84 f12">${(notSettledCommission.time)?datetime}</div>
		</a>
	</li>
	</#list>
</ul>
</#if>
<#if commissionList??&&(commissionList.items2)??&&(commissionList.items2?size>0)>
<ul class="mui-table-view" name="hasSettled">
	
	<#list commissionList.items2 as hasSettledCommission>
	<li class="mui-table-view-cell">
		<a class="mui-navigate-right" href="#" onclick="location.href='/ufans/order/detail.sc?orderId=${hasSettledCommission.id}&level=${hasSettledCommission.level}'">
			<span class="mui-badge">
			<#if (hasSettledCommission.amount)?number lt 0>
				-
			<#else>
				+
			</#if>
			￥${(((hasSettledCommission.amount)?number)?abs)?string('0.00')}</span>
			<div>${hasSettledCommission.orderNo}</div>
			<div class="cor-84 f12">${(hasSettledCommission.time)?datetime}</div>
		</a>
	</li>
	</#list>
</ul>
</#if>
