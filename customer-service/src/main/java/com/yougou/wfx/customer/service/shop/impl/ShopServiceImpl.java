package com.yougou.wfx.customer.service.shop.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yougou.wfx.commodity.api.front.ICommodityStyleFrontApi;
import com.yougou.wfx.commodity.dto.input.CommodityStyleOrderInputDto;
import com.yougou.wfx.commodity.dto.output.CommodityStyleFilterOutputDto;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.mall.PropertyVo;
import com.yougou.wfx.customer.model.shop.ShopCatVo;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.customer.service.order.IOrderService;
import com.yougou.wfx.customer.service.shop.IShopService;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.SellerStateEnum;
import com.yougou.wfx.member.api.front.IMemberAccountFrontApi;
import com.yougou.wfx.member.dto.output.MemberAccountOutputDto;
import com.yougou.wfx.seller.api.front.ISellerInfoFrontApi;
import com.yougou.wfx.seller.dto.output.SellerInfoOutputDto;
import com.yougou.wfx.shop.api.front.IShopFrontApi;
import com.yougou.wfx.shop.dto.output.ShopCatOutputDto;
import com.yougou.wfx.shop.dto.output.ShopOutputDto;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/28
 */
@Service
public class ShopServiceImpl implements IShopService {

	private static final Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);
	@Autowired
	private IMemberAccountFrontApi memberAccountFrontApi;
	@Autowired
	private IShopFrontApi shopFrontApi;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private ISellerInfoFrontApi sellerInfoFrontApi;
	@Autowired
	private ICommodityStyleFrontApi commodityStyleFrontApi;

	/**
	 * 根据店铺id获取店铺信息
	 *
	 * @param shopId
	 *            店铺id
	 * @return
	 */
	@Override
	public ShopVo getShopModelById(String shopId) {
		if (StringUtils.isEmpty(shopId))
			return null;
		ShopOutputDto dto = shopFrontApi.getShopById(shopId, Boolean.FALSE);
		if (dto == null)
			return null;
		return this.transform(dto);
	}

	@Override
	public ShopVo getShopBySellerId(String sellerId) {
		if (StringUtils.isEmpty(sellerId))
			return null;
		ShopOutputDto dto = shopFrontApi.getShopBySeller(sellerId);
		if (dto == null)
			return null;
		return this.transform(dto);
	}

	/**
	 * 获取店铺下的分类信息
	 *
	 * @param shopId
	 * @return
	 */
	@Override
	public List<ShopCatVo> getShopCatByShopId(String shopId) {
		if (StringUtils.isEmpty(shopId))
			return null;
		List<ShopCatOutputDto> shopCatOutputDtos = shopFrontApi.getShopCatByShopId(shopId);
		return transform(shopCatOutputDtos);
	}

	/**
	 * 申请开店之前判断用户是否有成功购买行为
	 *
	 * @return
	 */
	@Override
	public boolean checkEnableCreateShop() {
		return orderService.existsValidOrders();
	}

	/**
	 * 检查是否当前登录用户已经创建过店铺
	 *
	 * @return
	 */
	@Override
	public Result<Boolean> checkHasCreateShop() {
		SessionUserDetails user = SessionUtils.getLoginUserDetails();
		MemberAccountOutputDto dto = memberAccountFrontApi.getMemberAccountById(user.getUserId());
		SellerInfoOutputDto sellerInfoDto = dto.getSellerInfo();
		if (sellerInfoDto == null)
			return Result.create(Boolean.TRUE, "", Boolean.TRUE);
		Result<Boolean> result = null;
		switch (sellerInfoDto.getState()) {
		case "1":
			result = Result.create(Boolean.FALSE, "您的店铺申请正在审核中", Boolean.FALSE);
			break;
		case "2":
			result = Result.create(Boolean.TRUE, "", Boolean.TRUE);
			break;
		case "3":
			result = Result.create(Boolean.FALSE, "您的店铺正在运营，不可重复申请", Boolean.FALSE);
			break;
		case "4":
			result = Result.create(Boolean.FALSE, "优购微店已取消与您的合作，如有疑问，请拨打客服电话", Boolean.FALSE);
			break;
		default:
			result = Result.create(Boolean.FALSE, "申请开店异常，请拨打客服电话", Boolean.FALSE);
			break;
		}
		return result;
	}

	@Override
	public Map<String, String> getShopDefaultImagesUrl() {
		Map<String, String> imgMap = Maps.newHashMap();
		imgMap.put(WfxConstant.WFX_SHOP_LOGO_URL_DEFALUT, "");
		imgMap.put(WfxConstant.WFX_SHOP_SIGN_URL_DEFALUT, "");
		Map<String, String> imgs = shopFrontApi.getShopDefaultImagesUrl();
		if (!CollectionUtils.isEmpty(imgs)) {
			if (imgs.containsKey(WfxConstant.WFX_SHOP_LOGO_URL_DEFALUT)) {
				imgMap.put(WfxConstant.WFX_SHOP_LOGO_URL_DEFALUT, imgs.get(WfxConstant.WFX_SHOP_LOGO_URL_DEFALUT));
			}
			if (imgs.containsKey(WfxConstant.WFX_SHOP_SIGN_URL_DEFALUT)) {
				imgMap.put(WfxConstant.WFX_SHOP_SIGN_URL_DEFALUT, imgs.get(WfxConstant.WFX_SHOP_SIGN_URL_DEFALUT));
			}
		}
		return imgMap;
	}

	@Override
	public ShopVo getAndCheckShopById(String shopId, HttpServletRequest request) {
		Object commodityNo = request.getAttribute("commodityNo");
		ShopVo shopVo = getShopModelById(shopId);
		if (shopVo == null) {
			request.setAttribute(WfxConstant.WFX_ERROR_MESSAGE_KEY, "店铺信息异常");
			if (!ObjectUtils.isEmpty(commodityNo))
				request.setAttribute("url", String.format("%s/item/%s", SessionUtils.getCurrentShopIdOrDefault(), commodityNo));
			SessionUtils.setCurrentShopId(SessionUtils.getCurrentShopIdOrDefault());
		}
		Assert.notNull(shopVo, String.format("获取店铺%s信息异常", shopId));
		if (1 != shopVo.getStatus()) {
			request.setAttribute(WfxConstant.WFX_ERROR_MESSAGE_KEY, "该店铺已被关闭");
			if (!ObjectUtils.isEmpty(commodityNo))
				request.setAttribute("url", String.format("%s/item/%s", SessionUtils.getCurrentShopIdOrDefault(), commodityNo));
			SessionUtils.setCurrentShopId(SessionUtils.getCurrentShopIdOrDefault());
		}
		Assert.isTrue(1 == shopVo.getStatus(), String.format("该店铺已被关闭%s", shopId));
		SessionUtils.setCurrentShopId(shopId);
		return shopVo;
	}

	private ShopVo transform(ShopOutputDto dto) {
		ShopVo shopVo = new ShopVo();
		shopVo.setId(dto.getId());
		shopVo.setName(dto.getName());
		shopVo.setContact(dto.getContact());
		shopVo.setLoginName(dto.getLoginName());
		shopVo.setLogoUrl(dto.getLogoUrl());
		shopVo.setMobile(dto.getMobile());
		shopVo.setNotice(dto.getNotice());
		shopVo.setQrCodeUrl(dto.getQrCodeUrl());
		shopVo.setSellerId(dto.getSellerId());
		shopVo.setShopUrl(dto.getShopUrl());
		shopVo.setSigUrl(dto.getSignUrl());
		shopVo.setStatus(dto.getStatus());
		shopVo.setShopCode(dto.getShopCode());
		return shopVo;
	}

	private List<ShopCatVo> transform(List<ShopCatOutputDto> shopCatOutputDtos) {
		if (CollectionUtils.isEmpty(shopCatOutputDtos))
			return null;
		List<ShopCatVo> shopCatVos = Lists.newArrayList();
		for (ShopCatOutputDto shopCatOutputDto : shopCatOutputDtos) {
			shopCatVos.add(transform(shopCatOutputDto));
		}
		return shopCatVos;
	}

	private ShopCatVo transform(ShopCatOutputDto shopCatOutputDto) {
		ShopCatVo catVo = new ShopCatVo();
		catVo.setId(shopCatOutputDto.getId());
		catVo.setLevel(shopCatOutputDto.getLevel());
		catVo.setSellerId(shopCatOutputDto.getSellerId());
		catVo.setName(shopCatOutputDto.getName());
		catVo.setNum(shopCatOutputDto.getNum());
		if (!CollectionUtils.isEmpty(shopCatOutputDto.getChilds())) {
			catVo.setChilds(new HashSet<ShopCatVo>(shopCatOutputDto.getChilds().size()));
			for (ShopCatOutputDto dto : shopCatOutputDto.getChilds()) {
				ShopCatVo item = new ShopCatVo();
				item.setId(dto.getId());
				item.setLevel(dto.getLevel());
				item.setSellerId(dto.getSellerId());
				item.setName(dto.getName());
				item.setNum(dto.getNum());
				catVo.getChilds().add(item);
			}
		}
		return catVo;
	}

	@Override
	public ShopVo getShopModelByUserId(String userId) {

		SellerInfoOutputDto sellerDto = sellerInfoFrontApi.getSellerByMemberId(userId);
		if(sellerDto == null || !sellerDto.getState().equals(SellerStateEnum.IN_COOPERATING.getState())){
			return null;
		}
		ShopOutputDto shopDto = shopFrontApi.getShopByMemberId(userId);
		if (shopDto == null) {
			return null;
		}
		return this.transform(shopDto);
	}

	@Override
	public ShopVo getShopByLoginName(String loginName) {
		ShopOutputDto shopdto = shopFrontApi.getShopByPhoneNumber(loginName);
		if (shopdto == null) {
			return null;
		}
		return this.transform(shopdto);
	}

	@Override
	public List<PropertyVo> getPropertys(String type,String shopId,String catId,String brandNo) {
		List<PropertyVo> proList = new ArrayList<PropertyVo>();
		if(org.apache.commons.lang3.StringUtils.isNotBlank(type)){
			CommodityStyleOrderInputDto inputDto = new CommodityStyleOrderInputDto();
			inputDto.setShopId(shopId);
			inputDto.setCatId(catId);
			inputDto.setBrandNo(brandNo);
			List<CommodityStyleFilterOutputDto> resList = null;
			if("catgory".equals(type)){
				resList = commodityStyleFrontApi.getCatInfo(inputDto);
				if(null != resList && resList.size() > 0){
					for(CommodityStyleFilterOutputDto pro:resList){
						PropertyVo proVo = new PropertyVo();
						proVo.setId(pro.getCatNo());
						proVo.setName(pro.getCatName());
						proList.add(proVo);
					}
				}
			}else if("brand".equals(type)){
				resList = commodityStyleFrontApi.getBrandInfo(inputDto);
				if(null != resList && resList.size() > 0){
					for(CommodityStyleFilterOutputDto pro:resList){
						PropertyVo proVo = new PropertyVo();
						proVo.setId(pro.getBrandNo());
						proVo.setName(pro.getBrandName());
						proList.add(proVo);
					}
				}
			}else if("sex".equals(type)){
				inputDto.setPropItemName("性别");
				resList = commodityStyleFrontApi.getPropInfo(inputDto);
				if(null != resList && resList.size() > 0){
					for(CommodityStyleFilterOutputDto pro:resList){
						PropertyVo proVo = new PropertyVo();
						proVo.setId(pro.getPropValueNo());
						proVo.setName(pro.getPropValue());
						proList.add(proVo);
					}
				}
			}else if("price".equals(type)){
				List<String> prices = commodityStyleFrontApi.getPriceInfo(inputDto);
				if(null != prices && prices.size() > 0){
					for(String price:prices){
						if(!StringUtils.isEmpty(price)){
							PropertyVo proVo = new PropertyVo();
							proVo.setId(price);
							proVo.setName(price);
							proList.add(proVo);
						}
					}
				}
			}else if("size".equals(type)){
				resList = commodityStyleFrontApi.getSizeInfo(inputDto);
				if(null != resList && resList.size() > 0){
					for(CommodityStyleFilterOutputDto pro:resList){
						PropertyVo proVo = new PropertyVo();
						proVo.setId(pro.getSizeNo());
						proVo.setName(pro.getSizeName());
						proList.add(proVo);
					}
				}
			}else if("color".equals(type)){
				resList = commodityStyleFrontApi.getSpecInfo(inputDto);
				if(null != resList && resList.size() > 0){
					for(CommodityStyleFilterOutputDto pro:resList){
						PropertyVo proVo = new PropertyVo();
						proVo.setId(pro.getSpecName());
						proVo.setName(pro.getSpecName());
						proList.add(proVo);
					}
				}
			}else if("style".equals(type)){
				inputDto.setPropItemName("款式");
				resList = commodityStyleFrontApi.getPropInfo(inputDto);
				if(null != resList && resList.size() > 0){
					for(CommodityStyleFilterOutputDto pro:resList){
						PropertyVo proVo = new PropertyVo();
						proVo.setId(pro.getPropValueNo());
						proVo.setName(pro.getPropValue());
						proList.add(proVo);
					}
				}
			}else if("manner".equals(type)){
				inputDto.setPropItemName("风格");
				resList = commodityStyleFrontApi.getPropInfo(inputDto);
				if(null != resList && resList.size() > 0){
					for(CommodityStyleFilterOutputDto pro:resList){
						PropertyVo proVo = new PropertyVo();
						proVo.setId(pro.getPropValueNo());
						proVo.setName(pro.getPropValue());
						proList.add(proVo);
					}
				}
			}
		}
		return proList;
	}
}
