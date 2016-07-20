<#-- @ftlvariable name="phoneNumber" type="java.lang.String" -->
<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl" />
    <title>优购微零售_找回密码</title>
<#include "../../layouts/style.ftl"/>
</head>
<body>
<div class="viewport yg-login">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">找回密码</h1>
    </header>
    <div class="mui-content bg-white">
        <form id="registerForm" class="mui-content-padded" method="post" action="${context}/reset_password.sc">
            <section class="plr10">
                <div class="error-msg" id="errorMsg">${errorMsg!''}</div>
                <input id="phoneNumber" type="text" name="phoneNumber" placeholder="请输入绑定手机号" value="${(phoneNumber!'')}" />
                <div class="mui-row code-wrap">
                    <div class="mui-col-sm-6 mui-col-xs-6">
                        <input type="text" id="imageCode" name="imageCode" placeholder="请输入验证码" />
                    </div>
                    <div class="mui-col-sm-6 mui-col-xs-6 tvcenter" id="nextImg">
                        <img id="myimg" class="yg-ecode ml10" src="${context}/getImageCode.sc" />
                        <a id="reloadImg" href="javascript:;" class="Blue ml10">换一张</a>
                    </div>
                </div>
            </section>
            <section class="pd10">
                <a href="javascript:;" class="mui-btn mui-btn-danger mui-btn-block" id="next_sms">下一步</a>
            </section>
        </form>
    </div>
</div>
<#include "../../layouts/corejs.ftl"/>
<script src="${context}/static/js/yg-reset-password.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>