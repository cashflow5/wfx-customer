<!DOCTYPE html>
<html>

	<head>
		<#include "../../layouts/meta.ftl" />
    		<title>您已经是优粉</title>
		<#include "../../layouts/style.ftl"/>
	</head>
	<body>
		<div class="viewport yg-page-ufans">
			<div class="mui-content">
				<section class="yg-floor yg-detail-img-floor pd0 mb0">
					<img class="w100p" src="${context}/static/images/imglist/banner2.png">
				</section>
				<section class="yg-floor pt20">
					<p class="tcenter">
						您的店铺编码为<#if (shop.shopCode)=='优购微零售总店'>${(shop.shopCode)!''}<#else>No.${(shop.shopCode)!''}</#if>, 可将您的店铺二维码分享至社交渠道赚取佣金, 请下载优购微零售APP进行店铺管理
					</p>
					<div class="mt10 tcenter">
						店铺二维码
						<br />
						<div id="qrcode" style="width:150px; height:150px;margin:0 auto;"><div id="codeico"></div></div>
					</div>
					<a href="${context}/agent.sc" class="mui-btn mui-btn-danger mui-btn-block mt20">下载优购微零售APP</a>
					<a class="mui-btn  mui-btn-block" style="width:150px;margin:0 auto;">返回首页购物</a>
				</section>
			</div>
		</div>
		<#include "../../layouts/corejs.ftl">
		
	</body>
	<#include "../../layouts/twoDimensionalCode.ftl"/>
	<script>
    function init() {
        generateQRCode("canvas",150, 150, "${context}/${shop.id}.sc?flag=1");
    }
    init();
	</script>
</html>
