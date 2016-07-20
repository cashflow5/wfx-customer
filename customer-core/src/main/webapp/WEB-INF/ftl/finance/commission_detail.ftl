<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_我的收益</title>
<#include "../layouts/style.ftl">
<link rel="stylesheet" type="text/css" href="${context}/static/css/plugins/dropload/dropload.css?v=${static_version}" />
</head>
<body>
<div class="viewport">
	<header class="mui-bar yg-header header-ext">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">佣金明细</h1>
	</header>
	<!--#include file="layouts/menu.shtml"-->
	<div class="mui-content yg-commission-detail">
		<nav class="mui-bar-tab yg-bar f12">
			<a class="mui-tab-item link-jump  mui-active" id="notSettled" href="#" >
				<div class="mui-tab-label">&yen;${(commissionList.summary.notSettled)?number?string('0.00')}</div>
				<span class="mui-tab-label">预估佣金</span>
			</a>
			<a class="mui-tab-item link-jump cor-252525" id="hasSettled" href="#" >
				<div class="mui-tab-label">&yen;${(commissionList.summary.hasSettled)?number?string('0.00')}</div>
				<span class="mui-tab-label">已结算</span>
			</a>
		</nav>
		<ul class="mui-table-view mt10 c-tab-view">
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right" href="#">
					<span class="mui-badge">佣金收入</span> 订单号
				</a>
			</li>
		</ul>
		<div id="listData">
		<ul class="mui-table-view" name="notSettled" >
		<#if commissionList??&&(commissionList.items1)??&&(commissionList.items1?size>0)>
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
           <#else>
            <li>
               <div class="yg-pro-detail-nomore pt10 pb10" style="text-align:center;"><img src="${context}/static/images/prodetail/nothing.png" style="width:30px"><br>没有相关信息</div>
            </li>
           </#if>
		</ul>
		
		<ul class="mui-table-view" name="hasSettled">
		<#if commissionList??&&(commissionList.items2)??&&(commissionList.items2?size>0)>
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
<script type="text/javascript">
	$("ul[name='hasSettled']").hide();
    var errorMsg = '${errorMsg!''}';
</script>
<script src="${context}/static/js/jquery-3.0.0.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script src="${context}/static/js/yg-commission-detail-list.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>