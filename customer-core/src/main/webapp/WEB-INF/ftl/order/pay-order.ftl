<#-- @ftlvariable name="shopId" type="java.lang.String" -->
<!DOCTYPE html>
<html>

<head>
<#include "../layouts/meta.ftl">
    <title>优购微零售_确认订单</title>
<#include "../layouts/style.ftl">
</head>

<body>
<div class="viewport">
    <input type="hidden" id="shopId" value="${(shop.id)!''}" />
    <#if buyType?? && buyType== '1'>
    	<script type="text/javascript" charset="utf-8">
			<#list orderPay.commoditys as commodity>
				<#if commodity_index == 0>
					var selectItem = {
		    			commodityNo: '${(commodity.commodityNo)!''}', 
		    			skuId: '${(commodity.skuId)!''}', 
		    			count: ${(commodity.count)!0}, 
		    			price: ${(commodity.wfxPrice)!0}
		    		};
	    		</#if>
			</#list>
    		
    	</script>
    </#if>
    
    <header class="mui-bar yg-header header-ext">
    	<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
        <h1 class="mui-title">确认订单</h1>
        <!--<a id="btnMenu" class="mui-pull-right header-button"><span class="iconfont">&#xe637;</span></a>-->
    </header>

    <nav class="mui-bar mui-bar-tab yg-nav">
        <div class="mui-tab-item yg-nav-text">
            <span class="mui-tab-label">实付款：&yen;${(orderPay.totalPrice+orderPay.freight)?string('0.00')}</span>
        </div>
        <a class="mui-tab-item yg-nav-btn-buy-now w37p" id="buyNow">
            <span class="mui-tab-label">提交订单</span>
        </a>
    </nav>
<#include "../layouts/menu.ftl">
    <div class="mui-content">
        <form id="orderCreateForm" action="${context}/confirm_order.sc" method="post">
            <input type="hidden" name="addressId" id="addressId"
                   value="<#if (orderPay.address)??>${orderPay.address.id}<#else>0</#if>">
            <a id="addAddress" class="yg-box address-box link-box mt10">
            <#if (orderPay.address)??>
                <div class="box-content-wrap">
                    <div class="box-title"><i class="iconfont Pink">&#xe620;</i>${(orderPay.address.name)!''} <i
                            class="iconfont Pink ml50">
                        &#xe648;</i>${(orderPay.address.phone)!''}
                    </div>
                    <div class="box-info-content">
						${(orderPay.address.province)!''} ${(orderPay.address.city)!''} ${(orderPay.address.district)!''} ${(orderPay.address.address)!''}
                        <#if (orderPay.address.defaultAddress)?? &&orderPay.address.defaultAddress>
                            <span class="yg-badge-pink">默认</span>
                        </#if>
                    </div>
                </div>
            <#else>
                <span class="enter-address-info">
						请填写收货人信息
				</span>
            </#if>
            </a>
            <a class="yg-box img-thum-list-box link-box">
                <div class="box-content-wrap">
                <#list orderPay.commoditys as commodity>
                    <div class="goods-item"><img src="${(commodity.imageUrl)!''}" alt="" /><span
                            class="thum-text">&yen;${(commodity.wfxPrice?string("0.00"))} x${(commodity.count)!0}</span>
                    </div>
                </#list>
                </div>
                <div class="box-remark">共${(orderPay.totalCount)!0}件</div>
            </a>
            <section class="border-top bg-white">
                <div class="box-title pd10">选择支付方式</div>
                <ul class="mui-table-view">
                <#if (orderPay.payTypes)??>
                    <#list orderPay.payTypes as payType>
                        <#if payType=='wechatpay'>
                        	<#if running_environment == '1'>
                            <li class="mui-table-view-cell ">
                                <div class="yg-radio-checkbox">
									<span class="mui-radio">
										<input id="payType1" type="radio" name="payType" value="wechatpay" checked />
									</span>
                                    <label for="payType1"><i class="iconfont yg-pay-weixin">&#xe657;</i>微信支付</label>
                                </div>
                            </li>
                            </#if>
                        <#elseif payType=='alipay'>
                            <li class="mui-table-view-cell ">
                                <div class="yg-radio-checkbox">
                                    <span class="mui-radio">
                                        <input id="payType2" type="radio" name="payType" value="alipay" />
                                    </span>
                                    <label for="payType2"><i class="iconfont yg-pay-zhifubao">&#xe658;</i>支付宝</label>
                                </div>
                            </li>
                        </#if>
                    </#list>
                </#if>
                </ul>
            </section>
            <a class="yg-box mb0 mt10">
					<span class="box-content-wrap">
						<p class="Gray">商品总金额 <span class="Red fr">&yen; ${orderPay.totalPrice?string("0.00")}</p>
						<div class="Gray">+运费<span class="Red fr">&yen; ${orderPay.freight?string('0.00')}</span></div>
					</span>

            </a>
            <!--<section class="yg-shop-certificate-floor Black">
                	正在使用百丽担保，提交订单视为同意《百丽担保协议》
            </section>-->
        </form>
    </div>
</div>
<#include "../layouts/corejs.ftl"/>
<script src="${context}/static/js/yg-pay-order.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
<script src="${context}/static/js/qrcode.min.js?v=${static_version}" type="text/javascript" charset="utf-8"></script>
</body>

</html>
