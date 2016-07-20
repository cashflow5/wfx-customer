package com.yougou.wfx.customer.service.shoppingcart;

import com.yougou.wfx.customer.model.commodity.CommodityShoppingCartVo;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.shoopingcart.SelectItemVo;
import com.yougou.wfx.customer.model.shoopingcart.ShoopingCartResultVo;
import com.yougou.wfx.customer.model.shoopingcart.ShopCartCountVo;
import com.yougou.wfx.customer.model.shoopingcart.ShoppingCartChangeVo;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/31
 */
public interface IShoppingCartService {

    /**
     * 获取当前用户购物车数据，登录用户直接取数据库，未登录用户取cookie,以店铺分组
     *
     * @return 商品列表
     */
    Map<String, List<CommodityShoppingCartVo>> getShoppingCartCommodityList();

    Map<String, List<CommodityShoppingCartVo>> getShoppingCartCommodityList(Map<String, String> shopIds);
    
    void upDateShoppingCartData(Collection<CommodityShoppingCartVo> cartVos);
    
    /**
     * 计算购物车数据
     *
     * @return
     */
    ShoopingCartResultVo countShoopingCartPrice();

    /**
     * 合并购物车数据
     *
     * @param isPay true:以未登录状态购物车勾选状态为主,false:合并勾选状态
     */
    void mergeShoppingCartData(boolean isPay);

    /**
     * 合并购物车数据
     * 默认isPay=false
     *
     * @see #mergeShoppingCartData(boolean)
     */
    void mergeShoppingCartData();

    /**
     * 修改购物车商品数量
     *
     * @param shoppingCartChangeVo
     * @return
     */
    Result<ShoopingCartResultVo> changeShoppingCartNum(ShoppingCartChangeVo shoppingCartChangeVo);

    /**
     * 移除购物车的sku
     *
     * @param ids
     * @return
     */
    Result<ShoopingCartResultVo> removeSkus(String ids);

    /**
     * 用于购物车商品 勾选操作
     *
     * @param selectItemVo
     * @return
     */
    Result<ShoopingCartResultVo> selectItem(SelectItemVo selectItemVo);

    /**
     * 加入购物车操作,加入成功则返回购物车数量，否则返回错误信息，例如超过最大限制99
     *
     * @param shoppingCartChangeVo
     * @return
     */
    Result<Integer> addCart(ShoppingCartChangeVo shoppingCartChangeVo);

    /**
     * 获取购物车商品列表
     *
     * @return
     */
    List<CommodityShoppingCartVo> getCommodityShoppingCartVos(int type);

    List<CommodityShoppingCartVo> getCheckedCommodityShoppingCartVos();

    /**
     * 获取购物车商品Sku条目数
     *
     * @return
     */
    ShopCartCountVo getShoopingCartSkusCount();

    /**
     * 清除指定购物车数据,用于下订单后操作
     *
     * @param cartVos
     */
    void clearShoppingcartByItems(List<CommodityShoppingCartVo> cartVos);

    /**
     * 立即购买
     *
     * @param shoppingCartChangeVo
     */
    void buyNow(ShoppingCartChangeVo shoppingCartChangeVo);

    /**
     * 加车之前检查购物车
     *
     * @param shoppingCartChangeVo
     * @return
     */
    Result<Boolean> checkAdd(ShoppingCartChangeVo shoppingCartChangeVo);
}
