package com.yougou.wfx.customer.service.commodity.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.yougou.wfx.commodity.dto.output.CommodityPicsOutputDto;
import com.yougou.wfx.commodity.dto.output.CommodityProductOutputDto;
import com.yougou.wfx.commodity.dto.output.CommodityStyleOutputDto;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.model.commodity.CommoditySearchVo;
import com.yougou.wfx.customer.model.commodity.CommodityStyleVo;
import com.yougou.wfx.customer.model.commodity.PictureVo;
import com.yougou.wfx.customer.model.commodity.ProductVo;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.mall.CommodityStyleSearchVo;
import com.yougou.wfx.customer.service.commodity.ICommodityService;
import com.yougou.wfx.dto.base.PageModel;

/**
 * Created by zhang.sj on 2016/3/22.
 */
@Service
public class CommodityServiceImpl implements ICommodityService {

	@Autowired
	private ICommodityStyleFrontApi commodityStyleFrontApi;

	/**
	 * 根据商品id获取同款同色的分销商品，用于单品页颜色展示
	 *
	 * @param styleVo
	 *            分销商品
	 * @return
	 */
	@Override
	public List<CommodityStyleVo> getSameSellerCommodity(CommodityStyleVo styleVo) {
		if (ObjectUtils.isEmpty(styleVo))
			return null;
		Map<String, Object> hashmap = Maps.newHashMap();
		hashmap.put("style_no", styleVo.getStyleNo());
		hashmap.put("brand_no", styleVo.getBrandNo());
		hashmap.put("years", styleVo.getYears());
		hashmap.put("cat_no", styleVo.getCatNo());
		// hashmap.put("is_onsale", 1);//只查询上架商品
		hashmap.put("delete_flag", 1);
		List<CommodityStyleVo> commoditys = this.getCommodityListByParams(hashmap);
		if (!CollectionUtils.isEmpty(commoditys)) {
			// 去除下架的商品(保留自己)
			Iterator<CommodityStyleVo> iterator = commoditys.iterator();
			while (iterator.hasNext()) {
				CommodityStyleVo vo = iterator.next();
				if (!vo.getNo().equals(styleVo.getNo()) && vo.getIsOnsale() != 1)
					iterator.remove();
			}
		}
		return commoditys;
	}

	/**
	 * 根据分销商品id获取商品详情
	 *
	 * @param commodityNo
	 *            分销商品No
	 * @return
	 */
	@Override
	public CommodityStyleVo getCommodityByNo(String commodityNo) {
		return getCommodityByNo(commodityNo, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE);
	}

	/**
	 * 根据分销商品id获取商品详情
	 *
	 * @param commodityNo
	 *            分销商品No
	 * @param includeProduct
	 *            是否包含货品
	 * @param includePicture
	 *            是否包含图片
	 * @param includeStock
	 *            是否包含库存
	 * @return
	 */
	@Override
	public CommodityStyleVo getCommodityByNo(String commodityNo, boolean includeProduct, boolean includePicture, boolean includeStock) {
		if (!StringUtils.hasText(commodityNo))
			return null;
		CommodityStyleOutputDto dto = commodityStyleFrontApi.queryCommodityDetails(commodityNo, includeProduct, includeStock);
		CommodityStyleVo commodityStyleVo = transform(dto);
		if (commodityStyleVo != null && includePicture) {
			// 获取M图
			List<PictureVo> pictureVos = getCommodityPictures(commodityStyleVo.getNo(), WfxConstant.WFX_COMMODITY_PICTURE_TYPE_ITEM);
			commodityStyleVo.setPictures(pictureVos);
			// 获取s图
			List<PictureVo> sPictures = getCommodityPictures(commodityStyleVo.getNo(), WfxConstant.WFX_COMMODITY_SMALL_PICTURE);
			if (null != sPictures && sPictures.size() > 0) {
				commodityStyleVo.setDefaultPic(sPictures.get(0).getUrl());
			}
			commodityStyleVo.setPictures(pictureVos);
		}
		return commodityStyleVo;
	}

	/**
	 * 批量方法，根据商品id集合获取商品列表
	 *
	 * @param commodityIds
	 *            商品列表集合
	 * @param includeProduct
	 *            是否包含货品信息
	 * @param includeStock
	 *            是否包含库存
	 * @return
	 */
	@Override
	public List<CommodityStyleVo> getCommodityListByIds(List<String> commodityIds, boolean includeProduct, boolean includeStock) {
		List<CommodityStyleOutputDto> styleOutputDtos = commodityStyleFrontApi.getCommodityListByIds(commodityIds, includeProduct, includeStock);
		return transform(styleOutputDtos);
	}

