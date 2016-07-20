<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl">
    <title>优购微零售_修改密码</title>
<#include "../../layouts/style.ftl">
</head>
<body>
<div class="viewport yg-login">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">修改密码</h1>
    </header>
    <div class="mui-content">
        <form id="UpdatePasswordForm" action="${context}/usercenter/moditypassword/modityPassword.sc" method="post">
            <section class="pd10 border-top">
                <p><label>请设置新的登录密码</label></p>
                <input id="password" name="password" type="password" placeholder="6-18位，不能与旧密码相同" />
                <input id="password2" name="password2" type="password" placeholder="请确认密码" />
                <div class="error-msg" id="errorMsg">${errorMsg!''}</div>
                <a href="javascript:;" class="mui-btn mui-btn-danger mui-btn-block" id="UpdatePasswordFormSubmit">完成</a>
            </section>
        </form>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
<script src="${context}/static/js/yg-modity-password.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>