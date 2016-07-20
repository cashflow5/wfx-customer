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
				<h1 class="mui-title">提现详情</h1>
			</header>
			<div class="mui-content yg-appli-info">
				<div class="appli-top">
	    			<p class="Gray"><#if cashDetail.billStatus=='1'>提现中<#elseif cashDetail.billStatus=='2'>提现中<#elseif cashDetail.billStatus=='3'>提现成功<#else>提现失败</#if></p>
	    			<h1 style="padding: 20px 0;">-${cashDetail.withdrawAmount}</h1>
	    			<p class="Gray">手续费￥${cashDetail.serviceAmount}</p>
	    		</div>
	    		<ul class="appli-bottom mui-table-view">
					<li class="mui-table-view-cell mui-clearfix border-bottom1">
						<p class="Gray mui-pull-left">提现申请单</p>
						<h5 class="mui-pull-right">${cashDetail.withdrawApplyNo} </h5>
					</li>
					<li class="mui-table-view-cell mui-clearfix border-bottom1">
						<p class="Gray mui-pull-left">提现到</p>
						<h5 class="mui-pull-right">${cashDetail.accountBankName}（${cashDetail.accountNo}） ${cashDetail.accountName}</h5>
					</li>
					<li class="mui-table-view-cell mui-clearfix border-bottom1">
						<p class="Gray mui-pull-left">提现申请时间</p>
						<h5 class="mui-pull-right">${(cashDetail.applyTime)?datetime}</h5>
					</li>
					<li class="mui-table-view-cell mui-clearfix border-bottom1">
						<p class="Gray mui-pull-left">提现完成时间</p>
						<h5 class="mui-pull-right"><#if (cashDetail.modifyTime)??>${(cashDetail.modifyTime)?datetime}</#if></h5>
					</li>
					<li class="mui-table-view-cell mui-clearfix">
						<p class="Gray mui-pull-left">备注</p>
						<h5 class="mui-pull-right">${(cashDetail.remark)!''}</h5>
					</li>
				</ul>
			</div>
		</div>
<#include "../layouts/corejs.ftl">
<script type="text/javascript">
    var errorMsg = '${errorMsg!''}';
</script>
</body>
</html>