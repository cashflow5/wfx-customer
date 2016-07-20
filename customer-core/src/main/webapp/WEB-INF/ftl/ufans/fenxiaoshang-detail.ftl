<!DOCTYPE html>
<html>

	<head>
		<#include "../layouts/meta.ftl">
	    <#include "../layouts/style.ftl">
		<title>优购微零售_优粉_我的分销商_分销商详情</title>
		<link rel="stylesheet" type="text/css" href="${context}/static/css/mui.picker.min.css" />
	</head>

	<body>
		<div class="viewport">
			<header class="mui-bar yg-header header-ext">
				<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
				<h1 class="mui-title">分销商详情</h1>
			</header>
			<div class="mui-content yg-account-information">
			<input type='hidden' id='subSellerId' value='${(subSellerId)!''}'></input>
			<input type='hidden' id='level' value='${(level)!2}'></input>
				<section>
					<ul class="mui-table-view  yg-visitor-records">
						<li class="mui-table-view-cell vr-after-pl0 ptb15">

							<div class="mui-row">
								<div class="mui-col-sm-6 mui-col-xs-4"><span >分销商编码</span></div>
								<div class="mui-col-sm-6 mui-col-xs-8 tright">
									<span class="pr20 Gray">No.${(detail.shopCode)!''}</span>
								</div>
							</div>

						</li>
						<li class="mui-table-view-cell vr-after-pl0 ptb15">

							<div class="mui-row">
								<div class="mui-col-sm-6 mui-col-xs-4"><span>分销商账号</span></div>
								<div class="mui-col-sm-6 mui-col-xs-8 tright">
									<span class="pr20 Gray">${(detail.account)!''}</span>
								</div>
							</div>

						</li>
						<li class="mui-table-view-cell vr-after-pl0 ptb15">

							<div class="mui-row">
								<div class="mui-col-sm-6 mui-col-xs-4"><span>性别</span></div>
								<div class="mui-col-sm-6 mui-col-xs-8 tright">
									<span class="mui-content-padded mr20 Gray">
											${(detail.sex)!''}
										</span>

								</div>
							</div>

						</li>
						<li class="mui-table-view-cell vr-after-pl0 ptb15">
							<div class="mui-row">
								<div class="mui-col-sm-6 mui-col-xs-4"><span>生日</span></div>
								<div class="mui-col-sm-6 mui-col-xs-8 tright">
									<span class="picker-data pr20 Gray">
									<#if (detail.birthDayDate)??>${(detail.birthDayDate)?string("yyyy-MM-dd")}</#if>
									</span>
								</div>
							</div>

						</li>
						<li class="mui-table-view-cell vr-after-pl0 ptb15">

							<div class="mui-row">
								<div class="mui-col-sm-6 mui-col-xs-4"><span>注册时间</span></div>
								<div class="mui-col-sm-6 mui-col-xs-8 tright">
									<span class="picker-data pr20 Gray">
									<#if (detail.regTimeDate)??>${(detail.regTimeDate)?string("yyyy-MM-dd HH:mm:ss")}</#if></span>
								</div>
							</div>

						</li>
						<#if level??&&(level<3) >
						<li class="mui-table-view-cell vr-after-pl0 ptb15">

							<div class="mui-row">
								<div class="mui-col-sm-6 mui-col-xs-4"><span>分销商数量</span></div>
								<div class="mui-col-sm-6 mui-col-xs-8 tright">
									<span class="picker-data pr20 Gray">${(detail.subSellerCount)!0}</span>
								</div>
							</div>

						</li>
						</#if>
					</ul>
				</section>
			</div>
		</div>
		 <#include "../layouts/corejs.ftl">
	</body>

</html>