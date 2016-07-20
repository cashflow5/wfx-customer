package com.yougou.wfx.customer.common.thread;

import com.yougou.wfx.customer.common.thread.handler.ThreadPoolExceptionHandler;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/19
 */
public class ThreadPool {
    //核心线程数量，即初始化线程池的启动线程数量
    private static final int corePoolSize = 10;
    //最大线程数量
    private static final int maximumPoolSize = 50;
    //线程的存活时间，即完成任务后多久可再使用
    private static final int keepAliveTime = 300;
    //等待队列的长度
    private static final int workQueueSize = 30000;

    public static ThreadPoolExecutor getInstance() {
        return ThreadPoolHolder.INSTANCE;
    }

    /**
     * 修改为线程安全的单例模式 2016-01-28 17:03
     */
    private static class ThreadPoolHolder {
        public static final ThreadPoolExecutor INSTANCE;

        static {
            //ThreadPoolExceptionHandler表示当线程池处理不了规定任务时的异常处理方式。
            INSTANCE = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(workQueueSize), new ThreadPoolExceptionHandler());
        }
    }
}
