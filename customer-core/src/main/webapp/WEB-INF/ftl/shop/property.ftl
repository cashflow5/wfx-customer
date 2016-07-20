<li class="mui-table-view-cell" date-id="" date-name="全部">
	<a class="mui-navigate-right" >全部<span class="iconfont">&#xe652;</span></a>
</li>
<#if proList??>
<#list proList as pro>
<li class="mui-table-view-cell" date-id="${pro.id!''}" date-name="${pro.name!''}">
	<a class="mui-navigate-right" >${pro.name!''}<span class="iconfont">&#xe652;</span></a>
</li>
</#list>
</#if>