	/**
	 * 批量方法，根据商品no集合获取商品列表
	 *
	 * @param nos
	 *            商品列表集合
	 * @param includeProduct
	 *            是否包含货品信息
	 * @param includeStock
	 *            是否包含库存
	 * @return
	 */
	@Override
	public Map<String, CommodityStyleVo> getCommodityByNos(List<String> nos, boolean includeProduct, boolean includeStock) {
		List<CommodityStyleOutputDto> commoditys = commodityStyleFrontApi.getCommodityByNos(nos, includeProduct, includeStock);
		Map<String, CommodityStyleVo> styleVoMap = Maps.newHashMap();
		if (!CollectionUtils.isEmpty(commoditys)) {
			for (CommodityStyleOutputDto dto : commoditys) {
				styleVoMap.put(dto.getNo(), transform(dto));
			}
		}
		return styleVoMap;
	}

	/**
	 * 查询分销商指定数量的热卖商品列表
	 *
	 * @param shopId
	 *            店铺id
	 * @param count
	 *            数量
	 * @return
	 */
	@Override
	public List<CommodityStyleVo> getHotCommodity(String shopId, int count) {
		if (!StringUtils.hasText(shopId))
			return null;
		List<CommodityStyleOutputDto> dtoList = commodityStyleFrontApi.queryHotCommodityList(shopId, count);
		List<CommodityStyleVo> commodityStyleVos = transform(dtoList);
		fillCommodityPictureMb(commodityStyleVos);
		return commodityStyleVos;
	}

	/**
	 * 店铺内按照分类收索商品
	 *
	 * @param searchVo
	 *            分类和分页参数
	 * @return
	 */
	@Override
	public Page<CommodityStyleVo> searchShopCategoryCommodity(CommoditySearchVo searchVo) {
		PageModel pageModel = new PageModel();
		PageModel<CommodityStyleOutputDto> searchResults = commodityStyleFrontApi.getShopCategoryCommodity(searchVo.getCategoryId(), searchVo.getSellerId(), pageModel);
		Page<CommodityStyleVo> styleVoPage = new Page<CommodityStyleVo>();
		styleVoPage.setItems(transform(searchResults.getItems()));
		styleVoPage.setPage(searchVo.getPage());
		return styleVoPage;
	}

	/**
	 * 商品列表页面填充mb图片
	 *
	 * @param commodityStyleVos
	 */
	private void fillCommodityPictureMb(List<CommodityStyleVo> commodityStyleVos) {
		if (CollectionUtils.isEmpty(commodityStyleVos))
			return;
		for (CommodityStyleVo commodityStyleVo : commodityStyleVos) {
			List<PictureVo> pictureVos = getCommodityPictures(commodityStyleVo.getNo(), WfxConstant.WFX_COMMODITY_PICTURE_TYPE_LIST);
			if (!CollectionUtils.isEmpty(pictureVos)) {
				commodityStyleVo.setPictureMb(pictureVos.get(0).getUrl());
			}
		}

	}

	/**
	 * 获取指定类型的商品图片
	 *
	 * @param commodityNo
	 *            商品no
	 * @param picType
	 *            图片类型,为空则获取所有类型图片
	 * @return
	 */
	public List<PictureVo> getCommodityPictures(String commodityNo, String picType) {
		Map<String, List<PictureVo>> pictureMap = batchGetCommodityPictures(Lists.newArrayList(commodityNo), picType);
		if (CollectionUtils.isEmpty(pictureMap) || !pictureMap.containsKey(commodityNo))
			return null;
		return pictureMap.get(commodityNo);
	}

	/**
	 * 批量获取指定类型的商品图片
	 *
	 * @param commodityNos
	 *            商品no集合
	 * @param picType
	 *            图片类型,为空则获取所有类型图片
	 * @return
	 */
	public Map<String, List<PictureVo>> batchGetCommodityPictures(List<String> commodityNos, String picType) {
		Map<String, List<CommodityPicsOutputDto>> picsMap = commodityStyleFrontApi.batchGetCommodityPictures(commodityNos, picType);
		if (CollectionUtils.isEmpty(picsMap))
			return null;
		Map<String, List<PictureVo>> picVoMap = Maps.newHashMap();
		for (Map.Entry<String, List<CommodityPicsOutputDto>> item : picsMap.entrySet()) {
			picVoMap.put(item.getKey(), transformPicture(item.getValue()));
		}
		return picVoMap;
	}

