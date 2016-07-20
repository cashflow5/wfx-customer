package com.yougou.wfx.customer.common.math;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数发生器
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/25 下午4:28
 * @since 1.0 Created by lipangeng on 16/3/25 下午4:28. Email:lipg@outlook.com.
 */
public class MathRandomUtils {
    private static final Logger logger = LoggerFactory.getLogger(MathRandomUtils.class);

    /**
     * 获取指定长度的随机数字符串
     *
     * @since 1.0 Created by lipangeng on 16/3/25 下午4:29. Email:lipg@outlook.com.
     */
    public static String getFixLengthString(int length) {
        // 获得随机数
        double pross = (1 + ThreadLocalRandom.current().nextDouble()) * Math.pow(10, length);
        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);
        // 返回固定的长度的随机数
        return fixLenthString.substring(1, length + 1);
    }
}
