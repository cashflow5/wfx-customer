<div class="yg-menu">
    <nav>
        <a href="${context}/${shop.id!''}.sc">
            <span class="iconfont">&#xe60b;</span>
            <p class="menu-text">首页</p>
        </a>
	    <#--<a href="javascript:void(0)">-->
	    <#--<span class="iconfont">&#xe622;</span>-->
	    <#--<p class="menu-text">分类搜索</p>-->
	    <#--</a>-->
        <a href="${context}/${shop.id!shopId!''}/shoppingcart.sc">
            <span class="iconfont">&#xe614;</span>
            <p class="menu-text">购物车</p>
        </a>
        <a href="${context}/usercenter.sc">
            <span class="iconfont">&#xe620;</span>
            <p class="menu-text">用户中心</p>
        </a>
    </nav>
</div>