<#if result??&&(result.items)??&&(result.items?size>0)>
	<#list result.items as item>
		<a class="yg-cms-column-1-floor mt10" href="${context}/discover/view/${item.id}.sc">
			<div class="cms-column-item">
				<img src="${item.picCover}" alt="" />
			</div>
			<div class="cms-column-item">
				<p>
				<#if item.classify??&&item.classify!=''><span class="Red">[${item.classify}] </span></#if>
				${item.title}
				</p>
				<p class="date-item">${item.showTime}</p>
			</div>
		</a>
	</#list>
</#if>