<!DOCTYPE html>
<html>
	<head>
		<#include "../layouts/meta.ftl">
	    <#include "../layouts/style.ftl">
		<title>优购微零售_优粉_我的分销商</title>
		<link rel="stylesheet" type="text/css" href="${context}/static/css/mui.picker.min.css" />
		<link rel="stylesheet" type="text/css" href="${context}/static/css/plugins/dropload/dropload.css?v=${static_version}" />
	</head>
	<body class="bg-gray">
		<div class="viewport yg-my-fenxiaoshang  bg-gray">
			<header class="mui-bar yg-header header-ext">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">我的分销商</h1>
			</header>
			<div class="mui-content yg-account-information relative">

				<div class="fxs-title-color fxs-title">
					<div class="tcenter pd10 f12 fxs-f-color">
						<span>二级分销商：${(result.total)!0}</span>&nbsp;&nbsp;<span>三级分销商:${(result.level3total)!0}</span>
					</div>
				</div>
			
				<section  class="yg-visitor-records bg-white">
				
					<ul id="listData" class="mui-table-view  vr-bofore-none">
					<#if result??&&(result.items)??&&(result.items)?size gt 0 >
           			 <#list result.items as item>
						<li class="mui-table-view-cell vr-after-pl0">
							<div class="yg-layout-column-left-80 fxs-colum-afterdiaplay">
								<div class="yg-layout-left">
										<a class="fxs-photo" href="${context}/ufans/mySeller/getSubDetail.sc?level=2&subSellerId=${(item.id)!''}">
										<img width="80" height="80" src="${(item.logoUrl)!(context+'/static/images/user-photo.png')}" alt="" /></a>
								</div>
								<div class="yg-layout-main">
									<div class="yg-layout-container mui-row">
										<div class="mui-col-sm-11 mui-col-xs-11">
											<div>No.${(item.shopCode)!''}</div>
											<div class="f12 mt5">时间：<#if (item.regTime)??>${(item.regTime)?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
											<div class="f12 mt5">佣金：<span class="Red"><#if (item.commissionTotalAmountForParent)??>￥${((item.commissionTotalAmountForParent)!0)?string('0.00')}<#else>0</#if></span>&nbsp;&nbsp;分销商：<span class="Red">${(item.subSellerCount)!'0'}</span></div>
										</div>
										<a href="${context}/ufans/mySeller/getLevel3SubList.sc?sellerId=${(item.id)!''}" class="mui-col-sm-1 mui-col-xs-1 fxs-lh80">
											<span class="iconfont Blue f18">&#xe636;</span>
										</a>
									</div>
								</div>
							</div>
						</li>
						
					</#list>
					
					<#else>
					   <li>
                          <div class="yg-pro-detail-nomore pt20 pb20" style="text-align:center;"><img src="${context}/static/images/prodetail/nothing.png" style="width:30px"><br>没有相关信息</div>
                       </li>
					</#if>
					</ul>
				</section>
			</div>

		</div>
 	<#include "../layouts/corejs.ftl">
 	<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
	<script src="${context}/static/js/ufans/yg-my-seller.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
	
	</body>

</html>