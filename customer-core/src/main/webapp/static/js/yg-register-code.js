/**
 * create by lijunfang
 */
var YG_RegisterCode = (function ($, M) {
    function Init() {
        //监听发送短信验证码
        GetCode();
        //监听注册短信验证码页面下一步单击事件
        $('#next_re_btn').on('tap', '#next_btn', function (e) {
            registerSMSCodeValid();
        });
        //监听设置密码页面下一步单击事件
        $('#setPwdSection').on('tap', '#setPwdNext', function (e) {
            register();
        });
    }

    // 监听短信验证码
    function GetCode() {
        //监听短信验证码
        $('.code-wrap').on('tap', '.code-btn', function (e) {
            if ($('.code-btn').attr('disabled') != 'true') {
                sendSMSCode();
                Code('.code-btn');
            }
        });
        Code('.code-btn');
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
    function sendSMSCode() {
        //清除提示信息
        $("#errorMsg").html("");
        $.ajax(rootPath + '/register/getSmsCode.sc', {
            data: {},
            dataType: 'json',//服务器返回json格式数据
            type: 'post', //HTTP请求类型
            success: function (data) {
                //服务器返回响应
                if (!data.success) {
                    if (data.code == 302) {
                        YG_Utils.Notification.error(data.message, function () {
                            location.href = rootPath + '/register.sc';
                        });
                    } else {
                        $("#errorMsg").html(data.message);
                    }
                } else {
                    YG_Utils.Notification.toast('验证码已发送');
                }
            },
            error: function (xhr, type, errorThrown) {
                //异常处理；
                $("#errorMsg").html('错误提示：系统异常!');
            }
        });
    }

    // 验证短信验证码
    function registerSMSCodeValid() {
        var smsCode = $('#smsCode').val();
        if (smsCode == '') {
            $('#errorMsg').html('错误提示:短信验证码不能为空.');
            return;
        }
        $("#registerForm").submit();
    }

    //设置密码下一步，完成用户注册
    function register() {
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
            $("#errorMsg").html("错误提示：请设置6-18位，数字+字母的密码");
            return false;
        }
        $("#registerForm").submit();
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));