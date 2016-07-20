<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_账户信息</title>
<#include "../layouts/style.ftl">
<link rel="stylesheet" type="text/css" href="${context}/static/css/mui.picker.min.css" />
<link rel="stylesheet" type="text/css" href="${context}/static/css/yg-account-set.css" />
<style>
.mui-icon-back:before, .mui-icon-left-nav:before{color:#848484;}
</style>
</head>
<body>
<#if authorizeStatus??>
	<#--0:待上传 1.待审核2,"审核通过"3,"审核不通过" -->
	<#assign authorizeFlag = authorizeStatus>
<#else>
	<#assign authorizeFlag = 0>
</#if>
<#if (authorizeFlag == 0) || (authorizeFlag == 1) || (authorizeFlag == 3)>
	<#assign sectionShow = true>
<#else>
	<#assign sectionShow = false>
	<input type="hidden" id="marlboro" />
</#if>
<div class="viewport">  
	<header class="mui-bar yg-header header-ext" style="background:#fff;background-image: linear-gradient(to bottom,#fff 0,#f0f0f0 100%);color:#252525">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title" style="color:#252525">账户设置</h1>  
	</header>
	<#if sectionShow == true><input class="account-input account-input-img identityCardImgZ" type="file" name="fileFront" id="identityCardImgZ" data-true="${bank.idPicUrl!''}" value="${bank.idPicUrl!''}" style='display:none'/></#if>
    <#if sectionShow == true><input class="account-input account-input-img" type="file" name="fileBack" id="identityCardImgF" data-true="${bank.idPicUrlBack!''}" value="${bank.idPicUrlBack!''}" style='display:none'/></#if>
	<form id="account-form" action="${context}/finance/modityAccountSetUp.sc" method="post" enctype="multipart/form-data">
	  <div class="mui-content yg-account-setup <#if authorizeFlag==2>fill-account-info-no</#if>">
		<section class="mui-table-view">
			<div class="mui-table-view-cell mui-collapse <#if sectionShow == true>mui-active</#if>">
				<a class="acconunt-bank mui-navigate-right user-infor">
					<p class="f16"><span class="iconfont Blue2" >&#xe62d;</span>身份认证<em class="showTip f14" style="color:#cbcbcb;">${authorizeStatusDesc!''}</em></p>
				</a>
				<ul class="mui-table-view fill-account-info mui-collapse-content">
					<li class="mb10">
						<span>真实姓名 :</span>
						<input type="text" data-true="${bank.realName!''}" <#if authorizeFlag==2>disabled</#if> id="userName" class="account-input account-input-no" name="realName" value="${bank.realName!''}" placeholder="请输入您的真实姓名" />
						<p class="errorInfor Red"></p>
					</li>
					<li class="mb10">
						<span>身份证号 :</span>
						<input type="text"  data-true="${bank.idCode!''}" <#if authorizeFlag==2>disabled</#if> id="identityCard"  class="account-input account-input-no" name="idCode" value="${bank.idCode!''}" placeholder="请输入您的身份证号" />
						<p class="errorInfor Red"></p>
					</li>
				</ul>  
				<div class="mui-table-view fill-account-phone mui-collapse-content">
					<div class="tcenter userCard mui-clearfix">
						<a href="#" class="mui-pull-left">身份证上传 :</a>
						<div class="cardImg f20" id="cardImg0"><div class="imgWrap"><#if authorizeFlag != 0><img src="${bank.idPicUrl!''}" /><#if sectionShow == true><a href="javascript:void(0);" class="deleteimg"></a></#if><#else>+<span class="f14">正面</span></#if></div><input type='hidden' name="idPicUrl"  value="" id="pic0"></div>
						<div class="cardImg f20" id="cardImg1"><div class="imgWrap"><#if authorizeFlag != 0><img src="${bank.idPicUrlBack!''}" /><#if sectionShow == true><a href="javascript:void(0);" class="deleteimg"></a></#if><#else>+<span class="f14">反面</span></#if></div><input type='hidden' value="" name="idPicUrlBack" id="pic1"></div>
						<p class="errorInfor Red"></p>
					</div>
					<p class="Gray tleft mb5 mt5">
					注意：</br>
					1.身份证主要用于确认身份的真实性。</br>
					2.首次绑卡需审核身份，审核通过后将不能再修改。
					</p>
				</div>
			</div>
		</section>
		<section>
			<div class="mui-table-view-cell mui-collapse <#if sectionShow == true>mui-active</#if>">
				<a class="acconunt-bank mui-navigate-right" >
					<p class="f16"><span class="iconfont Blue2" >&#xe663;</span>绑定银行卡<em class="showTip f14" style="color:#cbcbcb;">${bank.cardNo!''}</em></p>
				</a>
				<!-- <p class="mb0 mt10 pd0" style="white-space: pre-wrap;">请填写真实资料，并保证姓名、身份证、银行卡开户人为同一人。</p> -->
				<ul class="mui-table-view fill-account-info mui-collapse-content">
					<li class="mb10">
						<span>持卡人 :</span>
						<input <#if authorizeFlag==2>disabled</#if> type="text" id="cardName" class="account-input account-input-no" name="cardName" value="${bank.realName!''}"  placeholder="请输入与身份证姓名一致的持卡人" />
						<p class="errorInfor Red"></p>
					</li>
					<li class="mb10">
						<span>开户银行 :</span>
						<input type="hidden" class="account-input" id="openBank" name="bankNo" value="${bank.bankNo!}" />
						<input type="hidden" class="account-input" id="bank" name="bank" value="${bank.bank!}" />
						<button id='showUserPicker' class="mui-btn Gray hasArrow" type='button'>${bank.bank!'请选择开户银行'}</button>
						<p class="errorInfor Red"></p>
					</li>
					<li class="mb10 twoElement">
						<p><span>支行省份  :</span>
						<input type="hidden" class="account-input" id="province" name="bankProvince" value="${bank.bankProvince!}" />
						<button id="showCityPicker" class="mui-btn Gray hasArrow" type='button'>${bank.bankProvince!'请选择'}</button>
						</p>
						<p><span>城市：</span>
						<input type="hidden" class="account-input" id="city" name="bankCity" value="${bank.bankCity!''}" />
						<button id="showCityPickerSecond" class="mui-btn Gray hasArrow" type='button'>${bank.bankCity!'请选择'}</button></p>
						<p class="errorInfor Red"></p>
					</li>
					<li class="mb10">
						<span>开户支行  :</span>
						<input type="text" class="account-input" id="openBranch" name="bankBranch" value="${bank.bankBranch!''}" placeholder="请填写开户支行"/>
						<!--<button class="mui-btn Gray hasArrow" type='button' id="showUserPicker1">请选择开户银行</button>-->
						<p class="errorInfor Red"></p>
					</li>
					<li class="mb10">
						<span>银行卡号  :</span>
						<input type="text" class="account-input" name="cardNo" id="cardNumber" data-true="${bank.cardNo!''}" value="${bank.cardNo!''}" placeholder="请输入银行卡卡号，不支持信用卡" />
						<p class="errorInfor Red"></p>
					</li>
					<li><p class="Gray tleft mb5 mt5">注意：</br>请填写真实资料，并保证银行卡持卡人与身份证为同一人。</p></li>
				</ul>
				
			</div>
		</section>
		<a id="account-form-submit" class="mui-btn mui-btn-danger mui-btn-block acconunt-submit acconunt-submit-yes hide" href='javascript:;'>
			确认提交
		</a>
		<a class="mui-btn mui-btn-block acconunt-submit acconunt-submit-no" href='javascript:;'>
			确认提交
		</a>
	</div>
</div>
</form>
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/plugins/mui.picker.min.js" type="text/javascript"></script>
<script src="${context}/static/js/city.data.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
// var status='${authorizeStatus !}';//0:待上传 1.待审核2,"审核通过"3,"审核不通过"
// var bankNo='${bank.bankNo !}';
// var bankCity='${bank.bankCity !}';
// var bankProvince='${bank.bankProvince !}';
var bankList = new Array();//所有银行列表
function initBankList() {
	<#if bankMap??>
  		<#list bankMap ? keys as key>
  			bankList.push({
  				value : '${key}',
  				text : '${bankMap[key]}'
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
			openBank.value=items[0].value;
			bank.value=items[0].text;
			YG_AccountInformation.allInputChecked();
			//返回 false 可以阻止选择框的关闭
			//return false;
		});
},false);   
})(mui);
</script>
<script src="${context}/static/js/yg-account-infor-wirte.js" type="text/javascript" charset="utf-8"></script>
<script src="${context}/static/js/localResizeIMG.js" type="text/javascript" charset="utf-8"></script>
<script src="${context}/static/js/mobileBUGFix.mini.js" type="text/javascript" charset="utf-8"></script>
<script>
$("#cardImg0,#cardImg1").each(function(index){
$(this).on('tap',function(){
if(index==0){$('#identityCardImgZ').trigger('click');}
else if(index==1){$('#identityCardImgF').trigger('click');}
  })
});
$('#identityCardImgZ,#identityCardImgF').each(function(index){
 $(this).localResizeIMG({
   width: 400,//宽度
   quality: 0.6,//质量
      success: function (result) {
      if($("#cardImg"+index).find('img').length>0)
       {$("#cardImg"+index).find('img').attr('src',result.base64);}
       else{
       $("#cardImg"+index).find(".imgWrap").html("<img src='"+result.base64+"'/><a href='javascript:void(0);' class='deleteimg'></a>")
       }
       $("#pic"+index).val(result.base64);
  //var identityCardImgZ=document.getElementById('identityCardImgZ') ;
    //   identityCardImgZ.value=result.base64;//result.clearBase64
      }
})
})
</script>
</body>
</html>
