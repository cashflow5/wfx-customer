<nav id="indexMenu" class="mui-bar mui-bar-tab">
	<a class="mui-tab-item wx-item <#if current_menu_id?? && (current_menu_id == 'shop')>mui-active</#if>" href="${context}/index.sc"> 
		<span class="iconfont">&#xe60e;</span>
		<span class="mui-tab-label">商城</span>
	</a>
	<a class="mui-tab-item <#if current_menu_id?? && (current_menu_id == 'fans')>mui-active</#if>" href="${context}/ufans/home.sc">
		<span class="iconfont">&#xe696;</span>
		<span class="mui-tab-label">优粉</span>
	</a>
	<a class="mui-tab-item wx-item <#if current_menu_id?? && (current_menu_id == 'discover')>mui-active</#if>" href="${context}/discover/home.sc?shopId=${current_shop_id!''}"> 
		<span class="iconfont">&#xe699;</span>
		<span class="mui-tab-label">发现</span>
	</a>
	<a class="mui-tab-item <#if current_menu_id?? && (current_menu_id == 'shopcart')>mui-active</#if>" href="${context}/shoppingcart.sc?from=bar">
		<span class="iconfont icon-gouwuche3"><span id="shopcartTotal-Menu" class="mui-badge mui-badge-bby">0</span></span>
		<span class="mui-tab-label">购物车</span>
	</a>
	<a class="mui-tab-item <#if current_menu_id?? && (current_menu_id == 'mine')>mui-active</#if>" href="${context}/usercenter.sc">
		<span class="iconfont">&#xe697;</span>
		<span class="mui-tab-label">我的</span>
	</a>
</nav>
