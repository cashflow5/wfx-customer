/**
 * 退款单
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/12 下午3:41
 * @since 1.0 Created by lipangeng on 16/4/12 下午3:41. Email:lipg@outlook.com.
 */
var YG_Order_Refund = (function ($, M) {
    function Init() {
        Init_SubmitOrderRefundForm();
        Init_CheckRefundMoney();
        Init_ErrorMsg();
        Init_ReturnTypeToggle();
    }

    function Init_ErrorMsg() {
        YG_Utils.Notification.error(errorMsg);
    }

    // 金额改变时检查金额,不让其超过最大可申请金额
    function Init_CheckRefundMoney() {
        $('#refundForm').on('change', '#refundFee', function (e) {
            var refundFee = parseFloat($(this).val());
            if (refundFee > refundFeeMax) {
                $(this).val(refundFeeMax.toFixed(2));
            } else {
                $(this).val(refundFee.toFixed(2));
            }
        });
    }

    // 初始化表单
    function Init_SubmitOrderRefundForm() {
        $('#refundForm').on('tap', '#submitRefundForm', function (e) {
            var refundFee = parseFloat($("#refundFee").val());
            if (refundFee == undefined || refundFee <= 0) {
                YG_Utils.Notification.error("退款金额不能小于等于0");
                return false;
            }
            if (refundFee > refundFeeMax) {
                $(this).val(refundFeeMax);
                YG_Utils.Notification.error("退款金额不正确");
                return false;
            }
            if ($('#reason').val() == '') {
                YG_Utils.Notification.error("请选择退款原因");
                return false;
            }
            $('#refundForm').submit();
        });
    }

    function Init_ReturnTypeToggle() {
        $('.yg-box').on('tap', '.return-goods', function () {
            if (isEditPage) {
                var _refundNo = $(this).data('refundno');
                location.href = rootPath + '/order/refund/' + _refundNo + '/editReturnGoods.sc';
            } else {
                var _orderId = $(this).data('orderid');
                var _orderDetailId = $(this).data('orderdetailid');
                location.href = rootPath + '/order/' + _orderId + '/' + _orderDetailId + '/returnGoods.sc';
            }
        });

        $('.yg-box').on('tap', '.only-refund', function () {
            if (isEditPage) {
                var _refundNo = $(this).data('refundno');
                location.href = rootPath + '/order/refund/' + _refundNo + '/editRefund.sc';
            } else {
                var _orderId = $(this).data('orderid');
                var _orderDetailId = $(this).data('orderdetailid');
                location.href = rootPath + '/order/' + _orderId + '/' + _orderDetailId + '/refund.sc';
            }
        });
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));