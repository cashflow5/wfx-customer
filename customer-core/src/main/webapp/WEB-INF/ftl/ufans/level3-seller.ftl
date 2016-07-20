<!DOCTYPE html>
<html>
	<head>
		<#include "../layouts/meta.ftl">
	    <#include "../layouts/style.ftl">
		<title>优购微零售_优粉_我的分销商_三级分销商</title>
		<link rel="stylesheet" type="text/css" href="${context}/static/css/mui.picker.min.css" />
		<link rel="stylesheet" type="text/css" href="${context}/static/css/plugins/dropload/dropload.css?v=${static_version}" />
		<style>
		.mui-content{background:none;}
		</style>
	</head>
	<body class="bg-gray">
		<div class="viewport yg-my-fenxiaoshang  bg-gray">
			<header class="mui-bar yg-header header-ext">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">三级分销商</h1>
			</header>
			<div class="mui-content yg-account-information relative">
			
				<section class="yg-visitor-records bg-white">
				
					<ul id="listData" class="mui-table-view  vr-bofore-none">
					 <#if result??&&(result.items)??&&(result.items)?size gt 0 >
           				<#list result.items as item>
						<li class="mui-table-view-cell vr-after-pl0">
							<input type='hidden' id='subSellerId' value='${(subSellerId)!''}'></input>
							<div class="yg-layout-column-left-80 fxs-colum-afterdiaplay">
								<div class="yg-layout-left">
									<div class="fxs-photo"><a href="${context}/ufans/mySeller/getSubDetail.sc?level=3&subSellerId=${(item.id)!''}" >
									<img width="80" height="80" src="${(item.logoUrl)!(context+'/static/images/user-photo.png')}" 
									alt="" /></a>
									</div>
								</div>
								<div class="yg-layout-main">
									<div class="yg-layout-container mui-row">
										<div class="mui-col-sm-11 mui-col-xs-11">
											<div>No.${(item.shopCode)!''}</div>
											<div class="f12 mt5">时间：<#if (item.regTime)??>${(item.regTime)?string("yyyy-MM-dd HH:mm:ss")}</#if></div>
											<div class="f12 mt5">佣金：<span class="Red"><#if (item.commissionTotalAmountForParent)??>￥${((item.commissionTotalAmountForParent)!0)?string('0.00')}<#else>0</#if></span></div>
										</div>
										
									</div>
								</div>

							</div>
						</li>
						
						</#list>
						<#else>
					       <li class="mui-table-view-cell">
                                  <div class="yg-pro-detail-nomore pt10 pb10" style="text-align:center;"><img src="${context}/static/images/prodetail/nothing.png" style="width:30px"><br>没有相关信息</div>
                           </li> 
						</#if>
						 </ul>
				</section>
			</div>
		</div>
 	<#include "../layouts/corejs.ftl">
 	<script src="${context}/static/js/plugins/dropload/dropload.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
	<script src="${context}/static/js/ufans/yg-level3-seller.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
	
	</body>

</html>