<!DOCTYPE html>
<html>
<head>
	<#include "../layouts/meta.ftl">
    <title><#if !(pageType??)>优购微零售_${(commodity.commodityName)!''}</#if> <#if pageType??>成功优粉,玩转分享时尚生活</#if></title>
	<#include "../layouts/style.ftl">
	<link rel="stylesheet" type="text/css" href="${context}/static/css/productListAndDetail.css?v=${static_version}" />
	<style>.mui-toast-container{z-index:19891140}</style>
</head>
<body>
 <#if !(pageType??)>  
<div class="viewport">
	<#include "../shoppingcart/shopping-cart-float.ftl">
    <header class="mui-bar yg-header header-ext fixed">
    <#if running_environment != '4'>
        <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
     </#if>
        <div class="header-ext-box">
            <div class="header-nav-box-content">
           
              <#-- <div class="yg-nav-bar"><a href="${context}/${shop.id}/item/${commodity.no}.sc" class="yg-nav-bar-item active">商品</a><a href="${context}/${shop.id}/item/${commodity.no}.sc" class="yg-nav-bar-item">详情</a><a href="${context}/${shop.id}/item/${commodity.no}.sc?pageType=1"  class="yg-nav-bar-item">成为优粉</a></div>-->
          <div class="yg-nav-bar"> <a href="#goodsPhoto" class="yg-nav-bar-item active">商品</a><a href="#goodsDetail" class="yg-nav-bar-item">详情</a><a  href="#gotoBefans" class="yg-nav-bar-item">成为优粉</a></div>
            </div>
        </div>
        <#if (running_environment == '3' || running_environment == '4') && isOldVersion == '1'>
		<#else>
		<a id="shared" class="mui-pull-right header-button"><span class="iconfont">&#xe665;</span></a>
		</#if>
    </header>
    <nav class="mui-bar mui-bar-tab yg-nav">
	    <#--<a class="mui-tab-item yg-nav-btn">-->
	    <#--<span class="iconfont">&#xe62a;</span>-->
	    <#--<span class="mui-tab-label">收藏</span>-->
	    <#--</a>-->
	    <#--<a class="mui-tab-item yg-nav-btn">-->
	    <#--<span class="iconfont">&#xe628;</span>-->
	    <#--<span class="mui-tab-label">分享</span>-->
	    <#--</a>-->
	    <a class="mui-tab-item-link yg-nav-btn" onclick="location.href='${context}/commodity/service.sc'">
			<span class="iconfont">&#xe68b;</span>
			<span class="mui-tab-label">客服</span>
		</a>
		<a class="mui-tab-item-link yg-nav-btn" onclick="location.href='${context}/index.sc'">
			<span class="iconfont">&#xe612;</span>
			<span class="mui-tab-label">商城</span>
		</a>
        <a id="btnAddShoppingCart" class="mui-tab-item yg-nav-btn-add-2-shopcar">
            	加入购物车
        </a>
        <a id="btnBuyNow" class="mui-tab-item yg-nav-btn-buy-now" href="javascript:void(0)">
            	立即购买
        </a>
    </nav>
	<#--<#include "../layouts/menu.ftl">-->
    <div class="mui-content">
    	<a href="javascript:;" class="mui-btn yg-silder-popovers block">
        <div class="mui-slider" id="goodsPhoto">
            <div class="mui-slider-group">
            	<#if (commodity.pictures)??>
                	<#list commodity.pictures as picture>
                    <div class="mui-slider-item">
                        <img src="${picture.url}" data-preview-src="" data-preview-group="1" alt=""/>
                    </div>
                	</#list>
            	</#if>
            </div>
            <div class="mui-slider-indicator">
            	<#if (commodity.pictures)??>
                	<#list commodity.pictures as picture>
                    	<#if picture_index==0>
                       		<div class="mui-indicator mui-active"></div>
                    	<#else >
                        	<div class="mui-indicator"></div>
                    	</#if>
                	</#list>
            	</#if>
            </div>
        	<#if commodity.isOnsale!=1>
            	<div class="yg-goods-off-sale"></div>
        	<#elseif commodity.stock lt 1 >
            	<div class="yg-goods-sold-out"></div>
        	</#if>

        	</div>
        	</a>
        	<#--<div id="middlePopover" class="mui-popover yg-popover" >
				<div class="mui-slider">
					<div class="mui-slider-group">
						<#if (commodity.pictures)??>
		                	<#list commodity.pictures as picture>
		                    <div class="mui-slider-item">
		                        <a href="#"><img src="${picture.url}" alt=""/></a>
		                    </div>
		                	</#list>
		            	</#if>
					</div>
					<div class="mui-slider-indicator">
						<#if (commodity.pictures)??>
		                	<#list commodity.pictures as picture>
		                	<#if picture_index == 0><div class="mui-indicator mui-active"><#else><div class="mui-indicator"></#if></div>
		                	</#list>
		            	</#if>
					</div>
				</div>
				
			</div>-->
			<section class="yg-floor mb0">
				<input type="hidden" id="shopId" value="${shop.id!''}">
	            <input type="hidden" id="commodityId" value="${commodity.id!''}">
	            <div class="yg-layout-column-right-55 mb10">
					<div class="yg-layout-right yg-detail-share tcenter f12">
						<a id="yg-erweima" class="Gray" href="javascript:;"><span class="iconfont f18"></span><div class="lh-1">二维码</div></a>
					</div>
					<div class="yg-layout-main">
						<div class="yg-layout-container lh-12p">
							${(commodity.commodityName)!''}
						</div>
					</div>
				</div>
				<input type="hidden" id="wfxPrice" value="${(commodity.wfxPrice!0)?string("0.00")}">
				<p class="detail-price tvcenter"><span>&yen;${(commodity.wfxPrice!0)?string("0.00")}</span>&nbsp;<del class="Gray f12">&yen;${(commodity.publicPrice!0)?string("0.00")}</del></p>
				<#--<p class="detail-price-source"></p>-->
			</section>
	        
	        <section class="yg-shop-certificate-floor mui-row">
	            <div class="mui-col-sm-3 mui-col-xs-3"><i class="iconfont Yellow">&#xe603;</i><span>官方认证</span></div>
	            <div class="mui-col-sm-3 mui-col-xs-3"><i class="iconfont Blue">&#xe600;</i><span>担保交易</span></div>
	            <div class="mui-col-sm-3 mui-col-xs-3"><i class="iconfont Red">&#xe601;</i><span>正品保证</span></div>
	            <div class="mui-col-sm-3 mui-col-xs-3"><i class="iconfont Green">&#xe603;</i><span>七天退货</span></div>
	        </section>  
	        <section class="yg-floor yg-service-info-floor f12">
	            <#--<p><span class="Gray">运费</span>&nbsp;店铺单笔订单满99元免运费</p>-->
	            <div class="Gray"><span>服务</span>&nbsp;由优购发货并提供售后</div>
	            <div class="Gray"><span>提示</span>&nbsp;支持7天无理由退货</div>
	        </section>
	        <section class="yg-floor yg-form-floor">
	            <div id="formExt" class="yg-layout-column-left mt10">
	                <div class="yg-layout-left">已选</div>
	                <div class="yg-layout-main">
	                    <div class="yg-layout-container">
	                        <span id="pdColor" data-id="${commodity.no!''}">${commodity.specName!''}</span>
	                        <span id="pdSize" data-id=""></span>
	                        <span id="pdNumber">1</span>件 <i class="iconfont formExt">&#xe61e;</i></div>
	                </div>
	            </div>
	            <form id="formBuy">
	                <div class="yg-layout-column-left mt10">
	                    <div class="yg-layout-left">颜色</div>
	                    <div class="yg-layout-main">
	                        <div class="yg-layout-container">
	                            <input type="hidden" name="specName"/>
	                        <#if styleCommoditys??>
	                            <#list styleCommoditys as styleCommodity>
	                                <#if styleCommodity.stock?? && styleCommodity.stock &gt; 0>
	                                <#if styleCommodity.id ==commodity.id>
	                                    <a class="mui-btn spec-btn active"
	                                       data-value="${styleCommodity.specName}"
	                                       data-id="${styleCommodity.no!''}">${styleCommodity.specName}</a>
	                                <#else>
	                                    <a class="mui-btn spec-btn"
	                                       href="${context}/${shop.id}/item/${styleCommodity.no}.sc"
	                                       data-value="${styleCommodity.specName}"
	                                       data-id="${styleCommodity.no!''}">${styleCommodity.specName}</a>
	                                </#if>
	                                </#if>
	                            </#list>
	                        </#if>
	                        </div>
	                    </div>
	                </div>
	                <div class="yg-layout-column-left">
	                    <div class="yg-layout-left">尺码</div>
	                    <div class="yg-layout-main">
	                        <div class="yg-layout-container">
	                            <input type="hidden" name="pdSize"/>
	                        <#if (commodity.products)??>
	                            <#list commodity.products as product>
	                                <#if commodity.isOnsale==1 && product.sellStatus==1 && product.inventoryNum gt 0>
	                                    <a class="mui-btn"
	                                       data-value="${product.sizeNo}"
	                                       data-id="${product.productNo!''}">${product.sizeName}</a>
	                                <#else>
	                                    <!--<a class="mui-btn mui-btn-grey mui-disabled"
	                                       data-value="${product.sizeNo}"
	                                       data-id="${product.productNo!''}">${product.sizeName}</a>-->
	                                </#if>
	                            </#list>
	                        </#if>
	                        </div>
	                    </div>
	                </div>
	                <div class="yg-layout-column-left">
	                    <div class="yg-layout-left">数量</div>
	                    <div class="yg-layout-main">
	                        <div class="yg-layout-container">
	                            <div class="mui-numbox" data-numbox-min="1" data-numbox-max="${skuMaxCount}">
	                                <button class="mui-btn mui-btn-numbox-minus" type="button">-</button>
	                                <input name="pdNumber" class="mui-input-numbox" type="number" disabled/>
	                                <button class="mui-btn mui-btn-numbox-plus" type="button">+</button>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </form>
	        </section>
	        <#if propInfo?? && propInfo?size gt 0>
			<section class="yg-floor yg-basic-info f12">
				<p class="mb10 f14">基本信息</p>
				<#list propInfo as propList>
				<p class="Gray">
					<#list propList as item>
						<span <#t/>>${item.propItemName!''}：${item.propValue!''}<#if showSkin?? && showSkin=='show' && item.propItemName?? && item.propItemNo=='HaR06'><i id="info-notes" data-no="${item.propValueNo!''}" class="iconfont Red">&nbsp;&#xe68e;</i></#if></span>
					</#list>
				</p>
				</#list>
			</section>
	        </#if>
			<section class="yg-floor yg-shop-detial-floor">
				<div class="yg-layout-column-left-80">
					<div class="yg-layout-left">
						<div class="user-photo"><img src="${(shop.logoUrl)!''}" alt="" /></div>
					</div>
					<div class="yg-layout-main">
						<div class="yg-layout-container">
							<a href="${context}/${(shop.id)!''}.sc?flag=1" class="shop-title">
							<span id="shopCode"><#if (shop.shopCode)=='优购微零售总店'>${(shop.shopCode)!''}<#else>No.${(shop.shopCode)!''}</#if></span>
								<i class="iconfont Gray">&#xe62b;</i>
							</a>
						</div>
					</div>
				</div>
				<!--<div class="mui-row mt10">
					<div class="mui-col-sm-6 mui-col-xs-6">
						<a href="category-list.shtml" class="mui-btn"><i class="iconfont">&#xe622;</i> 查看商品分类</a>
					</div>
					<div class="mui-col-sm-6 mui-col-xs-6">
						<a href="index.shtml" class="mui-btn"><i class="iconfont Red">&#xe60e;</i> 进入店铺</a>
					</div>
				</div>-->
			</section>
        	<section class="yg-floor yg-shop-detial-new">
        	<a href="javascript:void(0)" onclick="window.location.href=global.ufans_url" class="yg-nav-bar-item">
				<img src="${context}/static/images/banner.png"/>
			</a>
 			</section>
 			<#if sizeConList?? && sizeConList?size gt 0>
 			<section class="yg-floor pb10">
				<header class="title-wrap">
					<h2 class="title f14">尺码对照表</h2>
				</header>
				<div class="yg-size-table">
					<table>
						<#list sizeConList as sizeLine>
						<tr>
						<#list sizeLine as sizeItem>
							<#if sizeItem_index == 0>
							<th>${sizeItem!''}</th>
							<#else>
							<td>${sizeItem!''}</td>
							</#if>
						</#list>
						</tr>
						</#list>
					</table>
				</div>
			</section>
			</#if>
 			<section id="goodsDetail" class="yg-floor yg-detail-img-floor">
	        	${(commodity.prodDesc)!''}
	        </section>
	        <div class="yg-page-ufans" id="gotoBefans">
				<section class="yg-floor yg-detail-img-floor pd0">
					<img class="w100p" src="${context}/static/images/imglist/ufans_02.png">
					<p class="pd10">
						优购科技有限公司是百丽集团（母公司为香港上市公司百丽国际，股票代码01880）控股的互联网电子商务平台运营主体， 2011年7月正式上线的优购网，目前是国内最大的鞋服类垂直互联网平台。
					</p>
				</section>
				<section class="yg-floor yg-floor-gray">
					<img class="fl" src="${context}/static/images/imglist/ufans_05.png" width="114" />
					<p>优购微零售(yougou.net)是优购旗下专注移动互联网的微店购物分享平台，经营的商品为优购网的精选商品，目前主要包括鞋类、运动及户外产品，品牌囊括了百丽，思加图，他她，天美意，耐克，阿迪达斯，哥伦比亚，CAT等一线国际品牌的成人及儿童产品。
					</p>
				</section>
				<section class="yg-floor pd10">
					<img class="fr" src="${context}/static/images/imglist/ufans_20.png" width="100" /> 优购微零售优势：知名品牌100%的正品保证（知名保险公司承保）；有竞争力的商品零售价格；丰厚的销售分成机制；完善的物流配送体系；7*24小时的客户服务；百丽集团及优购的商誉担保。
				</section>
				<section class="yg-floor-gray">
					<img class="w100p" src="${context}/static/images/imglist/ufans_12.png" />
					<p class="pd10">
						优购微零售是面对基于移动互联网及微信平台的商业应用，主要服务对象为有志于创业的小微企业及个体合作者，顾客第一次购物成功后，即可申请成为优购微零售的合伙伙伴（以下简称“优粉”）。其基本商业模式为：优购精选商品并确定其零售价格，优粉适时在各自的社交网络及微信社区分享商品链接和推荐优购微零售官方店铺，在完成有效销售前提下，作为优粉将享有约定的佣金分成，具体分成的模式及比例详见如下。
					</p>
					<div class="mui-clearfix pd10">
						<img class="fl" src="${context}/static/images/imglist/ufans_15.png" width="107" />
						<p>
							<h4>优粉</h4> 认可优购微零售的商品和服务，本人发生有效购买后即可成为优购微零售分销商。优粉负责拓展、维护自己的社交网络及分享，优购微零售负责所有的订单发货、售后服务等。
						</p>
					</div>
					<div class="mui-clearfix pd10">
						<img class="fr" src="${context}/static/images/imglist/ufans_19.png" width="100" />
						<p>
							<h4>如何成为优粉？</h4> 完成首次有效购买（有效购买指交易完成后未发生任何退换货的行为）后即可成为优粉。
						</p>  
					</div>
					<p class="tcenter">
						<a href="javascript:void(0)" onclick="$(window).scrollTop(0);$('.yg-nav-bar a').removeClass('active').eq(0).addClass('active')"  class="mui-btn mui-btn-danger w50p">马上购物</a>
					</p>
				</section>
				<section class="yg-floor pd10">
					<img class="fl" src="${context}/static/images/imglist/ufans_21.png" width="100" />
					<p>
						<h4>佣金及分级</h4> 为鼓励优粉推广、拓展优购微零售，采用三级佣金分成模式。以下所有佣金计算的基础均指实现有效销售（指交易完成后未发生任何退换货的行为）的销售额。
					</p>
				</section>
				<section class="yg-floor">
					<img class="w100p" src="${context}/static/images/imglist/yongjin.jpg" />
				</section>
				<section class="yg-floor pd10">
					<img src="${context}/static/images/imglist/ufans_32.png" alt="" class="fl" width="78" />
					<p>
						<h4>佣金的的发放方式</h4> 1)获得佣金的时间周期。优购微零售店铺收到客户 订单之后，订单将流转至优购仓库，由系统自动分配 至就近仓库发出商品。一般而言，综合不同地区的距离，快递时间2-5天左右，待客户收到商品，再给予客户7天无理由退换货时间。如果产生退换货，退换货时间约7天， 三项相加约19天，加上提现时间，约总计21天。即实现有效销售的 21天后佣金可以到达优粉的账户。
					</p>
					<div class="mui-clearfix">
						<img src="${context}/static/images/imglist/ufans_36.png" alt="" class="fr" width="100" /> 2) 优购微零售账户中佣金的使用。账户内 的佣金可以直接作为现金在微零售的平台上 购买商品，也可以向优购微零售提交提现申 请，在2个工作日内，平台审核完成后直接 汇入到优粉本人在平台上登记的银行卡账户 ，必须与优粉本人姓名一致。
					</div>
				</section>
				<section class="yg-floor yg-floor-gray">
					<h4 class="tcenter">优粉贴心服务</h4>
					<p> 优粉服务管家，配合优粉的分享及管理工作。</p>
					<ul class="links-box mui-clearfix">
						<li><span class="iconfont">&#xe662;</span><p>客服电话： <br />400-163-1055</p>
						</li>
						<li><span class="iconfont f28">&#xe65c;</span><p>管理微信号： <br />yougouwls <br />yougouwls01</p>
						</li>
						<li class="w100p"><span class="iconfont">&#xe663;</span><p>微信服务号： <br />优购微零售官方服务号</p>
						</li>
					</ul>
					<!--<p>2) 优粉推广支持</p>
					<p>为了保证优粉的利益，优粉可以自行建群。如需要平台的支持，可 以邀请优粉管家进群协助管理。新加入的优粉可以询问推荐人，进 入推荐人的优粉群。
					</p>-->
				</section>
			</div>
	    </div>
    </div>
