package com.yougou.wfx.customer.annotations;

import java.lang.annotation.*;

/**
 * Created by zhang.sj on 2016/3/22.
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginValidate {
    boolean isValidate() default true;
}
