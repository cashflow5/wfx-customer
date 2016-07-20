<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="orderRefund" type="com.yougou.wfx.customer.model.order.refund.OrderRefundVo" -->
<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl">
    <title>优购微零售_退款详情(${(orderRefund.statusDesc)!''})</title>
<#include "../../layouts/style.ftl">
</head>
<body>
<div class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">退款详情</h1>
    </header>
    <div class="mui-content">
        <section class="yg-box <#if (orderRefund.canCancel)!false||(orderRefund.canEdit)!false>yg-two-layout-btn</#if>">
            <div>
                <p><span class="Gray">退款状态：</span>
                <#--退款成功的显示为绿色字体-->
                <#if ((orderRefund.status)!'')=='SUCCESS_REFUND'>
                    <span class="Green">${(orderRefund.statusDesc)!''}</span>
                <#else >
                    <span class="Red">${(orderRefund.statusDesc)!''}</span>
                </#if>
                </p>
                <p><span class="Gray">金额退回到：</span>原路返回至您的微信或支付宝账户</p>
            <#--如果退款仅仅有取消退款按钮,则表示是尚未发货的订单.-->
            <#if ((orderRefund.refundType)!'')=='ONLY_REFUND' && ((orderRefund.status)!'')!='SUCCESS_REFUND'>
                <p><span class="Gray">温馨提示：</span>如果卖家发货,申请将关闭,关闭后您可再次申请退款</p>
            <#elseif ((orderRefund.status)!'')=='REJECT_REFUND'||((orderRefund.status)!'-1')=='CLOSE_REFUND'>
                <p><span class="Gray">退款拒绝原因：</span>${(orderRefund.denyReason)!''}</p>
            <#elseif ((orderRefund.status)!'')=='SUCCESS_REFUND'>
                <p><span class="Gray">退款时间：</span><#if (orderRefund.payTime)??>${(orderRefund.payTime)?datetime}</#if></p>
            </#if>
            </div>
        <#if (orderRefund.canCancel)!false>
            <#if (orderRefund.canEdit)!false>
                <div class="mui-row mt10">
                    <div class="mui-col-sm-6 mui-col-xs-6">
                        <a class="mui-btn edit-btn"
                           data-refundno="${(orderRefund.refundNo)!''}"
                           data-refundtype="${(orderRefund.refundType)!''}">修改退款申请</a>
                    </div>
                    <div class="mui-col-sm-6 mui-col-xs-6">
                        <a class="cancel-btn mui-btn"
                           data-refundno="${(orderRefund.refundNo)!''}">取消退款申请</a>
                    </div>
                </div>
            <#else >
                <a class="cancel-btn mui-btn w100p"
                   data-refundno="${(orderRefund.refundNo)!''}">取消退款申请</a>
            </#if>
        </#if>
        </section>
        <section class="mt15">
            <ul class="mui-table-view">
                <li class="mui-table-view-cell">
                    <a class="mui-navigate-right"><span class="Gray pr10">店铺编码</span><#if (orderRefund.shopCode)=='优购微零售总店'>${(orderRefund.shopCode)!''}<#else>No.${(orderRefund.shopCode)!''}</#if></a>
                </li>
                <li class="mui-table-view-cell">
                    <a class="mui-navigate-right"><span class="Gray pr10">退款类型</span>${(orderRefund.refundTypeDesc)!''}</a>
                </li>
                <li class="mui-table-view-cell">
                    <a class="mui-navigate-right"><span class="Gray pr10">退款金额</span>${((orderRefund.refundFee)!0)?string('0.00')}元</a>
                </li>
                <li class="mui-table-view-cell">
                    <a class="mui-navigate-right"><span class="Gray pr10">退款原因</span>${(orderRefund.reason)!''}</a>
                </li>
                <li class="mui-table-view-cell">
                    <a class="mui-navigate-right"><span class="Gray pr10">退款说明</span>${(orderRefund.description)!''}</a>
                </li>
                <li class="mui-table-view-cell">
                    <a class="mui-navigate-right"><span class="Gray pr10">退款编号</span>${(orderRefund.refundNo)!""}</a>
                </li>
                <li class="mui-table-view-cell">
                    <a class="mui-navigate-right"><span class="Gray pr10">申请时间</span>${((orderRefund.createTime)!.now)?datetime}</a>
                </li>
            </ul>
        </section>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
<script src="${context}/static/js/yg-refund-details.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var errorMsg = '${errorMsg!''}';
</script>
</body>
</html>