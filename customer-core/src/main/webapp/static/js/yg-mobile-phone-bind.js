/**
 * create by guoran 20160614
 */
var YG_Mobile_Phone_Bind = (function($, M) {
	function Init() {
		GetCode();
	}

	function GetCode() {
		//获取短信验证码
		M('.code-wrap').on('tap', '.code-btn', function(e) {
			Code('.code-btn');
		});
	}

	//60秒倒计时获取短信验证码按钮屏蔽
	function Code(object) {
		var second = 60,
			$this = $(object);
		$this.attr('disabled', true);
		var codeSetInterval = setInterval(function() {
			if (--second) {
				$this.html(second + '秒后重新获取');
			} else {
				$this.html('获取验证码');
				$this.removeAttr('disabled');
				clearInterval(codeSetInterval);
			}
		}, 1000);
	}

	$(function() {
		Init();
	});

	return {};
}(Zepto, mui));