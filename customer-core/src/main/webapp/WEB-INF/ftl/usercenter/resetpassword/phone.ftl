<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="phoneNumber" type="java.lang.String" -->
<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl">
    <title>优购微零售_找回密码(验证码发送)</title>
<#include "../../layouts/style.ftl">
</head>
<body>
<div class="viewport yg-login">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">找回密码</h1>
    </header>
    <div class="mui-content bg-white">
        <form id="registerForm" class="mui-content-padded" method="post" action="${context}/reset_password/phone.sc">
            <section class="pd10 mt20 tcenter">
                <p>已发送<span class="Red"> 验证码短信 </span>至</p>
                <p>${phoneNumber!''}</p>
                <div class="mui-row code-wrap">
                    <div class="mui-col-sm-6 mui-col-xs-6">
                        <input id="smsCode" name="smsCode" type="text" placeholder="请输入验证码" />
                        <div class="error-msg" id="errorMsg">${errorMsg!''}</div>
                    </div>
                    <div class="mui-col-sm-6 mui-col-xs-6 tleft">
                        <button class="mui-btn code-btn ml10 mt5" id="smsCodeBtn">60秒后重新获取</button>
                    </div>
                </div>
            </section>
            <section class="pd10">
                <a href="javascript:;" class="mui-btn mui-btn-danger mui-btn-block" id="next_password">下一步</a>
            </section>
        </form>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
<script src="${context}/static/js/yg-reset-password.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>