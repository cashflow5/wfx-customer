<!DOCTYPE html>
<html>

	<head>
		<#include "../layouts/meta.ftl"/>
		<title>发现_列表</title>
		<#include "../layouts/style.ftl"/>
		 <link rel="stylesheet" type="text/css" href="${context}/static/css/plugins/dropload/dropload.css?v=${static_version}" />
		<script>
	     var scCount = "${scCount!'0'}";
       </script>
	</head>

	<body>
		<div class="viewport yg-list-page">
			<!--<header class="mui-bar yg-header header-ext">
				<h1 class="mui-title">发现</h1>
			</header>-->
			<#include "../layouts/index-menu.ftl"/>
			<div class="mui-content">
				<div id="listData" class="mui-control-content mui-active">
				
					<div class="swiper-container">
						<div class="swiper-wrapper">
							<a class="swiper-slide" href="${context}/discover/home.sc?shopId=${current_shop_id!''}">首页</a>
							<#if channelList??>
								<#list channelList as item>
								 <a class="swiper-slide" href="${context}/discover/list/${item.id}.sc" rel="${item.id}">${item.channelName}</a>
								</#list>
							</#if>
						</div>
					</div>
					
					<#if result??&&(result.items)??&&(result.items?size>0)>
					<#list result.items as item>
						<a class="yg-cms-column-1-floor mt10" href="${context}/discover/view/${item.id}.sc">
							<div class="cms-column-item">
								<img src="${item.picCover}" alt="" />
							</div>
							<div class="cms-column-item">
								<p>
								<#if item.classify??&&item.classify!=''>
								<span class="Red">[${item.classify}] </span>
								</#if>
								${item.title}</p>
								<p class="date-item">${item.showTime}</p>
							</div>
						</a>
					</#list>
					 <#else>
		                <section class="yg-floor">
		                    <div class="title-wrap mui-text-center">
		                        <div class="title">该频道下暂时没有文章！</div>
		                    </div>
		                </section>
					</#if>
				</div>
                <div id="ajaxPage"></div>
			</div>
		</div>
		<input id="channel" type="hidden" value="${channelId}">
		<#include "../layouts/corejs.ftl">
		<script src="${context}/static/js/plugins/mui.swiper.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
		<script src="${context}/static/js/yg-discover-list.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
	</body>

</html>