	/**
	 * 批量方法，根据货品编码集合获取货品列表
	 *
	 * @param productNos
	 * @return Map<String, ProductVo>
	 */
	@Override
	public Map<String, ProductVo> getProductByProductNos(List<String> productNos) {
		Assert.notEmpty(productNos, "参数不能为空");
		List<CommodityProductOutputDto> productDtos = commodityStyleFrontApi.getProductByProductNos(productNos);
		Map<String, ProductVo> products = Maps.newHashMap();
		if (!CollectionUtils.isEmpty(productDtos)) {
			for (CommodityProductOutputDto dto : productDtos) {
				products.put(dto.getProductNo(), transformProduct(dto));
			}
		}
		return products;
	}

	/**
	 * 根据货品编码获取货品
	 *
	 * @param productNo
	 * @return CommodityStyleOutputDto
	 */
	@Override
	public ProductVo getProductByProductNo(String productNo) {
		if (StringUtils.isEmpty(productNo))
			return null;
		Map<String, ProductVo> productVoMap = getProductByProductNos(Lists.newArrayList(productNo));
		if (CollectionUtils.isEmpty(productVoMap))
			return null;
		return productVoMap.get(productNo);
	}

	/**
	 * 根据条件获取商品列表
	 *
	 * @param paramMap
	 *            id,no,styleNo
	 * @return
	 */
	@Override
	public List<CommodityStyleVo> getCommodityListByParams(Map<String, Object> paramMap) {
		List<CommodityStyleOutputDto> dtos = commodityStyleFrontApi.getCommodityByParameter(paramMap);
		return transform(dtos);
	}

	/**
	 * dto转换，与outputDto解耦
	 *
	 * @param dto
	 * @return
	 */

	private CommodityStyleVo transform(CommodityStyleOutputDto dto) {
		if (dto == null)
			return null;
		CommodityStyleVo styleVo = new CommodityStyleVo();
		styleVo.setId(dto.getId());
		styleVo.setIsOnsale(dto.getIsOnsale());
		styleVo.setBrandName(dto.getBrandName());
		styleVo.setBrandNo(dto.getBrandNo());
		styleVo.setCatName(dto.getCatName());
		styleVo.setCatNo(dto.getCatNo());
		styleVo.setCatStructname(dto.getCatStructname());
		styleVo.setCommodityName(dto.getCommodityName());
		styleVo.setCostPrice(dto.getCostPrice());
		styleVo.setCostPrice2(dto.getCostPrice2());
		styleVo.setDeleteFlag(dto.getDeleteFlag());
		styleVo.setWfxCommodityNo(dto.getWfxCommodityNo());
		styleVo.setWfxPrice(dto.getWfxPrice());
		styleVo.setIsWfxCommodity(dto.getIsWfxCommodity());
		styleVo.setMaxPrice(dto.getMaxPrice());
		styleVo.setPublicPrice(dto.getPublicPrice());
		styleVo.setMerchantCode(dto.getMerchantCode());
		styleVo.setMinPrice(dto.getMinPrice());
		styleVo.setNo(dto.getNo());
		styleVo.setProdDesc(dto.getProdDesc());
		styleVo.setSalePrice(dto.getSalePrice());
		styleVo.setSellingPoint(dto.getSellingPoint());
		styleVo.setSpecName(dto.getSpecName());
		styleVo.setSpecNo(dto.getSpecNo());
		styleVo.setStyleNo(dto.getStyleNo());
		styleVo.setSupplierCode(dto.getSupplierCode());
		styleVo.setSupplierId(dto.getSupplierId());
		styleVo.setStock(dto.getStock());
		styleVo.setDefaultPic(dto.getDefaultPic());
		styleVo.setYears(dto.getYears());
		if (!CollectionUtils.isEmpty(dto.getProductDto())) {
			styleVo.setProducts(transformProduct(dto.getProductDto()));
		}
		return styleVo;
	}

	private List<ProductVo> transformProduct(List<CommodityProductOutputDto> productDtos) {
		if (CollectionUtils.isEmpty(productDtos))
			return null;
		List<ProductVo> products = Lists.newArrayList();
		for (CommodityProductOutputDto productDto : productDtos) {
			products.add(transformProduct(productDto));
		}
		return products;
	}

