/**
 * create by lijunfang
 */
var YG_SetNewPassSuccess = (function ($, M) {

    function Init() {
        $('#nextImg').on('tap', '#reloadImg', function (e) {
            reloadImg();
        });
        // 提交表单,进入下一步验证手机验证码
        $('#registerForm').on('tap', '#next_sms', function (e) {
            next_sms();
        });
        //监听短信验证码
        $('.code-wrap').on('tap', '.code-btn', function (e) {
            if ($('.code-btn').attr('disabled') != 'true') {
                SendSMSCode();
                Code('.code-btn');
            }
        });
        //监听注册短信验证码页面下一步单击事件
        $('#registerForm').on('tap', '#next_password', function (e) {
            next_password();
        });

        $('#registerForm').on('tap', '#next_done', function (e) {
            next_done();
        });
        // 屏蔽获取验证码按钮
        Code('.code-btn');
    }

    //重新加载图文验证码
    function reloadImg() {
        document.getElementById("myimg").src = rootPath + "/getImageCode.sc?" + new Date().getTime();
    }

    // 提交表单,进入下一步手机验证码
    function next_sms() {
        $('#errorMsg').html('');
        var phone = $("#phoneNumber").val();
        var imgCode = $("#imageCode").val();
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

    //60秒倒计时获取短信验证码按钮屏蔽
    function Code(object) {
        var second = 60, $this = $(object);
        $this.attr('disabled', 'true');
        var codeSetInterval = setInterval(function () {
            if (second > 0) {
                second--;
                $this.html(second + '秒后重新获取');
            } else {
                $this.html('获取验证码');
                $this.removeAttr('disabled');
                clearInterval(codeSetInterval);
            }
        }, 1000);
    }

    //获取短信验证码
    function SendSMSCode() {
        //清除提示信息
        $("#errorMsg").html("");
        M.ajax(rootPath + '/reset_password/getSmsCode.sc', {
            data: {},
            dataType: 'json',//服务器返回json格式数据
            type: 'post', //HTTP请求类型
            success: function (data) {
                //服务器返回响应
                if (data.success) {
                    YG_Utils.Notification.toast('验证码已发送');
                } else {
                    if (data.code == 302) {
                        YG_Utils.Notification.error(data.message, function () {
                            location.href = rootPath + '/reset_password.sc';
                        });
                    } else {
                        $("#errorMsg").html(data.message);
                    }
                }
            },
            error: function (xhr, type, errorThrown) {
                //异常处理；
                $("#errorMsg").html('错误提示：系统异常!');
            }
        });
    }

    // 验证短信验证码
    function next_password() {
        var smsCode = $('#smsCode').val();
        if (smsCode == '') {
            $('#errorMsg').html('错误提示:短信验证码不能为空.');
            return;
        }
        $("#registerForm").submit();
    }

    // 完成设置密码
    function next_done() {
        $("#errorMsg1").html("");
        $("#errorMsg2").html("");
        var password = $("#password").val();
        var password2 = $("#password2").val();

        if (password == "") {
            $("#errorMsg1").html("错误提示：密码不能为空");
            return false;
        }
        if (password2 == "") {
            $("#errorMsg2").html("错误提示：密码不能为空");
            return false;
        }
        if (password != password2) {
            $("#errorMsg2").html("错误提示：密码不一致");
            return false;
        }
        if (!YG_Utils.Valid.Password(password).status) {
            $("#errorMsg1").html("错误提示：请设置6-18位，数字+字母的密码");
            return false;
        }
        $("#registerForm").submit();
    }


    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));