<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="isNotDeliverd" type="java.lang.Boolean" -->
<#-- @ftlvariable name="refundForm" type="com.yougou.wfx.customer.model.order.refund.form.OrderRefundFormVo" -->
<#-- @ftlvariable name="orderDetail" type="com.yougou.wfx.customer.model.order.OrderDetailVo" -->
<#-- @ftlvariable name="order" type="com.yougou.wfx.customer.model.order.OrderVo" -->
<#-- @ftlvariable name="orderRefund" type="com.yougou.wfx.customer.model.order.refund.OrderRefundVo" -->
<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl">
    <title>优购微零售_申请退款</title>
<#include "../../layouts/style.ftl">
</head>
<body>
<div id="pageShippedTightRefund" class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">申请退款</h1>
    </header>
    <div class="mui-content">
        <form id="refundForm" action="${context}/order/${(order.id)!''}/${(orderDetail.id)!''}/refund.sc" method="post">
        <#if ((order.status)!'')=='WAIT_DELIVER' || !((orderRefund.delivered)!false)>
            <input hidden name="refundType" id="refundType" value='ONLY_REFUND' />
        <#else >
            <input hidden name="refundType" id="refundType" value='DELIVERD_REFUND' />
            <section class="plr20 border-top">
                <p class="Gray pt10">请选择退款类型</p>
            </section>
            <section class="yg-box yg-two-layout-btn yg-btn-straight">
                <div class="mui-row mt10 return-type-wrap">
                    <div class="mui-col-sm-6 mui-col-xs-6">
                        <a href="javascript:;" class="mui-btn return-type-btn return-goods" data-orderid="${(order.id)!''}"
                           data-orderdetailid="${(orderDetail.id)}">退货退款</a>
                    </div>
                    <div class="mui-col-sm-6 mui-col-xs-6">
                        <a href="javascript:;" class="mui-btn selected return-type-btn only-refund" data-orderid="${(order.id)!''}"
                           data-orderdetailid="${(orderDetail.id)}">仅退款</a>
                    </div>
                </div>
            </section>
        </#if>
            <section class="plr20">
                <p><strong class="Red">*</strong>退款原因</p>
            <#--根据订单是否已经发货,显示不同的内容-->
            <#--如果是仅退款订单,则退款金额不可修改-->
            <#if ((order.status)!'')=='WAIT_DELIVER' || !((orderRefund.delivered)!false)>
                <select name="reason" id="reason">
                    <option value="">退款原因</option>
                    <option>不想要了</option>
                    <option>拍错了/订单信息有误</option>
                    <option>七天无理由退换货</option>
                    <option>未按约定时间发货</option>
                </select>
                <p><strong class="Red">*</strong>退款金额<span class="ml10 Gray">（不可修改）</span></p>
                <input id="refundFee"
                       name="refundFee"
                       type="text"
                       placeholder="${((orderRefund.canReturnFee)!0)?string('0.00')}"
                       value="${((orderRefund.canReturnFee)!0)?string('0.00')}"
                       readonly />
            <#--如果是已发货订单,则退款金额可修改-->
            <#else >
                <select id="reason" name="reason">
                    <option value="">退款原因</option>
                    <option>七天无理由退换货</option>
                    <option>收到商品破损</option>
                    <option>少件/漏发</option>
                    <option>收到商品与描述不符</option>
                    <option>商品质量问题</option>
                    <option>未收到货</option>
                    <option>未按约定时间发货</option>
                </select>
                <p><strong class="Red">*</strong>退款金额<span class="ml10 Gray">(最多可退 ${((orderRefund.canReturnFee)!0)?string('0.00')}元)
                </span></p>
                <input id="refundFee"
                       name="refundFee"
                       type="text"
                       placeholder="${((refundForm.refundFee)!0)?string('0.00')}"
                       value="${((refundForm.refundFee)!0)?string('0.00')}" />
            </#if>
                <p>退款说明 <span class="ml10 Gray">（可不填）</span></p>
                <textarea name="description" id="description" rows="5" placeholder="0-200字">${(refundForm.description)!''}</textarea>
            </section>
            <a href="javascript:;" class="mui-btn mui-btn-danger mui-btn-block" id="submitRefundForm">提交申请</a>
        </form>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
<script src="${context}/static/js/yg-order-refund.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    var refundFeeMax = ${((orderRefund.canReturnFee)!0)?string('0.00')};
    var errorMsg = '${errorMsg!''}';
    var isEditPage = false;
</script>
</body>
</html>