	private ProductVo transformProduct(CommodityProductOutputDto dto) {
		ProductVo productVo = new ProductVo();
		productVo.setId(dto.getId());
		productVo.setCommodityId(dto.getCommodityId());
		productVo.setBrandName(dto.getBrandName());
		productVo.setBrandNo(dto.getBrandNo());
		productVo.setCatName(dto.getCatName());
		productVo.setCatNo(dto.getCatNo());
		productVo.setCatStructName(dto.getCatStructName());
		productVo.setInventoryNum(dto.getInventoryNum());
		productVo.setPrestoreInventoryNum(dto.getPrestoreInventoryNum());
		productVo.setSellStatus(dto.getSellStatus());
		productVo.setSizeName(dto.getSizeName());
		productVo.setSizeNo(dto.getSizeNo());
		productVo.setDeleteFlag(dto.getDeleteFlag());
		productVo.setCostPrice(dto.getCostPrice());
		productVo.setSalePrice(dto.getSalePrice());
		productVo.setProductNo(dto.getProductNo());
		productVo.setInsideCode(dto.getInsideCode());
		productVo.setThirdPartyCode(dto.getThirdPartyCode());
		return productVo;
	}

	private List<CommodityStyleVo> transform(List<CommodityStyleOutputDto> dtos) {
		if (CollectionUtils.isEmpty(dtos))
			return null;
		List<CommodityStyleVo> styleVos = Lists.newArrayList();
		for (CommodityStyleOutputDto dto : dtos) {
			styleVos.add(transform(dto));
		}
		return styleVos;
	}

	private PictureVo transformPicture(CommodityPicsOutputDto dto) {
		if (dto == null)
			return null;
		PictureVo pictureVo = new PictureVo();
		pictureVo.setId(dto.getId());
		pictureVo.setCommodityNo(dto.getCommodityNo());
		pictureVo.setPictureType(dto.getPicType());
		pictureVo.setUrl(dto.getPicPath() + dto.getPicName());
		return pictureVo;
	}

	private List<PictureVo> transformPicture(List<CommodityPicsOutputDto> dtos) {
		if (CollectionUtils.isEmpty(dtos))
			return null;
		List<PictureVo> pictureVos = Lists.newArrayList();
		for (CommodityPicsOutputDto picsOutputDto : dtos) {
			pictureVos.add(transformPicture(picsOutputDto));
		}
		return pictureVos;
	}

	@Override
	public Page<CommodityStyleVo> getHotCommodityByPage(String shopId, Page page) {
		Page<CommodityStyleVo> voPage = new Page<>();
		voPage.setItems(Lists.<CommodityStyleVo> newArrayList());
		// 创建分页请求
		PageModel pageModel = null;
		if (page != null) {
			pageModel = page.toPageModelDto();
		} else {
			pageModel = new PageModel();
		}
		// 请求订单
		PageModel<CommodityStyleOutputDto> dtos = commodityStyleFrontApi.queryHotCommodity(shopId, pageModel);
		// 填充订单数据
		if (dtos != null && dtos.getItems() != null) {
			List<CommodityStyleOutputDto> dtoList = dtos.getItems();
			List<CommodityStyleVo> voList = transform(dtoList);
			fillCommodityPictureMb(voList);
			for (CommodityStyleVo vo : voList) {
				voPage.getItems().add(vo);
			}
		}
		return voPage;
	}

	@Override
	public Page<CommodityStyleVo> getCommodityBySearch(String shopId, CommodityStyleSearchVo search, Page page) {
		Page<CommodityStyleVo> voPage = new Page<>();
		voPage.setItems(Lists.<CommodityStyleVo> newArrayList());
		// 创建分页请求
		PageModel pageModel = null;
		if (page != null) {
			pageModel = page.toPageModelDto();
		} else {
			pageModel = new PageModel();
		}
		CommodityStyleOrderInputDto inputDto = this.getStyleDtoBySearch(search);
		// 请求订单
		PageModel<CommodityStyleOutputDto> dtos = commodityStyleFrontApi.queryCommodityOrder(inputDto, pageModel);
		// 填充订单数据
		if (dtos != null && dtos.getItems() != null) {
			List<CommodityStyleOutputDto> dtoList = dtos.getItems();
			List<CommodityStyleVo> voList = transform(dtoList);
			fillCommodityPictureMb(voList);
			if (null != voList && voList.size() > 0) {
				for (CommodityStyleVo vo : voList) {
					voPage.getItems().add(vo);
				}
			}
		}
		return voPage;
	}

