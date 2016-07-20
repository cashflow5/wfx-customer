<!DOCTYPE html>
<html>

	<head>
		<#include "../layouts/meta.ftl" />
	    <title>优购微零售-发现首页</title>
		<#include "../layouts/style.ftl"/>
		<style type="text/css">
		.yg-cms-column-2-floor>.cms-column-item>p{white-space: normal;height: 30px;overflow: hidden;}
		</style>
		<script>
	var scCount = "${scCount!'0'}";
</script>
	</head>

	<body>
	    <div class="viewport">
	        <header class="mui-bar yg-header header-ext" style="height: 0;">
				<h1 class="mui-title">发现</h1>
			</header>
	        <#include "../layouts/index-menu.ftl"/>
			<div class="mui-content">
					<div id="listData" class="mui-control-content mui-active">
				<!--<div class="swiper-container yg-visible">
					<div class="swiper-wrapper">
						<a class="swiper-slide slide-active" href="${context}/discover/home.sc?shopId=${current_shop_id!''}">首页</a>
						<#if discoverHome??&&(discoverHome.discoverChannelList)??>
							<#list discoverHome.discoverChannelList as item>
							 <a class="swiper-slide" href="${context}/discover/list/${item.id}.sc">${item.channelName}</a>
							</#list>
						</#if>
					</div>
				</div>-->
				<div class="mui-slider">
					<div class="mui-slider-group">
					    <#if discoverHome??&&(discoverHome.discoverCarouselFigureList)??>
					      <#list discoverHome.discoverCarouselFigureList as item>
					         <div class="mui-slider-item">
							    <a href="${context}/discover/view/${item.discoverArticle.id}.sc"><img src="${item.picture}" alt="${item.name}" /></a>
						     </div>
						  </#list>
                        </#if>
					</div>
					<div class="mui-slider-indicator">
						<#if discoverHome??&&(discoverHome.discoverCarouselFigureList)??>
							<#list discoverHome.discoverCarouselFigureList as item>
						      <div class="mui-indicator"></div>
						  </#list>
                        </#if>
					</div>
				</div>
				<#if discoverHome??&&(discoverHome.pageDiscoverArticleResult.items)??>
				     <section class="yg-cms-column-2-floor mt10">
					  <#list discoverHome.pageDiscoverArticleResult.items as item>
					    <#if item_index lt 2>
					     <a class="cms-column-item" href="${context}/discover/view/${item.id}.sc">
						   <img src="${item.picCover}" alt="" />
						    <p>
						    <#if item.classify??&&item.classify!=''>
						    <span class="Red">[${item.classify}] </span>
						    </#if>
						    ${item.title}</p>
					     </a>
					     </#if>
				       </#list>
                      </section>
				      <#list discoverHome.pageDiscoverArticleResult.items as item>
				         <#if item_index gte 2>
				         <a class="yg-cms-column-1-floor mt10" href="${context}/discover/view/${item.id}.sc">
					         <div class="cms-column-item">
						       <img src="${item.picCover}" alt="" />
					         </div>
					         <div class="cms-column-item">
						         <p>
						        <#if item.classify??&&item.classify!=''>
						         <span class="Red">[${item.classify}] </span> 
						         </#if> ${item.title}</p>
						         <p class="date-item">${item.showTime}</p>
					         </div>
				           </a>
				         </#if>
				      </#list>
                 </#if>
			</div>
		</div>
	    <#include "../layouts/corejs.ftl"/>
<script>
 var shareobj={  
	   title:"", // 分享标题
	   desc:'', // 分享描述
       link:'', // 分享链接
       imgUrl:'', // 分享图标
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
	    <script src="${context}/static/js/plugins/mui.swiper.min.js" type="text/javascript" charset="utf-8"></script>
		<script src="${context}/static/js/plugins/mui.lazyload.js"></script>
		<script src="${context}/static/js/plugins/mui.lazyload.img.js"></script>
		<script src="${context}/static/js/yg-index.js" type="text/javascript" charset="utf-8"></script>
		<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
		<script src="${context}/static/js/yg-discover-list.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
	</body>
</html>