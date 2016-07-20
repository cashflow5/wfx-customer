package com.yougou.wfx.customer.common.constant;

/**
 * redis中的一些常量
 *
 * @author lipangeng, Email:lipg@outlook.com
 * @version 1.0 on 16/3/25 下午2:52
 * @since 1.0 Created by lipangeng on 16/3/25 下午2:52. Email:lipg@outlook.com.
 */
public class RedisConstant {
    /**
     * 在redis中存储短信验证码的值
     */
    public static final String WFX_SMS_CODE_PREFIX = "wfx:sms:code:phone:";

    /**
     * 微信token key
     */
    public static final String WFX_REDIS_WX_TOKEN_KEY = "wfx:wx:token";

    /**
     * 微信ticket key
     */
    public static final String WFX_REDIS_WX_TICKET_KEY = "wfx:wx:ticket";
    /**
     * 微信刷新时间
     */
    public static final long WFX_REDIS_WX_REFRESH_EXPIRESIN = 60 * 115;

    /**
     * wx分布式锁key
     */
    public static final String WFX_REDIS_TICKET_LOCK_KEY = "wfx:wx:ticket.lock";

    /**
     * wx分布式锁key
     */
    public static final String WFX_REDIS_ACCESS_TOKEN_LOCK_KEY = "wfx:wx:access.token.lock";


}
