package com.yougou.wfx.customer.shop;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yougou.wfx.commodity.api.front.ICommodityCortexFrontApi;
import com.yougou.wfx.commodity.api.front.ICommodityStyleFrontApi;
import com.yougou.wfx.commodity.dto.input.CommodityStyleOrderInputDto;
import com.yougou.wfx.commodity.dto.output.CommodityCortexOutputDto;
import com.yougou.wfx.commodity.dto.output.CommodityPropertyOutputDto;
import com.yougou.wfx.commodity.dto.output.CommodityStyleFilterOutputDto;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.annotations.WXLoginValidate;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.enums.MenuEnum;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.configurations.weixin.WeiXinProperties;
import com.yougou.wfx.customer.model.commodity.CommoditySearchVo;
import com.yougou.wfx.customer.model.commodity.CommodityStyleVo;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.mall.CommodityStyleSearchVo;
import com.yougou.wfx.customer.model.mall.MallIndexVo;
import com.yougou.wfx.customer.model.mall.PropertyVo;
import com.yougou.wfx.customer.model.order.OrderPayVo;
import com.yougou.wfx.customer.model.shop.SelectItemVo;
import com.yougou.wfx.customer.model.shop.ShopCatVo;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.customer.model.usercenter.UserLoginAccountVo;
import com.yougou.wfx.customer.model.usercenter.UserLoginVo;
import com.yougou.wfx.customer.model.weixin.base.WXUserInfoResponse;
import com.yougou.wfx.customer.service.bapp.IBShopService;
import com.yougou.wfx.customer.service.commodity.ICommodityService;
import com.yougou.wfx.customer.service.mall.IMallService;
import com.yougou.wfx.customer.service.order.IOrderService;
import com.yougou.wfx.customer.service.shop.IShopService;
import com.yougou.wfx.customer.service.shoppingcart.IShoppingCartService;
import com.yougou.wfx.customer.service.system.ISystemService;
import com.yougou.wfx.customer.service.usercenter.ILoginService;
import com.yougou.wfx.customer.service.visitor.IVisitorService;
import com.yougou.wfx.customer.service.weixin.IWXService;
import com.yougou.wfx.customer.ufans.VisitorComponent;

/**
 * Created by zhang.sj on 2016/3/22.
 */

/**
 * Created by zhang.sj on 2016/3/22.
 */

@Controller
public class ShopController {

	private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

	@Autowired
	private IShopService shopService;
	@Autowired
	private ICommodityService commodityService;
	@Autowired
	private IShoppingCartService shoppingCartService;
	@Autowired
	private ISystemService systemService;
	@Autowired
	private ILoginService loginService;
	@Autowired
	private IWXService wXService;
	@Autowired
	private WeiXinProperties weiXinProperties;
	@Autowired
	private IMallService mallService;
	@Autowired
	private ICommodityStyleFrontApi commodityStyleFrontApi;
	@Autowired
	private ICommodityCortexFrontApi commodityCortexFrontApi;
	@Autowired
	private IBShopService bShopService;
	@Autowired
	private IVisitorService visitorService;
	@Autowired
	private VisitorComponent visitorComponent;
	@Autowired
	private IOrderService orderService;

	/**
	 * C端店铺首页
	 *
	 * @param shopId
	 *            店铺id
	 * @return
	 */
	@WXLoginValidate
	@RequestMapping("/{shopId}")
	public String index(@PathVariable("shopId") String shopId, ModelMap modelMap, Page page, HttpServletRequest request) {

		// 设置当前菜单
		SessionUtils.setCurrentMenuID(MenuEnum.MENU_SHOP.getKey());
		// 重定向发现首页
		String flag = request.getParameter("flag");
		if (StringUtils.isEmpty(flag)) {
			return "redirect:/discover/home.sc";
		}
		// 记录访客信息
		visitorComponent.saveVisitorInfo(shopId, null, request);
		String currentShopId = SessionUtils.getCurrentShopIdOrDefault();
		// 判断用户的身份并设置当前访问的店铺
		if (SessionUtils.getLoginUserDetails() != null) {
			String userId = SessionUtils.getLoginUserDetails().getUserId();
			// 判断会员是否是优粉，是则设置当前店铺ID为自己的店铺，否则为总店店铺
			ShopVo shopInfo = shopService.getShopModelByUserId(userId);
			if (shopInfo != null && !currentShopId.equals(shopInfo.getId())) {
				// 设置当前访问的店铺
				SessionUtils.setCurrentShopId(shopInfo.getId());
				return "redirect:/" + shopInfo.getId() + ".sc?flag=1";
			} else {
				shopId = currentShopId;
			}
		}
		if (!shopId.equals(currentShopId)) {
			SessionUtils.setCurrentShopId(shopId);
		}

		// 获取店铺信息
		ShopVo shopVo = shopService.getAndCheckShopById(shopId, request);
		modelMap.addAttribute("shop", shopVo);

		// 获取轮播图、公告、热门品片及热门分类
		MallIndexVo mall = mallService.getMallIndexVo();
		modelMap.addAttribute("mall", mall);

		// 获取店铺热卖商品
		Page<CommodityStyleVo> voPage = commodityService.queryHotCommodity(shopId, page);
		List<CommodityStyleVo> commodityStyleVos = voPage.getItems();
		modelMap.addAttribute("hotCommoditys", commodityStyleVos);

		return "shop/index";
	}

