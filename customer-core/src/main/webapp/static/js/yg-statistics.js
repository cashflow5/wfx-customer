/**
 * 优购数据统计,基础js
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/11 上午9:53
 * @since 1.0 Created by lipangeng on 16/5/11 上午9:53. Email:lipg@outlook.com.
 */
var YG_Statistics = (function ($, M) {
    // 初始化方法
    function Init() {
        Init_TodayVisit();
    }

    // 记录访问量,判断是不是今日第一次访问
    function Init_TodayVisit() {
        var _shopId = $('#shopId').val();
        if (YG_Utils.Strings.NotBlank(_shopId)) {
            var cookie = getCookie('visit:' + _shopId);
            if (cookie == null) {
                $.ajax({
                           type: 'post',
                           url: rootPath + '/statistics/addDayShopVisitCount.sc',
                           data: {shopId: _shopId},
                           dataType: 'json',
                           success: function (data, status, xhr) {
                               if (data == '' || !data.success) {
                                   M.alert('错误:' + data.message, '错误提示');
                                   return;
                               } else {
                                   setCookie('visit:' + _shopId, 'ok');
                               }
                           },
                           error: function (xhr, errorType, error) {
                           }
                       });
            }
        }
    }

    // 获取cookie
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

        if (arr = document.cookie.match(reg)) {
            return unescape(arr[2]);
        } else {
            return null;
        }
    }

    // 设置cookie
    function setCookie(name, value) {
        var exp = new Date();
        exp.setHours(23);
        exp.setMinutes(59);
        exp.setSeconds(59);
        exp.setMilliseconds(999);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString() + ";path=/";
    }

    // 删除cookie
    function delCookie(name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval = getCookie(name);
        if (cval != null) {
            document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
        }
    }

    // 初始化
    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));