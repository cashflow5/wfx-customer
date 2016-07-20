package com.yougou.wfx.customer.base;

import com.yougou.wfx.customer.common.session.SessionUtils;
import com.yougou.wfx.customer.model.common.Result;
import com.yougou.wfx.customer.service.statistics.IStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统计相关接口
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/5/11 下午2:20
 * @since 1.0 Created by lipangeng on 16/5/11 下午2:20. Email:lipg@outlook.com.
 */
@Controller
@RequestMapping("statistics")
public class StatisticsController {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);
    @Autowired
    private IStatisticsService statisticsService;

    /**
     * 店铺的每日访问量增加1
     *
     * @since 1.0 Created by lipangeng on 16/5/11 下午2:34. Email:lipg@outlook.com.
     */
    @RequestMapping(value = "addDayShopVisitCount", method = RequestMethod.POST)
    @ResponseBody
    public Result<String> addDayShopVisitCount(@RequestParam(name = "shopId", required = false) String shopId) {
        return statisticsService.addDayShopVisitCount(shopId == null ? SessionUtils.getCurrentShopId() : shopId);
    }
}
