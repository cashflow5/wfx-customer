<!DOCTYPE html>
<html class="h100">

	<head>
		<#include "../layouts/meta.ftl">
	    <#include "../layouts/style.ftl">
		<title>优购微零售：带你勇闯时尚圈</title>
		<meta name="description" content="搜罗穿搭潮流，时尚圈热点推荐品牌尖品，100%正品，7天无理由退换！" />
		<style>
			
			.mui-content {
				
				height: -moz-calc(100% - 44px);
				height: -webkit-calc(100% - 44px);
				height: calc(100% - 44px);
				
			}  
		</style>
	</head>

	<body>
		<div class="viewport yg-shop-erweima">
			<header class="mui-bar yg-header header-ext">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">店铺二维码</h1>
				<a id="shared" class="mui-pull-right header-button"><span class="iconfont">&#xe665;</span></a>
				<input type="hidden" id="shopCode" name="shopCode" value="${(shop.shopCode)!''}"/>
				<input type="hidden" id="logoUrl" name="logoUrl" value="${(shop.logoUrl)!''}"/>
			</header>  
			<div class="mui-content shop-bg-div tcenter">
				<img id="qrcode" style="width:100%; margin:0 auto;" src="${(shop.qrCodeUrl)!''}"/>
			</div>
		</div>
	<#include "../layouts/corejs.ftl">
	<script>
    var shareobj={  
	   title:"优购微零售：带你勇闯时尚圈", // 分享标题
	   desc: '搜罗穿搭潮流，时尚圈热点推荐品牌尖品，100%正品，7天无理由退换！', // 分享描述
       link:'${context}/discover/home.sc?shopId=${shop.id!''}', // 分享链接
       imgUrl:'${(shop.qrCodeUrl)!''}', // 分享图标
       type:'link',
       success: function () {},
       cancel: function () {}
	 }; 
	 wx.ready(function () {
	    //分享到朋友圈
	    wx.onMenuShareTimeline(shareobj); 
	     //分享给朋友
         wx.onMenuShareAppMessage(shareobj);
	     //分享qq
	     wx.onMenuShareQQ(shareobj);
	     //分享到腾讯微博
	     wx.onMenuShareWeibo(shareobj);
	     //分享到QQ空间
	     wx.onMenuShareQZone(shareobj);
	     wx.showOptionMenu();
	   });
	    
	</script>
	<script type="text/javascript" src="http://v3.jiathis.com/code/jiathis_m.js" charset="utf-8"></script>
</html>