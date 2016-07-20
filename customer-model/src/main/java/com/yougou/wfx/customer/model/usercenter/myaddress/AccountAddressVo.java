package com.yougou.wfx.customer.model.usercenter.myaddress;

import com.google.common.base.Strings;
import com.yougou.wfx.customer.model.usercenter.myaddress.form.AddAccountAddressFormVo;
import com.yougou.wfx.customer.model.usercenter.myaddress.form.EditAccountAddressFormVo;
import com.yougou.wfx.member.dto.input.MemberAddressInputDto;
import com.yougou.wfx.member.dto.output.MemberAddressOutputDto;

import java.io.Serializable;

/**
 * 我的地址信息
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/31 上午9:56
 * @since 1.0 Created by lipangeng on 16/3/31 上午9:56. Email:lipg@outlook.com.
 */
public class AccountAddressVo implements Serializable {
    private boolean defaultAddress = false;
    private String name;
    private String phone;
    private String phoneEncode;
    private String address;
    private String city;
    private String id;
    private String province;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPhoneEncode() {
        return phoneEncode;
    }

    public void setPhoneEncode(String phoneEncode) {
        this.phoneEncode = phoneEncode;
    }

    /**
     * 通过MemberAddressOutputDto创建AccountAddressVo对象
     *
     * @since 1.0 Created by lipangeng on 16/4/6 上午11:42. Email:lipg@outlook.com.
     */
    public static AccountAddressVo valueOf(MemberAddressOutputDto memberAddressOutput) {
        if (memberAddressOutput == null) {
            return new AccountAddressVo();
        }
        AccountAddressVo accountAddress = new AccountAddressVo();
        accountAddress.setDefaultAddress(memberAddressOutput.getIsDefaultAddress() != 2);
        accountAddress.setName(memberAddressOutput.getReceivingName());
        accountAddress.setCity(memberAddressOutput.getReceivingCity());
        accountAddress.setAddress(memberAddressOutput.getReceivingAddress());
        accountAddress.setPhone(memberAddressOutput.getReceivingMobilePhone());
        accountAddress.setPhoneEncode(shortPhone(accountAddress.getPhone()));
        accountAddress.setId(memberAddressOutput.getId());
        accountAddress.setProvince(memberAddressOutput.getReceivingProvince());
        accountAddress.setDistrict(memberAddressOutput.getReceivingDistrict());
        return accountAddress;
    }

    /**
     * 通过表单构建AccountAddress对象
     *
     * @since 1.0 Created by lipangeng on 16/4/29 上午9:41. Email:lipg@outlook.com.
     */
    public static AccountAddressVo valueOf(AddAccountAddressFormVo form) {
        if (form == null) {
            return null;
        }
        AccountAddressVo accountAddress = new AccountAddressVo();
        accountAddress.setName(form.getName());
        accountAddress.setPhone(form.getPhone());
        accountAddress.setAddress(form.getAddress());
        accountAddress.setProvince(form.getProvince());
        accountAddress.setDistrict(form.getDistrict());
        accountAddress.setCity(form.getCity());
        accountAddress.setDefaultAddress(form.isDefaultAddress());
        return accountAddress;
    }

    /**
     * 通过表单构建AccountAddress对象
     *
     * @since 1.0 Created by lipangeng on 16/4/29 上午10:47. Email:lipg@outlook.com.
     */
    public static AccountAddressVo valueOf(EditAccountAddressFormVo form) {
        if (form == null) {
            return null;
        }
        AccountAddressVo accountAddress = new AccountAddressVo();
        accountAddress.setId(form.getId());
        accountAddress.setName(form.getName());
        accountAddress.setPhone(form.getPhone());
        accountAddress.setAddress(form.getAddress());
        accountAddress.setProvince(form.getProvince());
        accountAddress.setDistrict(form.getDistrict());
        accountAddress.setCity(form.getCity());
        accountAddress.setDefaultAddress(form.isDefaultAddress());
        return accountAddress;
    }

    /**
     * 转换为Dto
     *
     * @since 1.0 Created by lipangeng on 16/4/29 上午10:56. Email:lipg@outlook.com.
     */
    public MemberAddressInputDto toMemberAddressInputDto(String userId) {
        MemberAddressInputDto memberAddressInputDto = new MemberAddressInputDto();
        memberAddressInputDto.setId(this.getId());
        memberAddressInputDto.setLoginacccountId(userId);
        memberAddressInputDto.setReceivingName(this.getName());
        memberAddressInputDto.setReceivingMobilePhone(this.getPhone());
        memberAddressInputDto.setReceivingCity(this.getCity());
        memberAddressInputDto.setReceivingProvince(this.getProvince());
        memberAddressInputDto.setReceivingDistrict(this.getDistrict());
        memberAddressInputDto.setReceivingAddress(this.getAddress());
        memberAddressInputDto.setIsDefaultAddress(this.isDefaultAddress() ? 1 : 2);
        return memberAddressInputDto;
    }

    private static String shortPhone(String phoneNum){
        if (Strings.isNullOrEmpty(phoneNum) || phoneNum.length() < 7) {
            return phoneNum;
        }
        return phoneNum.substring(0, 3) + "****" + phoneNum.substring(phoneNum.length() - 4);
    }
}
