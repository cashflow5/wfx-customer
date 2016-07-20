package com.yougou.wfx.customer.common.utils;

import com.yougou.wfx.customer.common.constant.WfxConstant;

import java.util.Random;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/18
 */
public class RandomUtils {

    private static String[] numbers = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private static Random random = new Random();

    /**
     * 获取随机字符串用于微信配置
     *
     * @return
     */
    public static String getSignature() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < WfxConstant.WFX_WX_CONFIG_SIGNATURE; i++) {
            sb.append(numbers[random.nextInt(numbers.length-1)]);
        }
        return sb.toString();
    }

}
