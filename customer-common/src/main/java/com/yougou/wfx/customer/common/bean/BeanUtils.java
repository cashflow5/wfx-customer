package com.yougou.wfx.customer.common.bean;

import com.google.common.base.Strings;
import com.yougou.wfx.customer.common.utils.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用bean工具类
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/4/25 上午11:47
 * @since 1.0 Created by lipangeng on 16/4/25 上午11:47. Email:lipg@outlook.com.
 */
public class BeanUtils {
    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    /**
     * 拷贝对象属性
     *
     * @since 1.0 Created by lipangeng on 16/4/25 上午11:49. Email:lipg@outlook.com.
     */
    public static void copyProperties(Object form, Object to) {
        if (form == null || to == null) {
            logger.error("无法拷贝空对象属相,form" + JacksonUtils.Convert(form) + ",to:" + JacksonUtils.Convert(to));
            return;
        }
        try {
            org.springframework.beans.BeanUtils.copyProperties(form, to);
        } catch (Exception e) {
            logger.error("拷贝对象属性发生错误", e);
        }
    }

    /**
     * 手机号短名称
     *
     * @since 1.0 Created by lipangeng on 16/4/25 下午6:24. Email:lipg@outlook.com.
     */
    public static String shortPhoneNum(String phoneNum) {
        if (Strings.isNullOrEmpty(phoneNum) || phoneNum.length() < 7) {
            return phoneNum;
        }
        return phoneNum.substring(0, 3) + "****" + phoneNum.substring(phoneNum.length() - 4);
    }
}
