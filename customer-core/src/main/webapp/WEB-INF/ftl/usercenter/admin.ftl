<#-- @ftlvariable name="shop" type="com.yougou.wfx.customer.model.shop.ShopVo" -->
<#-- @ftlvariable name="orderCount" type="com.yougou.wfx.customer.model.order.OrderCountVo" -->
<#-- @ftlvariable name="userAccount" type="com.yougou.wfx.customer.model.usercenter.account.UserAccountVo" -->
<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_用户中心_首页</title>
<#include "../layouts/style.ftl">
</head>

<body>
		<div class="viewport">
			<header class="mui-bar yg-header header-ext">
				<h1 class="mui-title">我的</h1>
			</header>
			<#if running_environment != '3' && running_environment != '4'>
				<#include "../layouts/index-menu.ftl">
			</#if>
			<div class="mui-content yg-admin">
				<ul class="mui-table-view mt0">
					<li class="mui-table-view-cell">
						<a class="mui-navigate-right yg-user-photo-cell" href="${context}/usercenter/userinfo.sc">
							<div class="user-photo"><img src="${(userAccount.userHeadUrl)!context+"static/images/admin-user-photo.png"}" alt="" /></div>
							<span class="user-descript">${(userAccount.userName)!'Error Name'}</span>
						</a>
					</li>
					<li class="mui-table-view-cell">
						<a class="mui-navigate-right" href="${context}/order/myorder.sc">
							我的订单
						</a>
					</li>
					<nav class="yg-tab-bar">
						<a href="${context}/order/myorder.sc?status=WAIT_PAY&tabType=1" class="tab-bar-item">
							<span class="iconfont">&#xe66b;
							<#if orderCount.waitPay gt 0>
								<span class="mui-badge mui-badge-bby">${orderCount.waitPay}</span>
							</#if>
							</span>
							<span class="tab-item-text">待付款</span>
						</a>
						<a href="${context}/order/myorder.sc?status=WAIT_DELIVER&tabType=2" class="tab-bar-item">
							<span class="iconfont f20">&#xe66a;
							</span>
							<span class="tab-item-text">待发货</span>
						</a>
						<a href="${context}/order/myorder.sc?mergeSearchFlag=1&tabType=3" class="tab-bar-item">
							<span class="iconfont">&#xe669;
							</span>
							<span class="tab-item-text">待收货</span>
							
						</a>
						<a href="${context}/order/myrefunds.sc" class="tab-bar-item">
							<span class="iconfont f20">&#xe668;
							<#if orderCount.waitRefund gt 0>
								<span class="mui-badge mui-badge-bby">${orderCount.waitRefund}</span>
							</#if>
							</span>
							<span class="tab-item-text">退款/售后</span>
						</a>
					</nav>
				</ul>
				<ul class="mui-table-view mt10">
					<li class="mui-table-view-cell">
						<a class="mui-navigate-right" href="${context}/usercenter/myaddress.sc">
							收货地址管理
						</a>
					</li>
				</ul>
				<#if running_environment == '1'>
					<#if memberType == '0_1' || memberType == '0_3' || memberType == '1_1' || memberType == '1_3'>
					<ul class="mui-table-view mt10">
						<li class="mui-table-view-cell">
							<a class="mui-navigate-right" href="${context}/usercenter/bind-phone.sc">
								绑定手机
								<span class="fr mr20 Gray f12">已绑${(userAccount.userName)!''}</span>
							</a>
						</li>
					</ul>
					<#else>
					<ul class="mui-table-view mt10">
						<li class="mui-table-view-cell">
							<a class="mui-navigate-right" href="${context}/usercenter/bind-phone.sc">
								绑定手机
							</a>
						</li>
					</ul>
					</#if>
				</#if>
				<div class="mt10 tvcenter mui-clearfix">
					<a href="${context}/usercenter/quality-assurance.sc" class="block tvcenter"><img class="w100p" src="${context}/static/images/admin-banner.png" alt="" /></a>
				</div>
				<#if running_environment == '2'>
				<section class="yg-box footer-box">
					<div class="pt20">
						<a href="${context}/logout.sc" class="mui-btn w50p">退出登录</a>
					</div>
				</section>
				</#if>
			</div>
		</div>
		<#include "../layouts/corejs.ftl">
		<script src="${context}/static/js/qrcode.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
	</body>
</html>