</div>
</#if>

<#include "../layouts/corejs.ftl">
<script type="text/javascript">
    var commodityNo = '${commodity.no!''}';
    function shareQzone(){window.open("http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url="+window.location.href+"&title="+$('.lh-12p').html().trim()+"&pics="+$('#shareimg').attr('src')+"&showcount=0")}
    function shareSina(){
      window.open("http://service.weibo.com/share/share.php?title="+$('.lh-12p').html().trim()+"&url="+window.location.href+"&source=bookmark&pic="+$('#shareimg').attr('src')+"&searchPic=false")
    };
    var  jiathis_config = {
      imageUrl:$("#goodsPhoto").find('.mui-slider-item').find('img').attr('src'),
      url:window.location.href,
      title:$("title").html(),
      pic:$("#goodsPhoto").find('.mui-slider-item').find('img').attr('src'),
     summary:'',
    };
  var shareobj={  
	   title:"优购微零售_${(commodity.commodityName)!''}", // 分享标题
	   desc:"售价:￥${(commodity.wfxPrice!0)?string("0.00")} [优购微零售，正品保证]", // 分享描述
       link: window.location.href, // 分享链接
       imgUrl:$('.mui-slider-item').eq(0).find("img").attr('src'), // 分享图标
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
<script src="${context}/static/js/plugins/mui.zoom.js?v=${static_version}"></script>
<script type="text/javascript" src="http://v3.jiathis.com/code/jiathis_m.js" charset="utf-8"></script>
<script src="${context}/static/js/plugins/mui.previewimageCommondityDetail.js?v=${static_version}"></script>
<script src="${context}/static/js/yg-detail.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>

</html>