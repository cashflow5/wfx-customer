package com.yougou.wfx.customer.service.redis;

/**
 * 分布式锁实现 加入自定义过期时间，用于耗时操作 defaultExpired:3000ms maxExpired:10*3000ms
 */
public interface IRedisLockService {
    /**
     * 获取redis锁
     *
     * @param lock
     *         redis锁的key
     *
     * @since 1.0 Created by lipangeng on 16/4/18 上午10:23. Email:lipg@outlook.com.
     */
    boolean acquireLock(String lock);

    /**
     * 获取redis锁
     *
     * @param lock
     *         redis锁的key
     * @param expired
     *         超时时间
     *
     * @since 1.0 Created by lipangeng on 16/4/18 上午10:23. Email:lipg@outlook.com.
     */
    boolean acquireLock(String lock, long expired);

    /**
     * 释放redis锁
     *
     * @param lock
     *         redis锁的key
     *
     * @since 1.0 Created by lipangeng on 16/4/18 上午10:24. Email:lipg@outlook.com.
     */
    void releaseLock(String lock);
}
