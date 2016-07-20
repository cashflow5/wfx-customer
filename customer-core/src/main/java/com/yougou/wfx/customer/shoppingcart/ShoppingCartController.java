package com.yougou.wfx.customer.shoppingcart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.yougou.wfx.customer.annotations.LoginValidate;
import com.yougou.wfx.customer.common.enums.MenuEnum;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.configurations.weixin.WeiXinProperties;
import com.yougou.wfx.customer.model.commodity.CommodityShoppingCartVo;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.shoopingcart.SelectItemVo;
import com.yougou.wfx.customer.model.shoopingcart.ShoopingCartResultVo;
import com.yougou.wfx.customer.model.shoopingcart.ShoppingCartChangeVo;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.customer.model.usercenter.UserLoginAccountVo;
import com.yougou.wfx.customer.model.usercenter.UserLoginVo;
import com.yougou.wfx.customer.model.weixin.base.WXUserInfoResponse;
import com.yougou.wfx.customer.service.shop.IShopService;
import com.yougou.wfx.customer.service.shoppingcart.IShoppingCartService;
import com.yougou.wfx.customer.service.system.ISystemService;
import com.yougou.wfx.customer.service.usercenter.ILoginService;

/**
 * Created by zhang.sj on 2016/3/22.
 */

@Controller
public class ShoppingCartController {

    @Autowired
    private IShoppingCartService shoppingCartService;
    @Autowired
    private ISystemService systemService;
    @Autowired
    private IShopService shopService;
    @Autowired
	private ILoginService loginService;
    @Autowired
	private WeiXinProperties weiXinProperties;

    @RequestMapping("/{shopId}/shoppingcart")
    public String index(ModelMap modelMap, @PathVariable String shopId, HttpServletRequest request) {
    	//设置当前菜单
		SessionUtils.setCurrentMenuID(MenuEnum.MENU_SHOPCART.getKey());
		
		modelMap.addAttribute("from", request.getParameter("from"));
		
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
			loginService.updateSessionUserDetails(userLoginAccount).getData();
		} else if(!StringUtils.isEmpty(unionId)){
			// 不考虑没有微信注册的情况
			WXUserInfoResponse userInfo = new WXUserInfoResponse();
			userInfo.setUnionid(unionId);
			loginService.doWithWXUserInfo(userInfo, weiXinProperties.getAppId(), weiXinProperties.getAppSecret());
		}
		modelMap.addAttribute("shopId", shopId);
		//获取购物车的商品列表
		List<CommodityShoppingCartVo> cartCommoditys = shoppingCartService.getCommodityShoppingCartVos(0);
		List<CommodityShoppingCartVo> cartCommoditys2 = new ArrayList<CommodityShoppingCartVo>();
		if(!CollectionUtils.isEmpty(cartCommoditys)){
			//过滤立即购买的商品并删除
			String ids = "";
			for(CommodityShoppingCartVo cartCommodity: cartCommoditys){
				if(cartCommodity.getBuyMode() == 1){
					ids += cartCommodity.getId() + ",";
				}else{
					cartCommoditys2.add(cartCommodity);
				}
			}
			if(ids.endsWith(",")){
				ids = ids.substring(0, ids.length()-1);
			}
			this.removeSku(ids);
		}
		
		
		if (CollectionUtils.isEmpty(cartCommoditys2)){
	        modelMap.addAttribute("shopId", shopId);
			return "shoppingcart/shopping-cart-empty";
		}
		
		modelMap.addAttribute("cartCommoditys", cartCommoditys2);
		//获取店铺信息
		ShopVo shopVo = shopService.getShopModelById(shopId);
		Assert.notNull(shopVo, "获取店铺信息异常!");
        
        modelMap.addAttribute("shop", shopVo);
        ShoopingCartResultVo cartResultVo = shoppingCartService.countShoopingCartPrice();
        modelMap.addAttribute("cartResultVo", cartResultVo);
        modelMap.addAttribute("skuMaxCount", systemService.getShoppingCartMaxSkuCount());
        return "shoppingcart/shopping-cart";
    }
    
    @RequestMapping("/shoppingcart")
    public String index2(ModelMap modelMap,String shopId, HttpServletRequest request) {
    	if(StringUtils.isBlank(shopId)){
    		shopId = SessionUtils.getCurrentShopIdOrDefault();
    	}
        return this.index(modelMap, shopId, request);
    }

    @RequestMapping(value = "/changeNum", method = RequestMethod.POST)
    @ResponseBody
    public Result<ShoopingCartResultVo> changeNum(ShoppingCartChangeVo shoppingCartChangeVo) {
        return shoppingCartService.changeShoppingCartNum(shoppingCartChangeVo);
    }

    @RequestMapping(value = "/removeSkus", method = RequestMethod.POST)
    @ResponseBody
    public Result<ShoopingCartResultVo> removeSku(String ids) {
        return shoppingCartService.removeSkus(ids);
    }

    /**
     * 勾选或者不勾选sku
     * 用于购物车商品 勾选操作
     *
     * @return
     */
    @RequestMapping(value = "/selectItem", method = RequestMethod.POST)
    @ResponseBody
    public Result<ShoopingCartResultVo> selectItem(SelectItemVo selectItemVo) {
        return shoppingCartService.selectItem(selectItemVo);
    }

    /**
     * 加入购物车操作
     *
     * @return
     */
    @RequestMapping(value = "/addCart", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> addCart(ShoppingCartChangeVo shoppingCartChangeVo) {
        return shoppingCartService.addCart(shoppingCartChangeVo);
    }

    /**
     * 加车之前检查购物车数据
     *
     * @param shoppingCartChangeVo
     * @return
     */
    @RequestMapping(value = "/shoppingcart/checkadd", method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> checkAdd(ShoppingCartChangeVo shoppingCartChangeVo) {
        return shoppingCartService.checkAdd(shoppingCartChangeVo);
    }

    /**
     * 加入购物车操作
     *
     * @return
     */
    @RequestMapping(value = "/buynow", method = RequestMethod.GET)
    @LoginValidate
    public String buyNow(ShoppingCartChangeVo shoppingCartChangeVo, RedirectAttributes redirectAttributes) {
        shoppingCartService.buyNow(shoppingCartChangeVo);
        return "redirect:/confirm_order.sc";
    }

    @RequestMapping(value = "/shoopingcart/count", method = RequestMethod.POST)
    @ResponseBody
    public int count() {
        return shoppingCartService.getShoopingCartSkusCount().getTotalCount();
    }
}