	public CommodityStyleOrderInputDto getStyleDtoBySearch(CommodityStyleSearchVo search) {
		CommodityStyleOrderInputDto style = new CommodityStyleOrderInputDto();
		if (null != search) {
			if (StringUtils.isEmpty(search.getSaleQuantityOrder()) && StringUtils.isEmpty(search.getUpdateOrder()) && StringUtils.isEmpty(search.getPriceOrder()) && StringUtils.isEmpty(search.getPopularity())) {
				search.setSaleQuantityOrder("desc");
			}
			style.setSaleQuantityOrder(search.getSaleQuantityOrder());
			style.setUpdateOrder(search.getUpdateOrder());
			style.setPriceOrder(search.getPriceOrder());
			style.setPopularity(search.getPopularity());
			style.setCatNo(search.getCatNo());
			style.setBrandNo(search.getBrandNo());
			style.setShopId(search.getShopId());
			style.setPropItemName(search.getPropItemName());
			style.setCatId(search.getCatId());
			List<String> catgoryList = this.parseFilter(search.getCatgory());
			if (null != catgoryList && catgoryList.size() > 0) {
				List<String> catList = new ArrayList<String>();
				for (String cat : catgoryList) {
					if (null != cat && cat.indexOf("#") > 0) {
						String[] cats = cat.split("#");
						catList.addAll(Arrays.asList(cats));
					} else {
						catList.add(cat);
					}
				}
				style.setCatNoList(catList);
			}
			List<String> brandList = this.parseFilter(search.getBrand());
			style.setBrandNoList(brandList);
			// 属性字段
			List<String> sexList = this.parseFilter(search.getSex());
			List<String> priceList = this.parseFilter(search.getPrice());
			List<Map<Integer, Integer>> pricesList = new ArrayList<Map<Integer, Integer>>();
			if (null != priceList && priceList.size() > 0) {
				Map priceMap = new HashMap();
				for (String price : priceList) {
					if (null != price && price.contains("-")) {
						String[] priceArr = price.split("-");
						if (null != priceArr && priceArr.length >= 2) {
							Integer price0 = Integer.parseInt(priceArr[0]);
							Integer price1 = Integer.parseInt(priceArr[1]);
							priceMap.put(price0, price1);
						}
					}
				}
				style.setPriceMap(priceMap);
			}
			List<String> sizeList = this.parseFilter(search.getSize());
			style.setSizeNoList(sizeList);
			List<String> colorList = this.parseFilter(search.getColor());
			style.setSpecNameList(colorList);
			// 属性字段
			List<String> styleList = this.parseFilter(search.getStyle());
			List<String> mannerList = this.parseFilter(search.getManner());
			// 所有属性字段的总和
			List<String> propItemList = new ArrayList<String>();
			if (null != sexList && sexList.size() > 0) {
				propItemList.addAll(sexList);
			}
			if (null != styleList && styleList.size() > 0) {
				propItemList.addAll(styleList);
			}
			if (null != mannerList && mannerList.size() > 0) {
				propItemList.addAll(mannerList);
			}
			style.setPropValueNolist(propItemList);
		}
		return style;
	}

	private List<String> parseFilter(String filter) {
		List<String> filterList = new ArrayList();
		if (org.apache.commons.lang3.StringUtils.isBlank(filter)) {
			return null;
		}
		String[] arrayFil = filter.split(",");
		filterList = Arrays.asList(arrayFil);
		return filterList;
	}

	@Override
	public Page<CommodityStyleVo> queryHotCommodity(String shopId, Page page) {
		Page<CommodityStyleVo> voPage = new Page<>();
		voPage.setItems(Lists.<CommodityStyleVo> newArrayList());
		// 创建分页请求
		PageModel pageModel = null;
		if (page != null) {
			pageModel = page.toPageModelDto();
		} else {
			pageModel = new PageModel();
		}
		// 请求订单
		PageModel<CommodityStyleOutputDto> dtos = commodityStyleFrontApi.queryHotCommodity(shopId, pageModel);
		// 填充订单数据
		if (dtos != null && !dtos.getItems().isEmpty()) {
			List<CommodityStyleOutputDto> dtoList = dtos.getItems();
			List<CommodityStyleVo> voList = transform(dtoList);
			fillCommodityPictureMb(voList);
			for (CommodityStyleVo vo : voList) {
				voPage.getItems().add(vo);
			}
		}
		return voPage;
	}
}
