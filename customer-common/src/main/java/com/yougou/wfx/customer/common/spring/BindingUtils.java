package com.yougou.wfx.customer.common.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * spring的数据绑定工具
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/25 上午1:17
 * @since 1.0 Created by lipangeng on 16/3/25 上午1:17. Email:lipg@outlook.com.
 */
public class BindingUtils {
    private static final Logger logger = LoggerFactory.getLogger(BindingUtils.class);

    private BindingUtils() {
    }

    /**
     * 获取全部的错误提示信息
     *
     * @since 1.0 Created by lipangeng on 16/3/25 上午1:18. Email:lipg@outlook.com.
     */
    public static String errors(BindingResult result) {
        StringBuilder sb = new StringBuilder();
        List<ObjectError> allErrors = result.getAllErrors();
        if (allErrors != null) {
            for (ObjectError error : allErrors) {
                sb.append(error.getDefaultMessage());
                sb.append(";");
            }
        }
        return sb.toString();
    }
}
