package com.yougou.wfx.customer.service.shop;

import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.mall.PropertyVo;
import com.yougou.wfx.customer.model.shop.ShopCatVo;
import com.yougou.wfx.customer.model.shop.ShopVo;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/25
 */
public interface IShopService {

	/**
	 * 根据店铺id获取店铺信息
	 *
	 * @param shopId
	 *            店铺id
	 * @return
	 */
	ShopVo getShopModelById(String shopId);

	ShopVo getShopBySellerId(String sellerId);

	/**
	 * 获取店铺下的分类信息
	 *
	 * @param shopId
	 * @return
	 */
	List<ShopCatVo> getShopCatByShopId(String shopId);

	boolean checkEnableCreateShop();

	/**
	 * 检查是否当前登录用户已经创建过店铺
	 *
	 * @return
	 */
	Result<Boolean> checkHasCreateShop();

	Map<String, String> getShopDefaultImagesUrl();

	/**
	 * 校验店铺信息的合法性
	 *
	 * @param shopId
	 * @param request
	 */
	ShopVo getAndCheckShopById(String shopId, HttpServletRequest request);

	/**
	 * 根据用户ID,loginaName查询用户店铺信息(为判断优粉使用)
	 * 
	 * @param userId
	 * @return
	 */
	ShopVo getShopModelByUserId(String userId);
	
	/**
	 * 根据店铺loginName获取店铺信息
	 * @param loginName
	 * @return
	 */
	ShopVo getShopByLoginName(String loginName);
	
	List<PropertyVo> getPropertys(String type,String shopId,String catId,String brandNo);
}
