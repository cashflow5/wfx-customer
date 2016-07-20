/**
 * create by lijunfang
 */
var YG_ReturnGoods = (function ($, M) {
    function Init() {
        Init_ReturnTypeToggle();
        Init_ErrorMsg();
        Init_SubmitReturnGoodsForm();
        Init_CheckRefundMoney();
        Init_ShowReturnFee();
    }

    // 初始化金额
    function Init_ShowReturnFee() {
        $('.return-goods-message').on('change', '#proNum', function () {
            var _num = $(this).val();
            $('#refundFee').val((proPrice * _num).toFixed(2));
            checkRefundFee();
        });
    }

    // 初始化提交订单按钮
    function Init_SubmitReturnGoodsForm() {
        $('#returnGoodsForm,#editReturnGoodsForm').on('tap', '#submitReturnGoodsForm,#submitEditReturnGoodsForm', function (e) {
            var _num = $('#proNum').val();
            if (_num == undefined || _num < 1) {
                YG_Utils.Notification.error("退货数量不能为0.如无需退货,请选择仅退款");
                return false;
            }
            var _refundFee = parseFloat($('#refundFee').val());
            if (_refundFee==undefined || _refundFee <=0){
                YG_Utils.Notification.error("退货数量不能小于等于0.");
                return false;
            }
            if (YG_Utils.Strings.IsBlank($('#expressNo').val())) {
                YG_Utils.Notification.error("快递单号不能为空");
                return false;
            }
            if ($('#reason').val() == '') {
                YG_Utils.Notification.error("请选择退款原因");
                return false;
            }
            if (!checkRefundFee()) {
                YG_Utils.Notification.error("退款金额有误");
                return false;
            }
            $('#returnGoodsForm,#editReturnGoodsForm').submit();
        })
    }

    // 检查价格
    function checkRefundFee() {
        var refundFee = parseFloat($('#refundFee').val());
        if (refundFee > refundFeeMax) {
            $('#refundFee').val(refundFeeMax.toFixed(2));
            return false;
        } else {
            $('#refundFee').val(refundFee.toFixed(2));
        }
        return true;
    }

    // 金额改变时检查金额,不让其超过最大可申请金额
    function Init_CheckRefundMoney() {
        $('.mui-content').on('change', '#refundFee', function (e) {
            checkRefundFee();
        });
    }

    // 初始化错误提示
    function Init_ErrorMsg() {
        YG_Utils.Notification.error(errorMsg);
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