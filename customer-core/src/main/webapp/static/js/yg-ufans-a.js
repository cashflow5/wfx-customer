var YG_Ufans_A = (function($, M) {
	function Init() {
		LinkJump();
	}

	function LinkJump() {
		// 分享
		M('.user-info-box').on('tap', '.system-share', function() {
			M.get('detail-share.html', {}, function(data) {
				layer.open({
					type: 1,
					className: 'yg-slide-in',
					content: data,
					style: 'position:fixed; bottom:-150px; left:0; padding:10px 0 0 0;background:none ',
					slide: true,
					callback: function() {
						
					}
				})

			});
		});
		M('.viewport').on('tap', '.link-jump', function() {
				var _href = $(this).attr('data-href');
				if (!_href) return;

				location.href = _href + ".shtml";			
		});
		/*M('.yg-ufans-a').on('tap', '.link-jump', function() {
			var $this = $(this),
				childText = $this.find('.mui-tab-label').html();
			console.log(childText)
			switch (childText) {
				case "可提现":
				case "我的收益":
					location.href = "my-income.shtml"
					break;
				case "订单管理":
					location.href = "#"
					break;
				case "下级分销商":
					location.href = "#"
					break;
				default:
					location.href = "#"
					break;
			}
		});*/
	}
	$(function() {
		Init();
	});

	return {};
}(Zepto, mui));