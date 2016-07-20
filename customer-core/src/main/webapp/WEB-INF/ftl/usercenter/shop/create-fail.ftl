<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl" />
    <title>优购微零售_店铺创建失败</title>
<#include "../../layouts/style.ftl"/>
</head>
<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">店铺创建失败</h1>
    </header>
    <div class="mui-content">
        <section class="yg-floor pt10 mt20">
            <div class="yg-fail-success">
                <i class="iconfont Red">&#xe64e;</i>
            </div>
            <p class="Red tcenter f14 pb20">${(result.message)!''}</p>
        </section>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
</body>
</html>