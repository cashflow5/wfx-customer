package com.yougou.wfx.customer.common.thread.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zhang.sj, Email:zhang.sj@yougou.com
 * @version 1.0 on 2016/4/19
 */
public class ThreadPoolExceptionHandler implements RejectedExecutionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        logger.error(String.format("线程池出现异常！线程池大小:%d，%s", executor.getQueue().size(), r.toString()));
//        r.run();
    }
}
