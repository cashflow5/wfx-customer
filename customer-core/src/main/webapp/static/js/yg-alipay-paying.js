/**
 * 微信支付
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/20 上午10:24
 * @since 1.0 Created by lipangeng on 16/4/20 上午10:24. Email:lipg@outlook.com.
 */
var YG_Alipay_Paying = (function ($, M) {

    function Init() {
    	Init_Alipay()
    }
	// 倒计时3s进入支付页面
    function Init_Alipay() {
        var second = 3;
        var index = setInterval(function () {
            second--;
            var _tip = $('.mui-content .tcenter');
            _tip.html('正在检查支付结果,' + second + '秒后跳转到支付宝支付页面.');
            if (second == 0) {
            	$('#payForm').submit();
                clearInterval(index);
            }
        }, 1000);
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));