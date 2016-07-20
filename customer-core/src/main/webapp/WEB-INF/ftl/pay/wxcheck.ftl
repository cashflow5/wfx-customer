<#-- @ftlvariable name="authorizeBaseUrl" type="java.lang.String" -->
<#-- @ftlvariable name="order" type="com.yougou.wfx.customer.model.order.OrderVo" -->
<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_微信支付</title>
<#include "../layouts/style.ftl">
</head>
<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">微信支付</h1>
    </header>
    <div class="mui-content">
        <section class="yg-floor pt10 mt20">
            <p class="f16 mt10 pb20" id="message">正在进行支付环境认证....</p>
        </section>
        <section class="plr10 mt20 pt5">
            <div class="mui-row pr10">
                <div class="mui-col-sm-12 mui-col-xs-12">
                    <a href="${context!''}/index.sc" class="mui-btn w100p ml10 mui-col-sm-6 mui-col-xs-6">继续逛逛</a>
                </div>
            </div>
        </section>
    </div>
</div>
<#include "../layouts/corejs.ftl">
<script src="${context}/static/js/yg-weixin-check.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var authorizeBaseUrl = '${authorizeBaseUrl!''}';
</script>
</body>
</html>