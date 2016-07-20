
var YG_Ufans_A = (function($, M) {
	function Init(){
		LinkJump();
	}
	function LinkJump(){
		
		M('.viewport').on('tap', '.link-jump', function() {
				var _href = $(this).attr('data-href');
				if (!_href) return;

				location.href = _href;			
		});
	}
	$(function() {
		Init();
	});

	return {};
}(Zepto, mui));