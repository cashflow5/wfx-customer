/**
 * create by guoran
 */
var YG_Common = (function ($, M) {
    function Init() {
    	LinkJump();
        Menu();
        Tabs();
        BindMobilePhone();
        NavBar();
//		NavTab('.nav-tags-scroll');
        CheckboxRadio();
        PullBack();
        Plugins();
        ShopCar();
        ShareHelp('ad.shtml');
        longTouchTocopy();
    }

    function Menu() {
        var menu = document.getElementById("btnMenu");
        menu ? menu.addEventListener('tap', function () {
            $('.yg-menu').toggle();
        }) : "";

    }

    function NavBar() {
        M('.yg-nav-bar').on('tap', '.yg-nav-bar-item', function (e) {
            e.preventDefault();
            var $this = $(this);
            $('body').scrollTop($($this.attr('href')).offset().top);
            $this.addClass('active').siblings().removeClass('active');
        });
    }
    
	function LinkJump() {
		M('#indexMenu').on('tap', '.mui-tab-item', function(e) {
			location.href = $(this).attr('href');
		});
	}
	
    function NavTab(target) {
        $(target).each(function (i, item) {
            $(target + ' .nav-tags').css('width', ($(target + ' .nav-tag').length * 50) + '%');
            $(target).scrollLeft($(target + ' .nav-tags .active').offset().left);
        });
    }
    
    function CheckboxRadio() {
        //mui checkbox radio bug
        $('.mui-checkbox, .mui-radio').on('focus', 'input', function () {
            this.blur();
        });
    }

    function PullBack() {
        $('.yg-header .mui-pull-left').on('tap', function () {
            if ($(this).hasClass('header-logo'))
                return;
            var href = $(this).data('href');
            if ($.trim(href).length > 0) {
                location.href = href;
            } else {
                //if (window.history && window.history.length > 0)
                 //   window.history.back();
            }
        });
    }

    function Plugins() {

        !function (a) {
            "use strict";
            var b = document, c = "querySelectorAll", d = "getElementsByClassName", e = function (a) {
                return b[c](a)
            }, f = {type: 0, shade: !0, shadeClose: !0, fixed: !0, anim: !0}, g = {
                extend: function (a) {
                    var b = JSON.parse(JSON.stringify(f));
                    for (var c in a)b[c] = a[c];
                    return b
                }, timer: {}, end: {}
            };
            g.touch = function (a, b) {
                var c;
                return /Android|iPhone|SymbianOS|Windows Phone|iPad|iPod/.test(navigator.userAgent) ? (a.addEventListener("touchmove", function () {
                    c = !0
                }, !1), void a.addEventListener("touchend", function (a) {
                    a.preventDefault(), c || b.call(this, a), c = !1
                }, !1)) : a.addEventListener("click", function (a) {
                    b.call(this, a)
                }, !1)
            };
            var h = 0, i = ["layermbox"], j = function (a) {
                var b = this;
                b.config = g.extend(a), b.view()
            };
            j.prototype.view = function () {
                var a = this, c = a.config, f = b.createElement("div");
                a.id = f.id = i[0] + h, f.setAttribute("class", i[0] + " " + i[0] + (c.type || 0)), f.setAttribute("index", h);
                var g = function () {
                    var a = "object" == typeof c.title;
                    return c.title ? '<h3 style="' + (a ? c.title[1] : "") + '">' + (a ? c.title[0] : c.title) + '</h3><button class="layermend"></button>' : ""
                }(), j = function () {
                    var b, a = (c.btn || []).length;
                    return 0 !== a && c.btn ? (b = '<span type="1">' + c.btn[0] + "</span>", 2 === a && (b = '<span type="0">' + c.btn[1] + "</span>" + b), '<div class="layermbtn">' + b + "</div>") : ""
                }();
                if (c.fixed || (c.top = c.hasOwnProperty("top") ? c.top : 100, c.style = c.style || "", c.style += " top:" + (b.body.scrollTop + c.top) + "px"), 2 === c.type && (c.content = '<i></i><i class="laymloadtwo"></i><i></i>'), f.innerHTML = (c.shade ? "<div " + ("string" == typeof c.shade ? 'style="' + c.shade + '"' : "") + ' class="laymshade"></div>' : "") + '<div class="layermmain" ' + (c.fixed ? "" : 'style="position:static;"') + '><div class="section"><div class="layermchild ' + (c.className ? c.className : "") + " " + (c.type || c.shade ? "" : "layermborder ") + (c.anim ? "layermanim" : "") + '" ' + (c.style ? 'style="' + c.style + '"' : "") + ">" + g + '<div class="layermcont">' + c.content + "</div>" + j + "</div></div></div>", !c.type || 2 === c.type) {
                    var k = b[d](i[0] + c.type), l = k.length;
                    l >= 1 && layer.close(k[0].getAttribute("index"))
                }
				//解决js无法执行问题  update by guoran 20160613
				$('body').append(f.outerHTML);
				//				document.body.appendChild(f);
				//当为slide时， 加上动画效果
				if (typeof c.callback === 'function') {
					if (!!c.slide) {
						setTimeout(function() {
							$(".yg-slide-in").css("bottom", 0);
						}, 130);
						$(".yg-slide-in").css('opacity', 1);
					}
					c.callback();
				}
                var m = a.elem = e("#" + a.id)[0];
                c.success && c.success(m), a.index = h++, a.action(c, m)
            }, j.prototype.action = function (a, b) {
                var c = this;
                if (a.time && (g.timer[c.index] = setTimeout(function () {
                        layer.close(c.index)
                    }, 1e3 * a.time)), a.title) {
                    var e = b[d]("layermend")[0], f = function () {
                        a.cancel && a.cancel(), layer.close(c.index)
                    };
                    g.touch(e, f)
                }
                var h = function () {
                    var b = this.getAttribute("type");
                    0 == b ? (a.no && a.no(), layer.close(c.index)) : a.yes ? a.yes(c.index) : layer.close(c.index)
                };
                if (a.btn)for (var i = b[d]("layermbtn")[0].children, j = i.length, k = 0; j > k; k++)g.touch(i[k], h);
                if (a.shade && a.shadeClose) {
                    var l = b[d]("laymshade")[0];
                    g.touch(l, function () {
                        layer.close(c.index, a.end)
                    })
                }
                a.end && (g.end[c.index] = a.end)
            }, a.layer = {
                v: "1.8", index: h, open: function (a) {
                    var b = new j(a || {});
                    return b.index
                }, close: function (a) {
                    var c = e("#" + i[0] + a)[0];
                    c && (c.innerHTML = "", b.body.removeChild(c), clearTimeout(g.timer[a]), delete g.timer[a], "function" == typeof g.end[a] && g.end[a](), delete g.end[a])
                }, closeAll: function () {
                    for (var a = b[d](i[0]), c = 0, e = a.length; e > c; c++)layer.close(0 | a[0].getAttribute("index"))
                }
            }
        }(window);

    };
	/**
     * 飘窗控制
     *
     * @Author   guoran
     *
     * @DateTime 2015-11-19T15:33:46+0800
     *
     * @param    {object} options {
			                        startPos:10, //滚动条滚动多高开始显示
			                        fixedBottom:false,//是否永远在底部
			                        show:false //初始化是否显示
			                       }
     *
     * @return   {jQuery} 默认对象
     */
    $.fn.jqScrollTop = function(options) {
        options = $.extend({
            startPos: 0,
            bottom: 150,
            fixedBottom: false,
            fixedRight: false,
            show: false
        }, options || {});
        var ie6 = !-[1, ] && !window.XMLHttpRequest;
        var _this = $(this);
        var _bottom = options.bottom;
        var _start = _this.parent().offset().top;
        _this.css({
            "bottom": _bottom,
            "top": "auto"
        });
        !options.fixedRight ? _this.css({
            left: '50%',
            'margin-left': $('body .viewport').width() / 2 + 10
        }) : '';
        ie6 ? _this.css({
            "top": $(window).height() - _this.height()
        }) : '';
        options.show ? _this.show() : _this.hide();
        $(window).scroll(function() {
            $('body').scrollTop() > options.startPos ? ($(':visible',_this).length >0? '':_this.fadeIn(200)) : _this.fadeOut(200);
            if (!options.fixedBottom) {
                var f = $('body').scrollTop() + $(window).height(),
                    $footer = $('.yg-goto-top');
                if ($footer[0]) {
                    f = f - $footer.offset().top;
                }
                if (f > 0) {
                    _this.css({
                        bottom: function() {
                            return f + options.bottom;
                        }
                    })
                } else {
                    _this.css({
                        "bottom": options.bottom,
                        "top": "auto"
                    });
                    if (ie6) {
                        _this.css({
                            "top": $('body').scrollTop() - _this.height() - options.bottom + $(window).height()
                        });
                    }
                }
            }

        });

        $(window).resize(function() {
            _this.css({
                "bottom": _bottom
            });
        });
        _this.find('.gotop-link').click(function() {
//          $('html,body').animate({
//              scrollTop: 0
//          }, 0);
//          _this.css({
//              "bottom": options.bottom
//          });
            document.body.scrollTop = 0;
            return false;
        });
        return $(this);
    };

    function longTouchTocopy(){
    	YG_Utils.Layer.closeAll();
    	var btncopy = document.getElementById("longTouchTocopy");
    	btncopy?btncopy.addEventListener('tap', function() {
        M.alert("请长按下面地址进行复制"+url);}): "";
    };
    
    function ShopCar() {
    	$.ajax({
            type: 'post',
            url: rootPath + '/shoopingcart/count.sc',
            dataType: 'json',
            data: {},
            success: function (data, status, xhr) {
            	if($('#shopcartTotal')){
            		$('#shopcartTotal').text(data);
            	}
                if($('#shopcartTotal-Menu')){
                	$('#shopcartTotal-Menu').text(data);
                }
                
            },
            error: function (xhr, errorType, error) {
                YG_Utils.Notification.toast("获取购物车数据超时!");
            }
        });
	}
    
	/**
	 * 分享
	 * @param {String} targetUrl 目标地址 
	 */
	function ShareHelp(targetUrl) {
		var btnShared = document.getElementById("shared");
		btnShared ? btnShared.addEventListener('tap', function() {
			var url=window.location.href;
			var title=$("title").html();
			var imgUrl='http://'+document.domain+'/static/images/logo.png';
			var type='link';
			var backUrl={
					url:url,
					title:title,
					img:imgUrl,
					type:type
			};
			if(running_environment==3){//iOS_APP平台
                window.location.href="shareWechat?param="+JSON.stringify(backUrl);
			}
			else if(running_environment==4){//ANDROID_APP平台
				 window.android_APP.getAppShare(JSON.stringify(backUrl))
			}
			else{
			if(IsWeixin()){
				layer.open({
				    type: 1,
				    content: '<div class="yg-shared">请点击右上角...进行分享。</div>',
				    style: 'position:fixed; left:0; top:0; width:100%; height:100%; border:none;background-color: rgba(0, 0, 0, 0.7);',
				    callback: function(){
				    	$('.yg-shared').parents('.layermanim').first()[0].addEventListener('tap', function(){
				    		layer.closeAll();
				    	})
				    }
				});
			} else {
                YG_Utils.Layer.show();
				M.get('/ufans/detailShare.sc', {}, function(data) {
                    YG_Utils.Layer.closeAll();
					layer.open({
						type: 1,
						className: 'yg-slide-in',
						content: data,
						style: 'position:fixed; bottom:-150px; left:0; padding:10px 0 0 0;background:none ',
						slide: true,
						callback: function() {
							$("body").css({
								overflow: 'hidden'
							});
							var detail_share = document.getElementById("detail-share");
							detail_share ? detail_share.addEventListener('tap', function() {
								layer.closeAll();

								$("body").removeAttr("style");
							}) : '';
							var yg_silde_in_lay = $('.yg-slide-in').parents('.layermbox').find('.laymshade').first()[0];
							yg_silde_in_lay ? yg_silde_in_lay.addEventListener('tap', function() {
								layer.closeAll();
								$("body").css("overflow", 'auto');
							}) : '';
							//							$('.yg-shared').parents('.layermanim').first()[0].addEventListener('tap', function() {
							//								layer.closeAll();
							//								$("body").removeAttr("style");
							//							});

						}
					})

				});
			 }
		  }
		}): '';
	}
	
	/**
	 * 判断是否为微信
	 */
	function IsWeixin(){
	    var ua = navigator.userAgent.toLowerCase();
	    if(ua.match(/MicroMessenger/i)=="micromessenger") {
	        return true;
	    } else {
	        return false;
	    }
	}
	function BindMobilePhone() {
		M('.yg-trip-message-box-btns').on('tap', '.btn-close', function() {
			$(this).parents('.yg-trip-message-box').remove();
		})
	}
/**
 *APP获取分享参数 
 */
	function ScrollAnimate(obj, anima, speed) {
		//滚动公告
		setInterval(function() {
			$('.noticle-item:first', obj).animate({
				marginTop: '-32px'
			}, anima, 'linear', function() {
				$('.noticle-item:last', obj).after($(this).css('marginTop', ''));
			});
		}, speed);
	}
	
	function Tabs() {
		if (typeof Swiper === 'undefined') {
			return
		}
		var swiper = new Swiper('.swiper-container', {
			//			pagination: '.swiper-pagination',
			slidesPerView: 4,
			paginationClickable: false,
			spaceBetween: 30
		});
		$('.yg-visible').removeClass('yg-visible');
	}
   /**
     * 回到顶部
     *
     * @Author   guoran
     *
     * @DateTime 2015-11-19T17:07:14+0800
     *
     * @param    {number}                 startPos 回到顶部
     * @param    {bool}                 show     是否默认显示
     * @param    {number}                 bottom   距底高度
     */
    function GotoTop(startPos, show, bottom) {
        startPos = startPos || 0;
        show = show || false;
        bottom = bottom || 110;
        if (!$('#fixedRight')[0]) {
            $('<div class="yg-fixed-right"><a href="javascript:;" class="gotop-link" title="返回顶部"><i class="iconfont">&#xe69c;</i></a></div>').appendTo('body');
            $('.yg-fixed-right').jqScrollTop({
                startPos: startPos,
                show: show,
                bottom: bottom,
                fixedRight: true
            });
        }
    }

	$(function() {
		Init();
	});

	return {
		ISWeixin: IsWeixin,
		ScrollAnimate: ScrollAnimate,
		GotoTop: GotoTop
	};
}(Zepto, mui));