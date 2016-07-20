<!DOCTYPE html>
<html>

<head>
<#include "../layouts/meta.ftl">
<meta http-equiv="x-dns-prefetch-control" content="on" />
<link rel=dns-prefetch” href=http://w1.ygimg.cn”/>
<link rel=dns-prefetch” href=http://w2.ygimg.cn”/>
<link rel=dns-prefetch” href=http://i1.ygimg.cn”/>
<link rel=dns-prefetch” href=http://i2.ygimg.cn”/>
    <title>优购微零售-<#if (shop.shopCode)=='优购微零售总店'>${(shop.shopCode)!''}<#else>No.${(shop.shopCode)!''}</#if></title>
<#include "../layouts/style.ftl">
</head>

<body>
<input id='currentShopId' type="hidden" value='${(shop.id)!''}'/>
<div id="pageIndex" class="viewport">
<header class="mui-bar yg-header">
	<a class="mui-pull-left header-logo" hreft="javascript:;"><span></span></a>
	<h1 class="mui-title"><span><#if (shop.shopCode)=='优购微零售总店'>${(shop.shopCode)!''}&nbsp;<#else>No.${(shop.shopCode)!''}&nbsp;&nbsp;</#if></span><!--<span class="yg-badge-primary">加盟</span>--></h1>
	<#if (running_environment == '3' || running_environment == '4') && (isOldVersion?? && isOldVersion == '1')>
	<#else>
	<a id="shared" class="mui-pull-right header-button" href="javascript:;"><span class="iconfont">&#xe667;</span></a>
	</#if>
	<!--<a id="search" class="mui-pull-right header-button" data-href="search.shtml"><span class="iconfont">&#xe666;</span></a>-->
</header>
<#if running_environment != '3' && running_environment != '4'>
	<#include "../layouts/index-menu.ftl">
</#if>

