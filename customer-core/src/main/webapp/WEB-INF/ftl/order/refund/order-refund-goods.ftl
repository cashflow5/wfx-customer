<#-- @ftlvariable name="errorMsg" type="java.lang.String" -->
<#-- @ftlvariable name="orderReturnGoodsForm" type="com.yougou.wfx.customer.model.order.refund.form.OrderReturnGoodsFormVo" -->
<#-- @ftlvariable name="expresses" type="java.util.List<com.yougou.wfx.customer.model.order.refund.OrderRefundExpressVo>" -->
<#-- @ftlvariable name="backAddress" type="com.yougou.wfx.customer.model.order.refund.OrderRefundAddressVo" -->
<#-- @ftlvariable name="orderDetail" type="com.yougou.wfx.customer.model.order.OrderDetailVo" -->
<#-- @ftlvariable name="order" type="com.yougou.wfx.customer.model.order.OrderVo" -->
<#-- @ftlvariable name="orderRefund" type="com.yougou.wfx.customer.model.order.refund.OrderRefundVo" -->
<!DOCTYPE html>
<html>
<head>
<#include "../../layouts/meta.ftl">
    <title>优购微零售_申请退货</title>
<#include "../../layouts/style.ftl">
</head>
<body>
<div id="pageShippedTightRefund" class="viewport">
    <header class="mui-bar yg-header header-ext">
        <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">申请退货</h1>
    </header>
    <div class="mui-content">
        <form id="returnGoodsForm" action="${context}/order/${(order.id)!''}/${(orderDetail.id)!''}/returnGoods.sc" method="post">
            <input name="refundType" id="refundType" value="REJECTED_REFUND" hidden />
            <section class="plr20 border-top">
                <p class="Gray pt10">请选择退款类型</p>
            </section>
            <section class="yg-box yg-two-layout-btn yg-btn-straight">
                <div class="mui-row mt10 return-type-wrap">
                    <div class="mui-col-sm-6 mui-col-xs-6">
                        <a href="javascript:;" class="mui-btn selected return-type-btn return-goods" data-orderid="${(order.id)!''}"
                           data-orderdetailid="${(orderDetail.id)}">退货退款</a>
                    </div>
                    <div class="mui-col-sm-6 mui-col-xs-6">
                        <a href="javascript:;" class="mui-btn return-type-btn only-refund" data-orderid="${(order.id)!''}"
                           data-orderdetailid="${(orderDetail.id)}">仅退款</a>
                    </div>
                </div>
            </section>
            <div class="return-goods-message">
                <section class="plr20">
                    <p class="Gray">请将商品寄回以下地址：</p>
                </section>
                <section class="yg-box plr20">
                    <div class="mui-row mt10">
                        <div class="mui-col-sm-6 mui-col-xs-6">收货人：${(backAddress.name)!''}</div>
                        <div class="mui-col-sm-6 mui-col-xs-6">联系电话：${(backAddress.phone)!''}</div>
                        <p>收货地址：${(backAddress.address)!''}</p>
                    </div>
                </section>
                <section class="plr20">
                    <p><strong class="Red">*</strong>物流公司</p>
                    <select id="express" name="express">
                    <#list expresses as express>
                        <option value="${(express.name)!''}"
                                <#if ((express.name)!'')==((orderReturnGoodsForm.express)!'')>selected</#if>>${(express.name)!''}</option>
                    </#list>
                    </select>
                    <p><strong class="Red">*</strong>运单号</p>
                    <input id="expressNo" name="expressNo" type="text" placeholder="" value="${(orderReturnGoodsForm.expressNo)!''}" />
                </section>
                <section class="plr20">
                    <p><strong class="Red">*</strong>退货数量</p>
                    <div class="mui-numbox" data-numbox-min="0" data-numbox-max="${(orderRefund.canReturnNum)!0}">
                        <button class="mui-btn mui-btn-numbox-minus" type="button">-</button>
                        <input id="proNum" name="proNum" class="mui-input-numbox" type="number" value="${(orderRefund.canReturnNum)!0}" />
                        <button class="mui-btn mui-btn-numbox-plus" type="button">+</button>
                    </div>
                </section>
            </div>
            <section class="plr20">
                <p><strong class="Red">*</strong>退货原因</p>
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
                <p><strong class="Red">*</strong>退款金额(不可修改)</p>
                <input id="refundFee"
                       name="refundFee"
                       type="text"
                       placeholder="${((orderRefund.canReturnFee)!0)?string('0.00')}"
                       value="${((orderRefund.canReturnFee)!0)?string('0.00')}"
                       readonly />
                <p>退款说明 <span class="ml10 Gray">（可不填）</span></p>
                <textarea id="description" name="description" rows="5" placeholder="0-200字"></textarea>
            </section>
            <section class="yg-btn-straight">
                <a href="javascript:;" class="mui-btn mui-btn-danger mui-btn-block" id="submitReturnGoodsForm">提交申请</a>
            </section>
        </form>
    </div>
</div>
<#include "../../layouts/corejs.ftl">
<script type="text/javascript" src="${context}/static/js/yg-order-refund-goods.js?v=${static_version}" charset="utf-8"></script>
<script type="text/javascript">
    var refundFeeMax = ${((orderRefund.canReturnFee)!0)?string('0.00')};
    var errorMsg = '${errorMsg!''}';
    var isEditPage = false;
    var proPrice =${(orderDetail.wfxPrice)!0};
</script>
</body>
</html>