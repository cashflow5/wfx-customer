<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl">
    <title>优购微零售_找回密码(设置新密码)</title>
<#include "../../layouts/style.ftl">
</head>
<body>
<div class="viewport yg-login">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">找回密码</h1>
    </header>
    <div class="mui-content bg-white">
        <form id="registerForm" class="mui-content-padded" method="post" action="${context}/reset_password/password.sc">
            <section class="pd10 mt20">
                <p><label>请设置新的登录密码</label></p>
                <input type="password" placeholder="6-18位，不能与旧密码相同" id="password" name="passWord" />
                <div class="error-msg" id="errorMsg1"></div>
                <input type="password" placeholder="请确认密码" id="password2" name="passWord2" />
                <div class="error-msg" id="errorMsg2">${(errorMsg!'')}</div>
                <a href="javascript:;" class="mui-btn mui-btn-danger mui-btn-block" id="next_done">完成</a>
            </section>
        </form>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
<script src="${context}/static/js/yg-reset-password.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>