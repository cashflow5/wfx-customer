package com.yougou.wfx.customer.preview;

import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.commodity.CommodityStyleVo;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.customer.service.commodity.ICommodityService;
import com.yougou.wfx.customer.service.shop.IShopService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 预览
 * 
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/5/9
 */
@Controller
@RequestMapping(("/preview"))
public class PreviewController {
	@Autowired
	private ICommodityService commodityService;
	@Autowired
	private IShopService shopService;

	/**
	 * @param shopId
	 *            店铺id
	 * @param commodityNo
	 *            商品编码
	 * @param isPreview
	 *            控制C端商品预览还是B端商品预览，C端需要加返回头部
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/{shopId}/commodity/{no}")
	public String commodityDetail(@PathVariable("shopId") String shopId, @PathVariable("no") String commodityNo, @RequestParam(name = "isPreview", defaultValue = "0") Boolean isPreview, ModelMap modelMap) {
		ShopVo shopVo = shopService.getShopModelById(shopId);
		if (StringUtils.isBlank(commodityNo) || null == shopVo) {
			modelMap.addAttribute(WfxConstant.WFX_ERROR_MESSAGE_KEY, "你预览的商品信息异常");
			return "base/error";
		}
		if (1 != shopVo.getStatus()) {
			modelMap.addAttribute(WfxConstant.WFX_ERROR_MESSAGE_KEY, "该店铺已经被关闭");
			return "base/error";
		}
		modelMap.addAttribute("shop", shopVo);
		CommodityStyleVo styleVo = commodityService.getCommodityByNo(commodityNo, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		// 查询同款商品
		List<CommodityStyleVo> styleCommoditys = commodityService.getSameSellerCommodity(styleVo);
		Assert.notEmpty(styleCommoditys, "同款商品不能为空");
		modelMap.addAttribute("styleCommoditys", styleCommoditys);
		modelMap.addAttribute("commodity", styleVo);
		modelMap.addAttribute("isPreview", isPreview);
		return "preview/commodity";
	}

	@RequestMapping("/shop/{id}")
	public String shopDetail(@PathVariable("id") String shopId, ModelMap modelMap) {
		// 获取店铺信息
		ShopVo shopVo = shopService.getShopModelById(shopId);
		if (null == shopVo) {
			modelMap.addAttribute("errorMsg", "你预览的店铺信息异常");
			return "base/error";
		}
		if (1 != shopVo.getStatus()) {
			modelMap.addAttribute("errorMsg", "该店铺已经被关闭");
			return "base/error";
		}
		SessionUtils.setCurrentShopId(shopId);
		modelMap.addAttribute("shop", shopVo);
		// 获取店铺热卖商品
		List<CommodityStyleVo> commodityStyleVos = commodityService.getHotCommodity(shopId, WfxConstant.COMMODITY_HOT_COUNT);
		modelMap.addAttribute("hotCommoditys", commodityStyleVos);
		return "preview/shop";
	}

	@RequestMapping("/shop/list/{id}")
	public String shopList(@PathVariable("id") String shopId, ModelMap modelMap, Page page,HttpServletRequest request) {
		ShopVo shopVo = shopService.getAndCheckShopById(shopId, request);
		Page<CommodityStyleVo> voPage = commodityService.getHotCommodityByPage(shopId, page);
		modelMap.addAttribute("shop", shopVo);
		modelMap.addAttribute("voPage",voPage);
		return "preview/shop-commodity-list";
	}

	/**
	 * ajax获取店铺的商品列表
	 * 
	 * @param shopId
	 * @param modelMap
	 * @param page
	 * @param request
	 * @return
	 */
	@RequestMapping("/shop/list/ajax/{id}")
	public String listAjax(@PathVariable("id") String shopId, ModelMap modelMap, Page page,HttpServletRequest request) {
		shopList(shopId, modelMap, page,request);
		return "preview/shop-commodity-list-ajax";
	}

}
