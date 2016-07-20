/**
 * create by lijunfang
 */
var YG_MyAddress = (function ($, M) {
    $(function () {
        Init();
    });
    // 初始化
    function Init() {
        changeefaultAddressEvent();
        delAddressEvent();
        editAddressEvent();
        Init_ShowError();
        Init_RedirectOrderSubmit();
    }

    function Init_ShowError() {
        YG_Utils.Notification.error(errorMsg);
    }

    // 删除收货地址事件
    function delAddressEvent() {
        $('.address-detail-list').on('tap', '.del-btn', function () {
            var $this = $(this);
            var addressId = $this.data('id');
            M.confirm("确定要删除？", '', function (e) {
                if (e.index == 1) {
                    $.ajax({
                               type: 'post',
                               url: rootPath + '/usercenter/myaddress/' + addressId + '/delete.sc',
                               dataType: 'json',
                               success: function (data, status, xhr) {
                                   $this.parents('.address-detail-list').remove();
                                   if (!data.success) {
                                       M.alert('Error:' + data.message, '删除收货地址失败', function () {
                                           location.reload();
                                       });
                                       return;
                                   }
                                   M.toast('成功删除收货地址.');
                               },
                               error: function (xhr, errorType, error) {
                                   M.alert('Error:' + error, '删除收货地址失败', function () {
                                       location.reload();
                                   })
                               }
                           });
                }
            });
        });
    }

    // 修改收货地址但牛事件
    function editAddressEvent() {
        $('.address-detail-list').on('tap', '.editor-btn', function () {
            var $this = $(this);
            var addressId = $this.data('id');
            location.href = rootPath + '/usercenter/myaddress/' + addressId + '/edit.sc';
        });
    }

    // 修改默认收货地址
    function changeefaultAddressEvent() {
        $('.mui-content').on('change', 'input[name=defaultAddress]', function (e) {
            var addressId = getDefaultAddress();
            if (addressId) {
                $.ajax({
                           type: 'post',
                           url: rootPath + '/usercenter/myaddress/defaultAddress.sc',
                           data: {addressId: addressId},
                           dataType: 'json',
                           success: function (data, status, xhr) {
                               if (!data.success) {
                                   M.alert('Error:' + data.message, '设定默认收货地址错误', function () {
                                       location.reload();
                                   });
                                   return;
                               }
                               M.toast('设置默认收货地址成功.');
                           },
                           error: function (xhr, errorType, error) {
                               M.alert('Error:' + error, '设定默认收货地址错误', function () {
                                   location.reload();
                               });
                           }
                       });
            }
        });
        $('.mui-content').on('tap', 'input[name=defaultAddress]', function (e) {
            var _checked = $(this).prop('checked');
            // 如果是checked,则表示是选择的是当前
            if (_checked) {
                var _addressId = $(this).val();
                if (_addressId) {
                    M.confirm('确认取消默认收货地址?', '', function (ele) {
                        if (ele.index == 1) {
                            $.ajax({
                                       type: 'post',
                                       url: rootPath + '/usercenter/myaddress/cancelDefaultAddress.sc',
                                       data: {addressId: _addressId},
                                       dataType: 'json',
                                       success: function (data, status, xhr) {
                                           if (!data.success) {
                                               M.alert('Error:' + data.message, '取消默认收货地址错误', function () {
                                                   location.reload();
                                               });
                                               return;
                                           }
                                           M.toast('取消默认收货地址成功.');
                                           location.reload();
                                       },
                                       error: function (xhr, errorType, error) {
                                           M.alert('Error:' + error, '取消默认收货地址错误', function () {
                                               location.reload();
                                           });
                                       }
                                   });
                        }
                    });
                }
            }
        });
    }

    function Init_RedirectOrderSubmit() {
        if (isPay) {
            $('.mui-content').on('tap', '.isPay', function () {
                var _id = $(this).parents('.address-detail-list').data('id');
                location.href = rootPath + '/confirm_order.sc?addressId=' + _id;
            });
        }
    }

    // 获取选中的默认收货地址
    function getDefaultAddress() {
        return $('input[name=defaultAddress]:checked').val();
    }

    return {};
}(Zepto, mui));