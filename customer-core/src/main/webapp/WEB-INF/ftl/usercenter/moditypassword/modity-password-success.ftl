<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl">
    <title>优购微零售_修改密码（设置新密码成功）</title>
<#include "../../layouts/style.ftl">
</head>
<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">修改密码</h1>
    </header>
    <div class="mui-content">
        <section class="yg-floor pt10 mt20">
            <div class="yg-fail-success">
                <i class="iconfont Green">&#xe64e;</i>
            </div>
            <div class="Green tcenter f14 mb3">重新设置密码成功</div>
            <p id="returnTip" class="Gray tcenter f14 pb20">倒计时3秒自动返回登录页</p>
        </section>
        <section class="pd10">
            <a href="${context}/login.sc" class="mui-btn mui-btn-danger mui-btn-block">立即返回登录页</a>
        </section>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
<script src="${context}/static/js/yg-modity-pass-success.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>