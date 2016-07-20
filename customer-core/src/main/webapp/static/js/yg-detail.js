/**
 * create by guoran
 */
var YG_Detail = (function ($, M) {
    var currentNumber=0;
    var scrollValue=0;
    function Init() {
        ImageLoad();
        FormAction();
        BuyNow();
        AddShoppingCart();
        BtnAdd();
        ShopCart();
        ShareHelpClick();
        loadingRuning();
    }
    function loadingRuning(){
        M.previewImage();
        $('.yg-silder-popovers .mui-slider').get(0).addEventListener('slide', function(event) {
            currentNumber=event.detail.slideNumber;
        });        
        $('.mui-content').on('tap','.yg-silder-popovers',function(){
            setTimeout(function(){
                M('.mui-fullscreen').slider().gotoItem(currentNumber);
                $('.mui-fullscreen').get(0).addEventListener('slide', function(event) {
                    currentNumber=event.detail.slideNumber;
                    M('.yg-silder-popovers .mui-slider').slider().gotoItem(currentNumber);
                });
            },100)
            if($('.mui-fullscreen .mui-slider-indicator').length==0){
                $('.mui-fullscreen ').append($('#goodsPhoto .mui-slider-indicator').clone());
            }
        })
        if($('.yg-shop-card-fixed').length>0){
            $('.yg-shop-card-fixed').on('tap',function(){
                window.location.href=$(this).attr('href');
            })
        }
        $('.shop-title').on('tap',function(){
            window.location.href=$(this).attr('href');
        })
    }
    
    function FormAction() {
        M('.yg-form-floor').on('tap', '.mui-btn:not(.mui-btn-numbox-minus)', function (e) {
            var $this = $(this),
                $text = $this.text(),
                $cpt = $this.siblings('input[type=hidden]');
            if ($this.hasClass('mui-btn-numbox-plus')) {
                return;
            }
            if ($.trim($this.attr('href')) != '') {
                location.href = $this.attr('href');
                return;
            }
            $this.addClass('active').siblings().removeClass('active');
            var name = $cpt.attr('name');
            if(name != 'pdSize'){
            	$('#' + $cpt.attr('name')).html($text ? $text : $this.val()).attr('data-id', $this.attr('data-id'));;
            }else{
            	var sizeName = $text ? $text : $this.val();
            	$('#' + $cpt.attr('name')).html(sizeName + '码').attr('data-id', $this.attr('data-id'));
            }
            $cpt.val($this.data('value'));
        });
        M('.yg-form-floor').on('change', '[name="pdNumber"]', function () {
            $('#' + this.name).html(this.value);
        });
        M('.yg-form-floor').on('tap', '#formExt', function (e) {
            var $this = $(this),
                _state = !$this.data('isExt');
            $this.data('isExt', _state).find('.formExt').toggleClass('active');
            if (_state) {
                $('#formBuy').hide();
            } else {
                $('#formBuy').show();
            }
        });
    }

    function BuyNow() {
        $('.yg-nav').on('tap', '#btnBuyNow', function () {
            var formData = GetFormData('btnBuyNow');
            if (!formData) return;
            $.ajax({
                type: 'post',
                url: rootPath + '/shoppingcart/checkadd.sc',
                dataType: 'json',
                data: formData,
                beforeSend: function () {
                    YG_Utils.Layer.show();
                },
                complete: function () {
                    YG_Utils.Layer.closeAll();
                },
                success: function (data, status, xhr) {
                    if (data.success) {
                        var result = [];
                        for (elm in formData) {
                            result.push(encodeURIComponent(elm) + '=' + encodeURIComponent(formData[elm]));
                        }
                        location.href = rootPath + '/buynow.sc?' + result.join('&');
                    } else {
                        YG_Utils.Notification.toast(data.message);
                    }
                },
                error: function (xhr, errorType, error) {
                    YG_Utils.Notification.toast("操作失败!");
                }
            });
        });
    }
    function AddShoppingCart() {
        $('.yg-nav').on('tap', '#btnAddShoppingCart', function () {
            var formData = GetFormData('btnAddShoppingCart');
            if (!formData) return;
            $.ajax({
                type: 'post',
                url: rootPath + '/addCart.sc',
                dataType: 'json',
                data: formData,
                beforeSend: function () {
                    YG_Utils.Layer.show();
                },
                complete: function () {
                    YG_Utils.Layer.closeAll();
                },
                success: function (data, status, xhr) {
                    if (data.success) {
                        $('#shopcartTotal').text(data.data);
                    }
                    YG_Utils.Notification.toast(data.message);
                },
                error: function (xhr, errorType, error) {
                    YG_Utils.Notification.toast("加入购物车失败!");
                }
            });
        });
    }

    function GetFormData(getBtnName) {
        var sid = $('#pdSize').data('id'),
            cid = $('#pdColor').data('id'),
            wfxPrice = $('#wfxPrice').val(),
            pdNumber = $('#pdNumber').text();
        if(getBtnName=='btnAddShoppingCart'){
            if($.trim(sid) == '' || pdNumber == NaN || pdNumber <= 0) {
                addShopSmallPop('addShoppingCart',commodityNo);
                return false;
            }
        }else if(getBtnName=='btnBuyNow'){
        	if($.trim(sid) == '' || pdNumber == NaN || pdNumber <= 0) {
                addShopSmallPop('buyNow',commodityNo);
                return false;
            }
        }else{
            if ($.trim(sid) == '') {
                YG_Utils.Notification.toast("请选择尺码!");
                return false;
            }
            pdNumber = parseInt(pdNumber);
            if (pdNumber == NaN || pdNumber <= 0) {
                YG_Utils.Notification.toast("选择数量异常!");
                return false;
            }
        }
        return {commodityNo: cid, skuId: sid, skuCount: pdNumber, shopId: $('#shopId').val()};
       
    }
    
    function getFloatFromData() {
        var cid = $('#pdColor1').data('id'),
        	sid = $('#pdSize1').data('id'),
        	wfxPrice = $('#wfxPrice').val(),
            pdNumber = $('#pdNumber1').text();
        if ($.trim(sid) == '') {
            YG_Utils.Notification.toast("请选择尺码!");
            return false;
        }
        pdNumber = parseInt(pdNumber);
        if (pdNumber == NaN || pdNumber <= 0) {
            YG_Utils.Notification.toast("选择数量异常!");
            return false;
        }
        return {commodityNo: cid, skuId: sid, skuCount: pdNumber, shopId: $('#shopId').val()};
    }
    
    function selectSizeOk(type,floatFormData){
    	if(type == 'addShoppingCart'){
    		$.ajax({
    			type: 'post',
    			url: rootPath + '/addCart.sc',
    			dataType: 'json',
    			data: floatFormData,
    			beforeSend: function () {
    				YG_Utils.Layer.show();
    			},
    			complete: function () {
    				YG_Utils.Layer.closeAll();
    			},
    			success: function (data, status, xhr) {
    				if (data.success) {
    					$('#shopcartTotal').text(data.data);
    				}
    				YG_Utils.Notification.toast(data.message);
    				 $(".viewportWrap").css({
    			            height:"100%",
    			            position: 'relative',
    			            overflow: 'scroll'
    			        });
    			},
    			error: function (xhr, errorType, error) {
    				YG_Utils.Notification.toast("加入购物车失败!");
    			}
    		});
    	}else if(type == 'buyNow'){
    		$.ajax({
                type: 'post',
                url: rootPath + '/shoppingcart/checkadd.sc',
                dataType: 'json',
                data: floatFormData,
                beforeSend: function () {
                    YG_Utils.Layer.show();
                },
                complete: function () {
                    YG_Utils.Layer.closeAll();
                },
                success: function (data, status, xhr) {
                    if (data.success) {
                        var result = [];
                        for (elm in floatFormData) {
                            result.push(encodeURIComponent(elm) + '=' + encodeURIComponent(floatFormData[elm]));
                        }
                        location.href = rootPath + '/buynow.sc?' + result.join('&');
                    } else {
                        YG_Utils.Notification.toast(data.message);
                    }
                },
                error: function (xhr, errorType, error) {
                    YG_Utils.Notification.toast("操作失败!");
                }
            });
    	}
    }

    function ImageLoad() {
        M('.yg-floor').imageLazyload({
            placeholder: '/static/images/thum.png'
        });
    }

    function BtnAdd() {
        $('.mui-btn-numbox-plus').on('tap', function () {
            var sku = $(this).siblings('.mui-input-numbox').val();
            var maxsku = $(this).parent().data('numbox-max');
            if (sku == maxsku) {
                YG_Utils.Notification.toast('每个尺码最多购买' + maxsku + '件！');
            }
        });
    }

    function OpenLayerEvent(target) {
		$(".viewport:first").css({
			overflow: 'hidden',
			height: $(window).height() - 90
		});
	}

	function CloseLayerEvent(target) {
		var yg_silde_in_lay = $(target).parents('.layermbox').find('.laymshade').first()[0],
			btn_close = document.getElementById("close");
		yg_silde_in_lay ? yg_silde_in_lay.addEventListener('tap', function() {
			layer.closeAll();
			$(".viewport").removeAttr('style');
		}) : '';

		btn_close ? btn_close.addEventListener('tap', function() {
			layer.closeAll();
			$(".viewport").removeAttr("style");
		}) : '';
	}

    function ShopCart() {
        $.ajax({
            type: 'post',
            url: rootPath + '/shoopingcart/count.sc',
            dataType: 'json',
            data: {},
            success: function (data, status, xhr) {
                $('#shopcartTotal').text(data);
            },
            error: function (xhr, errorType, error) {
                YG_Utils.Notification.toast("获取购物车数据超时!");
            }
        });
    }
    function addShopSmallPop(type,commNo,countNo){
        if(typeof countNo !='undefined' && countNo===1){
        }else{
            YG_Utils.Layer.show();
        }
        M.get('/commodity/selectSize/'+commNo+'.sc', {}, function(data) {
            if(typeof countNo !='undefined' && countNo===1){
                $('.layermmain .layermcont').html(data);
                tapClick(type);
            }else{
                 YG_Utils.Layer.closeAll();
                layer.open({
                    type: 1,
                    className: 'yg-slide-in',
                    content: data,
                    style: 'position:fixed; bottom:-150px; left:0; padding:10px 0 0 0; ',
                    slide: true,
                    callback: function() {
                        tapClick(type);
                    }
                })
            }
        });
    }
    function tapClick(typeValue){
        var type=typeValue;
        scrollValue=$(window).scrollTop();
        $(".viewportWrap").css({
            height: $(window).height(),
            position: 'relative',
            overflow: 'hidden'
        });
        $("#close").on('tap', function() {
            layer.closeAll();
            $(".viewportWrap").removeAttr("style");
            window.scrollTo(0,scrollValue);
        });
        $(".laymshade").on('tap', function() {
            layer.closeAll();
            $(".viewportWrap").removeAttr("style");
            window.scrollTo(0,scrollValue);
        });
        $("#add-shopcart-sure").on('tap', function() {
            //var formExt1_html = $("#formExt1").html();
            //layer.closeAll();
            //$("#formExt2").html(formExt1_html);
            //$("body").removeAttr("style");
            var floatFormData = getFloatFromData();
            if (!floatFormData) return;
            selectSizeOk(type,floatFormData);
        });
        CloseLayerEvent.call(this, '.yg-slide-in');
        M('.yg-form-floor1').on('tap', '.mui-btn', function(e) {
            var $this = $(this),
                $text = $this.text(),
                $id = $this.attr('data-id'),
                $cpt = $this.siblings(':hidden');

            if ($this.hasClass('mui-btn-numbox-plus1')) {
                var maxCount = $(this).attr('data-maxCount');
                var currCount = parseInt($("input[name='pdNumber1']").val());
                if(currCount >= maxCount){
                    YG_Utils.Notification.toast('每个尺码最多购买' + maxCount + '件！');
                }else{
                    $("input[name='pdNumber1']").val(parseInt($("input[name='pdNumber1']").val()) + 1);
                    $("#pdNumber1").html($("input[name='pdNumber1']").val());
                }

            } else if ($this.hasClass('mui-btn-numbox-minus1')) {
                if ($("input[name='pdNumber1']").val() == 1) {
                    return;
                }
                $("input[name='pdNumber1']").val(parseInt($("input[name='pdNumber1']").val()) - 1);
                $("#pdNumber1").html($("input[name='pdNumber1']").val());
            } else if($(this).hasClass('spec-btn')) {
                if($(this).hasClass('active')){
                    return;
                }else{
                    var commNo = $(this).attr('data-id');
                    //layer.closeAll();
                    addShopSmallPop(type,commNo,1);
                }
            } else {
                $this.addClass('active').siblings().removeClass('active');
                var name = $cpt.attr('name');
                if(name != 'pdSize'){
                	$('#' + $cpt.attr('name')+'1').html($text ? $text : $this.val()).attr('data-id',$id);
                }
                var sizeName = $text ? $text : $this.val();
                $('#pdSize1').html(sizeName + '码').attr('data-id',$id);
                $cpt.val($this.data('value'));

            }
        });
        M('.yg-form-floor1').on('change', '[name="pdNumber1"]', function() {
            $('#' + this.name).html(this.value);
        });
        M('.yg-form-floor1').on('tap', '#formExt1', function(e) {
            var $this = $(this),
                _state = !$this.data('isExt');
            $this.data('isExt', _state).find('.formExt1').toggleClass('active');
            if (_state) {
                $('#formBuy1').hide();
            } else {
                $('#formBuy1').show();
            }
        });   
    }
    function ShareHelpClick() {
        if($('#info-notes').length>0){
            document.getElementById("info-notes").addEventListener('tap', function() {
              //  YG_Utils.Layer.show();
            	var protItemNo = $(this).attr('data-no');
                M.get('/commodity/cortext/'+protItemNo+'.sc', {}, function(data) {
                  //  YG_Utils.Layer.closeAll();
                    layer.open({
    					type: 1,
    					className: 'yg-slide-in',
    					content: data,
    					anim: false,
    					style: 'position:fixed; bottom: 0; left:0; padding:10px 0 0 0; height: 300px;',
    					slide: true,
    					btn:['关闭'],
    					yes: function(index){
    						$(".viewport:first").removeAttr("style");
    						layer.close(index);
    					},
    					callback: function() {
    						OpenLayerEvent.call(this);
    						CloseLayerEvent.call(this, '.yg-slide-in');
//    						document.getElementById("add-shopcart-sure").addEventListener('tap', function() {
//    							layer.closeAll();
//    							$("body").removeAttr("style");
//    						});
    					}
    				})
                });
            });
        }
        if($('#yg-erweima').length>0){
            document.getElementById("yg-erweima").addEventListener('tap', function() {
            	var url=window.location.href;
    			var title=$("title").html();
    			var imgUrl=$("#goodsPhoto").find('.mui-slider-item').find('img').attr('src');
    			var type='img';
    			var shopLogo=$(".user-photo").find("img").attr("src");
    			var shopCode=$("#shopCode").html();
    			var proPrice=$(".detail-price span").html();
    			var backUrl={
    					url:url,
    					title:title,
    					img:imgUrl,
    					type:type,
    					shopLogo:shopLogo,
    					shopCode:shopCode,
    					proPrice:proPrice
    			};
    			if(running_environment==3 && isOldVersion == '0'){//iOS_APP平台
                    window.location.href="shareWechat?param="+JSON.stringify(backUrl);
    			}
    			else if(running_environment==4 && isOldVersion == '0'){//ANDROID_APP平台
    				 window.android_APP.getAppShare(JSON.stringify(backUrl))
    			}else{
                YG_Utils.Layer.show();
                M.get('/'+commodityNo+'/qrCodeOfComm.sc', {}, function(data) {
                    YG_Utils.Layer.closeAll();
                    layer.open({
                        type: 1,
                        className: 'yg-slide-in',
                        content: data,
                        style: 'margin:0 3%;width:94%;border-radius:5px;background:#f7f7f7;',
                        callback: function() {
                        	OpenLayerEvent.call(this);
    						CloseLayerEvent.call(this, '.yg-slide-in');
                            if (YG_Common.ISWeixin() || isOldVersion == '1') {
                                $("#yg-browser").hide();
                            }
                            if($('#close').length>0){
                                document.getElementById("close").addEventListener('tap', function() {
                                    layer.closeAll();
                                    $(".viewportWrap").removeAttr("style");
                                });
                            }
                            $('.laymshade').on('tap',function(){
                                    layer.closeAll();
                                    $(".viewportWrap").removeAttr("style");
                                })
                            CloseLayerEvent.call(this, '.yg-slide-in');
                        }
                    })
                  });
                }
            });
        }
    }
    $(function () {
        Init();
    });
    return {};
}(Zepto, mui));