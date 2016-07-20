package com.yougou.wfx.customer.service.statistics.impl;

import com.google.common.base.Strings;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.service.statistics.IStatisticsService;
import com.yougou.wfx.shop.api.front.IShopFrontApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 统计相关方法
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/11 下午2:24
 * @since 1.0 Created by lipangeng on 16/5/11 下午2:24. Email:lipg@outlook.com.
 */
@Service
public class StatisticsService implements IStatisticsService {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsService.class);
    @Autowired
    private IShopFrontApi shopFrontApi;

    /**
     * 增加店铺日访问量
     *
     * @param shopId
     *
     * @since 1.0 Created by lipangeng on 16/5/11 下午2:23. Email:lipg@outlook.com.
     */
    @Override
    public Result<String> addDayShopVisitCount(String shopId) {
        if (Strings.isNullOrEmpty(shopId)) {
            return Result.create(false, "店铺id不能为空");
        }
        try {
            int i = shopFrontApi.addShopVisitCount(shopId);
            if (i <= 0) {
                return Result.create(false, "访问量小于等于0,为:" + i);
            }
        } catch (Exception e) {
            logger.error("增加店铺" + shopId + "日访问量失败.", e);
        }
        return Result.create(true, "保存成功", shopId);
    }
}
