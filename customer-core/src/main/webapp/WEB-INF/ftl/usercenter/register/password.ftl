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
    <div class="error-msg" id="errorMsg">${errorMsg!''}</div>
    <form id="registerForm" class="mui-content-padded" method="post" action="${context}/register/password.sc">
        <div class="mui-content bg-white">
            <section class="plr10">
                <div>
                    <input type="password" placeholder="请设置6-18位，数字+字母密码" id="password" name="passWord" />
                    <div class="error-msg" id="errorMsg1"></div>
                </div>
                <div>
                    <input type="password" placeholder="请确认密码" id="password2" name="passWord2" />
                    <div class="error-msg" id="errorMsg2"></div>
                </div>
                <div class="pt10" id="setPwdSection">
                    <a href="javascript:void(0);" id="setPwdNext" class="mui-btn mui-btn-danger mui-btn-block">注册</a>
                </div>
            </section>
        </div>
    </form>
</div>
<#include "../../layouts/corejs.ftl"/>
<script src="${context}/static/js/yg-register-code.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>