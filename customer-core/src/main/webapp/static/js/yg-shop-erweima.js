//
//var YG_Shop_ErWeiMa = (function($, M) {
//	function Init(){
//		LinkJump();
//	}
//	function LinkJump(){
//		M('#shared').on('tap','.system-share',function(){
//			M.get('${context}/ufans/detailShare.sc', {}, function(data) {
//				layer.open({
//					type: 1,
//					className: 'yg-slide-in',
//					content: data,
//					style: 'position:fixed; bottom:-150px; left:0; padding:10px 0 0 0;background:none ',
//					slide: true,
//					callback: function() {
//						
//					}
//				})
//
//			});
//		});
//		M('.viewport').on('tap', '#shared', function() {
//				var _href = $(this).attr('data-href');
//				if (!_href) return;
//
//				location.href = _href ;			
//		});
//	}
//	$(function() {
//		Init();
//	});
//
//	return {};
//}(Zepto, mui));