<!DOCTYPE html>
<html>

	<head>
		<#include "../layouts/meta.ftl">
		<#include "../layouts/style.ftl">
		<title>优购微零售_我的粉丝</title>
		<link rel="stylesheet" type="text/css" href="${context}/static/css/mui.picker.min.css" />
	</head>

	<body>
		<div class="viewport">
			<header class="mui-bar yg-header header-ext">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">我的粉丝</h1>
			</header>
			<div class="mui-content yg-my-fans yg-account-information bg-white">
				<div class="yg-my-income">
					<div class="pd10 pl20">
						我的粉丝(${count!0})<i class="iconfont fr" id="fanshelp">&#xe68e;</i>
					</div>
					<div class="mf-bg-help none" id="fanshelptext" style="background:#fafafa">
						<div class="pd10 pl20 relative mf-color-gray">
							其他人通过分享出的店铺或商品链接注册登录后，则锁定成为你的粉丝。
粉丝成功支付一笔订单后会成为你的下级分销商，在其店铺的每一笔交易都会为你带来佣金。此时可以到“下级分销商”中查看。
                       <div class="mf-snajiao" style='border-left:10px solid #fafafa transparent;border-right:10px solid #fafafa transparent;border-bottom:15px solid #fafafa;'></div>
						</div>
					</div>
				</div>
				<section class="">
					<ul id="listData" class="yg-visitor-records mui-table-view  vr-bofore-none">
						<input type="hidden" id="pageCount" value="${(result.pageCount)!1}">
						<#if result??&&(result.items)??&&(result.items?size>0)>
						<#list result.items as item>
							<li class="mui-table-view-cell vr-after-pl0">
	
								<a class="">
									<div class="mui-row">
										<div class="mui-col-sm-2 mui-col-xs-2 mf-pt3">
											<span class="user-head-portrait mr20 lh-44">
												<img src="${(item.headShowImg)!'${context}/static/images/admin-user-photo.png'}"/>
											</span>
										</div>
										<div class="mui-col-sm-10 mui-col-xs-10 lh-44">
										<span><#if (item.platformUsername)??>${(item.platformUsername)}<#else>${(item.loginName)!''}</#if></span>
										</div>
									</div>
								</a>
							</li>
						</#list>
						<#else>	
						<li class="mui-table-view-cell vr-after-pl0">

							<a class="">
								<div class="mui-row">
									
									<div class="mui-col-sm-10 mui-col-xs-10 lh-44"><span>无数据</span></div>
									
								</div>
							</a>
						</li>
					</#if>	
					
					</ul>
				</section>
			</div>
		</div>
		<#include "../layouts/corejs.ftl">
		<script src="${context}/static/js/jquery-3.0.0.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
		<script src="${context}/static/js/ufans/yg-fans-list.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
		
		<script>
			$("#fanshelp").bind('click',function(){
				$("#fanshelptext").toggleClass('none');
				
			});
		</script>
	</body>
</html>