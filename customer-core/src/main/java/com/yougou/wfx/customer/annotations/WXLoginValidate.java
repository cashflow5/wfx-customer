package com.yougou.wfx.customer.annotations;

import java.lang.annotation.*;

/**
 * Created by zheng.qq.
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WXLoginValidate {
    boolean isValidate() default true;
}
