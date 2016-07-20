/**
 * create by guoran
 */
var YG_Order_Detail = (function ($, M) {
    function Init() {
        Init_Pay();
        Init_buyAgin();
        Init_CancelOrder();
        Init_ConfirmOrderGoods();
        CountDown();
        Init_ErrorMsg();
        Init_WaitPayWithOrderLeftTime();
        Init_ShowCommity();
    }

    function Init_ShowCommity() {
        $('.shopping-cart-list').on('tap', '.yg-layout-left,.goods-title', function () {
            var _this = $(this);
            var _parent = _this.parents('.shopping-cart-list-item');
            var _shopid = _parent.data('shopid');
            var _commodityNo = _parent.data('commodityid');
            if (YG_Utils.Strings.IsBlank(_shopid) || YG_Utils.Strings.IsBlank(_commodityNo)) {
                YG_Utils.Notification.error('无法查看商品信息,参数不足');
                return false;
            }
            location.href = rootPath + '/' + _shopid + '/item/' + _commodityNo + '.sc';
        });
    }

    // 初始化订单剩余时间提示
    function Init_WaitPayWithOrderLeftTime() {
        var index = setInterval(function () {
            orderLeftTime--;
            flush_ShowLeftTime()
            if (orderLeftTime == 0) {
                location.reload();
            }
        }, 1000);

        function flush_ShowLeftTime() {
            if (orderLeftTime != undefined && orderLeftTime > 0) {
                var _residualTime = $('.residualTime');
                var d = Math.floor(orderLeftTime / 60 / 60 / 24);
                var h = Math.floor(orderLeftTime / 60 / 60 % 24);
                var m = Math.floor(orderLeftTime / 60 % 60);
                var s = Math.floor(orderLeftTime % 60);
                var showText = '';
                if (d > 0) { showText = showText + d + '天'}
                if (h > 0) { showText = showText + h + '小时'}
                if (m > 0) { showText = showText + m + '分'}
                if (s > 0) { showText = showText + s + '秒'}
                _residualTime.html(showText);
            }
        }
    }

    // 初始化错误提示
    function Init_ErrorMsg() {
        YG_Utils.Notification.error(errorMsg);
    }

    // 初始化支付
    function Init_Pay() {
        $('.mui-bar-tab').on('tap', '#payNow', function (e) {
            var orderId = $(this).data('id');
            location.href = rootPath + '/order/' + orderId + '/pay.sc';
        });
    }
    
    // 初始化支付
    function Init_buyAgin() {
    	$('.mui-bar-tab').on('tap', '#buyAgin', function (e) {
    		var orderId = $(this).data('id');
    		$.ajax({
                type: 'post',
                url: rootPath + '/order/buyAginCheck.sc?orderId=' + orderId,
                dataType: 'json',
                success: function (data, status, xhr) {
                    if (data.code == 'fail') {
                        M.alert('所有商品售磬，无法继续购买');
                        return;
                    } else {
                    	location.href = rootPath + '/order/buyAgin.sc?orderId=' + orderId;
                    }
                },
                error: function (xhr, errorType, error) {
                    M.alert('重新购买失败，系统异常');
                }
            })
    	});
    }

    // 初始化取消订单按钮
    function Init_CancelOrder() {
        $('.viewport').on('tap', '#cancelOrder', function (event) {
            event.preventDefault();
            var _this = $(this);
            var orderId = _this.data('id');
            M.confirm('您确定要取消订单吗?', '', function (ele) {
                if (ele.index == 1) {
                    $.ajax({
                               type: 'post',
                               url: rootPath + '/rest/order/' + orderId + '/cancel.sc',
                               dataType: 'json',
                               success: function (data, status, xhr) {
                                   if (data == '' || !data.success) {
                                       M.alert('错误:' + data.message, '错误提示', function () {
                                           location.reload();
                                           return false;
                                       });
                                   } else {
                                       M.alert('订单已经成功取消', '温馨提示', function () {
                                           location.href = rootPath + '/order/' + orderId + '.sc';
                                       });
                                   }
                               },
                               error: function (xhr, errorType, error) {
                                   M.alert('取消订单发生错误:' + error, '错误提示', function () {
                                       location.reload();
                                       return false;
                                   });
                               }
                           })
                }
            })
        })
    }

    // 订单确认收货按钮
    function Init_ConfirmOrderGoods() {
        $('.viewport').on('tap', '#confirmOrderGoods', function (event) {
            event.preventDefault();
            var _this = $(this);
            var orderId = _this.data('id');
            M.confirm('您已确认收到货了吗？<br/>操作确认收货后货款将转账到商家。', '', function (ele) {
                if (ele.index == 1) {
                    $.ajax({
                               type: 'post',
                               url: rootPath + '/rest/order/' + orderId + '/confirmGoods.sc',
                               dataType: 'json',
                               success: function (data, status, xhr) {
                                   if (data == '' || !data.success) {
                                       M.alert('错误:' + data.message, '错误提示');
                                       return;
                                   } else {
                                       M.alert('订单已经确认收货', '温馨提示', function () {
                                           location.href = rootPath + '/order/' + orderId + '.sc';
                                       });
                                   }
                               },
                               error: function (xhr, errorType, error) {
                                   M.alert('确认收货发生错误:' + error, '错误提示');
                               }
                           })
                }
            })
        })
    }

    /**
     *倒计时函数
     * */
    function CountDown() {
        var hours = 2, min = 0, secound = 60;
        var countTime = setInterval(function () {
            secound--;
            if (secound == 0) {
                secound = 60;
                if (min == 0) {
                    min = 60;
                    hours--;
                }
                min--;
                $('.residualTime').html(hours + "小时" + min + "分钟");//每隔一分钟才写入一次
            }
            if (hours == 0 && min == 0) {
                min = 0;
                clearInterval(countTime);
            }

        }, 1000)
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));