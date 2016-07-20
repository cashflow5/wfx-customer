package com.yougou.wfx.customer.service.shoppingcart.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.yougou.tools.common.utils.UUIDUtil;
import com.yougou.wfx.customer.common.constant.CookieConstant;
import com.yougou.wfx.customer.common.constant.WfxConstant;
import com.yougou.wfx.customer.common.cookie.CookieSecurity;
import com.yougou.wfx.customer.common.cookie.CookieUtils;
import com.yougou.wfx.customer.common.datetime.DateUtils;
import com.yougou.wfx.customer.common.session.SessionUserDetails;
import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.common.utils.FormatUtils;
import com.yougou.wfx.customer.common.utils.JacksonUtils;
import com.yougou.wfx.customer.model.commodity.*;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.model.shoopingcart.SelectItemVo;
import com.yougou.wfx.customer.model.shoopingcart.ShoopingCartResultVo;
import com.yougou.wfx.customer.model.shoopingcart.ShopCartCountVo;
import com.yougou.wfx.customer.model.shoopingcart.ShoppingCartChangeVo;
import com.yougou.wfx.customer.model.shop.ShopVo;
import com.yougou.wfx.customer.service.commodity.ICommodityService;
import com.yougou.wfx.customer.service.shop.IShopService;
import com.yougou.wfx.customer.service.shoppingcart.IShoppingCartService;
import com.yougou.wfx.customer.service.system.ISystemService;
import com.yougou.wfx.order.api.front.IShoppingcartFrontApi;
import com.yougou.wfx.order.dto.input.ShoppingcartInputDto;
import com.yougou.wfx.order.dto.output.ShoppingcartOutputDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/31
 */
