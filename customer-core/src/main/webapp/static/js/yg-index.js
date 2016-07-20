var YG_Index = (function($, M) {
	function Init() {
		Slider();
		InfoExt();
		LazyImage();
		LinkJump();
		YG_Common.ScrollAnimate('.yg-floor-noticle2 ul', 500, 3000);
		YG_Common.GotoTop(1000);
	}

	function InfoExt() {
		M('.yg-shop-infos').on('tap', '.yg-shop-info-detail-ext', function(e) {
			var $this = $(this),
				isext = $this.data('infoExt');
			if (!isext) {
				$('.yg-shop-info-detail').css('height', 'auto');
				$this.data('infoExt', true).addClass('on-ext');
			} else {
				$('.yg-shop-info-detail').css('height', 20);
				$this.data('infoExt', false).removeClass('on-ext');
			}
		});
	}

	function LinkJump() {
		M('.yg-header').on('tap', '#myList', function() {
			location.href = "orders.shtml";
		});
		M('.yg-header').on('tap', '#search', function(e){
			location.href = $(this).data('href');
		})
	}

	function LazyImage() {
		M('.yg-floor').imageLazyload({
			placeholder: '/static/images/thum.png'
		});
	}

	function Slider() {
		var slider = mui("#indexSlider");
		slider.slider({
			interval: 5000
		});

	}

	$(function() {
		Init();
	});

	return {};
}(Zepto, mui));