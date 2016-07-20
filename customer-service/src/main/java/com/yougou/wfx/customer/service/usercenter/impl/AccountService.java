package com.yougou.wfx.customer.service.usercenter.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.yougou.wfx.customer.common.bean.BeanUtils;
import com.yougou.wfx.customer.common.crypto.CryptoUtils;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.usercenter.account.UserAccountVo;
import com.yougou.wfx.customer.model.usercenter.account.UserInfoVo;
import com.yougou.wfx.customer.service.usercenter.IAccountService;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.OperatePlatformEnum;
import com.yougou.wfx.member.api.front.IMemberAccountFrontApi;
import com.yougou.wfx.member.dto.input.MemberAccountInputDto;
import com.yougou.wfx.member.dto.input.MemberProfileInputDto;
import com.yougou.wfx.member.dto.output.MemberAccountOutputDto;
import com.yougou.wfx.member.dto.output.MemberProfileOutputDto;
import com.yougou.wfx.shop.api.front.IShopFrontApi;
import com.yougou.wfx.shop.dto.output.ShopOutputDto;
import com.yougou.wfx.system.api.IWFXSystemApi;

/**
 * 用户service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/30 下午4:46
 * @since 1.0 Created by lipangeng on 16/3/30 下午4:46. Email:lipg@outlook.com.
 */
