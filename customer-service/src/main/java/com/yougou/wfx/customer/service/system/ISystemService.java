package com.yougou.wfx.customer.service.system;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/5/5
 */
public interface ISystemService {

    /**
     * 获取购物车最大sku数量
     *
     * @return
     */
    int getShoppingCartSkusCount();

    /**
     * 购物车每个sku最多购买数量
     *
     * @return
     */
    int getShoppingCartMaxSkuCount();

    /**
     * 获取用户一天可下单的订单总数量
     *
     * @since 1.0 Created by lipangeng on 16/5/6 下午2:37. Email:lipg@outlook.com.
     */
    int getUserOneDayOrderNum();

    String getConfigByKey(String key);

    /**
     * 获取图片域名
     *
     * @return
     */
    String obtainImgBaseUrl();

    /**
     * 获取全国地址的json
     *
     * @since 1.0 Created by lipangeng on 16/5/18 下午4:30. Email:lipg@outlook.com.
     */
    String areaJson();

}