	/**
	 * C端店铺异步加载商品
	 * 
	 * @param shopId
	 * @param page
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("/{shopId}/index_commodity_ajax")
	public String index_ajax(@PathVariable("shopId") String shopId, Page page, ModelMap modelMap, HttpServletRequest request) {
		Page<CommodityStyleVo> voPage = commodityService.queryHotCommodity(shopId, page);
		List<CommodityStyleVo> commodityStyleVos = voPage.getItems();
		modelMap.addAttribute("hotCommoditys", commodityStyleVos);
		modelMap.addAttribute("shopId", shopId);
		return "shop/index-commodity-ajax";
	}

	/**
	 * B端嵌入C端店铺首页
	 *
	 * @param shopId
	 *            店铺id
	 * @return
	 */
	@RequestMapping("/wfx-app/{shopId}")
	public String indexForApp(@PathVariable("shopId") String shopId, ModelMap modelMap,Page page, HttpServletRequest request) {
		// 设置当前菜单
		SessionUtils.setCurrentMenuID(MenuEnum.MENU_SHOP.getKey());
		// 直接登录
		String loginName = request.getParameter("loginName");
		String loginPassword = request.getParameter("loginPassword");
		String unionId = request.getParameter("unionId");
		
		
		// 注册账号登录
		if (!StringUtils.isEmpty(loginName)) {
			// 直接登录
			UserLoginVo loginVo = new UserLoginVo();
			loginVo.setLoginName(loginName);
			loginVo.setLoginPassword(loginPassword);
			loginVo.setAutoLogin(false);
			loginVo.setSaveLogin(false);
			UserLoginAccountVo userLoginAccount = loginService.doLogin(loginVo);
			// 更新session中的用户信息
			Result<SessionUserDetails> result = loginService.updateSessionUserDetails(userLoginAccount);
			if(result.isSuccess()){
				logger.error("B端用户(用户名："+loginName+")登录失败!原因：" + result.getMessage());
			}
		} else {
			// 不考虑没有微信注册的情况
			WXUserInfoResponse userInfo = new WXUserInfoResponse();
			userInfo.setUnionid(unionId);
			loginService.doWithWXUserInfo(userInfo, weiXinProperties.getAppId(), weiXinProperties.getAppSecret());
		}

		// 获取店铺信息
		ShopVo shopVo = shopService.getAndCheckShopById(shopId, request);
		modelMap.addAttribute("shop", shopVo);

		// 获取轮播图、公告、热门品片及热门分类
		MallIndexVo mall = mallService.getMallIndexVo();
		modelMap.addAttribute("mall", mall);
		
		// 获取店铺热卖商品
		Page<CommodityStyleVo> voPage = commodityService.queryHotCommodity(shopId, page);
		List<CommodityStyleVo> commodityStyleVos = voPage.getItems();
		modelMap.addAttribute("hotCommoditys", commodityStyleVos);
		
		// 设置当前店铺ID
		SessionUtils.setCurrentShopId(shopId);
		modelMap.addAttribute("isOldVersion", request.getSession().getAttribute("isOldVersion"));
		return "shop/index";
	}

	/**
	 * 优粉
	 *
	 * @param shopId
	 *            店铺id
	 * @return
	 */
	@RequestMapping("/ufans")
	public String ufans(ModelMap modelMap, HttpServletRequest request) {
		return "shop/ufans";
	}

