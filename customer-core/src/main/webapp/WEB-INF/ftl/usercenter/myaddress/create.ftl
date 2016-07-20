<#-- @ftlvariable name="isPay" type="java.lang.Boolean" -->
<#-- @ftlvariable name="address" type="com.yougou.wfx.customer.model.usercenter.myaddress.form.AddAccountAddressFormVo" -->
<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl">
    <title>优购微零售_新建收货地址</title>
<#include "../../layouts/style.ftl">
    <link href="${context}/static/css/mui.picker.css?v=${static_version}" rel="stylesheet" />
    <link href="${context}/static/css/mui.poppicker.css?v=${static_version}" rel="stylesheet" />
    <style type="text/css">
        .yg-city-right {
            position: absolute;
            right: 0;
            top: 10px;
        }
    </style>
</head>
<body>
<div class="viewport yg-add-address">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">新建收货地址</h1>
    </header>
    <div class="mui-content">
        <section>
            <form class="mui-input-group" id="addressForm" action="${context}/usercenter/myaddress/create.sc" method="post">
                <div class="mui-input-row">
                    <label class="Gray">收货人：</label>
                    <input id="name" name="name" type="text" value="${(address.name)!''}" />
                </div>
                <div class="mui-input-row">
                    <label class="Gray">手机号码：</label>
                    <input id="phone" name="phone" type="text" value="${(address.phone)!''}" />
                </div>
                <div class="mui-input-row">
                    <label class="Gray">所在地区：</label>
                    <input type="text" id="showCityPickerThree" readonly
                           value="${(address.province)!''} ${(address.city)!''} ${(address.district)!''}" />
                    <input type="text" id="province" name="province" value="${(address.province)!''}" hidden />
                    <input type="text" id="city" name="city" value="${(address.city)!''}" hidden />
                    <input type="text" id="district" name="district" value="${(address.district)!''}" hidden />
                    <i id="showCityPickerThree2" class="iconfont fr yg-city-right">&#xe60d;</i>
                </div>
                <div class="mui-input-row">
                    <label class="Gray">详情地址：</label>
                    <input id="address" name="address" type="text" value="${(address.address)!''}" />
                </div>
                <div class="yg-floor ptb10 border-bottom">
                    <div class="mui-row">
                        <div class="mui-col-sm-10 mui-col-xs-10">
                            <div class="Gray">设置为默认地址</div>
                            <span class="Gray">注：每次下单时会默认使用该地址</span>
                        </div>
                        <div class="mui-col-sm-2 mui-col-xs-2">
                            <div class="mui-switch mui-switch-mini fr mt5" id="defaultAddressSwitch">
                                <div class="mui-switch-handle"></div>
                            </div>
                        </div>
                    </div>
                    <input hidden
                           name="defaultAddress"
                           id="defaultAddress"
                           type="checkbox"
                           <#if (address.defaultAddress)!false>checked</#if> />
                </div>
                <section class="yg-floor mt10">
                    <a class="mui-btn mui-btn-danger mui-btn-block" id="submitAddressForm"><#if (isPay!false)>保存并使用<#else >保存</#if></a>
                </section>
            </form>
        </section>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
<script src="${context}/static/js/plugins/mui.picker.js?v=${static_version}"></script>
<script src="${context}/static/js/plugins/mui.poppicker.js?v=${static_version}"></script>
<script src="${context}/static/js/yg-city-data.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script src="${context}/static/js/yg-my-address-create.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var errorMsg = '${errorMsg!''}';
</script>
</body>
</html>