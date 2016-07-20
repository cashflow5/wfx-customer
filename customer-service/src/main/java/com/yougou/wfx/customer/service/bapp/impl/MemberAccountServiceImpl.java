package com.yougou.wfx.customer.service.bapp.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.yougou.wfx.appserver.vo.BooleanResult;
import com.yougou.wfx.appserver.vo.login.LoginVo;
import com.yougou.wfx.appserver.vo.login.MemberBindPhoneVo;
import com.yougou.wfx.appserver.vo.login.MemberForWXVo;
import com.yougou.wfx.appserver.vo.login.ModifyPasswordVo;
import com.yougou.wfx.appserver.vo.login.NewPasswordVo;
import com.yougou.wfx.appserver.vo.login.Sms;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.appserver.vo.login.ValidCode;
import com.yougou.wfx.appserver.vo.login.ValidResult;
import com.yougou.wfx.appserver.vo.login.WXParam;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.visitor.VisitorVo;
import com.yougou.wfx.customer.service.bapp.IMemberAccountService;
import com.yougou.wfx.dto.base.PageModel;
import com.yougou.wfx.dto.base.UserContext;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.member.dto.input.MemberAccountInputDto;
import com.yougou.wfx.member.dto.input.MemberForWXInputDto;
import com.yougou.wfx.member.dto.output.MemberAccountOutputDto;
import com.yougou.wfx.visitor.dto.output.VisitorOutputDto;

/**
 * Created by lizhw on 2016/4/12.
 */
@Service
public class MemberAccountServiceImpl extends BaseServiceImpl implements IMemberAccountService {

	@Override
	public UserInfo login(LoginVo loginVo) {
		UserContext uc = getUserContext(loginVo, "login");
		MemberAccountOutputDto member = new MemberAccountOutputDto();
		if ("1".equals(loginVo.getLoginType())) {
			member = this.memberAccountFrontApi.memberLoginForApp(loginVo.getLoginName(), loginVo.getPassword(), uc);
		} else if ("2".equals(loginVo.getLoginType())) {
			member = memberAccountFrontApi.wxMemberLoginForApp(loginVo.getOpendId(), uc);
		}
		UserInfo userInfo = convertMember2UserInfo(member);
		return userInfo;

	}

	@Override
	public BooleanResult modifyPassword(ModifyPasswordVo mp) {
		BooleanResult result = new BooleanResult();
		String userId = mp.getUserInfo().getId();
		UserContext uc = getUserContext(mp, "updatePassWordAfterLogin");
		WFXResult<Boolean> wfxResult = this.memberAccountFrontApi.updatePassWordAfterLogin(userId, mp.getPassword(), mp.getNewPassword(), uc);
		setBoolResult(result, wfxResult);
		return result;
	}

	@Override
	public BooleanResult sendSms(Sms sms) {
		BooleanResult result = new BooleanResult();
		MemberAccountOutputDto member = this.memberAccountFrontApi.getMemberAccountByPhoneNumber(sms.getPhone());
		if (null == member || StringUtils.isBlank(member.getId())) {
			result.setError("用户不存在！");
			return result;
		}
		UserContext uc = getUserContext(sms, "sendPhoneCode");
		WFXResult<Boolean> booleanWFXResult = this.memberAccountFrontApi.sendPhoneCode(sms.getPhone(), uc);
		if (booleanWFXResult == null) {
			return result;
		}

		result.setResult(booleanWFXResult.getResult());
		if (!booleanWFXResult.getResult()) {
			result.setError(booleanWFXResult.getResultMsg());
		} else {
			result.setMsg(booleanWFXResult.getResultMsg());
		}
		return result;
	}

	@Override
	public BooleanResult sendSmsNew(Sms sms) {
		BooleanResult result = new BooleanResult();
		MemberAccountOutputDto member = this.memberAccountFrontApi.getMemberAccountByPhoneNumber(sms.getPhone());
		if (null != member) {
			result.setError("该手机号已被使用！");
			return result;
		}
		UserContext uc = getUserContext(sms, "sendPhoneCode");
		WFXResult<Boolean> booleanWFXResult = this.memberAccountFrontApi.sendPhoneCode(sms.getPhone(), uc);
		if (booleanWFXResult == null) {
			return result;
		}

		result.setResult(booleanWFXResult.getResult());
		if (!booleanWFXResult.getResult()) {
			result.setError(booleanWFXResult.getResultMsg());
		} else {
			result.setMsg(booleanWFXResult.getResultMsg());
		}
		return result;
	}

