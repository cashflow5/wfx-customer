/**
 * create by guoran
 */
var YG_Login = (function ($, M) {
    function Init() {
        //监听图片验证码更换单击事件
        M('#nextImg').on('tap', '#reloadImg', function (e) {
            reloadImg();
        });
        //监听注册用户下一步单击事件
        M('#nextBtn').on('tap', '#next_a', function (e) {
            next();
        });
    }


    //重新加载图文验证码  
    function reloadImg() {
        document.getElementById("myimg").src = rootPath + "/getImageCode.sc?" + new Date().getTime();
    }

    //下一步
    function next() {
        var phone = $("#loginName").val();
        var imgCode = $("#imgCode").val();
        if (phone == "") {
            $("#errorMsg").html("错误提示：手机号码不能为空!");
            return false;
        }
        var isPhone = YG_Utils.Valid.Phone(phone).status;
        if (!isPhone) {
            $("#errorMsg").html("错误提示：手机号格式不正确!");
            return false;
        }

        if (imgCode == "") {
            $("#errorMsg").html("错误提示：验证码不能为空!");
            return false;
        }
        $("#registerForm").submit();
    }


    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));