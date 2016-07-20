<!DOCTYPE html>
<html>

<#include "../../layouts/meta.ftl">
    <title>优购微零售_支付成功提示</title>
<#include "../../layouts/style.ftl">

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
					<#if isFirstUfans><!--首次付款-->
						<#if running_environment == '1'><!--微信环境下-->
							<#if memberType == '0_1' || memberType == '0_3' || memberType == '1_1' || memberType == '1_3'>
								
							<#else>
								<p class="tcenter mt30">
									您还未绑定手机，绑定手机后可用手机账号进行登录管理
								</p>
								<p class="tcenter mt15 mb30">
									<a href="${context}/usercenter/bind-phone.sc" class="mui-btn">绑定手机</a>
								</p>
							</#if>
						<#else> <!--非微信环境下-->
							    <p class="tcenter mt30">
									您已成为优购微零售的优粉，分享商品，佣金赚不停！
								</p>
						</#if>
						
					</#if>
				</section>
				<section class="pd10">
					 <p class="f14">支付金额：<span class="Red">${((order.payment)!'0')?string('0.00')} 元</span></p>
           			 <p class="f14">支付方式：${(order.payTypeDesc)!''}</p>
					<div class="f12">
						<div>温馨提示</div>
						<p>您的订单已支付成功，我们会尽快安排发货，请注意查收。</p>
					</div>
					 <div class="mui-row pr10">
		                <div class="mui-col-sm-6 mui-col-xs-6">
		                    <a href="${context!''}/order/${(order.id)!''}.sc" class="mui-btn mui-btn-danger w100p">查看订单</a>
		                </div>
		                <div class="mui-col-sm-6 mui-col-xs-6">
		                    <a href="${context!''}/index.sc" class="mui-btn w100p ml10">继续购买</a>
		                </div>
		            </div>
				</section>
			</div>
		</div>
		<#include "../../layouts/corejs.ftl">
	</body>

</html>