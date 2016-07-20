/**
 * create by lijunfang
 */
var YG_ModityPassword = (function ($, M) {
    function Init() {
        Init_GetCode();
        Init_ValidPhoneFormSubmit();
        Init_UpdatePasswordFormSubmit();
    }

    // 提交更新新密码表单
    function Init_UpdatePasswordFormSubmit() {
        $('#UpdatePasswordForm').on('tap', '#UpdatePasswordFormSubmit', function (e) {
            $('#errorMsg').html('');
            var password = $('#password').val();
            var password2 = $('#password2').val();
            if (YG_Utils.Strings.IsBlank(password) || YG_Utils.Strings.IsBlank(password2)) {
                $('#errorMsg').html('两次密码均不能为空');
                return false;
            }
            if (password != password2) {
                $('#errorMsg').html('两次密码不一致');
                return false;
            }
            if (!YG_Utils.Valid.Password(password).status) {
                $("#errorMsg").html("错误提示：请设置6-18位，数字+字母的密码");
                return false;
            }
            $('#UpdatePasswordForm').submit();
        });
    }

    // 提交验证短信验证码表单
    function Init_ValidPhoneFormSubmit() {
        $('#validPhoneForm').on('tap', '#ValidFormSubmit', function (e) {
            if (YG_Utils.Strings.IsBlank($('#smsCode').val())) {
                $('#errorMsg').html('短信验证码不能为空');
                return;
            }
            $('#validPhoneForm').submit();
        });
    }

    // 获取短信验证码
    function Init_GetCode() {
        M('.code-wrap').on('tap', '.code-btn', function (e) {
            sendSMSCode();
            Code('.code-btn');
        });
        Code('.code-btn');
    }

    function Code(object) {
        var second = 60, $this = $(object);
        $this.attr('disabled', '');
        var codeSetInterval = setInterval(function () {
            if (second > 0) {
                second--;
                $this.html(second + '后重新获取');
            } else {
                $this.html('获取验证码');
                $this.removeAttr('disabled');
                clearInterval(codeSetInterval);
            }
        }, 1000);
    }

    function sendSMSCode() {
        $.ajax({
                   type: 'post',
                   url: rootPath + '/usercenter/moditypassword/sendSMSCode.sc',
                   dataType: 'json',
                   success: function (data) {
                       //服务器返回响应
                       if (data.success) {
                           YG_Utils.Notification.toast('验证码已发送');
                       } else {
                           if (data.code == 302) {
                               YG_Utils.Notification.error(data.message, function () {
                                   location.href = rootPath + '/usercenter/moditypassword.sc';
                               });
                           } else {
                               $("#errorMsg").html(data.message);
                           }
                       }
                   },
                   error: function (xhr, type, errorThrown) {
                       //异常处理；
                       alert(type);
                       $("#errorMsg").html('错误提示：系统异常!');
                   }
               });
    }

    $(function () {
        Init();
    });

    return {};
}(Zepto, mui));