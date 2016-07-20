<!DOCTYPE html>
<html>
	<head>
		<#include "../layouts/meta.ftl">
		<title>授权绑定微信账号</title>
		<#include "../layouts/style.ftl">
	</head>

	<body>
		<div class="viewport yg-weixin-authorisation">
			<header class="mui-bar yg-header header-ext">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">授权绑定微信账号 </h1>
			</header>
			<div class="mui-content">
				<div class="yg-floor yg-floor-weixin">
					<div class="logo-box"><img src="${context}/static/images/wx-logo.png"/></div>
					<div class="pt15 pb20 Gray">绑定后，可使用微信账号直接登录APP（安卓版）。</div>
					<a href="${context}/usercenter/bind-weixin.sc" class="mui-btn mui-btn-danger mui-btn-block">绑定微信账号</a>
					<#if from == "ufans">
						<a href="${context}/usercenter/ufans.sc?flag=1" class="block pt10">跳过此步骤</a>
					<#elseif from == "center">
						<a href="${context}/usercenter.sc?flag=1" class="block pt10">跳过此步骤</a>
					</#if>
				</div>
			</div>
		</div>
		<!--#include file="layouts/corejs.shtml"-->
	</body>

</html>