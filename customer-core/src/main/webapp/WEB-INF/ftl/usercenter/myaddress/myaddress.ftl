<#-- @ftlvariable name="isPay" type="java.lang.Boolean" -->
<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="addresses" type="java.util.List<com.yougou.wfx.customer.model.usercenter.myaddress.AccountAddressVo>" -->
<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl">
    <title>优购微零售_收货地址管理</title>
<#include "../../layouts/style.ftl">
</head>
<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">收货地址管理</h1>
    </header>
    <div class="mui-content">
    <#list addresses as address>
        <section class="mt15">
            <ul class="mui-table-view address-detail-list" data-id="${(address.id)!''}">
                <li class="mui-table-view-cell isPay">
                    <div class="mui-row">
                        <div class="mui-col-sm-3 mui-col-xs-3 isPay">${(address.name)!''}</div>
                        <div class="mui-col-sm-9 mui-col-xs-9 isPay">${(address.phoneEncode)!''}</div>
                    </div>
                    <p class="mt5 isPay">${(address.province)!''} ${(address.city)!''} ${(address.district)!''} ${(address.address)!''}</p>
                </li>
                <li class="mui-table-view-cell ">
                    <div class="mui-row">
                        <div class="mui-col-sm-6 mui-col-xs-6">
                            <div class="yg-radio-checkbox">
										<span class="mui-radio">
											<input type="radio" name="defaultAddress" <#if (address.defaultAddress)!false>checked</#if>
                                                   value="${(address.id)!''}" />
										</span>
                                <label for="address1">默认地址</label>
                            </div>

                        </div>
                        <div class="mui-col-sm-6 mui-col-xs-6 tright">
                            <span data-id="${(address.id)!''}" class="editor-btn"><i class="icon iconfont">&#xe63e;</i> 编辑</span>
                            <span data-id="${(address.id)!''}" class="del-btn ml20"><i class="icon iconfont">&#xe61a;</i> 删除</span>
                        </div>
                    </div>
                </li>
            </ul>
        </section>
    </#list>
        <section class="pd10 mt5">
            <a href="${context}/usercenter/myaddress/create.sc" class="mui-btn mui-btn-danger mui-btn-block">+ 新建地址</a>
        </section>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
<script src="${context}/static/js/yg-my-address.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var errorMsg = '${errorMsg!''}';
    var isPay = ${isPay?string('true','false')};
</script>
</body>
</html>