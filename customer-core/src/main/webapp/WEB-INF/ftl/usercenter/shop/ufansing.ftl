<!DOCTYPE html>
<html>

	<head>
		<#include "../../layouts/meta.ftl" />
    		<title>什么是优粉</title>
		<#include "../../layouts/style.ftl"/>
	</head>

	<body>
		<div class="viewport yg-page-ufans">
			<div class="mui-content">
				<section class="yg-floor yg-detail-img-floor pd0 mb0">
					<img class="w100p" src="${context}/static/images/imglist/banner.png">
				</section>
				<section class="yg-floor">
					<div class="mui-clearfix pd10 column-2-40 yg-border-dashed-bottom">
						<p><img class="fl" src="${context}/static/images/imglist/fangdajing.png" width="39" /></p>
						<h4>优粉</h4> 认可优购微零售的商品和服务，本人发生有 效购买后申请开店并获得审核的优购微零售 分销商。优粉负责拓展、维护自己的社交网 络及分享，优购微零售负责所有订单交易、 发货，售后服务等。
					</div>
					<div class="mui-clearfix pd10 column-2-40 yg-border-dashed-bottom">
						<p><img class="fl" src="${context}/static/images/imglist/yen.png" width="39" /></p>
						<h4>如何成为优粉？</h4> 完成首次下单并实现有效销售（有效销售指 交易完成后未发生退换货）后经优购微零售 审核确认后成为优粉。
					</div>
					<div class="mui-clearfix pd10 column-2-40 yg-border-dashed-bottom">
						<p><img class="fl" src="${context}/static/images/imglist/link.png" width="39" /></p>
						<h4>如何成为优粉？</h4> 完成首次下单并实现有效销售（有效销售指 交易完成后未发生退换货）后经优购微零售 审核确认后成为优粉。
					</div>
					<a href="${context}/index.sc" class="mui-btn mui-btn-danger mui-btn-block mt50">去购物，成为优粉</a>
				</section>
			</div>
		</div>
		<#include "../../layouts/corejs.ftl">
	</body>
<script src="${context}/static/js/qrcode.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</html>