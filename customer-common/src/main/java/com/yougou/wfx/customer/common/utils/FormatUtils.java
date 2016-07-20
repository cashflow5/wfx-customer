package com.yougou.wfx.customer.common.utils;

import java.text.DecimalFormat;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/5/10
 */
public class FormatUtils {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.00");
    public static String format(double number) {
        return String .format("%.2f",number);
    }

    public static double formatDouble(double number) {
        String value = format(number);
        return Double.parseDouble(value);
    }
}
