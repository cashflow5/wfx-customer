<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl" />
    <title>优购微零售_店铺创建成功</title>
<#include "../../layouts/style.ftl"/>
</head>
<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left" data-href="${context}/${shopId!''}.sc?flag=1"></a>
        <h1 class="mui-title">店铺创建成功</h1>
    </header>
    <div class="mui-content">
        <section class="yg-floor pt10 mt20">
            <div class="yg-fail-success">
                <i class="iconfont Green">&#xe64e;</i>
            </div>
            <p class="Green tcenter f14 pb20">${(result.message)!''}</p>
        </section>
        <section class="f12 pd10">
            <p>温馨提示</p>
            <p>请下载优购微零售分销商App，装修店铺和上架商品，开启赚取高额佣金之旅！</p>
            <a class="mui-btn mui-btn-danger mui-btn-block" href="${context}/agent.sc">下载优购微零售分销商App</a>
            <div class="yg-apk-download">
                <div class="download-cell">
                    <img class="title-img" src="${context}/static/images/adnroid-title.png" alt="" />
                    <br />
                    <img class="qrcode-img" src="${context}/static/images/adndroid_300_300.png" alt="" />
                </div>
            </div>
        </section>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
</body>
</html>