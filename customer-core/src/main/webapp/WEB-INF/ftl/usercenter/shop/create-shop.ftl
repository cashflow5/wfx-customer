<!DOCTYPE html>
<html>

<head>
<#include "../../layouts/meta.ftl" />
    <title>优购微零售_创建店铺</title>
<#include "../../layouts/style.ftl"/>
</head>

<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">创建店铺</h1>
    </header>
    <div class="mui-content yg-create-shop">
        <#--<input type="file" name="" id="userPhoto" style="display: none;"/>-->
        <section class="user-info-box">
            <div class="user-photo">
                <img src="${imgurl!''}" alt=""/>
            </div>
            <div class="edit-box" id="chooseImage">
                <#--<a class="edit-block">-->
                    <#--<i class="iconfont">&#xe63e;</i>-->
                <#--</a>-->
            </div>
        </section>
        <form class="mui-content-padded yg-validate" action="${context}/${shopId!''}/create.sc" method="post">
            <section class="yg-floor mt10">
               <#-- <div class="mui-input-row">
                    <input type="text" name="name" placeholder="请输入店铺名称（建议少于15个字）" value="${(shopVo.name?trim)!''}">
                    <div class="error-msg">${errorMsg_name!''}</div>
                </div>-->
                <div class="mui-input-row">
                    <input type="text" name="contact" placeholder="联系人真实姓名" value="${(shopVo.contact?trim)!''}">
                    <div class="error-msg">${errorMsg_contact!''}</div>
                </div>
            </section>
            <section class="pl20 pr20 pt20">
                <button class="mui-btn mui-btn-danger mui-btn-block">完成</button>
            </section>
        </form>

    </div>
</div>
<#include "../../layouts/corejs.ftl">
</body>

</html>