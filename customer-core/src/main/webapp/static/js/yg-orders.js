/**
 * create by guoran
 */
var YG_Orders = (function ($, M) {
    var requestPage;

    function Init() {
        DropDownMenu();
        Init_ConfirmOrderGoods();
        Init_buyAgin();
        Init_PullRefresh();
        Init_CancelOrder();
        Init_ErrorMsg();
    }

    function Init_ErrorMsg() {
        YG_Utils.Notification.error(errorMsg);
    }

    // 取消订单按钮
    function Init_CancelOrder() {
        $('.mui-content').on('tap', '#cancelOrder', function (event) {
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
        $('.mui-content').on('tap', '#confirmOrderGoods', function (event) {
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
    
    // 订单重新购买按钮
    function Init_buyAgin() {
        $('.mui-content').on('tap', '#buyAgin', function (event) {
            _this = $(this);
            var orderId = _this.data('id');
            
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
        })
    }

    // 下拉刷新页面
    function Init_PullRefresh() {
        requestPage = YG_Utils.Url.getParams()['page'];
        if (YG_Utils.Strings.IsBlank(requestPage)) {
            requestPage = 1;
        }
        $('.mui-content').dropload({
                                       scrollArea: window,
                                       loadDownFn: function (me) {
                                           var params = YG_Utils.Url.getParams();
                                           params['page'] = requestPage + 1;
                                           $.ajax({
                                                      type: 'get',
                                                      url: rootPath + '/order/myorder/ajax.sc',
                                                      data: params,
                                                      success: function (data, status, xhr) {
                                                          if (data == '') {
                                                              me.noData(true);
                                                              me.resetload();
                                                              me.lock();
                                                              $('.dropload-down').remove();
                                                          } else {
                                                              $('#myListData').append(data);
                                                              requestPage = requestPage + 1;
                                                              me.resetload();
                                                          }
                                                      },
                                                      error: function (xhr, errorType, error) {
                                                          M.toast('刷新订单失败,错误:' + error);
                                                          me.noData(true);
                                                          me.resetload();
                                                      }
                                                  });
                                       }
                                   });
    }

    function DropDownMenu() {
        $('.mui-table-view').on('tap', '.mui-tab-item', function () {
            var $this = $(this);
            var staticText = $this.text();
            if (staticText == "待付款") {
                location.href = rootPath + '/order/myorder.sc?status=WAIT_PAY';
            } else if (staticText == "待发货") {
                location.href = rootPath + '/order/myorder.sc?status=WAIT_DELIVER';
            } else if (staticText == "部分发货") {
                location.href = rootPath + '/order/myorder.sc?status=PART_DELIVERED';
            } else if (staticText == "已发货") {
                location.href = rootPath + '/order/myorder.sc?status=DELIVERED';
            } else if (staticText == "交易成功") {
                location.href = rootPath + '/order/myorder.sc?status=TRADE_SUCCESS';
            } else if (staticText == "交易关闭") {
                location.href = rootPath + '/order/myorder.sc?status=TRADE_CLOSED';
            }
            mui('.mui-popover').popover('toggle');//关闭弹出层
        });
        mui('.mui-scroll-wrapper').scroll();//模态框可以往上滚动
    }

    $(function () {
        Init();
    });

    return {
        pullFresh: function () {
            pullfresh();
        }
    };
}(Zepto, mui));