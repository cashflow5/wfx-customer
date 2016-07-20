package com.yougou.wfx.customer.service.usercenter.impl;

import com.google.common.collect.Lists;
import com.yougou.wfx.customer.common.utils.JacksonUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.usercenter.myaddress.AccountAddressVo;
import com.yougou.wfx.customer.model.usercenter.myaddress.form.EditAccountAddressFormVo;
import com.yougou.wfx.customer.service.usercenter.IAccountAddressService;
import com.yougou.wfx.dto.base.PageModel;
import com.yougou.wfx.member.api.front.IMemberAddressFrontApi;
import com.yougou.wfx.member.dto.input.MemberAddressInputDto;
import com.yougou.wfx.member.dto.input.MemberAddressPageInputDto;
import com.yougou.wfx.member.dto.output.MemberAddressOutputDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户收货地址Service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/31 下午5:49
 * @since 1.0 Created by lipangeng on 16/3/31 下午5:49. Email:lipg@outlook.com.
 */
@Service
public class AccountAddressService implements IAccountAddressService {
    private static final Logger logger = LoggerFactory.getLogger(AccountAddressService.class);
    @Autowired
    private IMemberAddressFrontApi memberAddressFrontApi;

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
    @Override
    public Result delAddress(String userId, String addressId) {
        MemberAddressOutputDto memberAddress = memberAddressFrontApi.getMemberAddressById(addressId);
        if (memberAddress == null) {
            logger.error("要删除的地址信息不存在,用户id:{},地址id:{}", userId, addressId);
            return Result.create(false, "地址不存在.");
        }
        if (memberAddress.getLoginacccountId() == null && ! memberAddress.getLoginacccountId().equals(userId)) {
            logger.error("无权删除用户地址,用户id:{},地址id:{}", userId, addressId);
            return Result.create(false, "无权删除用户的收货地址信息");
        }
        int result = memberAddressFrontApi.removeMemberAddressById(addressId);
        if (result == 0) {
            return Result.create(false, "无法删除用户收货地址,Remote Api Server Error.");
        }
        return Result.create();
    }

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
    @Override
    public Result setDefaultAddress(String userId, String addressId) {
        MemberAddressOutputDto memberAddress = memberAddressFrontApi.getMemberAddressById(addressId);
        if (memberAddress == null) {
            logger.error("要设定为默认收获地址的地址信息不存在,用户id:{},地址id:{}", userId, addressId);
            return Result.create(false, "地址不存在.");
        }
        if (memberAddress.getLoginacccountId() == null && ! memberAddress.getLoginacccountId().equals(userId)) {
            logger.error("无权设定该地址为默认收货地址,用户id:{},地址id:{}", userId, addressId);
            return Result.create(false, "无权设置该地址为用户的默认收货地址");
        }
        // 更新默认收货地址信息
        AccountAddressVo address = new AccountAddressVo();
        address.setId(addressId);
        address.setDefaultAddress(true);
        int update = memberAddressFrontApi.updateMemberAddress(address.toMemberAddressInputDto(userId));
        if (update == 0) {
            logger.error("设置成默认收货地址失败.收货地址ID:{}", addressId);
            return Result.create(false, "设置默认收货地址失败");
        }
        return Result.create();
    }

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
    @Override
    public Result<AccountAddressVo> addAddress(String userId, AccountAddressVo accountAddress) {
        String insert = memberAddressFrontApi.insertMemberAddress(accountAddress.toMemberAddressInputDto(userId));
        if (insert == null) {
            logger.error("创建收货地址失败,操作用户ID:{},数据内容:{}", userId, JacksonUtils.Convert(accountAddress));
            return Result.create(false, "创建收货地址失败.");
        }
        if (accountAddress.isDefaultAddress()) {
            Result result = setDefaultAddress(userId, insert);
            if (result.hasError()) {
                logger.error("添加收货地址时设定为默认收货地址失败.用户Id:{},收货地址Id:{}", userId, accountAddress.getId());
                return Result.create(false, 501, "添加收货地址成功,但将收货地址设定为默认收货地址失败.");
            }
        }
        MemberAddressOutputDto memberAddress = memberAddressFrontApi.getMemberAddressById(insert);
        return Result.create(true, "", AccountAddressVo.valueOf(memberAddress));
    }

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
    @Override
    public Result<AccountAddressVo> updateAddress(String userId, AccountAddressVo accountAddress) {
        MemberAddressOutputDto memberAddress = memberAddressFrontApi.getMemberAddressById(accountAddress.getId());
        if (memberAddress == null) {
            logger.error("要更新的地址信息不存在,用户id:{},地址id:{}", userId, accountAddress.getId());
            return Result.create(false, "地址不存在.");
        }
        if (memberAddress.getLoginacccountId() == null && ! memberAddress.getLoginacccountId().equals(userId)) {
            logger.error("无权更新用户地址,用户id:{},地址id:{}", userId, accountAddress.getId());
            return Result.create(false, "无权删除用户的收货地址信息");
        }
        int update = memberAddressFrontApi.updateMemberAddress(accountAddress.toMemberAddressInputDto(userId));
        if (update == 0) {
            logger.error("更新收货地址信息异常,调取API错误.");
            return Result.create(false, "更新收货地址信息异常.");
        }
        memberAddress = memberAddressFrontApi.getMemberAddressById(accountAddress.getId());
        return Result.create(true, "更新成功", AccountAddressVo.valueOf(memberAddress));
    }

    /**
     * 获取用户的收货地址
     *
     * @param userId
     *         用户Id
     * @param addressId
     *         地址id
     *
     * @since 1.0 Created by lipangeng on 16/4/1 下午5:05. Email:lipg@outlook.com.
     */
    @Override
    public AccountAddressVo getAddress(String userId, String addressId) {
        MemberAddressOutputDto memberAddress = memberAddressFrontApi.getMemberAddressById(addressId);
        if (memberAddress == null) {
            logger.error("收货地址信息不存在,用户id:{},地址id:{}", userId, addressId);
            return null;
        }
        if (memberAddress.getLoginacccountId() == null && ! memberAddress.getLoginacccountId().equals(userId)) {
            logger.error("无权操作用户地址,用户id:{},地址id:{}", userId, addressId);
            return null;
        }
        return AccountAddressVo.valueOf(memberAddress);
    }

    /**
     * 获取用户全部的收货地址,默认获取前200条进行显示
     *
     * @param userId
     *         用户id
     *
     * @since 1.0 Created by lipangeng on 16/4/6 上午10:43. Email:lipg@outlook.com.
     */
    @Override
    public List<AccountAddressVo> getAllAddress(String userId) {
        PageModel<MemberAddressOutputDto> pageModel = new PageModel<>();
        pageModel.setLimit(200);
        MemberAddressPageInputDto memberAddressPageInputDto = new MemberAddressPageInputDto();
        memberAddressPageInputDto.setLoginacccountId(userId);
        PageModel<MemberAddressOutputDto> page = memberAddressFrontApi.findMemberAddressPage(memberAddressPageInputDto, pageModel);
        if (page != null && page.getItems() != null && page.getItems().size() > 0) {
            List<AccountAddressVo> addresses = Lists.newArrayList();
            for (MemberAddressOutputDto memberAddressOutputDto : page.getItems()) {
                addresses.add(AccountAddressVo.valueOf(memberAddressOutputDto));
            }
            return addresses;
        }
        return Lists.newArrayList();
    }
}
