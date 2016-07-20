package com.yougou.wfx.customer.discover;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yougou.wfx.appserver.vo.discover.DiscoverArticle;
import com.yougou.wfx.appserver.vo.discover.DiscoverArticleDetail;
import com.yougou.wfx.appserver.vo.discover.DiscoverChannel;
import com.yougou.wfx.appserver.vo.discover.DiscoverHome;
import com.yougou.wfx.appserver.vo.discover.DiscoverSearcher;
import com.yougou.wfx.customer.annotations.WXLoginValidate;
import com.yougou.wfx.customer.common.enums.MenuEnum;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.common.Page;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.customer.service.bapp.DiscoverService;
import com.yougou.wfx.customer.service.shop.IShopService;
import com.yougou.wfx.customer.configurations.weixin.WeiXinProperties;
import com.yougou.wfx.customer.service.weixin.IWXService;
import com.yougou.wfx.customer.ufans.VisitorComponent;

/**
 * 发现模块
 * 
 * @author li.lq
 * @Date 2016年6月19日
 */
@Controller
public class DiscoverController {
	@Autowired
	private DiscoverService discoverService;
	@Autowired
	private IShopService shopService;
	@Autowired
	private IWXService wXService;
	@Autowired
	private WeiXinProperties weiXinProperties;
	@Autowired
	private VisitorComponent visitorComponent;

	/**
	 * 发现首页
	 * 
	 * @return
	 */
	@WXLoginValidate
	@RequestMapping("/discover/home")
	public String home(@RequestParam(name = "shopId", required = false) String shopId, DiscoverSearcher discoverSearcher, ModelMap modelMap, HttpServletRequest request) {
		// 设置当前菜单
		SessionUtils.setCurrentMenuID(MenuEnum.MENU_DISCOVER.getKey());
		if(StringUtils.isEmpty(shopId)){
			return "redirect:/discover/home.sc?shopId=" + SessionUtils.getCurrentShopIdOrDefault();
		}
		
		DiscoverHome result = discoverService.getHomeData(discoverSearcher);
		modelMap.addAttribute("discoverHome", result);
		// 记录访客信息
		visitorComponent.saveVisitorInfo(shopId, null, request);
		return "discover/discoverIndex";
	}

	/**
	 * 发现列表
	 * 
	 * @return
	 */
	@RequestMapping("/discover/list/{channelId}")
	public String list(@PathVariable("channelId") String channelId, ModelMap modelMap, Page page, HttpServletRequest request) {

		Page<DiscoverArticle> pageArticleList = discoverService.getDiscoverArticlePageList(channelId, page);
		List<DiscoverChannel> channelList = discoverService.getShowDiscoverChannelList();
		modelMap.put("channelList", channelList);
		modelMap.put("result", pageArticleList);
		modelMap.put("channelId", channelId);
		return "discover/discoverNewlist";
	}

	/**
	 * 发现列表ajax
	 * 
	 * @return
	 */
	@RequestMapping("/discover/list_ajax/{channelId}")
	public String list_ajax(@PathVariable("channelId") String channelId, ModelMap modelMap, Page page, HttpServletRequest request) {
		list(channelId, modelMap, page, request);
		return "discover/discoverNewlistAjax";
	}

	/**
	 * 发现详情
	 * 
	 * @param modelMap
	 * @param articleId
	 * @return
	 */
	@RequestMapping("/discover/view/{articleId}")
	public String view(@PathVariable("articleId") String articleId, ModelMap modelMap) {
		DiscoverArticleDetail discoverArticle = discoverService.getDiscoverArticleById(articleId);
		modelMap.put("discoverArticle", discoverArticle);
		modelMap.addAttribute("wxConfig", wXService.getWXConfig(weiXinProperties.getAppId()));
		if (discoverArticle.getAuthorType().intValue() == 2) {// 分销商
			ShopVo shopVo = shopService.getShopByLoginName(discoverArticle.getAuthorAccount());
			modelMap.put("shopInfoDetail", shopVo);
		}

		return "discover/discoverDetail";
	}

	/**
	 * 发现详情(分享)
	 * 
	 * @param modelMap
	 * @param articleId
	 * @return
	 */
	@WXLoginValidate
	@RequestMapping("/{shopId}/discover/view/{articleId}")
	public String viewArticleId(@PathVariable("shopId") String shopId, @PathVariable("articleId") String articleId, ModelMap modelMap, HttpServletRequest request) {
		DiscoverArticleDetail discoverArticle = discoverService.getDiscoverArticleById(articleId);
		modelMap.put("discoverArticle", discoverArticle);
		if (discoverArticle.getAuthorType().intValue() == 2) {// 分销商
			ShopVo shopVo = shopService.getShopByLoginName(discoverArticle.getAuthorAccount());
			modelMap.put("shopInfoDetail", shopVo);
		}
		// 记录访客信息
		visitorComponent.saveVisitorInfo(shopId, null, request);
		return "discover/discoverDetail";
	}

}
