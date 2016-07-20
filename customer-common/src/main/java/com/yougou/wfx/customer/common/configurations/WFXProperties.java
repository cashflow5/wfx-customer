package com.yougou.wfx.customer.common.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 项目配置文件
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/25 下午4:31
 * @since 1.0 Created by lipangeng on 16/3/25 下午4:31. Email:lipg@outlook.com.
 */
@ConfigurationProperties("com.yougou.config")
public class WFXProperties {
    /** 默认订单列表显示的商品行数 */
    private int myOrderListShowCommoditySize = 3;
    private String iosUrl = "";
    private String androidUrl = "";
    private int waitPayOrderLeftTimeHour = 1;
    private String indexShopId = "yougoushop";
    private String ufan_article_url = "http://m.yougou.net/discover/view/8bfa535b2a1a4679a7d5da5002142523.sc";

    public int getMyOrderListShowCommoditySize() {
        return myOrderListShowCommoditySize;
    }

    public void setMyOrderListShowCommoditySize(int myOrderListShowCommoditySize) {
        this.myOrderListShowCommoditySize = myOrderListShowCommoditySize;
    }

    public String getIosUrl() {
        return iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public int getWaitPayOrderLeftTimeHour() {
        return waitPayOrderLeftTimeHour;
    }

    public void setWaitPayOrderLeftTimeHour(int waitPayOrderLeftTimeHour) {
        this.waitPayOrderLeftTimeHour = waitPayOrderLeftTimeHour;
    }

    public String getIndexShopId() {
        return indexShopId;
    }

    public void setIndexShopId(String indexShopId) {
        this.indexShopId = indexShopId;
    }

	public String getUfan_article_url() {
		return ufan_article_url;
	}

	public void setUfan_article_url(String ufan_article_url) {
		this.ufan_article_url = ufan_article_url;
	}
}
