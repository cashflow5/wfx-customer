<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_我的收益_提现明细</title>
<#include "../layouts/style.ftl">
</head>
<body>
<div class="viewport">
	<header class="mui-bar yg-header header-ext">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">提现明细</h1>
	</header>
	<!--#include file="layouts/menu.shtml"-->
	<div class="mui-content yg-commission-detail">
		<nav class="mui-bar-tab yg-bar f12">
			<a class="mui-tab-item link-jump" href="#" id='cashing'>
				<div class="mui-tab-label">￥${cash.cashing.amount}</div>
				<span class="mui-tab-label">提现中</span>
			</a>
			<a class="mui-tab-item link-jump cor-252525" href="#" id='cashed'>
				<div class="mui-tab-label">￥${cash.cashed.amount}</div>
				<span class="mui-tab-label">已提现</span>
			</a>
			<a class="mui-tab-item link-jump cor-252525" href="#" id='cashErr'>
				<div class="mui-tab-label">￥${cash.cashErr.amount}</div>
				<span class="mui-tab-label">提现失败</span>
			</a>
		</nav>
		<ul class="mui-table-view mt10 c-tab-view">
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right" href="#">
					<span class="mui-badge">提现金额</span> 提现申请单
				</a>
			</li>
		</ul>
		<div id="listData">
		<ul class="mui-table-view" name="cashing">
		<#if cash??&&(cash.cashing)??&&(cash.cashing.items)??&&(cash.cashing.items?size>0)>
			<#list cash.cashing.items as cashing>
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right" href="applyCashDetail.sc?id=${cashing.id}">
					<span class="mui-badge">+￥${cashing.withdrawAmount}</span>
					<div>${cashing.withdrawApplyNo}</div>
					<div class="cor-84 f12">${cashing.applyTime?datetime}</div>
				</a>
			</li>
			</#list>
			<#else>
			<li>
               <div class="yg-pro-detail-nomore pt10 pb10" style="text-align:center;"><img src="${context}/static/images/prodetail/nothing.png" style="width:30px"><br>没有相关信息</div>
            </li>
		</#if>
		</ul>
		<ul class="mui-table-view" name="cashed">
		<#if cash??&&(cash.cashed)??&&(cash.cashed.items)??&&(cash.cashed.items?size>0)>
			<#list cash.cashed.items as cashed>
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right" href="applyCashDetail.sc?id=${cashed.id}">
					<span class="mui-badge">+￥${cashed.withdrawAmount}</span>
					<div>${cashed.withdrawApplyNo}</div>
					<div class="cor-84 f12">${cashed.applyTime?datetime}</div>
				</a>
			</li>
			</#list>
			<#else>
			<li>
               <div class="yg-pro-detail-nomore pt10 pb10" style="text-align:center;"><img src="${context}/static/images/prodetail/nothing.png" style="width:30px"><br>没有相关信息</div>
            </li>
		</#if>
		</ul>
		<ul class="mui-table-view" name="cashErr">
		<#if cash??&&(cash.cashErr)??&&(cash.cashErr.items)??&&(cash.cashErr.items?size>0)>
			<#list cash.cashErr.items as cashErr>
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right" href="applyCashDetail.sc?id=${cashErr.id}">
					<span class="mui-badge">+￥${cashErr.withdrawAmount}</span>
					<div>${cashErr.withdrawApplyNo}</div>
					<div class="cor-84 f12">${cashErr.applyTime?datetime}</div>
				</a>
			</li>
			</#list>
			<#else>
			<li>
               <div class="yg-pro-detail-nomore pt10 pb10" style="text-align:center;"><img src="${context}/static/images/prodetail/nothing.png" style="width:30px"><br>没有相关信息</div>
            </li>
		</#if>
		</ul>
		</div>
	</div>
</div>
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/jquery-3.0.0.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="${context}/static/js/yg-withdraw-cash-detail.js?v=${static_version}"></script>
<script type="text/javascript">
    var tab = '${tab}';
    if(tab == '1'){
		$("ul[name='cashing']").hide();
    }else{
    	$("ul[name='cashed']").hide();
    }
	$("ul[name='cashErr']").hide();
	
    var errorMsg = '${errorMsg!''}';
</script>
</body>
</html>