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
				<h1 class="mui-title">手机绑定</h1>
			</header>
			<form class="mui-content-padded yg-form-block yg-validate" >
			<div class="mui-content bg-white">
					<div class="mui-input-row">
						<input type="text" id="phoneNum" name="phoneNum" value="${phoneNumber}" readonly="readonly">
						
						<div ><br/>绑定手机后暂不支持修改哦</div>
					</div>
			</div>
		</form>
		</div>
		<#include "../layouts/corejs.ftl">
	</body>

</html>