var YG_My_Income = (function($, M) {
	function Init() {
		applyCash();
	}
	function applyCash(){
	    M('.viewport').on('tap','#btnSubmit', function() {
			$.ajax({
	            type: 'get',
	            url: 'applyCashNow.sc',
	            data: {'amount':$("#amount").val()},
	            success: function (data, status, xhr) {
	                if (data == '') {
	                	 M.toast('申请提现成功');
	                	 location.href = 'applyCashList.sc?tab=0';
	                } else {
	                	M.toast('申请提现失败,错误:' + data);
	                }
	            },
	            error: function (xhr, errorType, error) {
	                M.toast('申请提现失败,错误:' + error);
	            }
	        });
        });
	}
	$(function() {
		Init();
	});

	return {};
}(Zepto, mui));