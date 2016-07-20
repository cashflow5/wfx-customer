package com.yougou.wfx.customer.model.usercenter.myaddress.form;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 编辑用户收货地址的vo
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/6 下午2:29
 * @since 1.0 Created by lipangeng on 16/4/6 下午2:29. Email:lipg@outlook.com.
 */
public class EditAccountAddressFormVo implements Serializable{
    private boolean defaultAddress = false;
    @NotBlank(message = "收货人姓名不能为空")
    private String name;
    @NotBlank(message = "收货人手机号不能为空")
    private String phone;
    @NotBlank(message = "收货人详细地址不能为空")
    private String address;
    @NotBlank(message = "收货人地区不能为空")
    private String city;
    @NotBlank(message = "ID为空,无法更新收货地址")
    private String id;
    @NotBlank(message = "收货人地区不能为空")
    private String province;
    @NotBlank(message = "收货人地区不能为空")
    private String district;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
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