	/**
	 * 店铺的商品列表页
	 *
	 * @param shopId
	 *            店铺id
	 * @return
	 */
	@RequestMapping("/{shopId}/list")
	public String list(@PathVariable("shopId") String shopId, CommodityStyleSearchVo search, ModelMap modelMap, Page page, HttpServletRequest request) {
		ShopVo shopVo = shopService.getAndCheckShopById(shopId, request);
		// 记录访客信息
		visitorComponent.saveVisitorInfo(shopId, null, request);
		Page<CommodityStyleVo> voPage = commodityService.getCommodityBySearch(shopId, search, page);
		int styleCount = 0;
		int mannerCount = 0;
		CommodityStyleOrderInputDto inputDto = new CommodityStyleOrderInputDto();
		inputDto.setShopId(shopId);
		inputDto.setCatId(search.getCatId());
		inputDto.setBrandNo(search.getBrandNo());
		inputDto.setPropItemName("款式");
		List<CommodityStyleFilterOutputDto> styleCommList = commodityStyleFrontApi.getPropInfo(inputDto);
		if (null != styleCommList) {
			styleCount = styleCommList.size();
		}
		inputDto.setPropItemName("风格");
		List<CommodityStyleFilterOutputDto> mannerCommList = commodityStyleFrontApi.getPropInfo(inputDto);
		if (null != mannerCommList) {
			mannerCount = mannerCommList.size();
		}
		modelMap.addAttribute("shop", shopVo);
		modelMap.addAttribute("voPage", voPage);
		modelMap.addAttribute("search", search);
		modelMap.addAttribute("styleCount", styleCount);
		modelMap.addAttribute("mannerCount", mannerCount);

		return "shop/commodity-list";
	}

