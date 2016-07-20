package com.yougou.wfx.customer.common.datetime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/25 下午5:25
 * @since 1.0 Created by lipangeng on 16/3/25 下午5:25. Email:lipg@outlook.com.
 */
public class DateUtils {
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 获取中文当天日期
     *
     * @since 1.0 Created by lipangeng on 16/3/25 下午5:26. Email:lipg@outlook.com.
     */
    public static String getTodayZh() {
        SimpleDateFormat sdf_zh_cn = new SimpleDateFormat("yyyy年MM月dd日");
        return sdf_zh_cn.format(new Date());
    }

    /**
     * 格式化显示日志
     *
     * @since 1.0 Created by lipangeng on 16/5/10 上午11:48. Email:lipg@outlook.com.
     */
    public static Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            logger.error("转换日期发生异常", e);
            return null;
        }
    }

    public static Date now() {
        return new Date();
    }
}
