var YG_Login = (function ($, M) {
    $(function () {
        Init();
    });
    /** 初始化 */
    function Init() {
        // 检查是否可以自动登录
        autoLogin();
        //监听登录按钮单击事件
        M('#loginDiv').on('tap', '#loginBtn', function (e) {
            if (checkValue()) {
                $("#loginForm").submit();
            }
        });
    }

    function autoLogin() {
        var arr, reg = new RegExp("(^| )YG_REMEMBER_ME=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg)) {
            YG_Utils.Notification.toast('即将自动登录');
            setTimeout(function () {
                $("#loginForm").submit();
            }, 500);
        }
    }

    /** 检查内容是否可以提交 */
    function checkValue() {
        var loginName = $("#loginName").val();    //账号
        var loginPassword = $("#loginPassword").val();    //密码
        var saveLogin = $("#saveLogin").attr("checked"); //一个月内免登陆
        if (loginName.length > 0 && loginPassword.length > 0) {
            return true;
        } else {
            $("#errorMsg").html("用户名或密码不能为空");
            return false;
        }
    }

    return {};
}(Zepto, mui));