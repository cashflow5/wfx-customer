/**
 * 常用工具类
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/6 下午4:42
 * @since 1.0 Created by lipangeng on 16/4/6 下午4:42. Email:lipg@outlook.com.
 */
var YG_Utils = (function ($, M) {
    return {
        // 验证工具
        Valid: {
            Phone: function (phone) {
                var status = true;
                var message = '';
                var regPhone = /^(13[0-9]|15[012356789]|17[05678]|18[0-9]|14[57])[0-9]{8}$/;
                status = regPhone.test(phone);
                if (!status) {
                    message = '手机号效验失败,请检查手机号格式.';
                }
                return {status: status, message: message};
            },
            Password: function (pass) {
                var status = true;
                var message = '';
                var pwdReg = /(?!^\d+$)(?!^[a-zA-Z]+$)^[0-9a-zA-Z]{6,18}$/g;
                if ((!pwdReg.test(pass))) {
                    status = false;
                    message = '密码验证失败,请设置6-18位，数字+字母的密码';
                }
                return {status: status, message: message};
            }
        },
        Notification: {
            toast: function (msg) {
                if (YG_Utils.Strings.NotBlank(msg)) {
                    M.toast(msg);
                }
            },
            error: function (msg) {
                if (YG_Utils.Strings.NotBlank(msg)) {
                    M.alert(msg, '错误提示');
                }
            },
            error: function (msg, fn) {
                if (YG_Utils.Strings.NotBlank(msg)) {
                    if (typeof (fn) == 'function') {
                        M.alert(msg, '错误提示', fn());
                    } else {
                        M.alert(msg, '错误提示');
                    }
                }
            }
        },
        Strings: {
            /** @return {boolean} */
            NotBlank: function (string) {
                return string != undefined && string != '';
            },
            /** @return {boolean} */
            IsBlank: function (string) {
                return string == undefined || $.trim(string) == '';
            }
        },
        Url: {
            getParams: function () {
                var _url = location.search;
                var _request = {};
                if (_url.indexOf("?") != -1) {
                    var str = _url.substr(1);
                    var strs = str.split("&");
                    for (var i = 0; i < strs.length; i++) {
                        _request[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
                    }
                }
                return _request;
            }
        },
        WeiXin: {
            checkBrower: function () {
                var ua = navigator.userAgent.toLowerCase();
                if (ua.match(/MicroMessenger/i) == "micromessenger") {
                    return true;
                } else {
                    return false;
                }
            }
        },
        Cookie: {},
        Form: {
            // 提交表单工具,form是用$()包装过的对象,同步提交表单,防止相同表单数据被多次提交
            syncSubmit: function (form, btn) {
                var _disabledCss = 'mui-disabled';
                if (btn.hasClass(_disabledCss)) {
                    return;
                }
                form.submit();
                btn.addClass(_disabledCss);
                if (form.data('changedEvent') == undefined) {
                    form.on('change', 'input,select,textarea', function () {
                        btn.removeClass(_disabledCss);
                    });
                    form.data('changedEvent', true);
                }
            }
        },
        Layer: {
            show: function () {
                layer.open({type: 2});
            },
            closeAll: function () {
                layer.closeAll();
            }
        }
    };
}(Zepto, mui));