<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_我的收益_收支明细_收入</title>
<#include "../layouts/style.ftl">
</head>
<body>
<div class="viewport">
	<header class="mui-bar yg-header header-ext">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">收支明细详情</h1>
	</header>
	<!--#include file="layouts/menu.shtml"-->
	<div class="mui-content yg-income-detail">
		<section class="c-mui-row">
			<div class="mui-row pt10">
				<div class="mui-col-sm-2 mui-col-xs-2 tcenter">
					<i class="iconfont icon-normal f36 cor-094">&#xe689;</i>
				</div>
				<div class="mui-col-sm-5 mui-col-xs-5">
					<#if income.incomingType=='1'>收入<#else>支出</#if>
				</div>
				<div class="mui-col-sm-5 mui-col-xs-5 tright cor-094">
					<b><#if income.transactionFlag=='1'>+<#else>-</#if>￥<#if income.incomingType=='1' && income.transactionFlag=='1'>${(income.incomeAmount)?string('0.00')}<#else>${(((income.expendAmount)?number)?abs)?string('0.00')}</#if></b>
				</div>
			</div>
		</section>
		<section class="yg-visitor-records mt10">
			<ul class="mui-table-view vr-bofore-none f12 pd-5-0">
				<li class="mui-table-view-cell">
					<div class="yg-layout-column-left-80 fxs-colum-afterdiaplay">
						<div class="yg-layout-left">
							类型
						</div>
						<div class="yg-layout-main">
							<div class="yg-layout-container mui-row">
								<div class="mui-col-sm-12 mui-col-xs-12">
									<#if income.incomingType=='1'>佣金收入<#else>提现</#if>
								</div>
							</div>
						</div>
					</div>
				</li>
				<li class="mui-table-view-cell">
					<div class="yg-layout-column-left-80 fxs-colum-afterdiaplay">
						<div class="yg-layout-left">
							状态
						</div>
						<div class="yg-layout-main">
							<div class="yg-layout-container mui-row">
								<div class="mui-col-sm-12 mui-col-xs-12">
									<#if income.state=='1'>交易成功<#elseif income.state=='2'>处理中<#elseif income.state=='3'>交易关闭<#elseif income.state=='4'>交易失败</#if>
								</div>
							</div>
						</div>
					</div>
				</li>
				<li class="mui-table-view-cell">
					<div class="yg-layout-column-left-80 fxs-colum-afterdiaplay">
						<div class="yg-layout-left">
							时间
						</div>
						<div class="yg-layout-main">
							<div class="yg-layout-container mui-row">
								<div class="mui-col-sm-12 mui-col-xs-12">
									${(income.time)?datetime}
								</div>
							</div>
						</div>
					</div>
				</li>
				<li class="mui-table-view-cell">
					<div class="yg-layout-column-left-80 fxs-colum-afterdiaplay">
						<div class="yg-layout-left">
							支付方式
						</div>
						<div class="yg-layout-main">
							<div class="yg-layout-container mui-row">
								<div class="mui-col-sm-12 mui-col-xs-12">
									${(income.payWayName)!''}
								</div>
							</div>
						</div>
					</div>
				</li>
				<li class="mui-table-view-cell">
					<div class="yg-layout-column-left-80 fxs-colum-afterdiaplay">
						<div class="yg-layout-left">
							交易单号
						</div>
						<div class="yg-layout-main">
							<div class="yg-layout-container mui-row">
								<div class="mui-col-sm-12 mui-col-xs-12">
									${(income.billNo)!''}
								</div>
							</div>
						</div>
					</div>
				</li>
				<li class="mui-table-view-cell">
					<div class="yg-layout-column-left-80 fxs-colum-afterdiaplay">
						<div class="yg-layout-left">
							备注
						</div>
						<div class="yg-layout-main">
							<div class="yg-layout-container mui-row">
								<div class="mui-col-sm-12 mui-col-xs-12">
									${(income.remark)!''}
								</div>
							</div>
						</div>
					</div>
				</li>
			</ul>
		</section>
	</div>
</div>
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/yg-my-income.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var errorMsg = '${errorMsg!''}';
</script>
</body>
</html>