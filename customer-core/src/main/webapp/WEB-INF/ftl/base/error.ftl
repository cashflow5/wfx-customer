<!DOCTYPE html>
<html>

<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售-错误页面</title>
<#include "../layouts/style.ftl">
    <style>
        html {
            height: 100%;
        }

        body, .mui-content {
            background-color: #dcf3fd !important;
            text-align: center;
        }

        .img404 {
            width: 200px;
        }

        .tip-msg {
            padding: 30px 50px;
            max-height: 123px;
            overflow: hidden;
            color: #8D9095;
        }
    </style>
</head>

<body>
<div id="pageIndex" class="viewport">
    <header class="mui-bar yg-header">
        <a class="mui-pull-left header-logo" hreft="javascript:;"></a>
    </header>
    <div class="mui-content">
        <img src="${context}/static/images/404.png" alt="" class="img404"/>
        <div class="tip-msg" data-url="${url!''}">
        ${errorMsg!''}
        </div>
    </div>
</div>
</div>
<#include "../layouts/corejs.ftl"/>
<script src="${context}/static/js/yg-error.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>