<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl" />
    <title>优购微零售_店铺创建导航</title>
<#include "../../layouts/style.ftl"/>
    <style>
        html {
            height: 100%;
        }
    </style>
</head>
<body class="yg-ad-page">
<div class="viewport">
    <div class="mui-content">
        <input type="hidden" id="shopId" value="${shopId}">
        <img src="${context}/static/images/ad-img.png" alt="" class="ad-img"/>
        <br/>
        <a href="javascript:void(0)" class="ad-btn-free" id="btnCreate">开&nbsp;&nbsp;店</a>
        <div class="ad-next">
            <a class="iconfont" href="javascript:;">&#xe606;</a>
        </div>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
<script src="${context}/static/js/yg-create-shop-guide.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>
</html>