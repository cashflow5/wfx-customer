<#-- @ftlvariable name="orderId" type="java.lang.String" -->
<#-- @ftlvariable name="payConfig" type="com.yougou.wfx.customer.model.weixin.order.WXJSPayVo" -->
<#-- @ftlvariable name="wxConfig" type="com.yougou.wfx.customer.model.weixin.config.WXConfigVo" -->
<!DOCTYPE html>
<html>

<head>
<#include "../layouts/meta.ftl" />
    <title>微信支付</title>
<#include "../layouts/style.ftl"/>
</head>

<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">微信支付</h1>
    </header>
    <div class="mui-content">
        <section class="yg-floor pt10 mt20">
            <div class="yg-fail-success">
                <i class="iconfont Green">&#xe64e;</i>
            </div>
            <p class="Green tcenter f14 pb20">正在跳转微信支付页面....</p>
        </section>
        <p class="blank10"></p>
        <div class="cen center Gray">
            Copyright &copy; 2011-2014 Yougou Technology Co., Ltd. <a href="http://www.miibeian.gov.cn" target="_blank" class="Gray">粤ICP备09070608号-4</a>
            增值电信业务经营许可证：<a href="http://www.miibeian.gov.cn" target="_blank" class="Gray" style="padding-left:0">粤 B2-20090203</a>
        </div>
    </div>
</div>
<#include "../layouts/corejs.ftl"/>
</body>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript" charset="utf-8"></script>
<script src="${context}/static/js/yg-weixin-paying.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var orderId = '${orderId!''}';
    var payConfig = {};
    payConfig.appId = '${(payConfig.appId)!''}';
    payConfig.timestamp = '${(payConfig.timeStamp)!''}';
    payConfig.nonceStr = '${(payConfig.nonceStr)!''}';
    payConfig.packages = '${(payConfig.packages)!''}';
    payConfig.signType = '${(payConfig.signType)!''}';
    payConfig.paySign = '${(payConfig.paySign)!''}';
</script>
<script>
</script>
</html>