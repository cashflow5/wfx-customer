<#-- @ftlvariable name="payResVo" type="com.yougou.pay.vo.PayResVo" -->
<#-- @ftlvariable name="orderId" type="java.lang.String" -->
<!DOCTYPE html>
<html>

<head>
<#include "../layouts/meta.ftl" />
    <title>支付宝支付</title>
<#include "../layouts/style.ftl"/>
</head>

<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">支付宝支付</h1>
    </header>
    <div class="mui-content">
        <section class="yg-floor pt10 mt20">
            <div class="yg-fail-success">
                <i class="iconfont Green">&#xe64e;</i>
            </div>
            <p class="Green tcenter f14 pb20">正在跳转支付宝支付页面....</p>
        </section>
        <p class="blank10"></p>
        <div class="cen center Gray">
            Copyright &copy; 2011-2014 Yougou Technology Co., Ltd. <a href="http://www.miibeian.gov.cn" target="_blank" class="Gray">粤ICP备09070608号-4</a>
            增值电信业务经营许可证：<a href="http://www.miibeian.gov.cn" target="_blank" class="Gray" style="padding-left:0">粤 B2-20090203</a>
        </div>
        <form id="payForm" name="payForm" method="${(payResVo.actionMethod)!"get"}" action="${(payResVo.actionUrl)!'/'}">
        <#if payResVo?? && payResVo.reqParamMap??>
            <#list payResVo.reqParamMap?keys as key>
                <input type="hidden" id="${key}" name="${key}" value='${payResVo.reqParamMap[key]}' />
            </#list>
        </#if>
        </form>
    </div>
</div>
<#include "../layouts/corejs.ftl"/>
<script src="${context}/static/js/yg-alipay-paying.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>