@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {

    private static final Logger log = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    @Autowired
    private ICommodityService commodityService;
    @Autowired
    private IShoppingcartFrontApi shoppingcartFrontApi;
    @Autowired
    private IShopService shopService;
    @Autowired
    private ISystemService systemService;


    /**
     * 获取当前用户购物车数据，登录用户直接取数据库，未登录用户取cookie,以店铺分组显示
     *
     * @return 商品列表
     */
    @Override
    public Map<String, List<CommodityShoppingCartVo>> getShoppingCartCommodityList() {
        return this.getShoppingCartCommodityList(null);
    }

    /**
     * 获取当前用户购物车数据，登录用户直接取数据库，未登录用户取cookie,以店铺分组显示
     *
     * @param shopMap key:shop
     * @return 商品列表
     */
    @Override
    public Map<String, List<CommodityShoppingCartVo>> getShoppingCartCommodityList(final Map<String, String> shopMap) {
        SessionUserDetails user = SessionUtils.getLoginUserDetails();
        Map<String, List<CommodityShoppingCartVo>> resultMap = null;
        List<CommodityShoppingCartVo> shoppingCartVos = getCommodityShoppingCartVos(1);
        if (CollectionUtils.isEmpty(shoppingCartVos)) {
            return null;
        }
        resultMap = Maps.newHashMap();
        for (CommodityShoppingCartVo cartVo : shoppingCartVos) {
            if (shopMap != null)
                shopMap.put(cartVo.getShopCode(), cartVo.getShopId());
            if (resultMap.containsKey(cartVo.getShopCode())) {
                resultMap.get(cartVo.getShopCode()).add(cartVo);
            } else {
                resultMap.put(cartVo.getShopCode(), Lists.newArrayList(cartVo));
            }
        }
        return resultMap;
    }

    /**
     * 计算购物车数据
     *
     * @return
     */
    @Override
    public ShoopingCartResultVo countShoopingCartPrice() {
        List<CommodityShoppingCartVo> shoppingCartVos = getCommodityShoppingCartVos(1);
        ShoopingCartResultVo resultVo = new ShoopingCartResultVo();
        if (CollectionUtils.isEmpty(shoppingCartVos)) {
            return resultVo;
        }
        double totalPrice = 0;
        int totalCount = 0;
        resultVo.setSelectAll(Boolean.TRUE);
        for (CommodityShoppingCartVo shoppingCartVo : shoppingCartVos) {
            if (shoppingCartVo.isChecked()) {
                totalPrice += shoppingCartVo.getWfxPrice() * shoppingCartVo.getCount();
                totalCount += shoppingCartVo.getCount();
            } else {
                resultVo.setSelectAll(Boolean.FALSE);
            }
        }
        resultVo.setTotalPrice(FormatUtils.formatDouble(totalPrice));
        resultVo.setTotalCount(totalCount);
        resultVo.setTotalSize(shoppingCartVos.size());
        return resultVo;
    }

    /**
     * 合并购物车数据，默认合并勾选状态
     */
    @Override
    public void mergeShoppingCartData() {
        mergeShoppingCartData(false);
    }

    /**
     * 合并购物车数据,新加购物上商品默认勾选，合并购物车商品勾选状态根据cookie的勾选状态 合并购物车操作发生在用户登录状态
     * 合并购物车的时候以未登录状态下的勾选状态为主
     *
     * @param isPay true:以未登录状态购物车勾选状态为主,false:合并勾选状态
     */
    @Override
    public void mergeShoppingCartData(boolean isPay) {
        int skuMaxCount = systemService.getShoppingCartMaxSkuCount();

        SessionUserDetails user = SessionUtils.getLoginUserDetails();
        if (user == null) {
            log.error("用户未登录不能合并购物车数据!");
            return;
        }
        List<CommodityShoppingCartVo> cookieData = getShoppingCartCommodityFromCookie();
        List<CommodityShoppingCartVo> dbCartList = getShoppingCartCommodityFromDb(user.getUserId());
        Map<String, Map<String, CommodityShoppingCartVo>> dbData = convertCommodityShoppingCart(dbCartList);
        //cookie数据为空则不需要合并
        if (CollectionUtils.isEmpty(cookieData)) {
            return;
        }
        if (CollectionUtils.isEmpty(dbCartList)) {
            dbCartList = Lists.newArrayList();
        }
        if (CollectionUtils.isEmpty(dbData)) {
            dbData = Maps.newHashMap();
        }
        //只有未登录下去提交订单才会触发一下操作
        if (isPay) {
            boolean isChecked = false;
            //再次查找cookie中有没有勾选状态的商品
            for (CommodityShoppingCartVo cartVo : cookieData) {
                if (cartVo.isChecked()) {
                    isChecked = true;
                    break;
                }
            }
            if (isChecked) {
                //取消勾选数据库中的勾选状态
                for (CommodityShoppingCartVo cartVo : dbCartList) {
                    cartVo.setChecked(false);
                    cartVo.setHasModify(true);
                }
            }
        }
        for (CommodityShoppingCartVo cookieCartVo : cookieData) {
            if (dbData.containsKey(cookieCartVo.getSkuId())) {
                Map<String, CommodityShoppingCartVo> dbCartMap = dbData.get(cookieCartVo.getSkuId());
                if (!CollectionUtils.isEmpty(dbCartMap) && dbCartMap.containsKey(cookieCartVo.getShopId())) {
                    CommodityShoppingCartVo dbCartVo = dbCartMap.get(cookieCartVo.getShopId());
                    dbCartVo.setCount(dbCartVo.getCount() + cookieCartVo.getCount());
                    //购物车数量限制判断
                    if (dbCartVo.getCount() > skuMaxCount) {
                        dbCartVo.setCount(skuMaxCount);
                    }
                    dbCartVo.setHasModify(Boolean.TRUE);
                    dbCartVo.setChecked(cookieCartVo.isChecked());
                }

            } else {
                //新增到数据库
                cookieCartVo.setId("");
                cookieCartVo.setHasModify(Boolean.TRUE);
                cookieCartVo.setChecked(cookieCartVo.isChecked());
                Map<String, CommodityShoppingCartVo> dataMap = Maps.newHashMap();
                dataMap.put(cookieCartVo.getShopId(), cookieCartVo);
                dbData.put(cookieCartVo.getSkuId(), dataMap);
            }
        }
        List<CommodityShoppingCartVo> totalCartVos = Lists.newArrayList();
        for (String key : dbData.keySet()) {
            totalCartVos.addAll(dbData.get(key).values());
        }
        //更新购物车数据
        upDateShoppingCartData(totalCartVos);
        //删除购物车cookie数据
        CookieUtils.removeCookie(CookieConstant.WFX_CUSTOMER_SHOPPING_CART_COOKIE);
    }

    /**
     * 修改购物车商品数量
     *
     * @param shoppingCartChangeVo
     * @return
     */
    @Override
    public Result<ShoopingCartResultVo> changeShoppingCartNum(ShoppingCartChangeVo shoppingCartChangeVo) {
        Result<ShoopingCartResultVo> result = Result.create();
        List<CommodityShoppingCartVo> shoppingCartVos = getCommodityShoppingCartVos(1);
        if (CollectionUtils.isEmpty(shoppingCartVos)) {
            return result.setHasError(Boolean.TRUE).setCode(500).setMessage("不存在购物车数据，添加操作异常!");
        }
        //计算购物车价格
        double totalPrice = 0;
        int totalCount = 0;
        int skuMaxCount = systemService.getShoppingCartMaxSkuCount();
        for (CommodityShoppingCartVo shoppingCartVo : shoppingCartVos) {
            if (shoppingCartVo.getId().equals(shoppingCartChangeVo.getId())) {
                //限制每个sku最多购买数量
                if (shoppingCartChangeVo.getSkuCount() > skuMaxCount) {
                    shoppingCartChangeVo.setSkuCount(skuMaxCount);
                }
                shoppingCartVo.setCount(shoppingCartChangeVo.getSkuCount());
                shoppingCartVo.setHasModify(Boolean.TRUE);
            }
            if (shoppingCartVo.isChecked()) {
                totalCount += shoppingCartVo.getCount();
                totalPrice += shoppingCartVo.getWfxPrice() * shoppingCartVo.getCount();
            }
        }
        //更新购物车数据
        upDateShoppingCartData(shoppingCartVos);
        ShoopingCartResultVo resultVo = new ShoopingCartResultVo();
        resultVo.setTotalCount(totalCount);
        resultVo.setTotalPrice(FormatUtils.formatDouble(totalPrice));
        resultVo.setViewPrice(FormatUtils.format(totalPrice));
        result.setData(resultVo);
        return result;
    }

    /**
     * 移除购物车的sku
     *
     * @param ids
     * @return
     */
    @Override
    public Result<ShoopingCartResultVo> removeSkus(String ids) {
        Result<ShoopingCartResultVo> result = Result.create();
        if (StringUtils.isEmpty(ids)) {
            return result.setHasError(Boolean.TRUE).setCode(500).setMessage("参数异常！");
        }
        List<String> idsList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(ids);
        if (CollectionUtils.isEmpty(idsList)) {
            return result.setHasError(Boolean.TRUE).setCode(500).setMessage("参数异常！");
        }
        Set<String> idsSet = Sets.newHashSet(idsList);
        List<CommodityShoppingCartVo> cartVos = getCommodityShoppingCartVos(0);
        if (CollectionUtils.isEmpty(cartVos)) {
            return result.setHasError(Boolean.TRUE).setCode(500).setMessage("购物车数据为空，删除操作异常!");
        }
        List<CommodityShoppingCartVo> removeSkus = Lists.newArrayList();
        Iterator<CommodityShoppingCartVo> iterator = cartVos.iterator();
        while (iterator.hasNext()) {
            CommodityShoppingCartVo cartVo = iterator.next();
            if (idsSet.contains(cartVo.getId())) {
                iterator.remove();
                removeSkus.add(cartVo);
            }
        }
        doRemoveSkusFromShoppingCart(cartVos, removeSkus);
        //计算购物车价格
        double totalPrice = 0;
        int totalCount = 0;
        for (CommodityShoppingCartVo shoppingCartVo : cartVos) {
            if (shoppingCartVo.isChecked()) {
                totalCount += shoppingCartVo.getCount();
                totalPrice += shoppingCartVo.getWfxPrice() * shoppingCartVo.getCount();
            }
        }
        ShoopingCartResultVo resultVo = new ShoopingCartResultVo();
        resultVo.setTotalCount(totalCount);
        resultVo.setTotalPrice(FormatUtils.formatDouble(totalPrice));
        resultVo.setViewPrice(FormatUtils.format(totalPrice));
        result.setData(resultVo);
        return result;
    }

    /**
     * 用于购物车商品 勾选操作
     *
     * @param selectItemVo
     * @return
     */
    @Override
    public Result<ShoopingCartResultVo> selectItem(SelectItemVo selectItemVo) {
        Result<ShoopingCartResultVo> result = Result.create();
        if (selectItemVo == null) {
            return result.setHasError(Boolean.TRUE).setCode(500).setMessage("参数异常！");
        }
        if (StringUtils.isEmpty(selectItemVo.getIds())) {
            return result.setHasError(Boolean.TRUE).setCode(500).setMessage("id参数异常！");
        }
        List<String> ids = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(selectItemVo.getIds());
        if (ObjectUtils.isEmpty(ids)) {
            return result.setHasError(Boolean.TRUE).setCode(500).setMessage("id参数异常！");
        }
        Set<String> idsSet = Sets.newHashSet(ids);
        List<CommodityShoppingCartVo> cartVos = getCommodityShoppingCartVos(0);
        if (CollectionUtils.isEmpty(cartVos)) {
            return result.setHasError(Boolean.TRUE).setCode(500).setMessage("购物车数据为空，勾选操作异常!");
        }
        double totalPrice = 0;
        int totalCount = 0;
        //计算价格
        for (CommodityShoppingCartVo shoppingCartVo : cartVos) {
            if (idsSet.contains(shoppingCartVo.getId())) {
                if (selectItemVo.isChecked()) {
                    if (!shoppingCartVo.isChecked()) {
                        shoppingCartVo.setChecked(Boolean.TRUE);
                        shoppingCartVo.setHasModify(Boolean.TRUE);
                    }
                    totalCount += shoppingCartVo.getCount();
                    totalPrice += shoppingCartVo.getWfxPrice() * shoppingCartVo.getCount();
                } else {
                    if (shoppingCartVo.isChecked()) {
                        shoppingCartVo.setChecked(Boolean.FALSE);
                        shoppingCartVo.setHasModify(Boolean.TRUE);
                    }
                }
            } else {
                if (shoppingCartVo.isChecked()) {
                    totalCount += shoppingCartVo.getCount();
                    totalPrice += shoppingCartVo.getWfxPrice() * shoppingCartVo.getCount();
                }
            }
        }
        upDateShoppingCartData(cartVos);
        ShoopingCartResultVo resultVo = new ShoopingCartResultVo();
        resultVo.setTotalCount(totalCount);
        resultVo.setTotalPrice(FormatUtils.formatDouble(totalPrice));
        resultVo.setViewPrice(FormatUtils.format(totalPrice));
        result.setData(resultVo);

        return result;
    }


    /**
     * 加入购物车操作,加入成功则返回购物车数量，否则返回错误信息，例如超过最大限制99
     *
     * @param shoppingCartChangeVo
     * @return
     */
    @Override
    public Result<Integer> addCart(ShoppingCartChangeVo shoppingCartChangeVo) {
        Result<Integer> result = Result.create();
        ShopCartCountVo countVo = getShoopingCartSkusCount();
        int total = countVo.getTotalCount();
        int maxSkuCount = systemService.getShoppingCartSkusCount();
        if (total == maxSkuCount) {
            return result.setHasError(Boolean.TRUE).setMessage("添加失败,您的购物车已满！");
        }
        int skuMaxCount = systemService.getShoppingCartMaxSkuCount();
        String msg = String.format("每个尺码最多购买%s件!", skuMaxCount);
        if (shoppingCartChangeVo.getSkuCount() > skuMaxCount) {
            return result.setHasError(Boolean.TRUE).setMessage(msg);
        }
        CommodityShoppingCartVo cartVo = new CommodityShoppingCartVo();
        cartVo.setCommodityNo(shoppingCartChangeVo.getCommodityNo());
        cartVo.setSkuId(shoppingCartChangeVo.getSkuId());
        cartVo.setCount(shoppingCartChangeVo.getSkuCount());
        cartVo.setShopId(shoppingCartChangeVo.getShopId());
        cartVo.setChecked(Boolean.TRUE);
        cartVo.setHasModify(Boolean.TRUE);
        cartVo.setBuyMode(0);
        List<CommodityShoppingCartVo> cartData = getCommodityShoppingCartVos(1);
        if (cartData == null) {
            cartData = Lists.newArrayList();
        }
        boolean hasMerge = Boolean.FALSE;
        for (CommodityShoppingCartVo item : cartData) {
            //判断店铺和skuid都相同才合并
            if (item.getSkuId().equals(cartVo.getSkuId()) && item.getShopId().equals(cartVo.getShopId())) {
                if ((item.getCount() + cartVo.getCount()) > skuMaxCount) {
                    item.setCount(skuMaxCount);
                } else {
                    item.setCount(item.getCount() + cartVo.getCount());
                }
                item.setChecked(Boolean.TRUE);
                item.setHasModify(Boolean.TRUE);
                result.setCode(1).setMessage("添加购物车成功," + msg);
                hasMerge = Boolean.TRUE;
                break;
            }
        }
        if (!hasMerge) {
            cartData.add(cartVo);
            result.setCode(1).setMessage("添加购物车成功！");
        }
        upDateShoppingCartData(cartData);
        total = 0;
        for (CommodityShoppingCartVo commodityShoppingCartVo : cartData) {
            total += commodityShoppingCartVo.getCount();
        }
        return result.setData(total);
    }

    /**
     * 如果没有登录则重新设置cookie数据，登录状态则删除数据库中对应的sku
     *
     * @param cartVos    剩余的购物车数据,为空则清空cookie
     * @param removeSkus 待删除的skuid集合，用于登录状态下的 删除操作
     */

    private void doRemoveSkusFromShoppingCart(List<CommodityShoppingCartVo> cartVos, List<CommodityShoppingCartVo> removeSkus) {
        SessionUserDetails user = SessionUtils.getLoginUserDetails();
        if (user == null) {
            if (CollectionUtils.isEmpty(cartVos)) {
                CookieUtils.removeCookie(CookieConstant.WFX_CUSTOMER_SHOPPING_CART_COOKIE);
            } else {
                upDateShoppingCartData(cartVos, null);
            }
        } else {
            for (CommodityShoppingCartVo cartVo : removeSkus) {
                deleteCommodityShoppingCartVo(user.getUserId(), cartVo.getId());
            }
        }
    }

    /**
     * 获取购物车商品列表
     *
     * @return
     */
    @Override
    public List<CommodityShoppingCartVo> getCommodityShoppingCartVos(int type) {
        SessionUserDetails user = SessionUtils.getLoginUserDetails();
        List<CommodityShoppingCartVo> shoppingCartVos1 = null;
        List<CommodityShoppingCartVo> shoppingCartVos2 = new ArrayList<CommodityShoppingCartVo>();
        if (user == null) {
            //未登录用户购物车数据从Cookie中获取
            shoppingCartVos1 = getShoppingCartCommodityFromCookie();
        } else {
            //从数据库里面获取购物车数据
            shoppingCartVos1 = getShoppingCartCommodityFromDb(user.getUserId());
        }
        //处理下架或者售罄的商品
        dealWithShoppingCartCommoditys(shoppingCartVos1);
        if(type == 1){
        	//过滤立即购买的商品
            if(shoppingCartVos1 !=null){
            	for (CommodityShoppingCartVo cartVo : shoppingCartVos1) {
                	if(cartVo.getBuyMode() == 1){
                    	 continue;
                    }
                	shoppingCartVos2.add(cartVo);
                }
            }
        }else{
        	shoppingCartVos2 = shoppingCartVos1;
        }
        return shoppingCartVos2;
    }

    @Override
    public List<CommodityShoppingCartVo> getCheckedCommodityShoppingCartVos() {
        List<CommodityShoppingCartVo> cartVos = getCommodityShoppingCartVos(0);
        if (CollectionUtils.isEmpty(cartVos)) {
            return null;
        }
        List<CommodityShoppingCartVo> checkedCartVos = Lists.newArrayList();
        for (CommodityShoppingCartVo cartVo : cartVos) {
            if (cartVo.isChecked()) {
                checkedCartVos.add(cartVo);
            }
        }
        return checkedCartVos;
    }

    /**
     * 更新购物车数据
     *
     * @param cartVos
     */
    private void upDateShoppingCartData(Collection<CommodityShoppingCartVo> cartVos, SessionUserDetails user) {
        if (user == null) {
            //将购物车数据重新写入cookie
            List<CommodityCookieVo> commodityCookieVos = Lists.newArrayList();
            for (CommodityShoppingCartVo cartVo : cartVos) {
                CommodityCookieVo cookieVo = new CommodityCookieVo();
                if (StringUtils.isEmpty(cartVo.getId()))
                    cartVo.setId(UUIDUtil.getUUID());
                cookieVo.setId(cartVo.getId());
                cookieVo.setMerchantCode(cartVo.getMerchantCode());
                cookieVo.setCommodityNo(cartVo.getCommodityNo());
                cookieVo.setCount(cartVo.getCount());
                cookieVo.setChecked(cartVo.isChecked());
                cookieVo.setBuyMode(cartVo.getBuyMode());
                cookieVo.setSkuid(cartVo.getSkuId());
                cookieVo.setShopId(cartVo.getShopId());
                commodityCookieVos.add(cookieVo);
            }
            //序列化
            String jsonStr = JacksonUtils.Convert(commodityCookieVos);
            CookieUtils.setCookie(CookieConstant.WFX_CUSTOMER_SHOPPING_CART_COOKIE,
                    CookieSecurity.encrypt(jsonStr),
                    CookieConstant.WFX_CUSTOMER_SHOPPING_CART_COOKIE_MAX_AGE);
        } else {
            for (CommodityShoppingCartVo cartVo : cartVos) {
                if (cartVo.hasModify()) {
                    saveCommodityShoppingCartVo(cartVo);
                }
            }
        }
    }

    /**
     * 更新购物车数据
     *
     * @param cartVos
     */
    @Override
    public void upDateShoppingCartData(Collection<CommodityShoppingCartVo> cartVos) {
        if (CollectionUtils.isEmpty(cartVos)) {
            return;
        }
        SessionUserDetails user = SessionUtils.getLoginUserDetails();
        upDateShoppingCartData(cartVos, user);
    }

    /**
     * 获取cookie中购物车数据
     *
     * @return
     */
    private List<CommodityShoppingCartVo> getShoppingCartCommodityFromCookie() {
        List<CommodityCookieVo> commodityCookieVos = getCurrentContextCookieData();
        if (CollectionUtils.isEmpty(commodityCookieVos)) {
            return null;
        }
        Set<String> commodityNos = Sets.newHashSet();
        Set<String> productNos = Sets.newHashSet();
        for (CommodityCookieVo item : commodityCookieVos) {
            commodityNos.add(item.getCommodityNo());
            productNos.add(item.getSkuid());
        }
        List<String> nos = Lists.newArrayList(commodityNos);
        //批量获取商品信息
        Map<String, CommodityStyleVo> commodityStyleVos = commodityService.getCommodityByNos(nos, Boolean.FALSE, Boolean.TRUE);
        if (CollectionUtils.isEmpty(commodityStyleVos)) {
            return null;
        }
        //批量获取图片
        commodityNos.clear();
        for (CommodityStyleVo c : commodityStyleVos.values()) {
            commodityNos.add(c.getNo());
        }
        Map<String, List<PictureVo>> pictures =
                commodityService.batchGetCommodityPictures(Lists.newArrayList(commodityNos), WfxConstant.WFX_COMMODITY_PICTURE_TYPE_LIST);
        if (CollectionUtils.isEmpty(pictures)) {
            pictures = Maps.newHashMap();
        }
        //批量获取货品信息
        Map<String, ProductVo> products = commodityService.getProductByProductNos(Lists.newArrayList(productNos));
        if (CollectionUtils.isEmpty(products)) {
            return null;
        }
        //组装购物车数据
        List<CommodityShoppingCartVo> commodityShoppingCartVos = Lists.newArrayList();
        CommodityShoppingCartVo cartVo = null;
        //缓存shop对象
        Map<String, ShopVo> shops = Maps.newHashMap();

        for (CommodityCookieVo item : commodityCookieVos) {
            cartVo = new CommodityShoppingCartVo();

            //获取店铺信息
            if (!shops.containsKey(item.getShopId())) {
                shops.put(item.getShopId(), shopService.getShopModelById(item.getShopId()));
            }
            ShopVo shopVo = shops.get(item.getShopId());

            CommodityStyleVo commodity = commodityStyleVos.get(item.getCommodityNo());

            ProductVo product = products.get(item.getSkuid());
            if (shopVo == null || commodity == null || product == null) {
                continue;
            }
            cartVo.setId(item.getId());
            cartVo.setMerchantCode(item.getMerchantCode());
            cartVo.setBuyMode(item.getBuyMode());
            cartVo.setName(commodity.getCommodityName());
            cartVo.setCommodityNo(commodity.getNo());
            cartVo.setCommodityId(commodity.getId());
            cartVo.setCount(item.getCount());
            cartVo.setChecked(item.isChecked());
            cartVo.setShopId(shopVo.getId());
            cartVo.setShopCode(shopVo.getShopCode());
            cartVo.setShopName(shopVo.getName());
            cartVo.setStock(product.getInventoryNum());
            cartVo.setSize(product.getSizeName());
            cartVo.setSpecName(commodity.getSpecName());
            cartVo.setWfxPrice(commodity.getWfxPrice());
            cartVo.setSkuId(product.getProductNo());
            cartVo.setIsOnsale(commodity.getIsOnsale());
            if (pictures.containsKey(commodity.getNo())) {
                List<PictureVo> pictureVos = pictures.get(commodity.getNo());
                if (!CollectionUtils.isEmpty(pictureVos)) {
                    cartVo.setImageUrl(pictureVos.get(0).getUrl());
                }
            }
            commodityShoppingCartVos.add(cartVo);
        }
        return commodityShoppingCartVos;
    }

    /**
     * 获取购物车商品Sku条目数
     *
     * @return
     */
    @Override
    public ShopCartCountVo getShoopingCartSkusCount() {
        ShopCartCountVo shopCartCountVo = new ShopCartCountVo();
        int total = 0;
        SessionUserDetails sessionUserDetails = SessionUtils.getLoginUserDetails();
        if (sessionUserDetails == null) {
            List<CommodityCookieVo> cookieDatas = getCurrentContextCookieData();
            if (!CollectionUtils.isEmpty(cookieDatas)) {
                for (CommodityCookieVo cookieData : cookieDatas) {
                	if(cookieData.getBuyMode() == 1){
                		continue;
                	}
                    total += cookieData.getCount();
                }
                shopCartCountVo.setTotalSku(cookieDatas.size());
            }
        } else {
            List<ShoppingcartOutputDto> cartVos = shoppingcartFrontApi.queryShopingCartList(sessionUserDetails.getUserId());
            if (!CollectionUtils.isEmpty(cartVos)) {
                for (ShoppingcartOutputDto cartVo : cartVos) {
                	if(cartVo.getBuyMode() == 1){
                		continue;
                	}
                    total += cartVo.getCount();
                }
                shopCartCountVo.setTotalSku(cartVos.size());
            }
        }
        shopCartCountVo.setTotalCount(total);
        return shopCartCountVo;

    }

    /**
     * 清除指定购物车数据,用于下订单后操作
     *
     * @param cartVos
     */
    @Override
    public void clearShoppingcartByItems(List<CommodityShoppingCartVo> cartVos) {
        if (!CollectionUtils.isEmpty(cartVos)) {
            for (CommodityShoppingCartVo cartVo : cartVos) {
                shoppingcartFrontApi.removeShoppingCartById(cartVo.getId());
            }
        }
    }

    /**
     * 立即购买
     * 前置条件已经判断，例如购物车商品数量限制
     * 首先合并购物车，勾选当前选中的商品，不勾选其它商品
     *
     * @param shoppingCartChangeVo
     */
    @Override
    public void buyNow(ShoppingCartChangeVo shoppingCartChangeVo) {
        List<CommodityShoppingCartVo> cartVos = getCommodityShoppingCartVos(0);
        if (CollectionUtils.isEmpty(cartVos))
            cartVos = Lists.newArrayList();
        
        Map<String, Map<String, CommodityShoppingCartVo>> skuMap = Maps.newHashMap();
        for (CommodityShoppingCartVo cartVo : cartVos) {
            cartVo.setChecked(Boolean.FALSE);
            cartVo.setHasModify(Boolean.TRUE);
        }
        CommodityShoppingCartVo cartVo = new CommodityShoppingCartVo();
        cartVo.setCommodityNo(shoppingCartChangeVo.getCommodityNo());
        cartVo.setSkuId(shoppingCartChangeVo.getSkuId());
        cartVo.setCount(shoppingCartChangeVo.getSkuCount());
        cartVo.setShopId(shoppingCartChangeVo.getShopId());
        cartVo.setBuyMode(1);
        cartVo.setChecked(Boolean.TRUE);
        cartVo.setHasModify(Boolean.TRUE);
        cartVos.add(cartVo);

        upDateShoppingCartData(cartVos);
    }

    /**
     * 加车之前检查购物车
     *
     * @param shoppingCartChangeVo
     * @return
     */
    @Override
    public Result<Boolean> checkAdd(ShoppingCartChangeVo shoppingCartChangeVo) {
        Result<Boolean> result = Result.create();
        ShopCartCountVo countVo = getShoopingCartSkusCount();
        int total = countVo.getTotalCount();
        int maxSkuCount = systemService.getShoppingCartSkusCount();
        if (total == maxSkuCount) {
            return result.setHasError(Boolean.TRUE).setMessage("操作失败,您的购物车已满！");
        }
        int skuMaxCount = systemService.getShoppingCartMaxSkuCount();
        String msg = String.format("同个款色的每个尺码最多购买%s件!", skuMaxCount);
        if (shoppingCartChangeVo.getSkuCount() > skuMaxCount) {
            return result.setHasError(Boolean.TRUE).setMessage(msg);
        }
        return result;
    }

    /**
     * 获取数据库中购物车数据
     *
     * @return
     */
    private List<CommodityShoppingCartVo> getShoppingCartCommodityFromDb(String ueserId) {
        List<CommodityShoppingCartVo> commodityShoppingCartVos = Lists.newArrayList();
        List<ShoppingcartOutputDto> cartVos = shoppingcartFrontApi.queryShopingCartList(ueserId);
        Map<String, ShopVo> shops = Maps.newHashMap();
        Set<String> commodityNos = Sets.newHashSet();
        Set<String> productNos = Sets.newHashSet();
        if (!CollectionUtils.isEmpty(cartVos)) {
            for (ShoppingcartOutputDto dto : cartVos) {
                commodityNos.add(dto.getCommodityNo());
                productNos.add(dto.getProductNo());
            }
        }
        if (CollectionUtils.isEmpty(commodityNos))
            return null;
        //批量货获取商品货品信息
        Map<String, CommodityStyleVo> commoditys =
                commodityService.getCommodityByNos(Lists.newArrayList(commodityNos), Boolean.FALSE, Boolean.TRUE);
        //可以清理脏数据
        Map<String, ProductVo> products = commodityService.getProductByProductNos(Lists.newArrayList(productNos));

        if (CollectionUtils.isEmpty(commoditys) || CollectionUtils.isEmpty(products)) {
            return null;
        }
        Map<String, List<PictureVo>> pictures =
                commodityService.batchGetCommodityPictures(Lists.newArrayList(commodityNos), WfxConstant.WFX_COMMODITY_PICTURE_TYPE_LIST);
        if (CollectionUtils.isEmpty(pictures)) {
            pictures = Maps.newHashMap();
        }
        for (ShoppingcartOutputDto dto : cartVos) {
            CommodityShoppingCartVo cartVo = new CommodityShoppingCartVo();
            //获取店铺信息
            if (!shops.containsKey(dto.getShopId())) {
                shops.put(dto.getShopId(), shopService.getShopModelById(dto.getShopId()));
            }
            ShopVo shopVo = shops.get(dto.getShopId());
            CommodityStyleVo commodity = commoditys.get(dto.getCommodityNo());
            ProductVo product = products.get(dto.getProductNo());
            if (shopVo == null || commodity == null || product == null) {
                continue;
            }
            cartVo.setId(dto.getId());
            cartVo.setMerchantCode(dto.getMerchantCode());
            cartVo.setBuyMode(dto.getBuyMode());
            cartVo.setName(commodity.getCommodityName());
            cartVo.setCommodityNo(commodity.getNo());
            cartVo.setCommodityId(commodity.getId());
            cartVo.setCount(dto.getCount());
            cartVo.setChecked(dto.getIsChecked() == 1);
            cartVo.setShopId(shopVo.getId());
            cartVo.setShopCode(shopVo.getShopCode());
            cartVo.setShopName(shopVo.getName());
            cartVo.setStock(product.getInventoryNum());
            cartVo.setSize(product.getSizeName());
            cartVo.setSpecName(commodity.getSpecName());
            cartVo.setWfxPrice(commodity.getWfxPrice());
            cartVo.setSkuId(product.getProductNo());
            cartVo.setIsOnsale(commodity.getIsOnsale());
            if (pictures.containsKey(commodity.getNo())) {
                List<PictureVo> pictureVos = pictures.get(commodity.getNo());
                if (!CollectionUtils.isEmpty(pictureVos)) {
                    cartVo.setImageUrl(pictureVos.get(0).getUrl());
                }
            }
            commodityShoppingCartVos.add(cartVo);
        }
        return commodityShoppingCartVos;
    }

    private List<CommodityCookieVo> getCurrentContextCookieData() {
        String cookieStr = CookieUtils.getCookie(CookieConstant.WFX_CUSTOMER_SHOPPING_CART_COOKIE);
        //CookieUtils.removeCookie(CookieConstant.WFX_CUSTOMER_SHOPPING_CART_COOKIE);
        if (StringUtils.isEmpty(cookieStr)) {
            return null;
        }
        //解密
        String jsonStr = CookieSecurity.decrypt(cookieStr);
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        //反序列化
        List<CommodityCookieVo> commodityCookieVos =
                JacksonUtils.Convert(jsonStr, JacksonUtils.createJavaType(ArrayList.class, CommodityCookieVo.class));
        if (CollectionUtils.isEmpty(commodityCookieVos)) {
            return null;
        }
        return commodityCookieVos;
    }

    private Map<String, Map<String, CommodityShoppingCartVo>> convertCommodityShoppingCart(List<CommodityShoppingCartVo> commodityShoppingCartVos) {
        if (CollectionUtils.isEmpty(commodityShoppingCartVos)) {
            return null;
        }
        Map<String, Map<String, CommodityShoppingCartVo>> cartVoMap = Maps.newHashMap();
        for (CommodityShoppingCartVo cartVo : commodityShoppingCartVos) {
            if (cartVoMap.containsKey(cartVo.getSkuId())) {
                cartVoMap.get(cartVo.getSkuId()).put(cartVo.getShopId(), cartVo);
            } else {
                Map<String, CommodityShoppingCartVo> skus = Maps.newHashMap();
                skus.put(cartVo.getShopId(), cartVo);
                cartVoMap.put(cartVo.getSkuId(), skus);
            }

        }
        return cartVoMap;
    }

    /**
     * 处理已下架或者已售罄的商品，
     *
     * @param cartVos
     */
    private void dealWithShoppingCartCommoditys(List<CommodityShoppingCartVo> cartVos) {
        if (CollectionUtils.isEmpty(cartVos)) return;
        for (CommodityShoppingCartVo cartVo : cartVos) {
        	//将下架、售罄的商品设为未勾选。
            if (cartVo.getIsOnsale() != 1 || cartVo.getStock() <= 0) {
                cartVo.setChecked(Boolean.FALSE);
                cartVo.setHasModify(Boolean.TRUE);
            }
        }
        upDateShoppingCartData(cartVos);
    }

    /**
     * 更新操作
     *
     * @param cartVo
     * @return
     */
    public void saveCommodityShoppingCartVo(CommodityShoppingCartVo cartVo) {
        SessionUserDetails user = SessionUtils.getLoginUserDetails();
        ShoppingcartInputDto dto = new ShoppingcartInputDto();
        dto.setCommodityNo(cartVo.getCommodityNo());
        dto.setMerchantCode(cartVo.getMerchantCode());
        dto.setBuyMode(cartVo.getBuyMode());
        dto.setCount(cartVo.getCount());
        dto.setCreateTime(DateUtils.now());
        dto.setId(cartVo.getId());
        dto.setIsChecked(cartVo.isChecked() ? 1 : 0);
        dto.setLoginId(user.getUserId());
        dto.setLoginName(user.getUserName());
        dto.setProductNo(cartVo.getSkuId());
        dto.setShopId(cartVo.getShopId());
        dto.setUpdateTime(DateUtils.now());
        if (StringUtils.isEmpty(dto.getId())) {
            dto.setId(UUIDUtil.getUUID());
            shoppingcartFrontApi.insertShoppingCart(dto);
        } else {
            shoppingcartFrontApi.updateShoppingCart(dto);
        }
    }

    /**
     * 删除数据库中的购物车数据
     *
     * @param userId 用户id
     * @param id     id
     * @return
     */
    public void deleteCommodityShoppingCartVo(String userId, String id) {
        shoppingcartFrontApi.removeShoppingCartById(id);
    }
}
