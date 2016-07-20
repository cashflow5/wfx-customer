<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_账户信息</title>
<#include "../layouts/style.ftl">
<link rel="stylesheet" type="text/css" href="${context}/static/css/mui.picker.min.css" />
<link rel="stylesheet" type="text/css" href="${context}/static/css/yg-account-set.css" />
</head>
<body>
<div class="viewport">
	<div class="viewport">
			<header class="mui-bar yg-header header-ext">
				<a class="mui-icon mui-icon-left-nav mui-pull-left" onclick="location.href='ufans-b.shtml'"></a>
				<h1 class="mui-title">账户设置</h1>
			</header>
			<form id="account-form" action="" method="">
			<div class="mui-content yg-account-setup">
				<section class="mui-table-view">
					<div class="mui-table-view-cell mui-collapse">
						<a class="acconunt-bank mui-navigate-right user-infor">
							<p class="f16"><span class="iconfont Blue2" >&#xe663;</span>身份认证<em class="showTip f14">待审核</em></p>
						</a>
						<ul class="mui-table-view fill-account-info mui-collapse-content fill-account-info-no">
							<li class="mb10">
								<span>真实姓名 :</span>
								<input disabled="disabled" type="text" id="userName" name="name" value="${(result.realName)!("")}" />
								<p class="errorInfor Red"></p>
							</li>
							<li class="mb10">
								<span>身份证号 :</span>
								<input disabled="disabled" type="text" id="identityCard" name="identityCard" value="${(result.idCode)!('')}" />
								<p class="errorInfor Red"></p>
							</li>
						</ul>
						<div class="mui-table-view fill-account-phone mui-collapse-content pb10">
							<div class="tcenter userCard mui-clearfix">
								<a href="#" class="mui-pull-left">身份证示例 :</a>
								<div class="cardImg acconunt-submit-no f20"><div class="imgWrap">+<span class="f14">正面</span></div></div>
								<div class="cardImg acconunt-submit-no f20"><div class="imgWrap">+<span class="f14">反面</span></div></div>
								<p class="errorInfor Red"></p>
							</div>
							<p class="Gray tleft mb5 mt5 acconunt-tip">
							注意：</br>
							1.身份证主要用于确认身份的真实性。</br>
							2.审核通过后将不能再修改。

							<a class="look-example" href="">查看示例</a>
							</p>
						</div>
					</div>
				</section>
				<section>
					<div class="mui-table-view-cell mui-collapse">
						<a class="acconunt-bank mui-navigate-right" >
							<p class="f16"><span class="iconfont Blue2" >&#xe663;</span>绑定银行卡<em class="showTip f14">62133****12312</em></p>
						</a>
						<!-- <p class="mb0 mt10 pd0" style="white-space: pre-wrap;">请填写真实资料，并保证姓名、身份证、银行卡开户人为同一人。</p> -->
						<ul class="mui-table-view fill-account-info mui-collapse-content fill-account-info-no">
							<li class="mb10">
								<span>持卡人 :</span>
								<input type="text" id="cardName" disabled="disabled" name="cardName" placeholder="持卡人与身份证姓名保持一致" value="${(result.realName)!("")}"/>
								<p class="errorInfor Red"></p>
							</li>
							<li class="mb10">
								<span>开户银行 :</span>
								<input type="hidden" class="account-input" id="openBank" name="bankNo" value="" />
								<input type="hidden" class="account-input" id="bank" name="bank" value="" />
								<button id='showUserPicker' class="mui-btn Gray hasArrow" type='button'>请选择开户银行</button>
								<p class="errorInfor Red"></p>
							</li>
							<li class="mb10 twoElement">
								<p><span>支行省份  :</span>
									<input type="hidden" class="account-input" id="province" name="" value="" />
								<button id="showCityPicker" class="mui-btn Gray hasArrow" type='button'>请选择</button>
								</p>
								<p><span>城市：</span>
								<input type="hidden" class="account-input" id="city" name="" value="" />
								<button id="showCityPickerSecond" class="mui-btn Gray hasArrow" type='button'>请选择</button></p>
								<p class="errorInfor Red"></p>
							</li>
							<li class="mb10">
								<span>开户支行  :</span>
								<input  class="mui-btn Gray" id="openBranch" name="bankBranch" value="" placeholder="请填写开户银行"/>
								<!--<button class="mui-btn Gray hasArrow" type='button' id="showUserPicker1">请选择开户银行</button>-->
								<p class="errorInfor Red"></p>
							</li>
							<li class="mb10">
								<span>银行卡号  :</span>
								<input type="text" name="" id="cardNumber"  placeholder="请输入银行卡卡号，不支持信用卡" />
								<p class="errorInfor Red"></p>
							</li>
						</ul>
						<p class="Gray tleft mb5 mt5 acconunt-tip">注意：</br>请填写真实资料，并保证银行卡持卡人与身份证为同一人</p>
					</div>
				</section>
				<a class="mui-btn mui-btn-danger mui-btn-block acconunt-submit acconunt-submit-no" href='javascript:;'>
					确认提交
				</a>
			</div>
		</div>
		</form>
		<#include "../layouts/corejs.ftl">
		<script src="${context}/static/js/plugins/mui.picker.min.js" type="text/javascript"></script>
		<script src="${context}/static/js/city.data.js" type="text/javascript" charset="utf-8"></script>
				<script type="text/javascript">
	var bankList = new Array();//所有银行列表
	function initBankList() {
		<#if bankMap??>
	  		<#list bankMap ? keys as key>
	  			bankList.push({
	  				"value" : '${key}',
	  				"text" : '${bankMap[key]}'
	  			});
	  		</#list>
		<#else>
			
 		</#if>
    }
    (function($) {
		initBankList();
		var bankPopPicker = new $.PopPicker();
		bankPopPicker.setData(bankList);
		var showBankPickerButton = document.getElementById('showUserPicker');
		var openBank= document.getElementById('openBank');
		var bank= document.getElementById('bank');
		showBankPickerButton.addEventListener('tap', function(event) {
			bankPopPicker.show(function(items) {
				showBankPickerButton.innerText = items[0].text;
				openBank.value=JSON.stringify(items[0].value);
				bank.value=JSON.stringify(items[0].text);
				//返回 false 可以阻止选择框的关闭
				//return false;
			});
		},false);
		})(mui);
		</script>
		<script src="${context}/static/js/yg-account-infor-wirte.js" type="text/javascript" charset="utf-8"></script>
</body>
</html>