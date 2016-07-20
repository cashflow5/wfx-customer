<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_我的佣金</title>
<#include "../layouts/style.ftl">
</head>
<body>
<div class="viewport">
	<header class="mui-bar yg-header header-ext">
		<a class="mui-icon mui-icon-left-nav mui-pull-left" onclick="location.href='/ufans/home.sc'"></a>
		<h1 class="mui-title">我的佣金</h1>
	</header>
	<div class="mui-content yg-my-income">
		<section class="add-income">
			<div class="f12">累计佣金</div>
			<div class=""><b>￥<#if (summary.commission)??>
				<#if (summary.commission<=0)>
				0.00
				<#else>
				${(summary.commission)?string('0.00')}
				</#if>
			<#else>
				0.00
			</#if></b></div>
			<div class="f12 mt10">可提现金额：￥${summary.balance}</div>					
			<i class="iconfont income-help" id="showMainHelp">&#xe68e;</i>
		</section>
		<section class="yg-visitor-records mb5 relative none" id="mainHelp">
			<i class="iconfont arrow-up-sjx">&#xe646;</i>
			<ul class="mui-table-view vr-bofore-none f12 pd-5-0 cor-84">
				<li class="mui-table-view-cell">
					<div class="yg-layout-column-left-80 fxs-colum-afterdiaplay">
						<div class="yg-layout-left">
							累计佣金：
						</div>
						<div class="yg-layout-main">
							<div class="yg-layout-container mui-row">
								<div class="mui-col-sm-12 mui-col-xs-12">
									<div class="f12 lh-1_3">
										预估佣金与已结算佣金之和（包含本人、本人粉丝及下级分销商、分销商粉丝带来的佣金）
									</div>
								</div>
							</div>
						</div>
					</div>
				</li>
				<li class="mui-table-view-cell">
					<div class="yg-layout-column-left-80 fxs-colum-afterdiaplay">
						<div class="yg-layout-left">
							可提现金额：
						</div>
						<div class="yg-layout-main">
							<div class="yg-layout-container mui-row">
								<div class="mui-col-sm-12 mui-col-xs-12">
									<div class="f12 lh-1_3">
										目前账户中可提现的佣金,每周可以提现一次。单笔提现金额不小于50元，不超过500元。
									</div>
								</div>
							</div>
						</div>
					</div>
				</li>
			</ul>
		</section>
		<nav class="mui-bar-tab yg-bar f12 bg-white">
			<a class="mui-tab-item link-jump cor-252525" href="#" data-href='applyCashList' data-param="tab=0">
				<div class="mui-tab-label">￥${summary.onCash}</div>
				<span class="mui-tab-label">提现中</span>
			</a>
			<a class="mui-tab-item link-jump cor-252525" href="#" data-href='applyCashList' data-param="tab=1">
				<div class="mui-tab-label">￥${summary.hasCash}</div>
				<span class="mui-tab-label">已提现</span>
			</a>
			<a class="mui-tab-item link-jump" data-href='applyCash' data-param="tab=1" href="#">
				<span class="iconfont">&#xe693;</span>
				<span class="mui-tab-label">申请提现</span>
			</a>
		</nav>
		<ul class="mui-table-view mt10">
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right" href="#" onclick="location.href='commissionDetail.sc'">
					<i class="iconfont">&#xe686;</i> 佣金明细
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right" href="#" onclick="location.href='getIncomingList.sc'">
					<i class="iconfont">&#xe677;</i> 收支明细
				</a>
			</li>
			<li class="mui-table-view-cell">
				<a class="mui-navigate-right" href="#" onclick="location.href='accountSetUp.sc'">
					<i class="iconfont">&#xe681;</i> 账户设置
				</a>
			</li>
		</ul>

	</div>
</div>
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/yg-my-income.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var errorMsg = '${errorMsg!''}';
</script>
</body>
</html>