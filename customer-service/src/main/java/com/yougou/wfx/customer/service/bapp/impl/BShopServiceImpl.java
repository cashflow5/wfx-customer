package com.yougou.wfx.customer.service.bapp.impl;

import org.springframework.stereotype.Service;

import com.yougou.wfx.appserver.vo.BooleanResult;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.appserver.vo.shop.ShopInfo;
import com.yougou.wfx.appserver.vo.shop.ShopInfoDetail;
import com.yougou.wfx.customer.service.bapp.IBShopService;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.finance.dto.output.FinSellerInfoOutputDto;
import com.yougou.wfx.seller.dto.output.SellerInfoOutputDto;
import com.yougou.wfx.shop.dto.input.ShopInputDto;
import com.yougou.wfx.shop.dto.output.ShopOutputDto;

/**
 * Created by lizhw on 2016/4/11.
 */
@Service
public class BShopServiceImpl extends BaseServiceImpl implements IBShopService {

	@Override
	public com.yougou.wfx.appserver.vo.home.ShopInfo getShop(UserInfo userInfo, boolean b) {

		com.yougou.wfx.appserver.vo.home.ShopInfo shopInfo = new com.yougou.wfx.appserver.vo.home.ShopInfo();
		String shopId = userInfo.getShopId();
		String sellerId = userInfo.getSellerId();
		if (isBlank(shopId) || isBlank(sellerId)) {
			return shopInfo;
		}

		ShopOutputDto shop = shopFrontApi.getShopBySeller(sellerId);

		FinSellerInfoOutputDto seller = finSellerInfoFrontApi.getById(sellerId);

		if (shop != null) {
			// 店铺id
			shopInfo.setId(shop.getId());
			// 店招
			shopInfo.setRecruitPic(shop.getSignUrl());
			// 店铺名称
			shopInfo.setName(shop.getName());
			// logo
			shopInfo.setLogo(shop.getLogoUrl());
			// 店铺编码
			shopInfo.setShopCode(shop.getShopCode());
		}
		if (seller != null) {
			// 可提现金额
			shopInfo.setCanCash(toDouble(seller.getAccountBalance()));
		}

		// ok:7日订单
		int sevenDayCount = orderFrontApi.getSellerSevenDayOrderCount(sellerId);
		shopInfo.setSevenDayOrderCount(sevenDayCount);
		// ok:今日访客
		int uv = shopFrontApi.getShopVisitCountById(shopId);
		shopInfo.setTodayVisitorCount(uv);

		// ok:总收益（累计收益,佣金收益）：佣金收益=预估佣金+已结算佣金：
		Double amount = finSellerInfoFrontApi.getTotalCommissionAmount(sellerId);// 已结算佣金
		Double amount1 = orderFrontApi.getAllPreCommission(sellerId);// 预估佣金
		shopInfo.setTotalProfit(addDouble(toDouble(amount), toDouble(amount1)));

		// ok:今日收益:今日收益=今日订单的预估佣金+今日已结算佣金
		Double todayAmount = finSellerInfoFrontApi.getTodayCommissionAmount(sellerId);// 今日已结算佣金
		Double todayAmount2 = orderFrontApi.getTodayCommission(sellerId);// 预估佣金
		shopInfo.setTodayProfit(addDouble(toDouble(todayAmount), toDouble(todayAmount2)));
		return shopInfo;
	}

	// ok
	@Override
	public ShopInfoDetail getShopDetailInfo(UserInfo userInfo) {
		// 店铺名称、店铺LOGO、店招信息、店铺公告、联系人、联系人电话、店铺的URL、店铺的二维码信息
		ShopInfoDetail result = new ShopInfoDetail();
		String shopId = userInfo.getShopId();
		String sellerId = userInfo.getSellerId();
		if (isBlank(shopId) || isBlank(sellerId)) {
			return result;
		}
		ShopOutputDto shop = shopFrontApi.getShopBySeller(sellerId);
		SellerInfoOutputDto sellerInfo = sellerInfoFrontApi.getSellerInfoById(sellerId);

		if (shop != null) {
			result.setId(shop.getId());
			// 店铺编号
			result.setStoreNum(shop.getId());
			result.setStoreName(shop.getName());
			result.setContacts(shop.getContact());
			// 店招图片
			result.setStoreZhao(shop.getSignUrl());
			result.setStoreNotice(shop.getNotice());
			result.setPhone(shop.getMobile());
			result.setStoreHeaderImg(shop.getLogoUrl());
			result.setStoreUrl(shop.getShopUrl());
			result.setShopCode(shop.getShopCode());

		}
		if (sellerInfo != null) {
			// 分销商名称
			result.setDistributors(sellerInfo.getSellerName());
		}

		return result;
	}

