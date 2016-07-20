<#if list??&&(list?size>0)>
<div class="mui-content" name='all'>
	<ul class="mui-table-view mt0">
		<#list list as income>
		<li class="mui-table-view-cell">
			<a class="mui-navigate-right" href="#" onclick="location.href='getIncomingDetail.sc?id=${income.id}'">
				<span class="mui-badge cor-094"><#if income.transactionFlag=='1'>+<#else>-</#if>￥<#if income.incomingType=='1' && income.transactionFlag=='1'>${(income.incomeAmount)?string('0.00')}<#else>${(((income.expendAmount)?number)?abs)?string('0.00')}</#if></span>
				<div><#if income.incomingType=='1'>佣金收入<#else>提现</#if></div>
				<div class="cor-84 f12">${(income.time)?datetime}</div>
			</a>
		</li>
		</#list>		
	</ul>
</div>
<div class="mui-content" name='income'>
	<ul class="mui-table-view mt0">
		<#list list as income>
		<#if income.incomingType=='1'>
		<li class="mui-table-view-cell">
			<a class="mui-navigate-right" href="#" onclick="location.href='getIncomingDetail.sc?id=${income.id}'">
				<span class="mui-badge cor-094"><#if income.transactionFlag=='1'>+<#else>-</#if>￥<#if income.incomingType=='1' && income.transactionFlag=='1'>${(income.incomeAmount)?string('0.00')}<#else>${(((income.expendAmount)?number)?abs)?string('0.00')}</#if></span>
				<div><#if income.incomingType=='1'>佣金收入<#else>提现</#if></div>
				<div class="cor-84 f12">${(income.time)?datetime}</div>
			</a>
		</li>
		</#if>
		</#list>		
	</ul>
</div>
<div class="mui-content" name='expense'>
	<ul class="mui-table-view mt0">
		<#list list as income>
		<#if income.incomingType=='2'>
		<li class="mui-table-view-cell">
			<a class="mui-navigate-right" href="#" onclick="location.href='getIncomingDetail.sc?id=${income.id}'">
				<span class="mui-badge cor-094"><#if income.transactionFlag=='1'>+<#else>-</#if>￥<#if income.incomingType=='1' && income.transactionFlag=='1'>${(income.incomeAmount)?string('0.00')}<#else>${(((income.expendAmount)?number)?abs)?string('0.00')}</#if></span>
				<div><#if income.incomingType=='1'>佣金收入<#else>提现</#if></div>
				<div class="cor-84 f12">${(income.time)?datetime}</div>
			</a>
		</li>
		</#if>
		</#list>		
	</ul>
</div>
</#if>
	
	