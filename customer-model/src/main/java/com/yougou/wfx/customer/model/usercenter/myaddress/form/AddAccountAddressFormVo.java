package com.yougou.wfx.customer.model.usercenter.myaddress.form;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 创建新收货地址的vo
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/1 下午3:20
 * @since 1.0 Created by lipangeng on 16/4/1 下午3:20. Email:lipg@outlook.com.
 */
public class AddAccountAddressFormVo implements Serializable {
    private boolean defaultAddress = false;
    @NotBlank(message = "收货人姓名不能为空")
    private String name;
    @NotBlank(message = "收货人手机号不能为空")
    private String phone;
    @NotBlank(message = "收货人详细地址不能为空")
    private String address;
    @NotBlank(message = "收货人地区不能为空")
    private String city;
    @NotBlank(message = "收货人地区不能为空")
    private String province;
    @NotBlank(message = "收货人地区不能为空")
    private String district;

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
