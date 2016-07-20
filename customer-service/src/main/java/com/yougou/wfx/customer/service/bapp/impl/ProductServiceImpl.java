package com.yougou.wfx.customer.service.bapp.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yougou.wfx.appserver.vo.PageSearchResult;
import com.yougou.wfx.appserver.vo.login.UserInfo;
import com.yougou.wfx.appserver.vo.product.OnAndOffProduct;
import com.yougou.wfx.appserver.vo.product.Product;
import com.yougou.wfx.appserver.vo.product.ProductDetail;
import com.yougou.wfx.appserver.vo.product.ProductSearcher;
import com.yougou.wfx.appserver.vo.product.ProductShelf;
import com.yougou.wfx.appserver.vo.product.ShopProductSearcher;
import com.yougou.wfx.appserver.vo.supply.BrandProductSearcher;
import com.yougou.wfx.appserver.vo.supply.CategoryBrandInfo;
import com.yougou.wfx.appserver.vo.supply.CategoryBrandSearcher;
import com.yougou.wfx.appserver.vo.supply.CategoryProductSearcher;
import com.yougou.wfx.cms.dto.input.CommoditySaleCatPageInputDto;
import com.yougou.wfx.cms.dto.output.CommoditySaleCatOutputDto;
import com.yougou.wfx.commodity.dto.output.CommodityStyleOutputDto;
import com.yougou.wfx.customer.service.bapp.IProductService;
import com.yougou.wfx.dto.base.PageModel;
import com.yougou.wfx.dto.base.WFXResult;
import com.yougou.wfx.enums.ResultCodeEnum;

