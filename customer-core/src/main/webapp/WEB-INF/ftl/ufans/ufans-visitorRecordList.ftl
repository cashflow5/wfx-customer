<!DOCTYPE html>
<html>

	<head>
		<#include "../layouts/meta.ftl">
	    <#include "../layouts/style.ftl">
		<title>优购微零售_访客记录</title>
		<link rel="stylesheet" type="text/css" href="${context}/static/css/mui.picker.min.css" />
	</head>

	<body>
		<div class="viewport">
			<header class="mui-bar yg-header header-ext">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">访客详情</h1>
			</header>
			<div class="mui-content yg-my-fans yg-account-information bg-white">
				<div class="yg-my-income">
			
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
										<div class="mui-col-sm-10 mui-col-xs-10 lh-44"><span>${(item.visitorName)!''}</span></div>
										
									</div>
								</a>
							</li>
						</#list>
					<#else>	
						<li class="mui-table-view-cell vr-after-pl0">
							<a class="">
								<div class="mui-row">									
									<div class="mui-col-sm-10 mui-col-xs-10 lh-44"><span>暂无访客记录</span></div>									
								</div>
							</a>
						</li>
					</#if>	
					
					</ul>
				</section>
				<input type="hidden" id="date" value="${date!''}">
			</div>
		</div>
		<#include "../layouts/corejs.ftl">
		<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
		<script src="${context}/static/js/yg-visitor-list.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
		
	</body>
	
</html>