	@Override
	public ValidResult checkValidCode(ValidCode validCode) {
		UserContext uc = getUserContext(validCode, "checkPhoneCode");
		WFXResult<Boolean> wfxResult = this.memberAccountFrontApi.checkPhoneCode(validCode.getPhone(), validCode.getCode(), uc);
		ValidResult result = new ValidResult();
		if (wfxResult == null) {
			return result;
		}
		result.setResult(wfxResult.getResult());

		if (!wfxResult.getResult()) {
			result.setError(wfxResult.getResultMsg());
		} else {
			result.setMsg(wfxResult.getResultMsg());
			String uuid = UUID.randomUUID().toString().replace("-", "");
			result.setToken(uuid);
		}
		return result;
	}

	@Override
	public BooleanResult changePassword(NewPasswordVo newPassword) {
		BooleanResult result = new BooleanResult();

		if (StringUtils.isBlank(newPassword.getSessiontoken())) {
			result.setError("请重新验证，toke为空！");
			return result;
		}
		if (StringUtils.isBlank(newPassword.getToken())) {
			result.setError("请重新验证，toke为空！");
			return result;
		}
		if (!newPassword.getToken().equals(newPassword.getSessiontoken())) {
			result.setError("请重新验证，token错误！");
			return result;
		}
		if (isBlank(newPassword.getNewPassword())) {
			result.setError("新密码不能为空！");
			return result;
		}
		MemberAccountOutputDto member = this.memberAccountFrontApi.getMemberAccountByPhoneNumber(newPassword.getPhone());
		UserContext uc = getUserContext(newPassword, "updatePassWord");
		WFXResult<Boolean> wfxResult = this.memberAccountFrontApi.updatePassWord(member.getId(), newPassword.getNewPassword(), newPassword.getPhone(), newPassword.getCode(), uc);
		if (wfxResult == null) {
			return result;
		}
		result.setResult(wfxResult.getResult());
		if (!wfxResult.getResult()) {
			result.setError(wfxResult.getResultMsg());
		} else {
			result.setMsg(wfxResult.getResultMsg());
		}
		return result;
	}

	@Override
	public Object wxSellerBind(MemberForWXVo vo) {
		BooleanResult result = new BooleanResult();
		UserContext uc = getUserContext(vo, "wxSellerBind");
		MemberForWXInputDto dto = new MemberForWXInputDto();
		dto.setOpenId(vo.getOpenId());
		dto.setNickname(vo.getNickname());
		dto.setSex(vo.getSex());
		dto.setHeadimgurl(vo.getHeadimgurl());
		dto.setMemberId(vo.getMemberId());
		WFXResult<MemberAccountOutputDto> wfxResult = this.memberAccountFrontApi.wxSellerBindForApp(dto, "app", uc);
		if (wfxResult.getResultCode() == 200) {
			UserInfo userInfo = convertMember2UserInfo(wfxResult.getResult());
			return userInfo;
		}
		result.setError(wfxResult.getResultMsg());
		return result;
	}

	@Override
	public Object memberBindPhone(MemberBindPhoneVo vo) {
		BooleanResult result = new BooleanResult();
		UserContext uc = getUserContext(vo, "memberBindPhone");
		MemberAccountInputDto dto = new MemberAccountInputDto();
		dto.setLoginName(vo.getLoginName());
		dto.setLoginPassword(vo.getLoginPassword());
		dto.setOpenId(vo.getOpenId());
		dto.setValidateCode(vo.getValidateCode());
		WFXResult<MemberAccountOutputDto> wfxResult = this.memberAccountFrontApi.phoneSellerBindForApp(dto, "app", uc);
		if (wfxResult.getResultCode() == 200) {
			UserInfo userInfo = convertMember2UserInfo(wfxResult.getResult());
			return userInfo;
		}
		result.setError(wfxResult.getResultMsg());
		return result;
	}

	@Override
	public WXParam getWXParam() {
		WXParam wxParam = new WXParam();
		wxParam.setAppId(this.systemApi.getSystemConfigValue("wfx.system.appId"));
		wxParam.setAppSecret(this.systemApi.getSystemConfigValue("wfx.system.appSecret"));
		return wxParam;
	}

	@Override
	public Page<MemberAccountOutputDto> queryFansList(String sellerId, Page page) {
		Page<MemberAccountOutputDto> voPage = new Page<>();
		voPage.setItems(Lists.<MemberAccountOutputDto> newArrayList());
		// 创建分页请求
		PageModel pageModel = null;
		if (page != null) {
			pageModel = page.toPageModelDto();
		} else {
			pageModel = new PageModel();
		}
		
		PageModel<MemberAccountOutputDto> searchResult = memberAccountFrontApi.queryFansList(sellerId, pageModel);	
		if (searchResult != null && searchResult.getItems() != null) {
			List<MemberAccountOutputDto> dtoList = searchResult.getItems();
			voPage.setItems(dtoList);
			voPage.setPageCount(searchResult.getPageCount());
		}
		return voPage;
	}

	@Override
	public int queryFansListCount(String sellerId) {
		return memberAccountFrontApi.queryFansListCount(sellerId);
	}
}
