/**
 *
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/9 下午4:03
 * @since 1.0 Created by lipangeng on 16/5/9 下午4:03. Email:lipg@outlook.com.
 */
var YG_WeiXin_Check = (function ($, M) {
    function Init() {
        if (!YG_Utils.WeiXin.checkBrower()) {
            $('#message').html('订单已生成，请在微信端登录优购微零售，在“我的订单”中完成支付。');
        } else {
            location.href = authorizeBaseUrl;
        }
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));