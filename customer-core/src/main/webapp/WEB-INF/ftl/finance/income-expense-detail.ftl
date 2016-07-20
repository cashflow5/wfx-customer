<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_我的收益_收支明细</title>
<#include "../layouts/style.ftl">
<link rel="stylesheet" type="text/css" href="${context}/static/css/plugins/dropload/dropload.css?v=${static_version}" />
</head>
<body class=" yg-commission-detail">
	<div class="viewport">
		<header class="mui-bar yg-header header-ext">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
			<h1 class="mui-title"><a href="#orderSelect"><span id="showSelectV">全部明细</span><i class="iconfont f18 cor-84">&#xe60c;</i></a></h1>
		</header>
		<!--#include file="layouts/menu.shtml"-->
		<div id="listData">
		<div class="mui-content" name='all'>
			<ul class="mui-table-view mt0">
			   <#if list??&&(list?size>0)>
				<#list list as income>
				<li class="mui-table-view-cell">
					<a class="mui-navigate-right" href="#" onclick="location.href='getIncomingDetail.sc?id=${income.id}'">
						<span class="mui-badge cor-094"><#if income.transactionFlag=='1'>+<#else>-</#if>￥<#if income.incomingType=='1' && income.transactionFlag=='1'>${(income.incomeAmount)?string('0.00')}<#else>${(((income.expendAmount)?number)?abs)?string('0.00')}</#if></span>
						<div><#if income.incomingType=='1'>佣金收入<#else>提现</#if></div>
						<div class="cor-84 f12">${(income.time)?datetime}</div>
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
		<div class="mui-content" name='income'>
			<ul class="mui-table-view mt0">
			<#if list??&&(list?size>0)>
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
                <#else>
                 <li>
                   <div class="yg-pro-detail-nomore pt10 pb10" style="text-align:center;"><img src="${context}/static/images/prodetail/nothing.png" style="width:30px"><br>没有相关信息</div>
                 </li>
              </#if>		
			</ul>
		</div>
		<div class="mui-content" name='expense'>
			<ul class="mui-table-view mt0">
			<#if list??&&(list?size>0)>
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
                <#else>
                 <li>
                   <div class="yg-pro-detail-nomore pt10 pb10" style="text-align:center;"><img src="${context}/static/images/prodetail/nothing.png" style="width:30px"><br>没有相关信息</div>
                 </li>
              </#if>		
			</ul>
		</div>
		</div>
	</div>
	<div id="orderSelect" class="mui-popover">
		<div class="mui-popover-arrow"></div>
		<div class="mui-scroll-wrapper">
			<div class="mui-scroll">
				<ul class="mui-table-view">
					<li class="mui-table-view-cell"><a class="mui-tab-item" type='0' href="#">全部明细</a>
					</li>
					<li class="mui-table-view-cell"><a class="mui-tab-item" type='1' href="#">收入明细</a>
					</li>
					<li class="mui-table-view-cell"><a class="mui-tab-item" type='2' href="#">支出明细</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<#include "../layouts/corejs.ftl">
	<script type="text/javascript">	
		$("div[name='income']").hide();
		$("div[name='expense']").hide();
	    var errorMsg = '${errorMsg!''}';
	</script>
	<script src="${context}/static/js/jquery-3.0.0.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
	<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
	<script src="${context}/static/js/yg-income-expense-detail.js?v=${static_version}"></script>
</body>
</html>