<#--<#include "../layouts/menu.ftl">-->
 	
 	<div class="mui-content" id="mui-content-index">
 	
	 	<#if (mall.showImageList)??>
	 	<!-- 轮播图 -->
		<div id="indexSlider" class="mui-slider">
			<div class="mui-slider-group mui-slider-loop">
				<#if (mall.showImageList?last)??>
					<div class="mui-slider-item mui-slider-item-duplicate">
						<a href="${(mall.showImageList?last).jumpUrl!''}"><img src="${(mall.showImageList?last).imageUrl!''}" alt="" /></a>
					</div>
				</#if>
				<#list mall.showImageList as image>
				<div class="mui-slider-item">
					<a href="${image.jumpUrl!''}"><img src="${image.imageUrl!''}" alt="" /></a>
				</div>
				</#list>
				<#if (mall.showImageList?first)??>
					<div class="mui-slider-item mui-slider-item-duplicate">
						<a href="${(mall.showImageList?first).jumpUrl!''}"><img src="${(mall.showImageList?first).imageUrl!''}" alt="" /></a>
					</div>
				</#if>
			</div>
			<div class="mui-slider-indicator">
				<#list mall.showImageList as image>
				<div class="mui-indicator <#if image_index==0>mui-active</#if>"></div>
				</#list>
			</div>
		</div>
 		</#if>
 		
 		<#if (mall.noticeList)??>
 		<!-- 公告 -->
 		<div class="yg-floor yg-floor-noticle2">
					<div class="yg-layout-column-left">
						<div class="yg-layout-left tcenter">
							<span class="top-text">头条</span>
						</div>
						<div class="yg-layout-main">
						   <div class="yg-layout-container">
 		                      <ul>
 			                       <#list mall.noticeList as notice>
			                            <li class="noticle-item"><a href="${notice.jumpLink!''}">${notice.title!''}</a></li>
			                       </#list>
		                       </ul>
		                    </div>
						</div>
					</div>
				</div>
		</#if>
 		<!-- 品牌购 -->
 		<section class="yg-floor yg-floor-brand">
			<header class="title-wrap-standard pl10">
				<h2 class="title">品牌购</h2>
			</header>
			<#if (mall.brandList)??>
			<div class="mui-slider">
				<div class="mui-slider-group">
					<#list mall.brandList as duBrand>
					<div class="mui-slider-item">
						<#list duBrand as brandItem>
						<div class="yg-brand-box mui-clearfix">
							<#list brandItem as brand>
							<a href="${context}/${shop.id!''}/list.sc?brandNo=${brand.brandNo!''}" class="brand-item">
								<img src="${brand.imageUrl!''}" alt="" class="brand-img" />
								<div class="brand-text">${brand.name!''}</div>
							</a>
							</#list>
						</div>
						</#list>
					</div>
					</#list>
				</div>
				<div class="yg-brand-indicator mui-slider-indicator">
					<#list mall.brandList as duBrand>
					<div class="mui-indicator <#if duBrand_index==0>mui-active</#if>"></div>
					</#list>
				</div>
			</div>
			<#else>
			<div class="mui-text-center ">
                <div>暂无品牌数据！</div>
            </div>
			</#if>
		</section>
 		
 		<!-- 精选分类 -->
 		<section class="yg-floor">
			<header class="title-wrap">
				<h2 class="title">精选分类</h2>
			</header>
			<#if (mall.catgoryList)??>
			<div class="yg-cat-box">
				<#list mall.catgoryList as cat>
				<a class="cat-item" href="${context}/${shop.id!''}/list.sc?catId=${cat.id!''}">
					<img src="${cat.imageUrl!''}" alt="" />
					<div class="cat-text">${cat.name!''}</div>
				</a>
				</#list>
			</div>
			<#else>
			<div class="mui-text-center ">
                <div>暂无精选分类数据！</div>
            </div>
			</#if>
		</section>
 		
        <section class="yg-floor">
            <header class="title-wrap">
                <h2 class="title">热卖商品</h2>
				<a href="${context}/${(shop.id)!''}/list.sc" class="more">更多</a>
            </header>
        <div class="yg-piclist normal">
        <#if hotCommoditys??>
        <div id="listData" class="mui-control-content mui-active">
            <#list hotCommoditys as commodity>
                <a class="yg-piclist-item" href="${context}/${(shop.id)!''}/item/${(commodity.no)!''}.sc">
                    <div class="content">
                        <div>
                            <img data-lazyload="${commodity.pictureMb!(context+'/static/images/thum.png')}"/>
	                        <p class="item-title">${(commodity.commodityName)!''}</p>
	                        <p class="item-price">&yen;${(commodity.wfxPrice!0)?string('0.00')}
	                        	<span class="item-price-source">&yen;${(commodity.publicPrice!0)?string('0.00')}</span>
	                        </p>
                        </div>
                        <#--<#if commodity.isOnsale!=1>-->
                            <div class="yg-goods-off-sale"></div>
                        <#--<#elseif commodity.stock lt 1 >-->
                        <#--<div class="yg-goods-sold-out"></div>-->
                        <#--</#if>-->
                    </div>
                </a>
            </#list>
 			</div>
        </div>
        <!--<div class="see-all">
            <a class="mui-btn" href="${context}/${(shop.id)!''}/list.sc"> 查看全部商品 </a>
        </div>-->
        <div class="yg-fixed-right" style="bottom: 110px; top: auto; opacity: 1; display: block;">
        	<a href="javascript:;" class="gotop-link" title="返回顶部"><i class="iconfont"></i></a>
        </div>
        <#else>
            <div class="mui-text-center ">
                <div>该店铺下暂无商品！</div>
            </div>
        </#if>
        </section>
    </div>
</div>
<#include "../layouts/corejs.ftl">
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
<script src="${context}/static/js/plugins/mui.lazyload.js?v=${static_version}"></script>
<script src="${context}/static/js/plugins/mui.lazyload.img.js?v=${static_version}"></script>
<script type="text/javascript" src="http://v3.jiathis.com/code/jiathis_m.js" charset="utf-8"></script>
<script src="${context}/static/js/yg-index.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script src="${context}/static/js/yg-index-page.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>