	@RequestMapping("/{shopId}/property")
	public String property(@PathVariable("shopId") String shopId, String type, String catId, String brandNo, ModelMap modelMap, Page page, HttpServletRequest request) {
		List<PropertyVo> proList = shopService.getPropertys(type, shopId, catId, brandNo);
		modelMap.addAttribute("proList", proList);
		return "shop/property";
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
	@RequestMapping("/{shopId}/list/ajax")
	public String listAjax(@PathVariable("shopId") String shopId, CommodityStyleSearchVo search, ModelMap modelMap, Page page, HttpServletRequest request) {
		list(shopId, search, modelMap, page, request);
		return "shop/commodity-list-ajax";
	}

	/**
	 * 店铺的单品详情页
	 *
	 * @param shopId
	 *            店铺id
	 * @param commodityNo
	 *            商品编码
	 * @return
	 */
	@WXLoginValidate
	@RequestMapping("/{shopId}/item/{commodityNo}")
	public String detail(@PathVariable("shopId") String shopId, @PathVariable("commodityNo") String commodityNo, String pageType, ModelMap modelMap, HttpServletRequest request) {
		// 记录访客信息
		visitorComponent.saveVisitorInfo(shopId, commodityNo, request);
		String currentShopId = SessionUtils.getCurrentShopIdOrDefault();
		// 判断用户的身份并设置当前访问的店铺
		if (SessionUtils.getLoginUserDetails() != null) {
			String userId = SessionUtils.getLoginUserDetails().getUserId();
			// 判断会员是否是优粉，是则设置当前店铺ID为自己的店铺，否则为总店店铺
			ShopVo shopInfo = shopService.getShopModelByUserId(userId);
			if (shopInfo != null && !currentShopId.equals(shopInfo.getId())) {
				// 设置当前访问的店铺
				SessionUtils.setCurrentShopId(shopInfo.getId());
				return "redirect:/" + shopInfo.getId() + ".sc?flag=1";
			} else {
				shopId = currentShopId;
			}
		}
		if (!shopId.equals(currentShopId)) {
			SessionUtils.setCurrentShopId(shopId);
		}

		CommodityStyleVo styleVo = commodityService.getCommodityByNo(commodityNo, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		Assert.notNull(styleVo, String.format("获取商品%s详情异常", commodityNo));
		request.setAttribute("commodityNo", commodityNo);
		ShopVo shopVo = shopService.getAndCheckShopById(shopId, request);
		// 查询同款商品
		List<CommodityStyleVo> styleCommoditys = commodityService.getSameSellerCommodity(styleVo);
		// 基本信息
		List<CommodityPropertyOutputDto> propList = commodityStyleFrontApi.getPropByCommodityId(styleVo.getId());
		List<List<CommodityPropertyOutputDto>> propInfo = null;
		String showSkin = "";
		if (propList != null && propList.size() > 0) {
			propInfo = new ArrayList<List<CommodityPropertyOutputDto>>();
			List<CommodityPropertyOutputDto> linePropList = new ArrayList<CommodityPropertyOutputDto>();
			for (int i = 0; i < propList.size(); i++) {
				CommodityPropertyOutputDto prop = propList.get(i);
				linePropList.add(prop);
				if (i % 2 == 1) {
					propInfo.add(linePropList);
					linePropList = new ArrayList<CommodityPropertyOutputDto>();
				}
			}
			if (null != linePropList && linePropList.size() > 0) {
				propInfo.add(linePropList);
			}

			for (CommodityPropertyOutputDto prop : propList) {
				String propItemNo = prop.getPropItemNo();
				if (null != propItemNo && "HaR06".equals(propItemNo)) {
					CommodityCortexOutputDto cortext = commodityCortexFrontApi.getByNo(prop.getPropValueNo());
					if (null != cortext && null != cortext.getIsNotDescription() && 100 == cortext.getIsNotDescription()) {
						showSkin = "show";
					}
				}
			}
		}
		// 尺码对照表图片
		Assert.notEmpty(styleCommoditys, String.format("同款商品不能为空,商品编码:%s", commodityNo));
		String sizePicture = commodityStyleFrontApi.getPictureUrl(commodityNo);
		List<String> sizeTitle = new ArrayList<String>();
		List<List<String>> sizeConList = new ArrayList();
		if (StringUtils.isNotBlank(sizePicture)) {
			try {
				JSONArray sizeArray = JSON.parseArray(sizePicture);
				for (int i = 0; i < sizeArray.size(); i++) {
					JSONObject sizeJson = sizeArray.getJSONObject(i);
					JSONArray dictionaryArr = sizeJson.getJSONArray("Dictionary");
					String size = sizeJson.getString("Size");
					String sizeName = sizeJson.getString("SizeName");
					if (i == 0) {
						sizeTitle.add(sizeName);
					}
					sizeTitle.add(size);
					if (null != dictionaryArr) {
						List<String> sizeCon = null;
						for (int j = 0; j < dictionaryArr.size(); j++) {
							JSONObject sizeMap = dictionaryArr.getJSONObject(j);
							String siStyleName = sizeMap.getString("Key");
							String sizeNo = sizeMap.getString("Value");
							if (i == 0) {
								sizeCon = new ArrayList<String>();
								sizeCon.add(siStyleName);
								sizeConList.add(sizeCon);
							} else {
								sizeCon = sizeConList.get(j);
							}
							sizeCon.add(sizeNo);
						}
					}
				}
			} catch (Exception e) {
				logger.error("解析尺码表出错,json详情：" + sizePicture, e);
			}
		}
		if (sizeTitle.size() > 0) {
			sizeConList.add(0, sizeTitle);
		}
		modelMap.addAttribute("styleCommoditys", styleCommoditys);
		modelMap.addAttribute("commodity", styleVo);
		modelMap.addAttribute("shop", shopVo);
		modelMap.addAttribute("skuMaxCount", systemService.getShoppingCartMaxSkuCount());
		modelMap.addAttribute("wxConfig", wXService.getWXConfig(weiXinProperties.getAppId()));
		modelMap.addAttribute("pageType", pageType);
		modelMap.addAttribute("propInfo", propInfo);
		modelMap.addAttribute("showSkin", showSkin);
		modelMap.addAttribute("sizeConList", sizeConList);

		return "shop/commodity-detail";
	}

	/**
	 * 店铺的单品详情页 -C端 （不用登陆）
	 *
	 * @param shopId
	 *            店铺id
	 * @param commodityNo
	 *            商品编码
	 * @return
	 */
	@RequestMapping("/{shopId}/itemC/{commodityNo}")
	public String detailC(@PathVariable("shopId") String shopId, @PathVariable("commodityNo") String commodityNo, String pageType, ModelMap modelMap, HttpServletRequest request) {
		CommodityStyleVo styleVo = commodityService.getCommodityByNo(commodityNo, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		Assert.notNull(styleVo, String.format("获取商品%s详情异常", commodityNo));
		request.setAttribute("commodityNo", commodityNo);
		ShopVo shopVo = shopService.getAndCheckShopById(shopId, request);
		// 查询同款商品
		List<CommodityStyleVo> styleCommoditys = commodityService.getSameSellerCommodity(styleVo);
		// 基本信息
		List<CommodityPropertyOutputDto> propList = commodityStyleFrontApi.getPropByCommodityId(styleVo.getId());
		List<List<CommodityPropertyOutputDto>> propInfo = null;
		if (propList != null && propList.size() > 0) {
			propInfo = new ArrayList<List<CommodityPropertyOutputDto>>();
			List<CommodityPropertyOutputDto> linePropList = new ArrayList<CommodityPropertyOutputDto>();
			for (int i = 0; i < propList.size(); i++) {
				CommodityPropertyOutputDto prop = propList.get(i);
				linePropList.add(prop);
				if (i % 2 == 1) {
					propInfo.add(linePropList);
					linePropList = new ArrayList<CommodityPropertyOutputDto>();
				}
			}
			if (null != linePropList && linePropList.size() > 0) {
				propInfo.add(linePropList);
			}
		}
		// 尺码对照表图片
		String sizePicture = commodityStyleFrontApi.getPictureUrl(commodityNo);
		Assert.notEmpty(styleCommoditys, String.format("同款商品不能为空,商品编码:%s", commodityNo));
		modelMap.addAttribute("styleCommoditys", styleCommoditys);
		modelMap.addAttribute("commodity", styleVo);
		modelMap.addAttribute("shop", shopVo);
		modelMap.addAttribute("skuMaxCount", systemService.getShoppingCartMaxSkuCount());
		modelMap.addAttribute("wxConfig", wXService.getWXConfig(weiXinProperties.getAppId()));
		// 设置当前店铺ID
		SessionUtils.setCurrentShopId(shopId);
		modelMap.addAttribute("pageType", pageType);
		modelMap.addAttribute("propInfo", propInfo);
		modelMap.addAttribute("sizePicture", sizePicture);
		return "shop/commodity-detail";
	}

	@RequestMapping("/commodity/cortext/{cortextNo}")
	public String getCortex(@PathVariable("cortextNo") String cortextNo, ModelMap map) {
		CommodityCortexOutputDto cortext = commodityCortexFrontApi.getByNo(cortextNo);
		map.addAttribute("cortext", cortext);
		return "shop/cortextInfo";
	}

	@RequestMapping("/commodity/service")
	public String service() {
		return "shop/service";
	}

	@RequestMapping("/commodity/selectSize/{commodityNo}")
	public String selectSize(@PathVariable("commodityNo") String commodityNo, ModelMap modelMap) {
		CommodityStyleVo styleVo = commodityService.getCommodityByNo(commodityNo, Boolean.TRUE, Boolean.TRUE, Boolean.TRUE);
		Assert.notNull(styleVo, String.format("获取商品%s详情异常", commodityNo));
		List<CommodityStyleVo> styleCommoditys = commodityService.getSameSellerCommodity(styleVo);
		modelMap.addAttribute("commodity", styleVo);
		modelMap.addAttribute("styleCommoditys", styleCommoditys);
		modelMap.addAttribute("skuMaxCount", systemService.getShoppingCartMaxSkuCount());
		return "shop/selectSize";
	}

	/**
	 * 店铺分类
	 *
	 * @param shopId
	 *            店铺id
	 * @return
	 */
	@RequestMapping("/{shopId}/category")
	public String category(@PathVariable("shopId") String shopId, ModelMap modelMap) {
		List<ShopCatVo> shopCatVos = shopService.getShopCatByShopId(shopId);
		if (!CollectionUtils.isEmpty(shopCatVos))
			modelMap.addAttribute("shopCats", shopCatVos);
		ShopVo shopVo = shopService.getShopModelById(shopId);
		Assert.notNull(shopVo, "获取店铺信息异常");
		modelMap.addAttribute("shop", shopVo);
		return "shop/category-list";
	}

	/**
	 * 获取店铺下该分类的商品列表
	 *
	 * @return
	 */
	@RequestMapping("/{shopId}/category/{categoryId}")
	public String categorySearch(ModelMap modelMap, CommoditySearchVo searchVo) {
		ShopVo shopVo = shopService.getShopModelById(searchVo.getShopId());
		Assert.notNull(shopVo, "获取店铺信息异常");
		modelMap.addAttribute("shop", shopVo);
		modelMap.addAttribute("categoryId", searchVo.getCategoryId());
		int count = shoppingCartService.getShoopingCartSkusCount().getTotalCount();
		modelMap.addAttribute("count", count);
		Page<CommodityStyleVo> page = commodityService.searchShopCategoryCommodity(searchVo);
		modelMap.addAttribute("page", page);
		return "shop/category-search";
	}

	/**
	 * 商品列表
	 *
	 * @param searchVo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/{shopId}/category-search/refresh")
	public String categorySearchRefresh(CommoditySearchVo searchVo, ModelMap modelMap) {
		Page<CommodityStyleVo> page = commodityService.searchShopCategoryCommodity(searchVo);
		modelMap.addAttribute("page", page);
		return "shop/search-refresh";
	}

	@RequestMapping(value = "/shop/check", method = RequestMethod.POST)
	@ResponseBody
	public Result<Boolean> checkShop() {
		return shopService.checkHasCreateShop();
	}

	@RequestMapping("/{commodityNo}/qrCodeOfComm")
	public String qrCodeOfComm(@PathVariable("commodityNo") String commodityNo, ModelMap modelMap, HttpServletRequest request) {
		String shopId = SessionUtils.getCurrentShopIdOrDefault();
		modelMap.addAttribute("commodityNo", commodityNo);
		modelMap.addAttribute("shopId", shopId);
		return "shop/commErweima";
	}

	/**
	 * 立即购买，不经过加购物车操作
	 *
	 * @return
	 */
	@RequestMapping(value = "/buy-right-now", method = RequestMethod.GET)
	@LoginValidate
	public String buyNow(SelectItemVo selectItemVo, ModelMap modelMap) {
		OrderPayVo orderPayVo = orderService.getOrderPayInfo(selectItemVo);
		if (!CollectionUtils.isEmpty(orderPayVo.getCommoditys())) {
			if (orderPayVo.getCommoditys().size() > 3) {
				orderPayVo.setCommoditys(orderPayVo.getCommoditys().subList(0, 3));
			}
		}
		modelMap.addAttribute("orderPay", orderPayVo);

		// 用户所在店铺
		String currentid = SessionUtils.getCurrentShopIdOrDefault();
		ShopVo shopVo = shopService.getShopModelById(currentid);
		Assert.notNull(shopVo, "获取店铺信息异常");
		modelMap.addAttribute("shop", shopVo);
		// 设置购买方式：1：直接购买， 2：从购物车中购买
		modelMap.addAttribute("buyType", "1");

		SessionUserDetails sessionUserDetails = SessionUtils.getLoginUserDetails();
		sessionUserDetails.setAddressPayRedirect(Boolean.FALSE);
		sessionUserDetails.save();
		modelMap.addAttribute("memberId", sessionUserDetails.getUserId());
		return "order/pay-order";
	}
	@RequestMapping("/help-seoul")
	public String helpSeoul(ModelMap modelMap, HttpServletRequest request) {
		return "shop/help-seoul";
	}

	// /**
	// * 保存记录访客信息
	// *
	// * @param shopId
	// * @param commodityNo
	// * @param request
	// */
	// private void saveVisitorInfo(String shopId, String commodityNo,
	// HttpServletRequest request) {
	// // 记录访客信息
	// // 从session 中获取用户信息
	// SessionUserDetails loginUser = SessionUtils.getLoginUserDetails();
	// if (null != loginUser) {
	// VisitorVo visitorVo = new VisitorVo();
	// visitorVo.setCommodityNo(commodityNo);
	// visitorVo.setShopId(shopId);
	// visitorVo.setVisitorId(loginUser.getUserId());
	// visitorVo.setVisitorIp(SessionUtils.getRemoteIP());
	// visitorVo.setVisitType(VisitorVisitType.COMMODITY_VISIT.getType());//
	// 访问单品页
	// visitorVo.setVisitTime(new Date());
	// HttpSession httpSession = request.getSession();
	// // 获取运行环境
	// Object runEnvironment =
	// httpSession.getAttribute(SessionConstant.RUNNING_ENVIRONMENT);
	// if
	// (runEnvironment.toString().equals(OperatingEnvironmentEnum.WX_PLATFORM.getKey()))
	// {
	// visitorVo.setSourceType(VisitorSourceType.WEIXIN.getType());//
	// 运行环境为微信平台访客来源为weixin
	// } else {
	// visitorVo.setSourceType(VisitorSourceType.OTHER.getType());// 其他访客来源
	// }
	//
	// setVisitorRecord(visitorVo);
	// }
	// }
	//
	// /**
	// * 异步保存访客信息
	// *
	// * @param visitorVo
	// */
	// private void setVisitorRecord(final VisitorVo visitorVo) {
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// visitorService.insertVistor(visitorVo);
	// }
	// }).start();
	// }

}
