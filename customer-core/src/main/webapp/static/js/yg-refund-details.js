/**
 * create by lijunfang
 */
var YG_RefundDetails = (function ($, M) {
    function Init() {
        CancelTip();
        EditTip();
        Init_ErrorMsg();
    }

    function Init_ErrorMsg() {
        YG_Utils.Notification.error(errorMsg);
    }

    function CancelTip() {
        M('.yg-box').on('tap', '.cancel-btn', function () {
            var _refundNo = $(this).data('refundno');
            M.confirm('确定要取消退款吗？', '', function (e) {
                if (e.index == 1) {
                    location.href = rootPath + '/order/refund/' + _refundNo + '/cancelRefund.sc';
                } else {
                }
            })
        });
    }

    function EditTip() {
        M('.yg-box').on('tap', '.edit-btn', function () {
            var _refundNo = $(this).data('refundno');
            var _refundType = $(this).data('refundtype');
            M.confirm('确定要修改退款吗？', '', function (e) {
                if (e.index == 1) {
                    // 如果是退款退货的,则跳转到退款退货的修改页面
                    if (_refundType == 'REJECTED_REFUND') {
                        location.href = rootPath + '/order/refund/' + _refundNo + '/editReturnGoods.sc';
                    } else {
                        location.href = rootPath + '/order/refund/' + _refundNo + '/editRefund.sc';
                    }
                } else {
                }
            })
        });
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));