	@Override
	public BooleanResult setShopInfo(ShopInfo shopInfo) {
		BooleanResult result = new BooleanResult();
		String shopId = getShopId(shopInfo.getUserInfo());
		String sellerId = shopInfo.getUserInfo().getSellerId();
		if (isBlank(shopId)) {
			result.setError("店铺不存在！");
			return result;
		}

		ShopInputDto inputDto = new ShopInputDto();
		inputDto.setId(shopId);
		inputDto.setSellerId(sellerId);
		if (!isBlank(shopInfo.getNotice())) {
			result.setResult(false);
			result.setMsg("抱歉，公告暂不能修改");
			return result;
			// inputDto.setNotice(shopInfo.getNotice());
		}
		if (!isBlank(shopInfo.getContact())) {
			inputDto.setContact(shopInfo.getContact());
		}
		if (!isBlank(shopInfo.getPhone())) {
			inputDto.setMobile(shopInfo.getPhone());
		}
		WFXResult<Boolean> wfxResult = shopFrontApi.updateShop(inputDto);

		setBoolResult(result, wfxResult);

		return result;
	}

	@Override
	public ShopInfoDetail getShopDetailInfoByLoginName(String loginName) {
		ShopInfoDetail result = new ShopInfoDetail();
		ShopOutputDto shop = shopFrontApi.getShopByPhoneNumber(loginName);
		if (shop != null) {
			result.setId(shop.getId());
			result.setStoreName(shop.getName());
			result.setStoreNotice(shop.getNotice());
			result.setStoreHeaderImg(shop.getLogoUrl());
			result.setShopCode(shop.getShopCode());
		}

		return result;
	}

	@Override
	public ShopOutputDto getShopByMemberId(String memberId){
		return shopFrontApi.getShopByMemberId(memberId);
	}

	@Override
	public com.yougou.wfx.appserver.vo.home.ShopInfo getShop(ShopOutputDto shop, boolean b) {
		String sellerId = shop.getSellerId() ;
		com.yougou.wfx.appserver.vo.home.ShopInfo shopInfo = new com.yougou.wfx.appserver.vo.home.ShopInfo();
		
		FinSellerInfoOutputDto seller = finSellerInfoFrontApi.getById(sellerId );
		// 店铺id
		shopInfo.setId(shop.getId());
		// 店招
		shopInfo.setRecruitPic(shop.getSignUrl());
		// 店铺名称
		shopInfo.setName(shop.getName());
		// logo
		shopInfo.setLogo(shop.getLogoUrl());
		// 店铺编码
		shopInfo.setShopCode(shop.getShopCode());
		shopInfo.setPhoneNumber( shop.getLoginName() );
		if (seller != null) {
			// 可提现金额
			shopInfo.setCanCash(toDouble(seller.getAccountBalance()));
		}
		// ok:7日订单
		int sevenDayCount = orderFrontApi.getSellerSevenDayOrderCount(sellerId);
		shopInfo.setSevenDayOrderCount(sevenDayCount);
		// ok:今日访客
		int uv = shopFrontApi.getShopVisitCountById(shop.getId());
		shopInfo.setTodayVisitorCount(uv);

		// ok:总收益（累计收益,佣金收益）：佣金收益=预估佣金+已结算佣金：
		Double amount = finSellerInfoFrontApi.getTotalCommissionAmount(sellerId);// 已结算佣金
		Double amount1 = orderFrontApi.getAllPreCommission(sellerId);// 预估佣金
		shopInfo.setTotalProfit(addDouble(toDouble(amount), toDouble(amount1)));

		// ok:今日收益:今日收益=今日订单的预估佣金+今日已结算佣金
		Double todayAmount = finSellerInfoFrontApi.getTodayCommissionAmount(sellerId);// 今日已结算佣金
		Double todayAmount2 = orderFrontApi.getTodayCommission(sellerId);// 预估佣金
		shopInfo.setTodayProfit(addDouble(toDouble(todayAmount), toDouble(todayAmount2)));
		
		// 粉丝数目
		Integer fansCount = memberAccountFrontApi.queryFansListCount(sellerId);
		if(null==fansCount){
			fansCount = 0 ;
		}
		shopInfo.setFansCount(fansCount);
		return shopInfo;
	}

	@Override
	public WFXResult<String> generateQrCode(String wxCodeUrl,
			ShopOutputDto shopOutputDto) {
		return shopFrontApi.generateQrCode(wxCodeUrl, shopOutputDto);
	}
}
