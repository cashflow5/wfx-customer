<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl" />
    <title>注册</title>
<#include "../../layouts/style.ftl"/>
</head>
<body>
		<div class="viewport">
			<header class="mui-bar yg-header header-ext">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">注册成功</h1>
			</header>
			<div class="mui-content">
				<section class="yg-floor mt20 pt10">
					<div class="yg-fail-success">
						<i class="iconfont Green">&#xe64e;</i>
					</div>
					<div class="Green tcenter f14 mb3">恭喜您注册成功！</div>
					<p id="returnTip" class="Gray tcenter f14 pb20">倒计时3秒自动返回上一页</p>
				</section>
				<section class="pd10">
					<a href="${context}/index.sc" class="mui-btn mui-btn-danger w100p">注册成功开始购物</a>
				</section>
				<section class="mui-row tcenter mt10">
					<div class="mui-col-xs-6">
						<div class="yg-icon-border"><span class="iconfont">&#xe660;</span></div>
						<p class="Gray mt10">支付成功，成为优粉</p>
					</div>
					<div class="mui-col-xs-6">
						<div class="yg-icon-border"><span class="iconfont">&#xe60a;</span></div>
						<p class="Gray mt10">分享商品，获得补贴</p>
					</div>
				</section>
			</div>
		</div>
	<#include "../../layouts/corejs.ftl"/>
	<script src="${context}/static/js/yg-register-success.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
	</body>
</html>