/**
 * create by guoran
 */
var YG_Shopping_Cart = (function ($, M) {
    function Init() {
        Delete();
        SelectAll();
        SelectOne();
        ChangeNumber();
        BuyNow();
        InitButton();
    }

    function Delete() {
        $('.shopping-cart-list-item').on('tap', '.goods-delete', function (e) {
            var $this = $(this),
                $item = $this.parents('.shopping-cart-list-item'),
                $size = $item.siblings('.shopping-cart-list-item').length + 1,
                $shopitem = $item.parents('.yg-shopping-cart-floor');

            M.confirm('确认要删除这些商品吗？', ' ', function (e) {
                if (e.index == 1) {
                    //开发者请注意：此处需使用ajax去调用接口删除，成功后，在回调中进行移除操作
                    $.ajax({
                        type: 'post',
                        url: rootPath + '/removeSkus.sc',
                        dataType: 'json',
                        data: {ids: $item.data('id'), shopId: $item.data('shopId')},
                        beforeSend:function(){
                            YG_Utils.Layer.show();
                        },
                        complete:function(){
                            YG_Utils.Layer.closeAll();
                        },
                        success: function (data, status, xhr) {
                            if (data.success) {
                                if ($size < 2) {
                                    $shopitem.hide('slow');
                                    $shopitem.remove();
                                } else {
                                    $item.hide('slow');
                                    $item.remove();
                                }
                                RequestCallback(data.data);
                                if ($.trim($('.mui-content').text()) == '') {
                                    location.reload();
                                }
                            } else {
                                YG_Utils.Notification.toast(data.message);
                            }
                        },
                        error: function (xhr, errorType, error) {
                            YG_Utils.Notification.toast("删除购物车数据失败!");
                        }
                    });
                } else {
                }
            })
        });
    }

    function ChangeNumber() {
        $('.mui-numbox').on('tap', '.mui-numbox-btn-plus,.mui-numbox-btn-minus', function () {
            var $this = $(this),
                $item = $this.parents('.shopping-cart-list-item'),
                $number = $this.siblings('input');
            var sku = $(this).siblings('.sku-number').val();
            var maxsku = $(this).parent().data('numbox-max');
            if (sku == maxsku && $(this).hasClass('mui-numbox-btn-plus')) {
                YG_Utils.Notification.toast('每个尺码最多购买' + maxsku + '件！');
            }
            setTimeout(function () {
                $.ajax({
                    type: 'post',
                    url: rootPath + '/changeNum.sc',
                    dataType: 'json',
                    data: {id: $item.data('id'), skuCount: $number.val()},
                    beforeSend:function(){
                        layer.open({type: 2});
                    },
                    complete:function(){
                        layer.closeAll();
                    },
                    success: function (data, status, xhr) {
                        if (data.success) {
                            RequestCallback(data.data);
                        } else {
                            YG_Utils.Notification.toast(data.message);
                        }
                    },
                    error: function (xhr, errorType, error) {
                        YG_Utils.Notification.toast("勾选操作失败!");
                    }
                });
            }, 10);

        });
    }

    function SelectAll() {
        $('.yg-nav').on('tap', '#selectAll', function () {
            var $this = $(this);
            var items = $('.shopping-cart-list-item');
            var ids = [];
            items.each(function (index, item) {
            	if($(item).find('input.sku-checkbox').attr('disabled')){
            	}else{
            		ids.push($(item).data('id'));
            	}
            });
            var data = {
                ids: ids.join(),
                checked: true,
                success: function () {
                    $('.yg-shopping-cart-floor .yg-layout-left [type=checkbox]:not(:disabled)').prop('checked', this.checked);
                },
                error: function () {
                    $('#selectAll').prop('checked', !this.checked);
                }
            };
            setTimeout(function () {
                if ($this.prop('checked')) {
                    data.checked = true;
                    SelectRequest(data);
                } else {
                    data.checked = false;
                    SelectRequest(data);
                }
            }, 10);
        });
    }

    //购物车数据异步请求
    function SelectRequest(itemData) {
        $.ajax({
            type: 'post',
            url: rootPath + '/selectItem.sc',
            dataType: 'json',
            data: {ids: itemData.ids, checked: itemData.checked},
            beforeSend:function(){
                YG_Utils.Layer.show();
            },
            complete:function(){
                YG_Utils.Layer.closeAll();
            },
            success: function (data, status, xhr) {
                if (data.success) {
                    itemData.success();
                    RequestCallback(data.data);
                } else {
                    itemData.error();
                    YG_Utils.Notification.toast(data.message);
                }
            },
            error: function (xhr, errorType, error) {
                itemData.error();
                YG_Utils.Notification.toast("勾选操作失败!");
            }
        });
    }

    function SelectOne() {
        $('.shopping-cart-list-item').on('tap', '.sku-checkbox', function (e) {
            var $this = $(this),
                $item = $this.parents('.shopping-cart-list-item');
            var data = {
                ids: $item.data('id'),
                checked: true,
                success: function () {
                    var unCheckeds = $('.sku-checkbox:not(:disabled)').not(':checked');
                    if (unCheckeds.length == 0) {
                        $('#selectAll').prop('checked', true);
                    } else {
                        $('#selectAll').prop('checked', false);
                    }
                },
                error: function () {
                    $this.prop('checked', !this.checked);
                }
            };
            setTimeout(function () {
                if ($this.prop('checked')) {
                    data.checked = true;
                    SelectRequest(data);
                } else {
                    data.checked = false;
                    SelectRequest(data);
                }
            }, 10);
        });
    }

    function RequestCallback(data) {
        $('#btn-total-money').text('合计：' + data.viewPrice);
        $('#btn-total-count').text('去结算（' + data.totalCount + '）');
    }

    function BuyNow() {
        $('.yg-nav').on('tap', '.yg-nav-btn-buy-now', function () {
            //结算前验证，
            var skus = $('.sku-checkbox:checked');
            var key = '', flag = false;
            if (skus.length <= 0) {
                YG_Utils.Notification.toast("请选择结算商品!");
                return;
            } 
            location.href = $(this).attr('href') + "?isPay=true";
        })
    }

    function InitButton() {
        var btns = document.getElementsByClassName('btn-disabled');
        if (btns.length > 0) {
            for (var index in btns) {
                btns[index].disabled = true;
            }
        }
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));