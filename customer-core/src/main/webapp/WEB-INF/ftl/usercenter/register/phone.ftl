<#-- @ftlvariable name="phoneNumber" type="java.lang.String" -->
<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl" />
    <title>注册</title>
<#include "../../layouts/style.ftl"/>
</head>
<body>
<div class="viewport yg-login">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">注册</h1>
    </header>
    <div class="mui-content bg-white">
        <form id="registerForm" class="mui-content-padded" method="post" action="${context}/register/phone.sc">
            <section class="pd10 mt20 tcenter">
                <p>已发送<span class="Red"> 验证码短信 </span>至</p>
                <input type="hidden" value="${phoneNumber!''}" name="loginName" id="loginName">
                <p>${phoneNumber!''}</p>
                <div class="mui-row code-wrap">
                    <div class="mui-col-sm-6 mui-col-xs-6">
                        <input type="text" id="smsCode" name="smsCode" placeholder="请输入验证码" />
                        <div class="error-msg" id="errorMsg">${errorMsg!''}</div>
                    </div>
                    <div class="mui-col-sm-6 mui-col-xs-6 tleft">
                        <button class="mui-btn code-btn ml10 mt5" id="smsCodeBtn">60秒后重新获取</button>
                    </div>
                </div>
            </section>
            <section class="pd10" id="next_re_btn">
                <a href="javascript:void(0)" class="mui-btn mui-btn-danger mui-btn-block" id="next_btn">下一步</a>
            </section>
        </form>
    </div>
</div>
<#include "../../layouts/corejs.ftl"/>
<script src="${context}/static/js/yg-register-code.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>