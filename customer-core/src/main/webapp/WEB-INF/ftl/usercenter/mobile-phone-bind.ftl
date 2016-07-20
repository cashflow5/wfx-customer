<!DOCTYPE html>
<html>

	<head>
		<#include "../layouts/meta.ftl">
		<title>手机绑定</title>
		<#include "../layouts/style.ftl">
	</head>

	<body>
		<div class="viewport yg-login">
			<header class="mui-bar yg-header header-ext">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">绑定手机</h1>
			</header>
			<div class="mui-content bg-white" >
			<form class="mui-content-padded yg-form-block yg-validate"  method="post" id="registerForm"  action="${context}/usercenter/bind-phone-action.sc">
					<div id="div_phone" >
						<div class="mui-input-row">
							<input type="text" id="phoneNum" name="phoneNum" placeholder="请输入手机号">
							<div class="error-msg" id="errorMsg"></div>
						</div>
						<div class="mui-button-row pt0 mb15">
							<a href="javascript:void(0);" id="checkPhone_a" class="mui-btn mui-btn-danger mui-btn-block mb0">下一步</a>
						</div>
					</div>
					<div id="div_sms" style="display: none">
						<div class="mui-row code-wrap">
							<div class="mui-col-sm-6 mui-col-xs-6">
								<input type="text" id="smsCode" name="smsCode" placeholder="请输入验证码">
								<div class="error-msg" id="errorMsg0"></div>
							</div>
							<div class="mui-col-sm-6 mui-col-xs-6 tvcenter">
								<input class="mui-btn code-btn ml10 mt5"  id="smsCodeBtn" value="获取手机验证码" type="button"/>
							</div>
						</div>
						<div class="mui-input-row">
							<input type="password" id="password" name="passWord" placeholder="请设置6-18位，数字+字母密码">
							<div class="error-msg" id="errorMsg1"></div>
						</div>
						<div class="mui-button-row pt0 mb15 mt20">
						<a href="javascript:void(0)" type="a" id="setPwdNext" class="mui-btn mui-btn-danger mui-btn-block mb0">绑定</a>
						</div>
					</div>
				</form>
			</div>
		</div>
		
		
		<#include "../layouts/corejs.ftl">
		<script src="${context}/static/js/yg-phone-bind.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
	</body>

</html>