@Service
public class AccountService implements IAccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
	@Autowired
	private IMemberAccountFrontApi memberAccountFrontApi;
	@Autowired
	private IWFXSystemApi wfxSystemApi;
	@Autowired
	private IShopFrontApi shopFrontApi;

	/**
	 * 检查手机号是否已经存在
	 *
	 * @param phoneNumber
	 *            手机号码
	 *
	 * @since 1.0 Created by lipangeng on 16/3/30 下午2:58.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public boolean hasPhoneNumber(String phoneNumber) {
		WFXResult<Boolean> result = memberAccountFrontApi.checkPhoneRegiter(phoneNumber, SessionUtils.getUserContext());
		return result != null && result.getResult();
	}

	/**
	 * 重置用户密码
	 *
	 * @param phoneNum
	 *            手机号
	 * @param loginPassword
	 *            密码
	 * @param validCode
	 *            验证 coed
	 *
	 * @since 1.0 Created by lipangeng on 16/3/30 下午4:18.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public Result resetPassword(String phoneNum, String loginPassword, String validCode) {
		// 对密码进行md5加密
		loginPassword = CryptoUtils.passwordMd5(loginPassword);
		MemberAccountOutputDto memberAccountByPhoneNumber = memberAccountFrontApi.getMemberAccountByPhoneNumber(phoneNum);
		if (memberAccountByPhoneNumber == null) {
			return Result.create(false, "该手机号未绑定任何用户");
		}
		// 更新用户密码
		WFXResult<Boolean> result = memberAccountFrontApi.updatePassWord(memberAccountByPhoneNumber.getId(), loginPassword, phoneNum, validCode, SessionUtils.getUserContext());
		return Result.valueOf(result);
	}

	/**
	 * 创建用户信息
	 *
	 * @param loginName
	 *            用户名
	 * @param loginPassword
	 *            密码
	 * @param validCode
	 *            短信验证码
	 *
	 * @since 1.0 Created by lipangeng on 16/3/30 下午4:02.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public Result createAccount(String loginName, String loginPassword, String validCode, String currentShopId) {
		loginPassword = CryptoUtils.passwordMd5(loginPassword);
		MemberAccountInputDto memberAccountInputDto = new MemberAccountInputDto();
		memberAccountInputDto.setLoginName(loginName);
		memberAccountInputDto.setLoginPassword(loginPassword);
		memberAccountInputDto.setState(1);
		memberAccountInputDto.setMemberName(loginName);
		memberAccountInputDto.setMemberType(1);
		memberAccountInputDto.setRegisterCheckMobile(loginName);
		memberAccountInputDto.setRegisterCheckMobileTime(new Date());
		memberAccountInputDto.setRegisterDate(new Date());
		memberAccountInputDto.setRegisterIp(SessionUtils.getRemoteIP());
		memberAccountInputDto.setPlatform(OperatePlatformEnum.SHOPPINGMALL.getKey());
		memberAccountInputDto.setValidateCode(validCode);
		
		ShopOutputDto parentShop =null;
		if(!StringUtils.isBlank(currentShopId)){
			parentShop = shopFrontApi.getShopById(currentShopId, Boolean.FALSE);
		}
		memberAccountInputDto.setParentSellerId(parentShop == null ? "-1" : parentShop.getSellerId());// “-1”代表总店
		WFXResult<Boolean> result = memberAccountFrontApi.registerMember(memberAccountInputDto, validCode, SessionUtils.getUserContext());
		return Result.valueOf(result);
	}

	/**
	 * 获取用户Profile信息
	 *
	 * @since 1.0 Created by lipangeng on 16/3/30 下午5:49.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public UserAccountVo getUserAccount(String userId) {
		MemberProfileOutputDto memberProfile = null;
		MemberAccountOutputDto memberAccount = memberAccountFrontApi.getMemberAccountById(userId);
		UserAccountVo userAccount = new UserAccountVo();
		
		if (memberAccount != null) {
			if(StringUtils.isEmpty(memberAccount.getLoginName())){
				//微信账号未绑定
				userAccount.setUserName(memberAccount.getPlatformUsername());
				userAccount.setShortUserName(memberAccount.getPlatformUsername());
				userAccount.setUserHeadUrl(memberAccount.getProfileInfo().getHeadShowImg());
			}else{
				//微信账号已绑定
				String userName = memberAccount.getLoginName();
				userAccount.setUserName(userName);
				userAccount.setShortUserName(BeanUtils.shortPhoneNum(userName));
				userAccount.setUserHeadUrl(memberAccount.getProfileInfo().getHeadShowImg());
			}
		}
		return userAccount;
	}

	/**
	 * 获取用户的profile信息
	 *
	 * @param userId
	 *            用户id
	 *
	 * @since 1.0 Created by lipangeng on 16/4/4 下午1:58. Email:lipg@outlook.com.
	 */
	@Override
	public UserInfoVo getUserInfo(String userId) {
		MemberAccountOutputDto memberAccount = memberAccountFrontApi.getMemberAccountById(userId);
		UserInfoVo userInfo = new UserInfoVo();
		if (memberAccount != null) {
			MemberProfileOutputDto memberProfile = memberAccount.getProfileInfo();
			if(StringUtils.isEmpty(memberAccount.getLoginName())){
				//微信账号未绑定
				userInfo.setUserName(memberAccount.getPlatformUsername());
				if(memberProfile!=null){
					userInfo.setSex(memberProfile.getMemberSex());
					userInfo.setBirthday(memberProfile.getBirthday());
					userInfo.setUserHeadUrl(memberProfile.getHeadShowImg());
				}
			}else{
				//微信账号已绑定
				String userName = memberAccount.getLoginName();
				userInfo.setUserName(userName);
				if(memberProfile!=null){
					userInfo.setSex(memberProfile.getMemberSex());
					userInfo.setBirthday(memberProfile.getBirthday());
					userInfo.setUserHeadUrl(memberAccount.getProfileInfo().getHeadShowImg());
				}
				
			}
		}
		return userInfo;
	}

	/**
	 * 更新用户信息
	 *
	 * @param userId
	 *            用户id
	 * @param birthday
	 *            出生日期
	 *
	 * @since 1.0 Created by lipangeng on 16/4/5 下午6:22. Email:lipg@outlook.com.
	 */
	@Override
	public Result updateUserBirthday(String userId, Date birthday) {
		MemberProfileOutputDto memberProfileOutputDto = memberAccountFrontApi.getMemberProfileByMemberId(userId);
		if (memberProfileOutputDto == null) {
			return Result.create(false, "用户Profile不存在,无法更新用户信息");
		}
		MemberProfileInputDto memberProfile = new MemberProfileInputDto();
		memberProfile.setId(memberProfileOutputDto.getId());
		memberProfile.setBirthday(birthday);
		memberProfile.setHeadShowImg(memberProfileOutputDto.getHeadShowImg());
		WFXResult<Boolean> result = memberAccountFrontApi.updateMemberProfile(memberProfile);
		return Result.valueOf(result);
	}

	/**
	 * 更新用户性别信息
	 *
	 * @param userId
	 *            用户id
	 * @param sex
	 *            性别
	 *
	 * @since 1.0 Created by lipangeng on 16/4/5 下午6:23. Email:lipg@outlook.com.
	 */
	@Override
	public Result updateUserSex(String userId, Integer sex) {
		MemberProfileOutputDto memberProfileOutputDto = memberAccountFrontApi.getMemberProfileByMemberId(userId);
		if (memberProfileOutputDto == null) {
			return Result.create(false, "用户Profile不存在,无法更新用户信息");
		}
		MemberProfileInputDto memberProfile = new MemberProfileInputDto();
		memberProfile.setId(memberProfileOutputDto.getId());
		memberProfile.setMemberSex(sex);
		memberProfile.setHeadShowImg(memberProfileOutputDto.getHeadShowImg());
		WFXResult<Boolean> result = memberAccountFrontApi.updateMemberProfile(memberProfile);
		return Result.valueOf(result);
	}

	/**
	 * 更新用户头像信息
	 *
	 * @param userId
	 *            用户id
	 * @param url
	 *            头像文件路径
	 *
	 * @since 1.0 Created by lipangeng on 16/4/5 下午6:24. Email:lipg@outlook.com.
	 */
	@Override
	public Result updateUserHeadImage(String userId, String url) {
		MemberProfileOutputDto memberProfileOutputDto = memberAccountFrontApi.getMemberProfileByMemberId(userId);
		if (memberProfileOutputDto == null) {
			return Result.create(false, "用户Profile不存在,无法更新用户信息");
		}
		MemberProfileInputDto memberProfile = new MemberProfileInputDto();
		memberProfile.setId(memberProfileOutputDto.getId());
		memberProfile.setHeadShowImg(url);
		WFXResult<Boolean> result = memberAccountFrontApi.updateMemberProfile(memberProfile);
		return Result.valueOf(result);
	}

	/**
	 * 发送手机短信验证码
	 *
	 * @since 1.0 Created by lipangeng on 16/4/25 上午10:44.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public Result sendSMSCode(String phoneNum) {
		WFXResult<Boolean> result = memberAccountFrontApi.sendPhoneCode(phoneNum, SessionUtils.getUserContext());
		return Result.valueOf(result);
	}

	/**
	 * 验证短信验证码是否正确
	 *
	 * @param phoneNum
	 *            手机号
	 * @param vaildCode
	 *            验证码
	 *
	 * @since 1.0 Created by lipangeng on 16/4/25 上午10:43.
	 *        Email:lipg@outlook.com.
	 */
	@Override
	public Result checkSMSCode(String phoneNum, String vaildCode) {
		WFXResult<Boolean> result = memberAccountFrontApi.checkPhoneCode(phoneNum, vaildCode, SessionUtils.getUserContext());
		return Result.valueOf(result);
	}

	@Override
	public String getRegisterCheckMobile(String userId) {
		MemberAccountOutputDto memberAccount = memberAccountFrontApi.getMemberAccountById(userId);
		if (memberAccount != null && !Strings.isNullOrEmpty(memberAccount.getRegisterCheckMobile())) {
			return memberAccount.getRegisterCheckMobile();
		}
		return null;
	}

	/**
	 * 处理图片路径
	 *
	 * @since 1.0 Created by lipangeng on 16/4/27 上午10:49.
	 *        Email:lipg@outlook.com.
	 */
	private String headImgUrl(String prefix, String uri) {
		try {
			if (Strings.isNullOrEmpty(prefix) || Strings.isNullOrEmpty(uri)) {
				return null;
			}
			return new URL(new URL(prefix), uri).toString();
		} catch (MalformedURLException e) {
			logger.error("拼接头像地址失败", e);
		}
		return null;
	}
}
