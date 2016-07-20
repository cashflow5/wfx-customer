package com.yougou.wfx.customer.model.order;

import com.yougou.wfx.customer.model.commodity.CommodityShoppingCartVo;
import com.yougou.wfx.customer.model.usercenter.myaddress.AccountAddressVo;

import java.util.List;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/20
 */
public class OrderPayVo {
    private AccountAddressVo address;
    private List<OrderCommodityVo> commoditys;
    /**
     * alipay:支付宝支付，wechatpay:微信支付
     */
    private List<String> payTypes;

    private double totalPrice;

    private double freight;

    private int totalCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public AccountAddressVo getAddress() {
        return address;
    }

    public void setAddress(AccountAddressVo address) {
        this.address = address;
    }

   

    public List<OrderCommodityVo> getCommoditys() {
		return commoditys;
	}

	public void setCommoditys(List<OrderCommodityVo> commoditys) {
		this.commoditys = commoditys;
	}

	public List<String> getPayTypes() {
        return payTypes;
    }

    public void setPayTypes(List<String> payTypes) {
        this.payTypes = payTypes;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }
}
