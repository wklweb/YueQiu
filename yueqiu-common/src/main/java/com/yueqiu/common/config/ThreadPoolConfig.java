package com.yueqiu.common.config;

import com.yueqiu.common.utils.Threads;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ThreadPoolConfig {
    /**
     * 周期性线程池
     */
    @Bean("scheduledExecutorService")
    public ScheduledExecutorService scheduledExecutorService(){
        return new ScheduledThreadPoolExecutor(50,new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build()
        , new ThreadPoolExecutor.CallerRunsPolicy())
        {
            //捕捉异常+返回执行任务结果
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                Threads.printException(r,t);
            }
        };
    }
}
