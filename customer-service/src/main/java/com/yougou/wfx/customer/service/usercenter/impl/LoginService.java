package com.yougou.wfx.customer.service.usercenter.impl;

import java.text.MessageFormat;

import com.google.common.base.Strings;
import com.yougou.wfx.customer.common.constant.WeiXinConstant;
import com.yougou.wfx.customer.common.crypto.CryptoUtils;
import com.yougou.wfx.customer.common.crypto.PasswordCryptoUtils;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.session.SessionWXUserInfo;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.seller.SellerinfoVo;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.customer.model.usercenter.UserLoginAccountVo;
import com.yougou.wfx.customer.model.usercenter.UserLoginVo;
import com.yougou.wfx.customer.model.weixin.base.WXUserInfoResponse;
import com.yougou.wfx.customer.model.weixin.custom.request.TextMessageRequest;
import com.yougou.wfx.customer.model.weixin.custom.response.MessageResponse;
import com.yougou.wfx.customer.service.shop.ISellerService;
import com.yougou.wfx.customer.service.shop.IShopService;
import com.yougou.wfx.customer.service.shoppingcart.IShoppingCartService;
import com.yougou.wfx.customer.service.usercenter.ILoginService;
import com.yougou.wfx.customer.service.weixin.IWXService;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.ResultCodeEnum;
import com.yougou.wfx.member.api.front.IMemberAccountFrontApi;
import com.yougou.wfx.member.dto.input.MemberAccountInputDto;
import com.yougou.wfx.member.dto.input.MemberForWXInputDto;
import com.yougou.wfx.member.dto.output.MemberAccountOutputDto;
import com.yougou.wfx.seller.api.front.ISellerInfoFrontApi;
import com.yougou.wfx.seller.dto.output.SellerInfoOutputDto;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 登录页面的Service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/27 下午6:48
 * @since 1.0 Created by lipangeng on 16/3/27 下午6:48. Email:lipg@outlook.com.
 */
