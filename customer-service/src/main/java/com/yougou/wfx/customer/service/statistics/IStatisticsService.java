package com.yougou.wfx.customer.service.statistics;

import com.yougou.wfx.customer.model.common.Result;

/**
 * 统计用的service
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/11 下午2:22
 * @since 1.0 Created by lipangeng on 16/5/11 下午2:22. Email:lipg@outlook.com.
 */
public interface IStatisticsService {

    /**
     * 增加店铺日访问量
     *
     * @since 1.0 Created by lipangeng on 16/5/11 下午2:23. Email:lipg@outlook.com.
     */
    Result<String> addDayShopVisitCount(String shopId);
}
