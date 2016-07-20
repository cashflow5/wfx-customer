<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="passWord" type="java.lang.String" -->
<#-- @ftlvariable name="userName" type="java.lang.String" -->
<#-- @ftlvariable name="errors" type="String" -->
<!DOCTYPE html>
<html>

<head>
<#include "../layouts/meta.ftl" />
    <title>登录</title>
<#include "../layouts/style.ftl"/>
</head>

<body>
<div class="viewport yg-login">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">登录</h1>
    </header>
    <div class="mui-content bg-white">
        <form class="mui-content-padded" method="post" id="loginForm" action="${context}/login.sc">

            <div class="error-msg" id="errorMsg">${(errorMsg)!''}</div>
            <div class="mui-input-row">
                <input type="text" id="loginName" name="loginName" placeholder="请输入注册手机号" value="${userName!''}">
            </div>
            <div class="mui-input-row mui-password">
                <input type="password"
                       name="loginPassword"
                       id="loginPassword"
                       class="mui-input-password"
                       placeholder="请输入密码"
                       value="${passWord!''}">
            </div>
            <div class="yg-radio-checkbox mb15">
						<span class="mui-checkbox">
							<input name="saveLogin" id="saveLogin" value="1" type="checkbox" <#if isAutoLogin??>checked</#if> />
						</span>
                <label>一个月内免登录</label>
            </div>
            <div class="mui-button-row pt0 mb15" id="loginDiv">
                <a class="mui-btn mui-btn-danger mui-btn-block mb0" id="loginBtn">登录</a>
            </div>
            <div class="mui-button-row mui-row pt0">
                <div class="mui-col-sm-6 mui-col-xs-6 tleft"><a href="${context}/register.sc" class="mui-btn">快速注册</a></div>
                <div class="mui-col-sm-6 mui-col-xs-6 tright"><a href="${context}/reset_password.sc" class="mui-btn
                mui-btn-link">找回密码</a></div>
            </div>
            <#if running_environment == '1'>
            <#--<div class="mui-button-row mui-row pt0">-->
               <#--<div class="mui-col-sm-6 mui-col-xs-6 tleft">-->
	                                         <#--       第三方快捷登陆：-->
	               <#--<a id="wxLogin" href="${context}/login-wx.sc" class="mui-icon mui-icon-weixin"></a>-->
	           <#--</div>    -->
           	<#--</div>-->
           	</#if>
        </form>
    </div>
</div>
<#include "../layouts/corejs.ftl"/>
</body>
<script src="${context}/static/js/yg-login.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>

</html>