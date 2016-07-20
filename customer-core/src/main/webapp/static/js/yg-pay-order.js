/**
 * create by guoran
 */
var YG_Pay_Order = (function ($, M) {
    function Init() {
        Payment();
        Goods();
        Submit();
        LinkJump();
        InitBtn();
    }

    function Payment() {
        //		M('.mui-content').on('tap', '.pay-box', function() {
        //			M.alert('请在第三方平台完成支付，如果确认已支付，请点击已完成支付。', ' ', '已完成支付');
        //		});
    }

    function Goods() {
        M('.mui-content').on('tap', '.img-thum-list-box', function () {
        	if(typeof(selectItem) == "undefined"){
        		location.href = rootPath + "/order/commodity/show.sc";
        	}else{
        		var result = [];
                for (elm in selectItem) {
                    result.push(encodeURIComponent(elm) + '=' + encodeURIComponent(selectItem[elm]));
                }
                location.href = rootPath + '/order/commodity/show.sc?' + result.join('&');
        	}
        });
    }

    function Submit() {
    	$('.yg-nav').on('tap', '.yg-nav-btn-buy-now.yg-nav-btn-buy-disable', function (){
    		var _payType = $('[name=payType]:checked').val();
            var _addressId = $('#addressId').val();
            if(_addressId==''||_addressId=='0'){
            	YG_Utils.Notification.toast("请填写收货人信息！");
            }
    		if(_payType==""||_payType==undefined){
    			YG_Utils.Notification.toast("请选择支付方式！");
    		}
    	});
    	
        $('.yg-nav').on('tap', '.yg-nav-btn-buy-now:not(.yg-nav-btn-buy-disable)', function () {
            var _payType = $('[name=payType]:checked').val();
            var _addressId = $('#addressId').val();
            if (YG_Utils.Strings.IsBlank(_payType)) {
                YG_Utils.Notification.error('请选择支付方式');
                return false;
            }
            if (YG_Utils.Strings.IsBlank(_addressId)) {
                YG_Utils.Notification.error('请选择收货地址');
                return false;
            }
            var _commodity = $('.box-content-wrap').text();
            if (YG_Utils.Strings.IsBlank(_commodity)) {
                YG_Utils.Notification.error('订单数据异常');
                return false;
            }
            $('#orderCreateForm').submit();
        });
    }

    function LinkJump() {
        M('.mui-content').on('tap', '#addAddress', function () {
            location.href = rootPath + "/usercenter/myaddress.sc?pay=true";
        });
    }

    function InitBtn() {
        var addressId = $('#addressId').val();
        var commodity = $('.box-content-wrap').text();
        if (YG_Utils.Strings.IsBlank(addressId) || addressId.length < 5 || YG_Utils.Strings.IsBlank(commodity)) {
            $('#buyNow').addClass('yg-nav-btn-buy-disable');
        }
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));