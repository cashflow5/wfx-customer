/**
 * create by lijunfang
 */
var YG_RegisterSuccess = (function($, M) {
	function Init(){
		ThreeCountdown();
	}
	function ThreeCountdown(e){
		var second = 3;
		var index = setInterval(function(){
			second--;
			$('#returnTip').html('倒计时'+second+'秒自动返回首页');
			if(second==0){
				location.href = rootPath+'/index.sc';
				clearInterval(index);
			}
		},1000);
	}
	$(function() {
		Init();
	});

	return {};
}(Zepto, mui));