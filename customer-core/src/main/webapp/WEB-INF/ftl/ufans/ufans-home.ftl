<!DOCTYPE html>
<html>

	<head>
	<#include "../layouts/meta.ftl">
	<#include "../layouts/style.ftl">
		<title>优购微零售_优粉</title>
		<link rel="stylesheet" type="text/css" href="${context}/static/css/mui.picker.min.css" />
	</head>

	<body>
		<div class="viewport">
			<div class="mui-content yg-admin yg-ufans-a">
			<header class="mui-bar yg-header header-ext">
					<h1 class="mui-title">优粉</h1>
		  </header>
	<#if running_environment != '3' && running_environment != '4'>
		<#include "../layouts/index-menu.ftl">
	</#if>
		
				<section class="user-info-box">
					<#--<a id="shared" class="system-share"><i class="iconfont">&#xe667;</i></a> -->
					<div class="user-photo user-head">
						<#if shop??&&shop.logo??><img src="${(shop.logo)!(context+'/static/images/admin-user-photo.png')}" alt="" />
						<#else>
						<img src="${context}/static/images/admin-user-photo.png" alt="" />
						</#if>
					</div>
					<div class="user-info f12">
						<div>No.${(shop.shopCode)!''}</div>
						 
						<#--<#if shop??&&shop.phoneNumber??&&shop.phoneNumber!=''>-->
						<#if memberType == '0_1' || memberType == '0_3' || memberType == '1_1' || memberType == '1_3'>
						<div><i class="iconfont cor-00c800">&#xe679;</i>&nbsp;已绑定手机</div>
						<#else>
						<div class="mt3"><span class="perfect-login-info">
						<a class="mui-tab-item link-jump no-border" href="#"  data-href="${context}/usercenter/bind-phone.sc"  >完善登录信息</a> 
						</span></div>
						</#if>
						
					</div>
				</section>
				<ul class="mui-table-view">
					<li class="mui-table-view-cell">
						<a class="mui-navigate-right link-jump" href="#"  data-href="${context}/finance/myIncome.sc">
							<div><b><#if (shop.totalProfit)??>${((shop.totalProfit)!0)}<#else>0</#if></b></div>
							<div class=" f12">累计佣金 </div>
						</a>
					</li>
				</ul>
				<nav class="mui-bar-tab yg-bar f12">
					<a class="mui-tab-item link-jump" href="#" data-href="${context}/finance/commissionDetail.sc">
						<div class="mui-tab-label">${((shop.todayProfit)!0)}</div>
						<span class="mui-tab-label">今日收益</span>
					</a>
					<a class="mui-tab-item link-jump" href="#" data-href="${context}/ufans/visitor/visitorRecords.sc">
						<div class="mui-tab-label">${totalVisitCount!0}</div>
						<span class="mui-tab-label">今日访客</span>
					</a>
					<a class="mui-tab-item link-jump" href="#" data-href="${context}/ufans/order/list.sc">
						<div class="mui-tab-label">${(shop.sevenDayOrderCount)!0}</div>
						<span class="mui-tab-label">7日订单</span>
					</a>
					<a class="mui-tab-item link-jump" href="#"  data-href="${context}/finance/myIncome.sc">
						<div class="mui-tab-label"><#if (shop.canCash)??>${((shop.canCash)!0)}<#else>0</#if></div>
						<span class="mui-tab-label">可提现</span>
					</a>
					<a class="mui-tab-item link-jump" href="#" data-href="${context}/ufans/fansList.sc">
						<div class="mui-tab-label">${(shop.fansCount)!0}</div>
						<span class="mui-tab-label">粉丝</span>
					</a>
				</nav>
				<ul class="mui-table-view mt10">
					<li class="mui-table-view-cell">
						<a class="mui-navigate-right link-jump" href="#" data-href="${context}/ufans/qrCodeOfShop.sc?wxCodeUrl=${context}/${(shop.id)!'yougoushop'}.sc">
							我的二维码
						</a>
					</li>
				</ul>
				<nav class="mui-bar-tab yg-bar c-bar-tab">
					<a class="mui-tab-item link-jump" href="#" data-href="${context}/ufans/order/list.sc?level=1">
						<span class="iconfont icon-cc cor-f8b453">&#xe602;</span>
						<span class="mui-tab-label">订单管理</span>
					</a>
					<a class="mui-tab-item link-jump" href="#" data-href="${context}/finance/myIncome.sc">
						<span class="iconfont cor-ff5256 icon-cc">&#xe686;</span>
						<span class="mui-tab-label">我的佣金</span>
					</a>
					<a class="mui-tab-item link-jump no-border" href="#" data-href="${context}/ufans/mySeller/getSubList.sc">
						<span class="iconfont cor-43a6fb icon-cc">&#xe660;</span>
						<span class="mui-tab-label">我的分销商</span>
					</a>
				</nav>
				<section class="mt10">&nbsp;</section>
				<!--<ul class="mui-table-view mt10">
					<li class="mui-table-view-cell">
						<a class="mui-navigate-right" href="#">
							入门必读
						</a>
					</li>
				</ul>-->
			</div>
		</div>

		<#include "../layouts/corejs.ftl">
		<script src="${context}/static/js/yg-ufans-b.js" type="text/javascript" charset="utf-8"></script>
		<script src="${context}/static/js/plugins/mui.lazyload.js?v=${static_version}"></script>
		<script src="${context}/static/js/plugins/mui.lazyload.img.js?v=${static_version}"></script>
		<script src="${context}/static/js/yg-index.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
	</body>

</html>