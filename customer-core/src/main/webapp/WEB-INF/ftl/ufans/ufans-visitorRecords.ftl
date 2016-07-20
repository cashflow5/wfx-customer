<!DOCTYPE html>
<html>

	<head>
		<#include "../layouts/meta.ftl">
	    <#include "../layouts/style.ftl">
		<title>优购微零售_访客记录</title>
		<link rel="stylesheet" type="text/css" href="${context}/static/css/mui.picker.min.css" />
	</head>

	<body>
		<div class="viewport yg-visitor-records">
			<header class="mui-bar yg-header header-ext">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">访客记录</h1>
			</header>
			<div class="mui-content yg-account-information">
				<div class="mui-content bg-white">
					<!--<div class="vs-border f12">
					    <div class="mt5">6月5日</div>
					    <div>访客数28人</div>
					</div>-->
					<div id="tody_data" class="yg-floor pd0 vis-height">
					</div>
				</div>
				<section class="mt10">
					<ul class="mui-table-view  vr-bofore-none">
						<li class="mui-table-view-cell vr-after-pl0">

							<a class="" >
								<div class="mui-row">
									<div class="mui-col-sm-2 mui-col-xs-2"></div>
									<div class="mui-col-sm-4 mui-col-xs-4 vr-pt6"><span>访客来源</span></div>
									<div class="mui-col-sm-6 mui-col-xs-6 tright vr-pt6">
										<span class="pr20">数量</span>
									</div>
								</div>
							</a>
						</li>
						<li class="mui-table-view-cell vr-after-pl0">

							<a class="mui-navigate-right" id="loadVisitorList" >
								<div class="mui-row">
									<div class="mui-col-sm-2 mui-col-xs-2">
										<img width="40" height="40" src="data:image/png;base64,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">

										<!--<i class="iconfont Green f36 span-lh-1">&#xe65c;</i>-->
									</div>
									<div class="mui-col-sm-4 mui-col-xs-4 vr-pt6"><span>微信</span></div>
									<div class="mui-col-sm-6 mui-col-xs-6 tright vr-pt6">
										<span id="weixinCountSpan" class="pr20">${(visitorCountMap.weixinCount)!0}</span>
									</div>
								</div>
							</a>
						</li>

						<li class="mui-table-view-cell vr-after-pl0>
						<a class=" ">
								<div class="mui-row ">
									<div class="mui-col-sm-2 mui-col-xs-2 ">
										<img width="40 " height="35 " src="data:image/png;base64,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">
											
										
									</div>
									<div class=" mui-col-sm-4 mui-col-xs-4 vr-pt6 "><span>其他</span></div>
									<div class="mui-col-sm-6 mui-col-xs-6 tright vr-pt6 ">
										<span id="otherCountSpan" class="pr20 ">${(visitorCountMap.otherCount)!0}</span>
									</div>
								</div>
							</a>
							</li>
					</ul>
					
				</section>
			</div>
		</div>
		<form id="visitorForm" action="/ufans/visitor/visitorRecordList.sc" type="hidden">
			<input type="hidden" id="date" name="date" >
		</form>
		
		<script type="text/javascript " charset="utf-8">			
			var categories = ${categories!''};
			var data = ${data!''};
			
			
		</script>
		<script src="${context}/static/js/jquery-3.0.0.min.js " type="text/javascript " charset="utf-8"></script>		
		<script src="${context}/static/js/mui.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
		<script src="${context}/static/js/plugins/highcharts/highcharts.js " type="text/javascript" charset="utf-8"></script>
		<script src="${context}/static/js/yg-visitors-record-chart.js" type="text/javascript " charset="utf-8"></script>
		
		
	</body>
	
</html>