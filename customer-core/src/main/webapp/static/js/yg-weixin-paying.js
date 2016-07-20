/**
 * 微信支付
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/20 上午10:24
 * @since 1.0 Created by lipangeng on 16/4/20 上午10:24. Email:lipg@outlook.com.
 */
var YG_WeiXin_Paying = (function ($, M) {

    function Init() {
        if (!YG_Utils.WeiXin.checkBrower()) {
            M.alert("请在微信中打开此页面,确定后跳转回订单页面.", '温馨提示', function () {
                location.href = rootPath + '/order/' + orderId + '.sc';
            });
        }
        WeiXinPay();
    }

    // 倒计时
    function ThreeCountdown() {
        var second = 3;
        var index = setInterval(function () {
            second--;
            var _tip = $('.mui-content .tcenter');
            _tip.html('正在检查支付结果,' + second + '秒后跳转到支付结果页面.');
            if (second == 0) {
                location.href = rootPath + '/order/' + orderId + '/payDetail.sc?status=SUCCESS';
                clearInterval(index);
            }
        }, 1000);
    }

    // 进行支付
    function WeiXinPay() {
        function onBridgeReady() {
            WeixinJSBridge.invoke(
                'getBrandWCPayRequest', {
                    "appId": payConfig.appId,     //公众号名称，由商户传入
                    "timeStamp": payConfig.timestamp,         //时间戳，自1970年以来的秒数
                    "nonceStr": payConfig.nonceStr, //随机串
                    "package": payConfig.packages,
                    "signType": payConfig.signType,         //微信签名方式：
                    "paySign": payConfig.paySign //微信签名
                },
                function (res) {
                    if (res.err_msg == 'get_brand_wcpay_request:ok') { //支付成功
                        ThreeCountdown();
                    } else { // 支付失败
                        location.href = rootPath + '/order/' + orderId + '/payDetail.sc?status=FAIL';
                    }
                }
            );
        }

        if (typeof WeixinJSBridge == "undefined") {
            if (document.addEventListener) {
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            } else if (document.attachEvent) {
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        } else {
            onBridgeReady();
        }
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));