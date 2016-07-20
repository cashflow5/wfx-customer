<!DOCTYPE html>
<html>
<head>
<#include "../layouts/meta.ftl" />
    <title>优购微零售_APP下载</title>
<#include "../layouts/style.ftl"/>
</head>
<body style="height: 100%;background: #f7f7f7;">
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
    </header>
    <div class="mui-content">
        <section class="f12 pd10">
            <p>温馨提示</p>
            <p>请下载优购微零售分销商App，装修店铺和上架商品，开启赚取高额佣金之旅！</p>
            <div class="yg-apk-download">
                <div class="download-cell">
                    <img class="title-img" src="${context}/static/images/adnroid-title.png" alt="" />
                    <br />
                    <img class="qrcode-img" src="${context}/static/images/android-mark.png" alt="" />
                </div>
                <div class="download-cell">
                    <img class="title-img" src="${context}/static/images/ios-title.png" alt="" />
                    <br />
                    <img class="qrcode-img" src="${context}/static/images/ios-mark.png" alt="" />
                </div>
            </div>
        </section>
    </div>
</div>
<#include "../layouts/corejs.ftl">
</body>
</html>