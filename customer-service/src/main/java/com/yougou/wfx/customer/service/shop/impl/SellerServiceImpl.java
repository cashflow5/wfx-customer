package com.yougou.wfx.customer.service.shop.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yougou.wfx.customer.common.enums.UserTypeEnum;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.seller.SellerinfoVo;
import com.yougou.wfx.customer.model.shop.ShopCatVo;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.customer.service.shop.ISellerService;
import com.yougou.wfx.customer.service.shop.IShopService;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.member.api.front.IMemberAccountFrontApi;
import com.yougou.wfx.member.dto.output.MemberAccountOutputDto;
import com.yougou.wfx.seller.api.front.ISellerInfoFrontApi;
import com.yougou.wfx.seller.dto.input.SellerInfoInputDto;
import com.yougou.wfx.seller.dto.output.SellerInfoOutputDto;
import com.yougou.wfx.shop.api.front.IShopFrontApi;
import com.yougou.wfx.shop.dto.input.ShopInputDto;
import com.yougou.wfx.shop.dto.output.ShopOutputDto;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/29
 */
@Service
public class SellerServiceImpl implements ISellerService {

    private static final Logger log = LoggerFactory.getLogger(SellerServiceImpl.class);

    @Autowired
    private ISellerInfoFrontApi sellerInfoFrontApi;

    @Autowired
    private IShopFrontApi shopFrontApi;

    @Autowired
    private IShopService shopService;
    @Autowired
    private IMemberAccountFrontApi memberAccountFrontApi;

    /**
     * 根据分销商id获取店铺销售分类信息
     *
     * @param id
     * @return
     */
    @Override
    public List<ShopCatVo> getShopCatByShopId(String id) {
        return null;
    }

    /**
     * 创建店铺接口
     *
     * @param shopVo       创建新店铺信息
     * @param parentShopId 上级分销商id
     * @return
     */
    @Override
    public Result<String> createShop(ShopVo shopVo, String parentShopId) {
        Result<String> result = Result.create();
        /*WFXResult<String> wfxResult = shopFrontApi.checkSensitiveWord(",", shopVo.getName());
        if (wfxResult != null &&!StringUtils.isEmpty(wfxResult.getResult())) {
            return result.setHasError(Boolean.TRUE).setMessage("店铺名称不能包含\""+wfxResult.getResult()+"\"敏感词！");
        }
        WFXResult<Boolean> checkResult = shopFrontApi.checkShopName(shopVo.getName());
        if (checkResult == null || !checkResult.getResult()) {
            return result.setHasError(Boolean.TRUE).setMessage("店铺名称已存在！");
        }*/
        ShopOutputDto parentShop = shopFrontApi.getShopById(parentShopId, Boolean.FALSE);
        if (parentShop == null)
            return result.setHasError(Boolean.TRUE).setMessage("父级店铺信息异常！");
        SessionUserDetails sessionUserDetails = SessionUtils.getLoginUserDetails();
        // TODO: 2016/3/30 具体的创建店铺逻辑
//        Map<String, String> shopImgmap = shopService.getShopDefaultImagesUrl();
        SellerInfoInputDto infoInputDto = new SellerInfoInputDto();
        infoInputDto.setParentId(parentShop.getSellerId());
        infoInputDto.setLoginaccountId(sessionUserDetails.getUserId());
        infoInputDto.setLoginName(sessionUserDetails.getPhoneNumber());
        infoInputDto.setSellerName(shopVo.getContact());
        ShopInputDto shopInputDto = new ShopInputDto();
        shopInputDto.setContact(shopVo.getContact());
        //shopInputDto.setName(shopVo.getName());
        //服务端指定默认图片
//        shopInputDto.setLogoUrl(shopImgmap.get(WfxConstant.WFX_SHOP_LOGO_URL_DEFALUT));
//        shopInputDto.setSignUrl(shopImgmap.get(WfxConstant.WFX_SHOP_SIGN_URL_DEFALUT));
        shopInputDto.setMobile(shopVo.getMobile());
        //新建店铺默认开启
        shopInputDto.setStatus(2);
        shopInputDto.setLoginName(sessionUserDetails.getPhoneNumber());
        infoInputDto.setShopInputDto(shopInputDto);
        //服务内部先创建分销商然后设置id
//        shopInputDto.setSellerId();
        WFXResult<Boolean> applyResult = null;
        try {
            applyResult = sellerInfoFrontApi.applyToSeller(infoInputDto);
        } catch (Exception e) {
            log.error("申请开店失败", e);
            result.setHasError(Boolean.TRUE).setMessage("接口调用异常!");
        }
        if (applyResult == null || !applyResult.getResult())
            return result.setHasError(Boolean.TRUE).setMessage("接口调用异常!");
        return result.setSuccess(applyResult.getResult()).setCode(applyResult.getResultCode()).setMessage(applyResult.getResultMsg());
    }

