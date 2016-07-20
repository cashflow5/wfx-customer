<!DOCTYPE html>
<html>

	<head>
		<#include "../layouts/meta.ftl" />
	    <title>成功优粉,玩转分享时尚生活</title>
		<#include "../layouts/style.ftl"/>
	</head>

	<body>
		<div class="viewport yg-page-ufans">
			<a class="yg-shop-card-fixed" href="shopping-cart.shtml">
				<span class="iconfont icon-gouwuche"><span id="shopcartTotal" class="mui-badge mui-badge-bby">9</span></span>
			</a>
			<header class="mui-bar yg-header header-ext fixed">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<div class="header-ext-box">
					<div class="header-nav-box-content">
						<div class="yg-nav-bar"><a href="detail.shtml#goodsPhoto" class="yg-nav-bar-item">商品</a><a href="detail.shtml#goodsDetail" class="yg-nav-bar-item">详情</a><a href="ufans.shtml" class="yg-nav-bar-item active">成为优粉</a></div>
					</div>
				</div>
				<a id="btnMenu" class="mui-pull-right header-button"><span class="iconfont">&#xe637;</span></a>
			</header>
			<nav class="mui-bar mui-bar-tab yg-nav">
				<a class="mui-tab-item yg-nav-btn">
					<span class="iconfont">&#xe62a;</span>
					<span class="mui-tab-label">收藏</span>
				</a>
				<a class="mui-tab-item yg-nav-btn">
					<span class="iconfont">&#xe628;</span>
					<span class="mui-tab-label">分享</span>
				</a>
				<a class="mui-tab-item yg-nav-btn-add-2-shopcar">
					<span class="mui-tab-label">加入购物车</span>
				</a>
				<a id="btnBuyNow" class="mui-tab-item yg-nav-btn-buy-now">
					<span class="mui-tab-label">立即购买</span>
				</a>
			</nav>
			<!--#include file="layouts/menu.shtml"-->
			<div class="mui-content">
				<section class="yg-floor yg-detail-img-floor pd0">
					<img class="w100p" src="static/images/imglist/ufans_02.png">
					<p class="pd10">
						优购科技有限公司是百丽集团（母公司为香港上市公司百 丽国际，股票代码01880）控股的互联网电子商务平台运 营主体， 其运营的优购网（域名：www.yougou.com） 于2011年7月正式上线，目前是国内最大的鞋服类垂直互 联网平台。
					</p>
				</section>
				<section class="yg-floor yg-floor-gray">
					<img class="fl" src="static/images/imglist/ufans_05.png" width="114" />
					<p>优购微零售( yougou.net ) 是优购旗下专注移动互联网的购物分享平 台，经营的商品为优购网的精选商品，品 类目前主要包括鞋类、运动及户外产品， 品牌囊括了百丽，思加图，他她，天美意 ，耐克，阿迪达斯，哥伦比亚，CAT等一 线国际品牌的成人及儿童产品。
					</p>
				</section>
				<section class="yg-floor pd10">
					<img class="fr" src="static/images/imglist/ufans_20.png" width="100" /> 优购微零售优势 知名品牌，100%的正品保证；有竞争力 的商品零售价格；丰厚的销售机制；完善 的物流配送体系；7*24小时的客户服务； 百丽集团及优购的商誉担保。
				</section>
				<section class="yg-floor-gray">
					<img class="w100p" src="static/images/imglist/ufans_12.png" />
					<p class="pd10">
						优购微零售是面对终端消费者基于移动互联网的商业应用， 主要服务对象为有志于创业的小微企业及个人。顾客第一次 购物成功后，即可申请成为优购微零售的分销商（以下简称 “优粉”）。基本商业模式为：优购精选商品并确定零售价 格，优粉适时在各自的社交网络及微信社区分享商品链接及 传播推广其店铺，在完成有效销售的前提下，优粉将享有约 定的佣金。具体的模式及比例详见以下。
					</p>
					<div class="mui-clearfix pd10">
						<img class="fl" src="static/images/imglist/ufans_15.png" width="107" />
						<p>
							<h4>优粉</h4> 认可优购微零售的商品和服务，本人发生有 效购买后申请开店并获得审核的优购微零售 分销商。优粉负责拓展、维护自己的社交网 络及分享，优购微零售负责所有订单交易、 发货，售后服务等。
						</p>
					</div>
					<div class="mui-clearfix pd10">
						<img class="fr" src="static/images/imglist/ufans_19.png" width="100" />
						<p>
							<h4>如何成为优粉？</h4> 完成首次下单并实现有效销售（有效销售指 交易完成后未发生退换货）后经优购微零售 审核确认后成为优粉。
						</p>
					</div>
					<p class="tcenter">
						<a href="${context}/index.sc" class="mui-btn mui-btn-danger w50p">马上购物</a>
					</p>
				</section>
				<section class="yg-floor pd10">
					<img class="fl" src="static/images/imglist/ufans_21.png" width="100" />
					<p>
						<h4>佣金及分级</h4> 为鼓励优粉推广、拓展优购微零售，采用三 级佣金模式。以下所有佣金计算的基础均指 实现有效销售的销售额。
					</p>
				</section>
				<section class="yg-floor yg-floor-gray">
					<img class="w100p" src="static/images/imglist/ufans_25.png" />
					<p>根据上述案例分析总结如下： 1、从纵向来说，无论多少层次只享受包括三级佣金，其计算基数为 自身的有效销售额、自己直接下级的有效销售额及再下级的有效销售 额，各级佣金的比例各不相同。 2、从业务的三级佣金逻辑来看，加大力度发展下级优粉及直接有效 销售可以带来显著及持续性的业务增长，特别是直接下级优粉的数量 将决定自身事业群的规模。
					</p>
					<div class="mui-clearfix"> <img class="w100p" src="static/images/imglist/ufans_28.png" />
						<p>
							*具体收益以实际交易完成为主，详情请咨询客服 户外、运动商品：一、二级分销收益6%，三级分销收益2% <br />以上说明：以单个订单为例
						</p>
					</div>
				</section>
				<section class="yg-floor pd10">
					<img src="static/images/imglist/ufans_32.png" alt="" class="fl" width="78" />
					<p>
						<h4>佣金的的发放方式</h4> 1)获得佣金的时间周期。优购微零售店铺收到客户 订单之后，订单将流转至优购仓库，由系统自动分配 至就近仓库发出商品。一般而言，综合不同地区的距 离，快递时间2-5天左右，待客户收到商品，再给予 客户7天无理由退换货时间。如果产生退换货，退换货时间约7天， 三项相加约19天，加上提现时间，约总计21天。即实现有效销售的 21天后佣金可以到达优粉的账户。
					</p>
					<div class="mui-clearfix">
						<img src="static/images/imglist/ufans_36.png" alt="" class="fr" width="100" /> 2) 优购微零售账户中佣金的使用。账户内 的佣金可以直接作为现金在微零售的平台上 购买商品，也可以向优购微零售提交提现申 请，在2个工作日内，平台审核完成后直接 汇入到优粉本人在平台上登记的银行卡账户 ，必须与优粉本人姓名一致。
					</div>
				</section>
				<section class="yg-floor yg-floor-gray">
					<h4 class="tcenter">优粉贴心服务</h4>
					<p>1) 优粉服务管家，配合优粉的分享及管理工作。</p>
					<ul class="links-box mui-clearfix">
						<li><span class="iconfont">&#xe662;</span><p>客服电话： <br />400-163-1055</p>
						</li>
						<li><span class="iconfont f28">&#xe65c;</span><p>管理微信号： <br />yougouwls <br />yougouwls01</p>
						</li>
						<li class="w100p"><span class="iconfont">&#xe663;</span><p>微信服务号： <br />优购微零售官方服务号</p>
						</li>
					</ul>
					<p>2) 优粉推广支持</p>
					<p>为了保证优粉的利益，优粉可以自行建群。如需要平台的支持，可 以邀请优粉管家进群协助管理。新加入的优粉可以询问推荐人，进 入推荐人的优粉群。
					</p>
				</section>
			</div>
		</div>
	    <#include "../layouts/corejs.ftl">
	</body>

</html>