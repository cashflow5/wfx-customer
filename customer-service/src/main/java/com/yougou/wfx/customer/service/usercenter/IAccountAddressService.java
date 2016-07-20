package com.yougou.wfx.customer.service.usercenter;

import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.usercenter.myaddress.AccountAddressVo;

import java.util.List;

/**
 * 用户收货地址的service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/31 下午4:01
 * @since 1.0 Created by lipangeng on 16/3/31 下午4:01. Email:lipg@outlook.com.
 */
public interface IAccountAddressService {

    /**
     * 删除用户的地址信息
     *
     * @param userId
     *         用户id,防止删除别人的地址
     * @param addressId
     *         地址id
     *
     * @since 1.0 Created by lipangeng on 16/3/31 下午4:20. Email:lipg@outlook.com.
     */
    Result delAddress(String userId, String addressId);


    /**
     * 设定默认收货地址
     *
     * @param userId
     *         用户id,防止跨用户删除地址
     * @param addressId
     *         地址id
     *
     * @since 1.0 Created by lipangeng on 16/3/31 下午5:02. Email:lipg@outlook.com.
     */
    Result setDefaultAddress(String userId, String addressId);

    /**
     * 创建用户收货地址
     *
     * @param userId
     *         用户id
     * @param accountAddress
     *         用户的收货地址封装信息
     *
     * @since 1.0 Created by lipangeng on 16/4/1 下午4:57. Email:lipg@outlook.com.
     */
    Result<AccountAddressVo> addAddress(String userId, AccountAddressVo accountAddress);

    /**
     * 更新用户收货地址
     *
     * @param userId
     *         用户id
     * @param accountAddress
     *         用户的收货地址封装信息
     *
     * @since 1.0 Created by lipangeng on 16/4/1 下午4:57. Email:lipg@outlook.com.
     */
    Result<AccountAddressVo> updateAddress(String userId, AccountAddressVo accountAddress);

    /**
     * 获取用户的收货地址
     *
     * @since 1.0 Created by lipangeng on 16/4/1 下午5:05. Email:lipg@outlook.com.
     */
    AccountAddressVo getAddress(String userId, String addressId);

    /**
     * 获取用户全部的收货地址
     *
     * @since 1.0 Created by lipangeng on 16/4/6 上午10:43. Email:lipg@outlook.com.
     */
    List<AccountAddressVo> getAllAddress(String userId);
}
