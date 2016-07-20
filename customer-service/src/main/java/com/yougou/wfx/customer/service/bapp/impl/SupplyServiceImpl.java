package com.yougou.wfx.customer.service.bapp.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yougou.wfx.appserver.vo.BooleanResult;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.appserver.vo.supply.BrandInfo;
import com.yougou.wfx.appserver.vo.supply.CategoryInfo;
import com.yougou.wfx.appserver.vo.supply.ShowImage;
import com.yougou.wfx.appserver.vo.supply.Style;
import com.yougou.wfx.appserver.vo.supply.SupplyMarketInfo;
import com.yougou.wfx.cms.dto.output.CarouselFigureOutputDto;
import com.yougou.wfx.cms.dto.output.HotBrandOutputDto;
import com.yougou.wfx.cms.dto.output.HotSaleCatOutputDto;
import com.yougou.wfx.customer.service.bapp.ISupplyService;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.ResultCodeEnum;

/**
 * Created by lizhw on 2016/4/12.
 */
@Service
public class SupplyServiceImpl extends BaseServiceImpl implements ISupplyService {
	// ok
	@Override
	public SupplyMarketInfo getSupplyMarketInfo() {
		SupplyMarketInfo result = new SupplyMarketInfo();
		// 图片列表
		List<CarouselFigureOutputDto> carousel = goodsMarketFrontApi.queryCarouselFigureList();
		if (carousel != null && carousel.size() > 0) {
			for (CarouselFigureOutputDto c : carousel) {
				ShowImage img = new ShowImage();
				img.setId(c.getId());
				img.setImageUrl(c.getPicUrl());
				result.getShowImages().add(img);
			}

		}

		// 热门品牌列表
		List<HotBrandOutputDto> hotBrands = goodsMarketFrontApi.queryHotBrandList();
		if (hotBrands != null && hotBrands.size() > 0) {
			List<BrandInfo> brands = new ArrayList<>();
			for (HotBrandOutputDto hotBrand : hotBrands) {
				BrandInfo brand = new BrandInfo();
				brand.setId(hotBrand.getId());
				brand.setBrandNo(hotBrand.getBrandNo());
				brand.setName(hotBrand.getBrandName());
				brand.setImageUrl(hotBrand.getMobilePic());
				brands.add(brand);
				// 每8个一组
				if (brands.size() == 8) {
					result.getBrands().add(brands);
					brands = new ArrayList<>();
				}
			}
			// 每8个一组，最后一组不足8个
			if (brands.size() > 0) {
				result.getBrands().add(brands);
			}
		}

		// 热门分类
		List<HotSaleCatOutputDto> hotCats = goodsMarketFrontApi.queryHotSaleCatList();
		if (hotCats != null && hotCats.size() > 0) {
			for (HotSaleCatOutputDto hotCat : hotCats) {
				CategoryInfo c = new CategoryInfo();
				c.setId(hotCat.getId());
				c.setImageUrl(hotCat.getPicUrl());
				c.setName(hotCat.getName());
				result.getFirstCategorys().add(c);
			}
		}
		return result;
	}

	// ok
	@Override
	public BooleanResult setProxyProduct(Style style) {
		BooleanResult result = new BooleanResult();
		List<String> styleIds = new ArrayList<>();
		String styleId = style.getStyleIds();
		if (isBlank(styleId)) {
			result.setError("styleIds不能为空！");
			return result;
		}
		String[] split = styleId.split("[|]");
		if (split != null) {
			for (String s : split) {
				if (!isBlank(s)) {
					styleIds.add(s);
				}

			}
		}
		if (styleIds == null || styleIds.size() == 0) {
			result.setError("styleIds不能为空！");
			return result;
		}

		WFXResult<Integer> integerWFXResult = goodsMarketFrontApi.batchProxyCommodity(style.getUserInfo().getSellerId(), styleIds);
		if (integerWFXResult == null) {
			return result;
		}
		if (integerWFXResult.getResult() != null && integerWFXResult.getResult() > 0) {
			result.setResult(true);
		} else {
			result.setError("代理商品失败！msg:" + integerWFXResult.getResultMsg());
		}
		return result;
	}

	@Override
	public BooleanResult setAllProxyProducts(UserInfo userInfo) {
		BooleanResult result = new BooleanResult();
		userInfo.getSellerId();
		WFXResult<Integer> resultTemp = goodsMarketFrontApi.proxyAll(userInfo.getSellerId());
		if (resultTemp != null && resultTemp.getResultCode() == ResultCodeEnum.SUCCESS.getKey()) {
			result.setResult(true);
			result.setMsg("代理成功！");
		} else {
			result.setResult(false);
			result.setError(resultTemp == null ? "未知异常" : resultTemp.getResultMsg());
		}
		return result;
	}

}
