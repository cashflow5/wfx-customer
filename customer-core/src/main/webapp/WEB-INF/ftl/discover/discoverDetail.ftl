<!DOCTYPE html>
<html>
	<head>
		<#include "../layouts/meta.ftl" />
	    <title>${discoverArticle.title}</title>
		<#include "../layouts/style.ftl"/>
	</head>  
	<body>
		<div class="viewport yg-cms-detail">  
			<header class="mui-bar yg-header header-ext">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">详情</h1>
				<a id="shared" class="mui-pull-right header-button" href="javascript:;"><span class="iconfont">&#xe667;</span></a>
			    <a class="iconfont header-button jumpLink"  href="${context}/discover/home.sc?shopId=${current_shop_id!''}" style="float:right;margin-right:36px;color:#848484;"></a>
			</header>
			<div class="mui-content"> 
				<div class="cms-content">
					<div class="cms-title-box">
						<div class="cms-item">
							<strong>${discoverArticle.title}</strong>
							<p class="Gray">${discoverArticle.showTime}</p>
						</div>
						<div class="cms-item">
							<span class="recommend"><i class="iconfont"></i> <#if discoverArticle.authorType==2 >
							                        推荐
							       <#else>
							                            官方
							       </#if></span>
							<!--<p>作者：张三</p>-->
						</div>
					</div>
					${discoverArticle.content}
					<#--<article class="cms-text-box">
						盼望着，盼望着，东风来了，春天的脚步近了。 一切都像刚睡醒的样子，欣欣然张开了眼。山朗润起 来了，水涨起来了，太阳的脸红起来了
					</article>
					<a class="cms-shop-box" href="http://m.yougou.net">
						<div class="cms-item"><img src="${discoverArticle.picCover}" onclick="void(0);" /></div>
						<div class="cms-item">
							<p>店铺编号xxxxxx</p>
							<p class="shop-desc Gray">店铺公告店铺公告店铺公告店铺公告店铺公告店铺公告铺铺铺铺铺铺铺店铺公告店铺公告店铺公告店铺公告店铺公告店铺公告铺铺铺铺铺铺铺店铺公告店铺公告店铺公告店铺公告店铺公告店铺公告铺铺铺铺铺铺铺</p>
						</div>
					</a>
					<div class="cms-img-box">
						<img src="static/images/imglist/details-img.png" onclick="void(0);" />
					</div>
					<a class="cms-goods-box" href="http://m.yougou.net">
						<div class="cms-item"><img src="static/images/imglist/goods-img.png" alt="" /></div>
						<div class="cms-item">
							<p>百丽2016夏季专柜同款女凉鞋</p>
							<p><span class="Red">售价：&yen;200.00</span><del class="ml15 Gray">&yen;888.00</del>
							</p>
						</div>
					</a>
					<articale class="cms-text-box">
						草偷偷地从土地里钻出来，嫩嫩的，绿绿的。 园子里，田野里，瞧去，一大片一大片满是的。坐 着，躺着，打两个滚，踢几脚球，赛几趟跑，捉几 回迷藏。风轻俏俏的，草软绵绵的。
					</articale>-->
					<input type="hidden" name="shopId" id="shopId" value="${(current_shop_id)!''}"/>
					<#if shopInfoDetail??>
					<#if discoverArticle.authorType==2>
					<div class="cms-dashed-box">
						本文作者店铺：
					</div>
					<a class="cms-shop-box" href="${shopInfoDetail.storeUrl!''}">
						<div class="cms-item"><img src="${shopInfoDetail.storeHeaderImg!''}" alt="" /></div>
						<div class="cms-item">
							<p><#if (shopInfoDetail.shopCode)=='优购微零售总店'>${(shopInfoDetail.shopCode)!''}<#else>No.${(shopInfoDetail.shopCode)!''}</#if></p>
							<p class="Gray"><#--店铺公告店铺公告店铺公告店铺公告店铺公告店铺公告铺铺铺铺铺铺铺...-->${shopInfoDetail.storeNotice!''}</p>
						</div>
					</a>
					</#if>
					</#if>
				</div>

			</div>
			
		</div>
<#include "../layouts/corejs.ftl">
<script> 
 var  shopId='${(current_shop_id)!''}';
 var shareLink='${context}/'+shopId+'/discover/view/${articleId}.sc';
 var dec=$(".cms-text-box").text();//;
 if(shopId==''){
    shareLink='${context}/discover/view/${articleId}.sc';
 }
    var shareobj={  
	   title:"${discoverArticle.title}", // 分享标题
	   desc:dec, // 分享描述
       link: shareLink, // 分享链接
       imgUrl:'${discoverArticle.picCover}', // 分享图标
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
	</body>
</html>
