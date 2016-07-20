<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_我的收益_提现明细</title>
<#include "../layouts/style.ftl">
</head>
<body>
<div class="viewport">
	<header class="mui-bar yg-header header-ext">
		<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
		<h1 class="mui-title">申请提现</h1>
	</header>
	<div class="mui-content yg-appli-m-info">
		<div class="bg-white">
    		<div class="mui-clearfix info-title ml10 pt20 pb10">
    			<span class="info-text">可提现金额：</span>
    			<span class="Red">&yen;${(profit.balance)!0}</span>
    		</div>
			<div class="mui-clearfix info-title ml10 ptb10">
    			<span class="info-text">姓名：</span>
    			<span>${bankInfo.realName}</span>
    		</div>
			<div class="mui-clearfix info-title ml10 ptb10">
    			<span class="info-text">开户行：</span>
    			<span>${bankInfo.bank}</span>
    		</div>
    		<div class="mui-clearfix info-title ml10 ptb10">
    			<span class="info-text">银行卡号：</span>
    			<span>${bankInfo.cardNo}</span>
    		</div>
    		<div class="mui-clearfix info-title ml10 pt10">
			<span class="info-text">提现金额：</span>
			<input type="text" id="amount" oninput="(this.value <= ${profit.balance} && this.value >= ${cashSetting.lowerstAmount} && this.value <= ${cashSetting.highestAmount}) ? $('#btnSubmit').removeAttr('disabled'):$('#btnSubmit').attr('disabled',true)">
    			<span class="ml5">元</span>
    		</div>
    		<div class="border-bottom pb20">
    			<h6>可提现金额总数不大于￥${profit.balance}元</h6>
    		</div>
		</div>
		<div class="tcenter mt10">
			<button id="btnSubmit" class="mui-btn mui-btn-danger w95p" disabled>
				确定
			</button>
		</div>
		<p class="mt10 ml10 mr10 f12">注：一周只能申请提现一次，每次提现金额必须在${cashSetting.lowerstAmount}元以上，单笔最高${cashSetting.highestAmount}元。</p>
	</div>
</div>
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/yg-apply-cash.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var errorMsg = '${errorMsg!''}';
</script>
</body>
</html>