    /**
     * 获取分销商信息
     *
     * @param sellerId
     * @return
     */
    @Override
    public SellerinfoVo getSellInfoById(String sellerId) {
        SellerInfoOutputDto dto = sellerInfoFrontApi.getSellerInfoById(sellerId);
        if (dto == null)
            return null;
        SellerinfoVo sellerinfoVo = new SellerinfoVo();
        sellerinfoVo.setId(dto.getId());
        sellerinfoVo.setShopName(dto.getShopName());
        sellerinfoVo.setBirthday(dto.getBirthday());
        sellerinfoVo.setLoginaccountId(dto.getLoginaccountId());
        sellerinfoVo.setMemberName(dto.getMemberName());
        sellerinfoVo.setMemberSex(dto.getMemberSex());
        sellerinfoVo.setParentId(dto.getParentId());
        sellerinfoVo.setSellerName(dto.getSellerName());
        sellerinfoVo.setState(dto.getState());
        return sellerinfoVo;
    }
    
    /**
     * 根据会员ID获取分销商信息
     *
     * @param sellerId
     * @return
     */
    @Override
    public SellerinfoVo getSellInfoByMemberId(String memberId) {
        SellerInfoOutputDto dto = sellerInfoFrontApi.getSellerByMemberId(memberId);
        if (dto == null)
            return null;
        SellerinfoVo sellerinfoVo = new SellerinfoVo();
        sellerinfoVo.setId(dto.getId());
        sellerinfoVo.setShopName(dto.getShopName());
        sellerinfoVo.setBirthday(dto.getBirthday());
        sellerinfoVo.setLoginaccountId(dto.getLoginaccountId());
        sellerinfoVo.setMemberName(dto.getMemberName());
        sellerinfoVo.setMemberSex(dto.getMemberSex());
        sellerinfoVo.setParentId(dto.getParentId());
        sellerinfoVo.setSellerName(dto.getSellerName());
        sellerinfoVo.setState(dto.getState());
        return sellerinfoVo;
    }
    
    @Override
    public UserTypeEnum getMemberTypeByMemberId(String memberId){
    	SellerInfoOutputDto dto = sellerInfoFrontApi.getSellerByMemberId(memberId);
    	MemberAccountOutputDto out = memberAccountFrontApi.getMemberAccountById(memberId);
    	if(dto!=null){
    		if(!StringUtils.isEmpty(out.getOpenId())&&StringUtils.isEmpty(out.getLoginName())){
    			//微信注册,账号没注册
    			return UserTypeEnum.MEMBER_AND_AGENT_1_2;
    		}else if(StringUtils.isEmpty(out.getOpenId())&&!StringUtils.isEmpty(out.getLoginName())){
    			//微信没注册,账号注册
    			return UserTypeEnum.MEMBER_AND_AGENT_1_1;
    		}else{
    			//微信注册,账号注册
    			return UserTypeEnum.MEMBER_AND_AGENT_1_3;
    		}
    	}else{
    		if(!StringUtils.isEmpty(out.getOpenId())&&StringUtils.isEmpty(out.getLoginName())){
    			//微信注册,账号没注册
    			return UserTypeEnum.MEMBER_NO_AGENT_0_2;
    		}else if(StringUtils.isEmpty(out.getOpenId())&&!StringUtils.isEmpty(out.getLoginName())){
    			//微信没注册,账号注册
    			return UserTypeEnum.MEMBER_NO_AGENT_0_1;
    		}else{
    			//微信注册,账号注册
    			return UserTypeEnum.MEMBER_NO_AGENT_0_3;
    		}
    	}
    }
}
