package com.yougou.wfx.customer.service.commodity;

import java.util.List;
import java.util.Map;

import com.yougou.wfx.customer.model.commodity.CommoditySearchVo;
import com.yougou.wfx.customer.model.commodity.CommodityStyleVo;
import com.yougou.wfx.customer.model.commodity.PictureVo;
import com.yougou.wfx.customer.model.commodity.ProductVo;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.mall.CommodityStyleSearchVo;

/**
 * Created by zhang.sj on 2016/3/22.
 */
public interface ICommodityService {

	/**
	 * 根据商品id获取同款同色的分销商品，用于单品页颜色展示
	 *
	 * @param styleVo
	 *            分销商品
	 * @return
	 */
	List<CommodityStyleVo> getSameSellerCommodity(CommodityStyleVo styleVo);

	/**
	 * 根据分销商品id获取商品详情
	 *
	 * @param commodityNo
	 *            分销商品No
	 * @return
	 */
	CommodityStyleVo getCommodityByNo(String commodityNo);

	/**
	 * 根据分销商品id获取商品详情
	 *
	 * @param commodityNo
	 *            分销商品No
	 * @param includeProduct
	 *            是否包含货品
	 * @param includePicture
	 *            是否包含图片
	 * @return
	 */
	CommodityStyleVo getCommodityByNo(String commodityNo, boolean includeProduct, boolean includePicture, boolean includeStock);

	/**
	 * 批量方法，根据商品id集合获取商品列表
	 *
	 * @param commodityIds
	 *            商品列表集合
	 * @param includeProduct
	 *            是否包含货品信息
	 * @return
	 */
	List<CommodityStyleVo> getCommodityListByIds(List<String> commodityIds, boolean includeProduct, boolean includeStock);

	/**
	 * 批量方法，根据商品no集合获取商品列表
	 *
	 * @param nos
	 *            商品列表集合
	 * @param includeProduct
	 *            是否包含货品信息
	 * @return
	 */
	Map<String, CommodityStyleVo> getCommodityByNos(List<String> nos, boolean includeProduct, boolean includeStock);

	/**
	 * 查询分销商指定数量的热卖商品列表
	 *
	 * @param shopId
	 *            店铺id
	 * @param count
	 *            数量
	 * @return
	 */
	List<CommodityStyleVo> getHotCommodity(String shopId, int count);

	/**
	 * 店铺内按照分类收索商品
	 *
	 * @param searchVo
	 *            分类和分页参数
	 * @return
	 */
	Page<CommodityStyleVo> searchShopCategoryCommodity(CommoditySearchVo searchVo);

	/**
	 * 获取指定类型的商品图片
	 *
	 * @param commodityNo
	 *            商品No
	 * @param picType
	 *            图片类型,为空则获取所有类型图片
	 * @return
	 */
	List<PictureVo> getCommodityPictures(String commodityNo, String picType);

	/**
	 * 批量获取指定类型的商品图片
	 *
	 * @param commodityIds
	 *            商品id集合
	 * @param picType
	 *            图片类型,为空则获取所有类型图片
	 * @return
	 */
	Map<String, List<PictureVo>> batchGetCommodityPictures(List<String> commodityIds, String picType);

	/**
	 * 批量方法，根据货品编码集合获取货品列表
	 *
	 * @param productNos
	 * @return Map<productNo, CommodityStyleOutputDto>
	 */
	Map<String, ProductVo> getProductByProductNos(List<String> productNos);

	ProductVo getProductByProductNo(String productNo);

	/**
	 * 根据条件获取商品列表
	 *
	 * @param paramMap
	 *            id,no,styleNo
	 * @return
	 */
	List<CommodityStyleVo> getCommodityListByParams(Map<String, Object> paramMap);

	/**
	 * 分页查询商品
	 * 
	 * @param shopId
	 * @param page
	 * @return
	 */
	Page<CommodityStyleVo> getHotCommodityByPage(String shopId, Page page);

	/**
	 * 分页查询商品
	 * 
	 * @param shopId
	 * @param page
	 * @return
	 */
	Page<CommodityStyleVo> getCommodityBySearch(String shopId, CommodityStyleSearchVo search, Page page);

	/**
	 * 分页获取热买商品
	 * 
	 * @param shopId
	 * @param page
	 * @return
	 */
	Page<CommodityStyleVo> queryHotCommodity(String shopId, Page page);
}
