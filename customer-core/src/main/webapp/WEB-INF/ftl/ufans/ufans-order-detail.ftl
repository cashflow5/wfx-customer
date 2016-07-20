<#-- @ftlvariable name="order" type="com.yougou.wfx.appserver.vo.order.OrderDetailInfo" -->
<!DOCTYPE html>
<html>

	<head>
		<#include "../layouts/meta.ftl">
   		<title>优购微零售_优粉_订单详情</title>
		<#include "../layouts/style.ftl">
	</head>

	<body class="bg-gray">
		<div class="viewport yg-my-order">
			<header class="mui-bar yg-header header-ext">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">${(order.state_flag)!''}</h1>
				<input type="hidden" id="level" value="${(identity.level)!1}"/>
			</header>
			<div class="mui-content">
				<div id="myListData" class="mui-control-content mui-active">
					<div class="yg-shopping-top w100p mui-clearfix pt10 pb10 pl5">
						<span class=" tcenter fl f12">订单号：${(order.order_num)!''}</span>
						<span class=" tcenter fr f12 Gray">下单时间：<#if (order.orderTime)??>${(order.orderTime)?string("yyyy-MM-dd HH:mm:ss")}</#if></span>

					</div>
					<section class="yg-floor yg-shopping-cart-floor no-border-bottom">
						<a class="shopping-cart-title border-top">
							<div class="mui-row pt5 pb5">
								<div class="mui-col-sm-7 mui-col-xs-7">
									<div class="fl pl5"><#if (order.shopCode)??&&(order.shopCode)=='优购微零售总店'>${(order.shopCode)}<#else>No.${(order.shopCode)!''}</#if></div>
									<!--<i class="iconfont Gray fl"></i>-->
								</div>

							</div>
						</a>

						<div class="shopping-cart-list">
							<#list order.products as product>
							<div class="shopping-cart-list-item yg-layout-column-left-80 my-orderlist-after relative"
									data-shopId="${(order.shopId)!''}" data-commodityNo="${(product.commodityNo)!''}">
								<a class="yg-layout-left" >
									<img src="${(product.url)!''}" alt="">
								</a>
								<div class="yg-layout-main">
									<div class="yg-layout-container mui-row">
										<div class="mui-col-sm-8 mui-col-xs-6">
											<a class="goods-title" >${(product.describe)!''}</a>
											
										</div>
										<div class="mui-col-sm-4 mui-col-xs-6 tright">
											<p>&yen;<#if (product.price)??>${(product.price)}<#else>0</#if></p>
											<p class="Gray">x<#if (product.num)??>${(product.num)}<#else>0</#if></p>
										</div>	
										<#if (product.refundShowStatus)??&&(product.refundShowStatus)=='3'>
                                        	<div class="Yellow pt10"><i class="iconfont"></i> 退款完成
                                            </div>
                                        </#if>
                                        <#if (product.refundShowStatus)??&&(product.refundShowStatus)=='2'>
                                        	<div class="Yellow pt10"><i class="iconfont"></i> 退款中
                                            </div>
                                        </#if>
										<div	class="tright">
											<p><span class="Red"><#if ((order.commissionStatus)!0)!=3>预计佣金：<#else>佣金：</#if>￥<#if (product.commission)??>${(product.commission)}<#else>0</#if></span></p>
										</div>
									</div>
								</div>
							</div>
							</#list>
					</section>
					<section>
						<ul class="mui-table-view">
							<li class="mui-table-view-cell">

								<a class="">
									<div class="mui-row">
										<div class="mui-col-sm-6 mui-col-xs-4"><span class="Gray">支付方式</span></div>
										<div class="mui-col-sm-6 mui-col-xs-8 tright">
											<span class="pr20">${(order.pay_mode)!''}</span>
										</div>
									</div>
								</a>
							</li>
							<li class="mui-table-view-cell">
								<a class="">
									<div class="mui-row">
										<div class="mui-col-sm-6 mui-col-xs-4"><span class="Gray">配送信息</span></div>
										<div class="mui-col-sm-6 mui-col-xs-8 tright">
											<span class="pr20">${(order.post_info)!''}</span>
										</div>
									</div>
								</a>
							</li>

						</ul>
					</section>
					<section class="mt10">
						<ul class="mui-table-view">
							<li class="mui-table-view-cell">

								<div class="mui-row">
									<div class="mui-col-sm-6 mui-col-xs-4"><span class="Gray">商品总金额</span></div>
									<div class="mui-col-sm-6 mui-col-xs-8 tright">
										<span class="pr20 Red">￥<#if (order.money_total)??>${(order.money_total)}<#else>0</#if></span>
									</div>
									<div class="mui-col-sm-6 mui-col-xs-4 mt5"><span class="Gray">+运费</span></div>
									<div class="mui-col-sm-6 mui-col-xs-8 tright mt5">
										<span class="pr20 Red">￥<#if (order.charges)??>${(order.charges)}<#else>0</#if></span>
									</div>
								</div>

							</li>
							<li class="mui-table-view-cell">

								<div class="mui-row">
									<div class="mui-col-sm-6 mui-col-xs-4"><span class="Gray"></span></div>
									<div class="mui-col-sm-6 mui-col-xs-8 tright">
										<span class="pr20">实付款：<span class="Red">￥<#if (order.paying)??>${(order.paying)}<#else>0</#if></span></span>
									</div>
								</div>

							</li>

						</ul>
					</section>
					<section>
						<div class="mui-row bg-gray pd10">
							<div class="tcenter">
								<#if ((order.commissionStatus)!0)!=3>预计佣金：<#else>佣金：</#if><span class="Red">￥<#if (order.brokerage)??>${(order.brokerage)}<#else>0</#if></span>
							</div>
						</div>
					</section>

				</div>
			</div>
		</div>
	<#include "../layouts/corejs.ftl">
	<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
	
	<script type="text/javascript">
	    var errorMsg = '${errorMsg!''}';
	    
	      $('.shopping-cart-list').on('tap', '.yg-layout-left,.goods-title', function () {
            var _this = $(this);
            var _parent = _this.parents('.shopping-cart-list-item');
            var _shopId = _parent.attr('data-shopId');
            var _commodityNo = _parent.attr('data-commodityNo');
            if (YG_Utils.Strings.IsBlank(_shopId) || YG_Utils.Strings.IsBlank(_commodityNo)) {
                YG_Utils.Notification.error('无法查看商品信息,参数不足');
                return false;
            }
            location.href = rootPath + '/' + _shopId + '/itemC/' + _commodityNo + '.sc';
        });
	</script>
	</body>

</html>