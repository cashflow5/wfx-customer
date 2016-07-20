var YG_My_Income = (function($, M) {
	function Init() {
		LinkJump();
	}

	function LinkJump() {
		M('.yg-my-income').on('tap', '#showMainHelp', function() {
			$("#mainHelp").hasClass("none") ?
				$("#mainHelp").removeClass("none") : $("#mainHelp").addClass("none");
		});
		M('.viewport').on('tap', '.link-jump', function() {
			var _href = $(this).attr('data-href'),
				_param = $(this).attr("data-param");
			if (!_href) return;

			if(_href == 'applyCash'){
				//异步校验是否符合提现要求
				$.ajax({
		            type: 'get',
		            url: 'applyCashValidation.sc',
		            data: {},
		            success: function (data, status, xhr) {
		                if (data == '') {
		                	location.href = _href + ".sc?" + _param;
		                } else if(data == 'setbank'){
		                	M.toast('请先进行账号设置，才能提现');
		                	location.href = 'accountSetUp.sc';
		                	return;
		                } else {
		                	M.toast(data);
		                	return;
		                }
		            },
		            error: function (xhr, errorType, error) {
		            	M.toast('申请提现失败,错误:' + error);
		                return;
		            }
		        });
			}else{
				location.href = _href + ".sc?" + _param;
			}
		});
	}
	$(function() {
		Init();
	});

	return {};
}(Zepto, mui));