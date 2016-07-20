<!DOCTYPE html>
<html>

	<head>
		<#include "../layouts/meta.ftl" />
   		 <title>优购微零售_支付成功提示</title>  
		<#include "../layouts/style.ftl"/>
	</head>

	<body>
		<div class="viewport">
			<header class="mui-bar yg-header header-ext">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">支付成功</h1>
			</header>
			<div class="mui-content">
				<section class="yg-floor pt10 mt20 yg-border-dashed-bottom">
					<div class="yg-fail-success">
						<i class="iconfont Green">&#xe64e;</i>
					</div>
					<p class="Green tcenter f14 pb20">订单支付成功</p>
					<p class="tcenter mt30">
						您已成为优购微零售的优粉，下载APP， 开启你的优粉之旅吧~
					</p>
					<p class="tcenter mt15 mb30">
						<a href="${context}/agent.sc" class="mui-btn">点击下载APP</a>
					</p>
				</section>
				<section class="pd10">
					<p class="f14">支付金额：<span class="Red">${payedCallBack.tradeAmount}</span></p>
					<p class="f14">支付方式：${payedCallBack.payType}</p>
					<div class="f12">
						<div>温馨提示</div>
						<p>您的订单商品分属不同仓库，将拆成2个包裹，分别由深圳、北京仓库发出，请注意签收。</p>
					</div>
					<div class="mui-row pr10">
						<div class="mui-col-sm-6 mui-col-xs-6">
							<a href="${context}/order/myorder.sc" class="mui-btn mui-btn-danger w100p">查看订单</a>
						</div>
						<div class="mui-col-sm-6 mui-col-xs-6">
							<a href="${context}/index.sc" class="mui-btn w100p ml10">继续购买</a>
						</div>
					</div>
				</section>
			</div>
		</div>
	</body>

</html>