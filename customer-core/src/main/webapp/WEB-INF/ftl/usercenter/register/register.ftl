
<#-- @ftlvariable name="phoneNumber" type="java.lang.String" -->
<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl" />
    <title>注册</title>  
<#include "../../layouts/style.ftl"/>
<style>
.yg-login #registerForm .error-msg{padding:3px 0px;}
</style>
</head>

<body>
<div class="viewport yg-login">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">注册</h1>
    </header>
    <div class="mui-content bg-white"><br/>
        <form id="registerForm" class="mui-content-padded" method="post" action="${context}/register/doRegister.sc">
            <div class="mui-input-row">
                <input type="text" id="loginName" name="loginName" placeholder="请输入手机号" value="${phoneNumber!''}">
                <div class="error-msg" id="errorMsg"></div>
            </div>
            
           <div class="mui-input-row mui-row">
                <div class="mui-col-sm-6 mui-col-xs-6">
                    <input type="text" id="imgCode" name="imgCode" placeholder="请输图片入验证码">
                    <div class="error-msg" id="errorMsgPic"></div>
                </div>
                <div class="mui-col-sm-6 mui-col-xs-6 tvcenter" id="nextImg">
                    <img src="${context}/getImageCode.sc" alt="" class="yg-ecode ml10" id="myimg" />
                    <a href="javascript:;" id="reloadImg" class="Blue ml10">换一张</a>
                </div>
            </div>
            <section class="">
                <#--<p>已发送<span class="Red"> 验证码短信 </span>至</p>
                <input type="hidden" value="${phoneNumber!''}" name="loginName" id="loginName">
                <p>${phoneNumber!''}</p>-->
                <div class="mui-row code-wrap">
                    <div class="mui-col-sm-6 mui-col-xs-6">
                        <input type="text" id="smsCode" name="smsCode" placeholder="请输入手机验证码" />
                        <div class="error-msg" id="errorMsg0"></div>
                    </div>
                    <div class="mui-col-sm-6 mui-col-xs-6 tleft">
                        <input class="mui-btn code-btn ml10 mt5" id="smsCodeBtn" value="获取验证码" type="button"/>
                    </div>
                </div>
            </section>
            <div class="mui-content bg-white">
            <section class="">
                <div>
                    <input type="password" placeholder="请设置6-18位，数字+字母密码" id="password" name="passWord" />
                    <div class="error-msg" id="errorMsg1"></div>
                </div>
                <div>
                    <input type="password" placeholder="请确认密码" id="password2" name="passWord2" />
                    <div class="error-msg" id="errorMsg2"></div>
                </div>
            </section>
        </div>
            <div class="protocol-info Gray">
                                                      注册即视为同意<a href="register/platform-protocol.sc">《<span class="Red">优购用户注册协议</span>》</a>
            </div>
            <div class="pt10" id="setPwdSection">
                    <a href="javascript:void(0);" id="setPwdNext" class="mui-btn mui-btn-danger mui-btn-block">注册</a>
             </div>
           <#-- <div class="mui-button-row pt0 mb15" id="nextBtn">
                <a type="a" id="next_a" class="mui-btn mui-btn-danger mui-btn-block mb0">下一步</a>
            </div>-->
        </form>
    </div>
</div>
<#include "../../layouts/corejs.ftl"/>
</body>
<#--<script src="${context}/static/js/yg-loginl.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script src="${context}/static/js/yg-register-code.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>-->
<script src="${context}/static/js/yg-register-new.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</html>