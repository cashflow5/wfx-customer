package com.yougou.wfx.customer.service.redis.impl;

import com.yougou.wfx.customer.service.redis.IRedisLockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


/**
 * 分布式锁实现 加入自定义过期时间，用于耗时操作 defaultExpired:3000ms maxExpired:10*3000ms
 */
@Service
public class RedisLockService implements IRedisLockService {
    /**
     * 默认最小过期时间
     */
    private static final long defaultExpired = 3000;
    /**
     * 最大过期时间
     */
    private static final long maxExpired = 10 * defaultExpired;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean acquireLock(String lock) {
        return acquireLock(lock, defaultExpired);
    }

    /**
     * 加入自定义过期时间
     *
     * @param lock
     *         指定需要锁定的key（此key具有特殊含义，需结合具体业务逻辑）
     * @param expired
     *         自定义过期时间 3000<expired<10*3000
     *
     * @return
     */
    @Override
    public boolean acquireLock(String lock, long expired) {
        if (expired <= 0) {
            expired = defaultExpired;
        }
        if (expired > maxExpired) {
            expired = maxExpired;
        }

        boolean res = false;
        long timestamp = System.currentTimeMillis() + expired + 1;
        boolean acquired = redisTemplate.opsForValue().setIfAbsent(lock, String.valueOf(timestamp));
        if (acquired) {
            res = true;
            logger.info("lock-->{},timestamp-->{}", lock, timestamp);
        } else {
            //SETNX失败，说明锁仍然被其他对象保持，检查其是否已经超时
            long oldValue = Long.valueOf(redisTemplate.opsForValue().get(lock));
            if (oldValue < System.currentTimeMillis()) {
                long getValue = Long.valueOf(redisTemplate.opsForValue().getAndSet(lock, String.valueOf(timestamp)));
                if (getValue == oldValue) {
                    res = true;
                    logger.info("超时获取锁成功，lock-->{},timestamp-->{}", lock, timestamp);
                } else {
                    res = false;
                    logger.info("超时获取锁失败，lock-->{},timestamp-->{}", lock, timestamp);
                }
            } else {
                res = false;
                logger.info("未超时，失败");
            }
        }
        return res;

    }

    @Override
    public void releaseLock(String lock) {
        long current = System.currentTimeMillis();
        if (current < Long.valueOf(redisTemplate.opsForValue().get(lock))) {
            redisTemplate.delete(lock);
            logger.info("release lock-->{}", lock);
        }
    }
}
