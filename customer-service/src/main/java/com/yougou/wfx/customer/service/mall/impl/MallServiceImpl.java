package com.yougou.wfx.customer.service.mall.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yougou.wfx.appserver.vo.supply.BrandInfo;
import com.yougou.wfx.appserver.vo.supply.CategoryInfo;
import com.yougou.wfx.appserver.vo.supply.ShowImage;
import com.yougou.wfx.cms.api.front.ICarouselFigureFrontApi;
import com.yougou.wfx.cms.api.front.IGoodsMarketFrontApi;
import com.yougou.wfx.cms.dto.output.CarouselFigureOutputDto;
import com.yougou.wfx.cms.dto.output.HotBrandOutputDto;
import com.yougou.wfx.cms.dto.output.HotSaleCatOutputDto;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.mall.MallIndexVo;
import com.yougou.wfx.customer.model.mall.Notice;
import com.yougou.wfx.customer.service.mall.IMallService;
import com.yougou.wfx.enums.RedirectTypeEnum;
import com.yougou.wfx.notice.api.front.INoticeFrontApi;
import com.yougou.wfx.notice.dto.output.NoticeOutputDto;

/**
 * Created by wang.zf on 2016/6/20.
 */
@Service
public class MallServiceImpl implements IMallService {
	
	/**
	 * 货源市场接口
	 */
	@Autowired
	protected IGoodsMarketFrontApi goodsMarketFrontApi;
	@Autowired
	private INoticeFrontApi noticeFrontApi;
	@Autowired
	private ICarouselFigureFrontApi carouselFigureFrontApi;
	
	@Override
	public MallIndexVo getMallIndexVo() {
		MallIndexVo mall = new MallIndexVo();
		// 轮播图列表
		List<CarouselFigureOutputDto> cfList = carouselFigureFrontApi.queryH5IndexCarouselFigureList();
		if (cfList != null && cfList.size() > 0) {
			for (CarouselFigureOutputDto cf : cfList) {
				ShowImage img = new ShowImage();
				img.setId(cf.getId());
				img.setImageUrl(cf.getPicUrl());
				String jumpUrl = cf.getLinkUrl();
				String type = cf.getRedirectType();
				if(null != type && (RedirectTypeEnum.COMMODITY_DETAIL.getKey()+"").equals(type)){
					String shopId = SessionUtils.getCurrentShopIdOrDefault();
					jumpUrl = cf.getLinkUrl();
					if(StringUtils.isNotBlank(jumpUrl)){
						jumpUrl = jumpUrl.replace("/yougoushop/", "/"+shopId+"/");
					}
				}
				img.setJumpUrl(jumpUrl);
				mall.getShowImageList().add(img);
			}
		}
		
		//公告
		//需要后续增加接口
		List<NoticeOutputDto> noticeList = noticeFrontApi.getActiveNotice();
		if(null != noticeList && noticeList.size() > 0){
			for(NoticeOutputDto noticeDto:noticeList){
				Notice notice = new Notice();
				notice.setId(noticeDto.getId());
				notice.setJumpType(noticeDto.getRedirectType());
				notice.setJumpLink(noticeDto.getRedirectUrl());
				notice.setTitle(noticeDto.getTitle());
				mall.getNoticeList().add(notice);
			}
		}
		// 热门品牌列表
		List<HotBrandOutputDto> hotBrands = goodsMarketFrontApi.queryHotBrandList();
		List<Object> obj = new ArrayList<>();
		List<List<BrandInfo>> duList = new ArrayList<>();
		List<BrandInfo> brands = new ArrayList<>();
		if (hotBrands != null && hotBrands.size() > 0) {
			int brandIndex = 1;
			int brandPage = 1;
			for (HotBrandOutputDto hotBrand : hotBrands) {
				BrandInfo brand = new BrandInfo();
				brand.setId(hotBrand.getId());
				brand.setBrandNo(hotBrand.getBrandNo());
				brand.setName(hotBrand.getBrandName());
				brand.setImageUrl(hotBrand.getMobilePic());
				brands.add(brand);
				if(brandIndex%4 == 0){
					duList.add(brands);
					brands = new ArrayList();
					brandPage++;
				}
				brandIndex++;
				if(brandPage%3 == 0){
					obj.add(duList);
					duList = new ArrayList();
					brandPage = 1;
				}
			}
		}
		if(brands.size() > 0 || duList.size() > 0){
			duList.add(brands);
			obj.add(duList);
		}
		mall.setBrandList(obj);

		// 热门分类
		List<HotSaleCatOutputDto> hotCats = goodsMarketFrontApi.queryHotSaleCatList();
		if (hotCats != null && hotCats.size() > 0) {
			for (HotSaleCatOutputDto hotCat : hotCats) {
				CategoryInfo ci = new CategoryInfo();
				ci.setId(hotCat.getId());
				ci.setImageUrl(hotCat.getPicUrl());
				ci.setName(hotCat.getName());
				mall.getCatgoryList().add(ci);
			}
		}
		return mall;
	}
}
