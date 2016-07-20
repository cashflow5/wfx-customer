<#-- @ftlvariable name="phoneNumber" type="java.lang.String" -->
<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl">
    <title>优购微零售_修改密码(验证手机)</title>
<#include "../../layouts/style.ftl">
</head>
<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">修改密码</h1>
    </header>
    <div class="mui-content">
        <section class="pd10 border-top">
            <p class="Gray">为了保障您的账户安全，请验证一下手机号，并确保该手机号为本人所有。</p>
        </section>
        <section class="yg-floor">
            <p class="tcenter pt10">您的绑定手机号</p>
            <p class="tcenter">${phoneNumber!'无法获取手机号'}</p>
        </section>
        <section class="pd10">
            <a href="${context}/usercenter/moditypassword/validPhone.sc" class="mui-btn mui-btn-danger mui-btn-block">验证身份</a>
        </section>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
</body>
</html>