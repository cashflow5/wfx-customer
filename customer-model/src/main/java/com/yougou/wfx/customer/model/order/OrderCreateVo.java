package com.yougou.wfx.customer.model.order;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/20
 */
public class OrderCreateVo implements Serializable {

    @NotBlank(message = "收货地址不能为空")
    private String addressId;
    @NotBlank(message = "支付类型不能为空")
    private String payType;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