/**
 * Created by lizhw on 2016/4/11.
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl implements IProductService {

	@Override
	public PageSearchResult<ShopProductSearcher, Product> getShopProduct(ShopProductSearcher searcher) {
		PageSearchResult<ShopProductSearcher, Product> result = new PageSearchResult<ShopProductSearcher, Product>();
		result.setSearcher(searcher);
		// 验证店铺ID是否
		String sellerId = getSellerId(searcher.getUserInfo());
		if (isBlank(sellerId)) {
			result.setTotal(0);
			return result;
		}

		PageModel<Object> page = new PageModel<>();
		page.setPage(searcher.getPage());
		page.setLimit(searcher.getPageSize());
		PageModel<CommodityStyleOutputDto> comms = commodityStyleFrontApi.queryProxyCommodity(sellerId, page);
		if (comms == null) {
			result.setTotal(0);
			return result;
		}

		result.setTotal(comms.getTotalCount());
		fillResult(result.getItems(), comms);

		return result;
	}

	@Override
	public OnAndOffProduct productShelfManage(ProductShelf onAndoff) {
		String onIds = onAndoff.getOnIds();
		String offIds = onAndoff.getOffIds();
		List<String> onIdList = new LinkedList<String>();
		List<String> offIdList = new LinkedList<String>();
		if (onIds != null) {
			String[] onIdArr = onIds.split(",");
			for (String s : onIdArr) {
				if (!isBlank(s)) {
					onIdList.add(s);
				}
			}
		}
		if (offIds != null) {
			String[] offIdArr = offIds.split(",");
			for (String s : offIdArr) {
				if (!isBlank(s)) {
					offIdList.add(s);
				}
			}
		}
		OnAndOffProduct oop = new OnAndOffProduct();
		if (onIdList.size() == 0 && offIdList.size() == 0) {
			oop.setError("onIds 和 offIds 不能都为空！");
			oop.setResult(false);
			return oop;
		}

		WFXResult<Map<String, List<String>>> wfxResult = new WFXResult<Map<String, List<String>>>();
		if (onIdList.size() != 0) {// 上架
			wfxResult = commodityStyleFrontApi.updateSellerCommodityShelvesStatus(onIdList, 1);
			oop.setType("1");

		} else if (offIdList.size() != 0) {// 下架
			wfxResult = commodityStyleFrontApi.updateSellerCommodityShelvesStatus(offIdList, 2);
			oop.setType("2");
		}
		
		if (ResultCodeEnum.SUCCESS.getKey() == wfxResult.getResultCode()) {
			oop.setResult(true);
			oop.setSuccessIds(listToString(wfxResult.getResult().get("success")));
			oop.setFailIds(listToString(wfxResult.getResult().get("error")));
		} else {
			oop.setResult(false);
			oop.setError(wfxResult.getResultMsg());
		}
		return oop;
	}

	public String listToString(List<String> stringList) {
		if (stringList == null) {
			return "";
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}

	@Override
	public PageSearchResult<CategoryBrandSearcher, CategoryBrandInfo> getCategoryBrandInfo(CategoryBrandSearcher searcher) {
		PageSearchResult<CategoryBrandSearcher, CategoryBrandInfo> result = new PageSearchResult<CategoryBrandSearcher, CategoryBrandInfo>();
		result.setSearcher(searcher);

		CommoditySaleCatPageInputDto pageInputDto = new CommoditySaleCatPageInputDto();
		PageModel<Object> page = new PageModel<Object>();
		page.setPage(searcher.getPage());
		page.setLimit(searcher.getPageSize());
		List<CommoditySaleCatOutputDto> cats = null;
		if (isBlank(searcher.getParentId())) {
			// 一级分类
			PageModel<CommoditySaleCatOutputDto> catsPage = commoditySaleCatFrontApi.queryCommoditySaleCat(pageInputDto, page);
			if (catsPage != null) {
				cats = catsPage.getItems();
				result.setTotal(catsPage.getTotalCount());
			}
		} else {
			// 二级分类
			cats = commoditySaleCatFrontApi.queryCommoditySaleCatLevelTwoList(searcher.getParentId());
			if (cats != null) {
				result.setTotal(cats.size());
			}
		}
		if (cats != null && cats.size() > 0) {
			for (CommoditySaleCatOutputDto c : cats) {
				CategoryBrandInfo cat = new CategoryBrandInfo();
				cat.setParentCategoryId(c.getParentId());
				cat.setLevel(c.getLevel() + "");
				cat.setCategoryId(c.getId());
				cat.setCategoryName(c.getName());
				// 一级分类和二级分类无品牌
				cat.setBrandId("");
				cat.setBrandName("");
				result.getItems().add(cat);
			}
		}
		return result;
	}

	@Override
	public PageSearchResult<CategoryProductSearcher, Product> getProductByLeafCategory(CategoryProductSearcher searcher) {
		PageSearchResult<CategoryProductSearcher, Product> result = new PageSearchResult<CategoryProductSearcher, Product>();
		result.setSearcher(searcher);
		if (isBlank(searcher.getCategoryId())) {
			return result;
		}
		PageModel<Object> page = new PageModel<Object>();
		page.setLimit(searcher.getPageSize());
		page.setPage(searcher.getPage());

		PageModel<CommodityStyleOutputDto> coms = commodityStyleFrontApi.getCommodityByCatId(searcher.getCategoryId(), searcher.getUserInfo().getSellerId(), page);
		if (coms == null) {
			result.setTotal(0);
			return result;
		}
		result.setTotal(coms.getTotalCount());
		fillResult(result.getItems(), coms);

		return result;
	}

	@Override
	public ProductDetail getProductDetailById(String productNo, UserInfo userInfo) {
		ProductDetail p = new ProductDetail();
		if (isBlank(productNo)) {
			return p;
		}

		CommodityStyleOutputDto com = commodityStyleFrontApi.queryCommodityDetails(productNo, userInfo.getSellerId(), false, false);
		if (com != null) {
			p.setNo(com.getNo());
			p.setDescribe(com.getCommodityName());
			p.setPrice(toDouble(com.getWfxPrice()));
			p.setOriginal_price(toDouble(com.getPublicPrice()));
			p.setPeople_num(com.getProxyQuantity());
			p.setImg_url(com.getPicBig());
			// styleNo,brandNo,catNo,years
			p.setStyleNo(com.getStyleNo());
			p.setBrandNo(com.getBrandNo());
			p.setCatNo(com.getCatNo());
			p.setYears(com.getYears());
			p.setHasProxy("1".equals(com.getSellerFlag()));
		}

		return p;
	}

	@Override
	public PageSearchResult<ProductSearcher, Product> searchProduct(ProductSearcher searcher) {
		PageSearchResult<ProductSearcher, Product> result = new PageSearchResult<ProductSearcher, Product>();
		result.setSearcher(searcher);
		if (isBlank(searcher.getKey())) {
			return result;
		}
		PageModel<Object> page = new PageModel<Object>();
		page.setLimit(searcher.getPageSize());
		page.setPage(searcher.getPage());
		PageModel<CommodityStyleOutputDto> coms = commodityStyleFrontApi.queryCommodityListForSearch(searcher.getKey(), searcher.getUserInfo().getSellerId(), page);
		if (coms == null) {
			result.setTotal(0);
			return result;
		}
		result.setTotal(coms.getTotalCount());
		fillResult(result.getItems(), coms);
		return result;
	}

	@Override
	public PageSearchResult<BrandProductSearcher, Product> getProductByBrandNo(BrandProductSearcher searcher) {
		PageSearchResult<BrandProductSearcher, Product> result = new PageSearchResult<>();
		result.setSearcher(searcher);
		if (isBlank(searcher.getBrandNo())) {
			return result;
		}
		// ok:根据品牌获取商品
		PageModel<Object> page = new PageModel<Object>();
		page.setLimit(searcher.getPageSize());
		page.setPage(searcher.getPage());
		PageModel<CommodityStyleOutputDto> coms = commodityStyleFrontApi.getCommodityByBrandNo(searcher.getBrandNo(), searcher.getUserInfo().getSellerId(), page);
		if (coms == null) {
			result.setTotal(0);
			return result;
		}
		result.setTotal(coms.getTotalCount());
		fillResult(result.getItems(), coms);

		return result;
	}

	private void fillResult(List<Product> result, PageModel<CommodityStyleOutputDto> coms) {
		List<CommodityStyleOutputDto> items = coms.getItems();
		if (items != null && items.size() > 0) {
			for (CommodityStyleOutputDto item : items) {
				Product p = new Product();
				p.setNo(item.getNo());// 商品编号
				p.setId(item.getSellerCommodityId());// 代理商品Id
				p.setDescribe(item.getCommodityName());// 代理商品名称

				p.setPrice(toDouble(item.getWfxPrice()));// 微分销价
				p.setOriginal_price(toDouble(item.getPublicPrice()));// 市场价
				p.setOne_level(toDouble(item.getCommissionLevel1()));// 一级佣金
				p.setTwo_level(toDouble(item.getCommissionLevel2()));// 二级佣金
				p.setThree_level(toDouble(item.getCommissionLevel3()));// 三级佣金
				Integer q = item.getProxyQuantity();
				p.setPeople_num(q == null ? 0 : q.intValue());// 代理人数
				p.setImg_url(item.getPicBig());// 默认显示大图
				p.setShelfOn(item.getIsOnsale() == 1);// 微分销平台上下架状态 1上架，2下架，3未上架
				Integer sellerComStatus = item.getSellerCommodityStatus();
				p.setPlatformShelfOn(sellerComStatus == null ? false : sellerComStatus.intValue() == 1);// 代理商品上下架状态
																										// 1上架，2下架

				p.setStyleNo(item.getStyleNo());// 款号
				p.setBrandNo(item.getBrandNo());// 品牌编码
				p.setCatNo(item.getCatNo());// 分类编码
				p.setYears(item.getYears());// 出品年份
				p.setHasProxy("1".equals(item.getSellerFlag()));// 是否为代理商品
																// 1代理商品，2非代理商品
				result.add(p);
			}
		}
	}
}
