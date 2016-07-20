package com.yougou.wfx.customer.model.order.refund;

/**
 * 退货地址
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/13 下午6:35
 * @since 1.0 Created by lipangeng on 16/4/13 下午6:35. Email:lipg@outlook.com.
 */
public class OrderRefundAddressVo {
    private String name;
    private String phone;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
