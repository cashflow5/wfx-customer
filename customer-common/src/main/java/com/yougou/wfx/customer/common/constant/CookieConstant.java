package com.yougou.wfx.customer.common.constant;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/3/31
 */
public class CookieConstant {
    /**
     * 购物车存储数据 cookie
     */
    public static final String WFX_CUSTOMER_SHOPPING_CART_COOKIE = "WFX_CUSTOMER_SHOPPING_CART_COOKIE";

    /**
     * cookie的AES加密的KEY
     */
    public static final byte[] COOKIE_SHOOPING_CART_AES_KEY =
            new byte[]{- 62, 40, 95, 38, - 37, 66, - 61, - 69, 12, 47, 12, - 36, 44, 22, - 35, - 13};

    /**
     * 购物车cookie过期时间
     */
    public static final int WFX_CUSTOMER_SHOPPING_CART_COOKIE_MAX_AGE = 3600 * 24 * 365;

    /** 用来记录用户是不是第一次访问的 */
    public static final String WFX_UV = "recordvistor";
}