@Service
public class LoginService implements ILoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
    @Autowired
    private IMemberAccountFrontApi memberAccountFrontApi;
    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private IShopService shopService;
    @Autowired
    private ISellerService sellerService;
    @Autowired
    private ISellerInfoFrontApi sellerInfoFrontApi;
    @Autowired
    private IWXService wXService;

    /**
     * 进行登录操作
     *
     * @since 1.0 Created by lipangeng on 16/3/27 下午6:54. Email:lipg@outlook.com.
     */
    @Override
    public UserLoginAccountVo doLogin(UserLoginVo loginVo) {
        UserLoginAccountVo userLoginAccount = new UserLoginAccountVo();
        userLoginAccount.setUserLoginInfo(loginVo);
        if (loginVo != null) { // 自动登陆,验证id和用户名
            // 验证用户登陆情况
            String password = null;
            if (loginVo.isAutoLogin()) {
                password = PasswordCryptoUtils.decryptPassword(loginVo.getLoginPasswordEncode());
            } else {
                password = loginVo.getLoginPassword();
                password = CryptoUtils.passwordMd5(password);
            }
            // 判断密码是否为空
            if (! Strings.isNullOrEmpty(password)) {
                MemberAccountOutputDto memberAccount =
                        memberAccountFrontApi.memberLogin(loginVo.getLoginName(), password, SessionUtils.getUserContext());
                if (memberAccount != null) {
                    userLoginAccount.setLogined(true);
                    userLoginAccount.setMemberAccount(memberAccount);
                    //判断会员是否是优粉，是则设置当前店铺ID为自己的店铺，否则为总店店铺
                    ShopVo shopInfo = shopService.getShopModelByUserId(memberAccount.getId());
                    if(shopInfo!=null){
                    	SessionUtils.setCurrentShopId(shopInfo.getId());
                    }else{
                    	SessionUtils.setCurrentShopId(null);
                    }
                }
            }
        }
        return userLoginAccount;
    }

    /**
     * 更新session中的用户信息
     *
     * @param loginAccountVo
     *         登陆执行结果
     *
     * @since 1.0 Created by lipangeng on 16/4/5 下午3:51. Email:lipg@outlook.com.
     */
    @Override
    public Result<SessionUserDetails> updateSessionUserDetails(UserLoginAccountVo loginAccountVo) {
        SessionUserDetails userDetails = new SessionUserDetails();
        if(loginAccountVo.getMemberAccount() == null){
        	return Result.create(false, "用户登录信息为空", null);
        }
        userDetails.setUserId(loginAccountVo.getMemberAccount().getId());
        userDetails.setUserName(loginAccountVo.getMemberAccount().getLoginName());
        userDetails.setAutoLogin(loginAccountVo.getUserLoginInfo().isAutoLogin());
        userDetails.setPhoneNumber(loginAccountVo.getMemberAccount().getRegisterCheckMobile());
        // 注入的是登录时的密码,加密后的
        userDetails.setPasswordEncode(PasswordCryptoUtils.encryptPassword(CryptoUtils.passwordMd5(loginAccountVo.getUserLoginInfo()
                                                                                                         .getLoginPassword())));
        //获取会员的分销商信息，保存分销商ID到session
        if(loginAccountVo.getMemberAccount().getSellerInfo()!=null){
        	userDetails.setSellerId(loginAccountVo.getMemberAccount().getSellerInfo().getId());
        }
        //判断会员是否是优粉，是则设置当前店铺ID为自己的店铺，否则为总店店铺
        if(loginAccountVo.getMemberAccount().getShopInfo()!=null){
        	SessionUtils.setCurrentShopId(loginAccountVo.getMemberAccount().getShopInfo().getId());
        }
        userDetails.save();
        return Result.create(true, "", userDetails);
    }

    /**
     * 更新记住我信息
     *
     * @param userDetails
     *         session中的用户信息
     *
     * @since 1.0 Created by lipangeng on 16/4/5 下午3:54. Email:lipg@outlook.com.
     */
    @Override
    public Result updateRememberMe(SessionUserDetails userDetails) {
        SessionUtils.setRememberMe(userDetails);
        return Result.create();
    }

	@Override
	public UserLoginAccountVo doWXLogin(WXUserInfoResponse userInfo) {
		// TODO Auto-generated method stub
		UserLoginAccountVo userLoginAccount = new UserLoginAccountVo();
		MemberForWXInputDto memberForWXInputDto = new MemberForWXInputDto();
		memberForWXInputDto.setH5OpenId(userInfo.getOpenid());
		memberForWXInputDto.setOpenId(userInfo.getUnionid());
		memberForWXInputDto.setHeadimgurl(userInfo.getHeadimgurl());
		if(!StringUtils.isEmpty(userInfo.getSex())){
			memberForWXInputDto.setSex(Integer.parseInt(userInfo.getSex()));
		}
		memberForWXInputDto.setNickname(userInfo.getNickname());
		MemberAccountOutputDto memberAccount = memberAccountFrontApi.wxMemberLogin(memberForWXInputDto, SessionUtils.getUserContext());
		if (memberAccount != null) {
            userLoginAccount.setLogined(true);
            userLoginAccount.setMemberAccount(memberAccount);
            SellerInfoOutputDto sellerInfo = sellerInfoFrontApi.getSellerByMemberId(memberAccount.getId());
            if(sellerInfo!=null){
            	userLoginAccount.getMemberAccount().setSellerInfo(sellerInfo);
            }
            
            //判断会员是否是优粉，是则设置当前店铺ID为自己的店铺，否则为总店店铺
            ShopVo shopInfo = shopService.getShopModelByUserId(memberAccount.getId());
            if(shopInfo!=null){
            	SessionUtils.setCurrentShopId(shopInfo.getId());
            }else{
            	SessionUtils.setCurrentShopId(null);
            }
        }
		return userLoginAccount;
	}
	
	@Override
	public WFXResult<MemberAccountOutputDto> saveWxUser(WXUserInfoResponse userInfo, String parentSellerId) {
		// TODO Auto-generated method stub
		MemberForWXInputDto memberForWXInputDto = new MemberForWXInputDto();
		memberForWXInputDto.setH5OpenId(userInfo.getOpenid());
		memberForWXInputDto.setOpenId(userInfo.getUnionid());
		memberForWXInputDto.setHeadimgurl(userInfo.getHeadimgurl());
		if(!StringUtils.isEmpty(userInfo.getSex())){
			memberForWXInputDto.setSex(Integer.parseInt(userInfo.getSex()));
		}
		memberForWXInputDto.setNickname(userInfo.getNickname());
		memberForWXInputDto.setParentSellerId(parentSellerId);
		WFXResult<MemberAccountOutputDto> result = memberAccountFrontApi.wxMemberRegister(memberForWXInputDto, SessionUtils.getUserContext());
		return result;
	}

	@Override
	public Result phoneSellerBind(String unionId, String phoneNum,
			String smsCode, String passWord) {
		// TODO Auto-generated method stub
		MemberAccountInputDto input = new MemberAccountInputDto();
		input.setOpenId(unionId);
		input.setLoginName(phoneNum);
		input.setValidateCode(smsCode);
		input.setLoginPassword(CryptoUtils.passwordMd5(passWord));
		memberAccountFrontApi.phoneSellerBindForApp(input, "h5", SessionUtils.getUserContext());
		//更新session信息
		SessionUserDetails loginUserDetails=  SessionUtils.getLoginUserDetails();
		loginUserDetails.setPhoneNumber(phoneNum);
		loginUserDetails.setUserName(phoneNum);
	    SessionUtils.setLoginUserDetails(loginUserDetails);
		return Result.create();
	}

	@Override
	public Result wxSellerBind(String unionId, String nickName, int sex,
			String headImgUrl, String memberId) {
		// TODO Auto-generated method stub
		MemberForWXInputDto input = new MemberForWXInputDto();
		input.setOpenId(unionId);
		input.setHeadimgurl(headImgUrl);
		input.setNickname(nickName);
		input.setSex(sex);
		input.setMemberId(memberId);
		WFXResult<MemberAccountOutputDto> result = memberAccountFrontApi.wxSellerBindForApp(input, "h5", SessionUtils.getUserContext());
		if(result.getResultCode()==ResultCodeEnum.SUCCESS.getKey()){
			return Result.create(true, result.getResultMsg(), result.getResult());
		}else{
			return Result.create(false, result.getResultMsg(), result.getResult());
		}
		
	}
	
	@Override
	public void doWithWXUserInfo(WXUserInfoResponse userInfo, String appId, String secret) {
		//保存微信授权的用户信息到session
		if(SessionUtils.getWXUserInfo()==null){
			SessionWXUserInfo sUserInfo = new SessionWXUserInfo();
			sUserInfo.setOpenid(userInfo.getOpenid());
			sUserInfo.setNickname(userInfo.getNickname());
			sUserInfo.setSex(userInfo.getSex());
			sUserInfo.setHeadimgurl(userInfo.getHeadimgurl());
			sUserInfo.setCity(userInfo.getCity());
			sUserInfo.setCountry(userInfo.getCountry());
			sUserInfo.setPrivilege(userInfo.getPrivilege());
			sUserInfo.setProvince(userInfo.getProvince());
			sUserInfo.setUnionid(userInfo.getUnionid());
			SessionUtils.setWXUserInfo(sUserInfo);
		}
		UserLoginAccountVo userLoginAccountVo = doWXLogin(userInfo);
		if(userLoginAccountVo.isLogined()){
			//有微信账号并有注册账号
			if(!StringUtils.isEmpty(userLoginAccountVo.getMemberAccount().getLoginName())){
				 //更新session中的用户信息
	        	SessionUserDetails userDetails = new SessionUserDetails();
	            userDetails.setUserId(userLoginAccountVo.getMemberAccount().getId());
	            userDetails.setUserName(userLoginAccountVo.getMemberAccount().getLoginName());
	            userDetails.setAutoLogin(false);
	            userDetails.setPhoneNumber(userLoginAccountVo.getMemberAccount().getLoginName());
	            // 注入的是登录时的密码,加密后的
	            userDetails.setPasswordEncode("");
	            //获取会员的分销商信息，保存分销商ID到session
	            if(userLoginAccountVo.getMemberAccount().getSellerInfo()!=null){
	            	userDetails.setSellerId(userLoginAccountVo.getMemberAccount().getSellerInfo().getId());
	            }
	            userDetails.save();
			}else{
				//有微信账号没有注册账号
	        	SessionUserDetails userDetails = new SessionUserDetails();
	            userDetails.setUserId(userLoginAccountVo.getMemberAccount().getId());
	            userDetails.setUserName(userLoginAccountVo.getMemberAccount().getPlatformUsername());
	            userDetails.setAutoLogin(false);
	            userDetails.setPhoneNumber("");
	            // 注入的是登录时的密码,加密后的
	            userDetails.setPasswordEncode("");
	            //获取会员的分销商信息，保存分销商ID到session
	            if(userLoginAccountVo.getMemberAccount().getSellerInfo()!=null){
	            	userDetails.setSellerId(userLoginAccountVo.getMemberAccount().getSellerInfo().getId());
	            }
	            userDetails.save();
			}
		} else {
			//既没有微信账号也没有注册账号
	        String shopId = SessionUtils.getCurrentShopIdOrDefault();
	        ShopVo shopVo = shopService.getShopModelById(shopId);
	        if(shopVo==null){
	        	logger.error("店铺ID:"+shopId+"不存在！");
	        	return;
	        }
	        SellerinfoVo sellerInfo = sellerService.getSellInfoById(shopVo.getSellerId());
	        if(sellerInfo==null){
	        	logger.error("店铺ID:"+shopId+"对应的分销商不存在！");
	        	return;
	        }
	        String parentSellerId = "";
	        if(StringUtils.isEmpty(sellerInfo.getParentId())){
	        	parentSellerId = "-1";
	        }else{
	        	parentSellerId = shopVo.getSellerId();
	        }
	        logger.error("微信注册锁粉---【微信昵称："+userInfo.getNickname()+"】---【unionid:"+userInfo.getUnionid()+"】---【上级分销商id:"+parentSellerId+"】");
	        WFXResult<MemberAccountOutputDto> result = saveWxUser(userInfo, parentSellerId);
	        
	        //发送新增粉丝（锁粉）微信通知
	        if(!parentSellerId.equals("-1")){
	        	try{
	        		MemberAccountOutputDto outDto = memberAccountFrontApi.getMemberAccountById(sellerInfo.getLoginaccountId());
	        		if(outDto!=null && !StringUtils.isEmpty(outDto.getH5OpenId())){
	        			TextMessageRequest request = new TextMessageRequest();
			 	        request.setToUser(outDto.getH5OpenId());
			 	        String msgContent = MessageFormat.format(WeiXinConstant.NEW_FANS_MESSAGE, userInfo.getNickname());
			 	        request.getText().setContent(msgContent);
			 	        Result<MessageResponse> r1 = wXService.sendTextMessage(wXService.getAccessToken(appId, secret), request);
			 	        if(r1.isSuccess()){
			 	        	logger.error("发送新增粉丝（锁粉）微信通知成功！");
			 	        }else{
			 	        	logger.error("发送新增粉丝（锁粉）微信通知失败!失败原因：" + r1.getMessage());
			 	        }
	        		}
		        }catch (Exception e){
		        	logger.error("发送新增粉丝（锁粉）微信通知失败：" + e);
		        }
	        }
	        if(result.getResultCode()==ResultCodeEnum.SUCCESS.getKey()){
	        	// 直接登录
	        	SessionUserDetails userDetails = new SessionUserDetails();
	            userDetails.setUserId(result.getResult().getId());
	            userDetails.setUserName(userInfo.getNickname());
	            userDetails.setAutoLogin(false);
	            userDetails.setPhoneNumber("");
	            // 注入的是登录时的密码,加密后的
	            userDetails.setPasswordEncode("");
	            //获取会员的分销商信息，保存分销商ID到session
	            SellerinfoVo sellerInfo1 = sellerService.getSellInfoByMemberId(result.getResult().getId());
	            if(sellerInfo1!=null){
	            	userDetails.setSellerId(sellerInfo1.getId());
	            }
	            //判断会员是否是优粉，是则设置当前店铺ID为自己的店铺，否则为总店店铺
	            ShopVo shopInfo = shopService.getShopModelByUserId(result.getResult().getId());
	            if(shopInfo!=null){
	            	SessionUtils.setCurrentShopId(shopInfo.getId());
	            }else{
	            	SessionUtils.setCurrentShopId(null);
	            }
	            userDetails.save();
	        }else{
	        	logger.error("保存微信用户信息失败：" + result.getResultMsg());
	        }
		}
	}
	
}
