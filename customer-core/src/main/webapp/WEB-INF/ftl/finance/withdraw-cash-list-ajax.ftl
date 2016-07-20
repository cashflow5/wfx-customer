<#if cash??&&(cash.cashing)??&&(cash.cashing.items)??&&(cash.cashing.items?size>0)>
<ul class="mui-table-view" name="cashing">
	<#list cash.cashing.items as cashing>
	<li class="mui-table-view-cell">
		<a class="mui-navigate-right" href="applyCashDetail.sc?id=${cashing.id}">
			<span class="mui-badge">+￥${cashing.withdrawAmount}</span>
			<div>${cashing.withdrawApplyNo}</div>
			<div class="cor-84 f12">${cashing.applyTime?datetime}</div>
		</a>
	</li>
	</#list>
</ul>
</#if>
<#if cash??&&(cash.cashed)??&&(cash.cashed.items)??&&(cash.cashed.items?size>0)>
<ul class="mui-table-view" name="cashed">
	<#list cash.cashed.items as cashed>
	<li class="mui-table-view-cell">
		<a class="mui-navigate-right" href="applyCashDetail.sc?id=${cashed.id}">
			<span class="mui-badge">+￥${cashed.withdrawAmount}</span>
			<div>${cashed.withdrawApplyNo}</div>
			<div class="cor-84 f12">${cashed.applyTime?datetime}</div>
		</a>
	</li>
	</#list>
</ul>
</#if>
<#if cash??&&(cash.cashErr)??&&(cash.cashErr.items)??&&(cash.cashErr.items?size>0)>
<ul class="mui-table-view" name="cashErr">
	<#list cash.cashErr.items as cashErr>
	<li class="mui-table-view-cell">
		<a class="mui-navigate-right" href="applyCashDetail.sc?id=${cashErr.id}">
			<span class="mui-badge">+￥${cashErr.withdrawAmount}</span>
			<div>${cashErr.withdrawApplyNo}</div>
			<div class="cor-84 f12">${cashErr.applyTime?datetime}</div>
		</a>
	</li>
	</#list>
</ul>
</#if>
