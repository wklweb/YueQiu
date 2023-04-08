package com.yueqiu.common.config;

import com.yueqiu.common.utils.Threads;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SimpleThreadPoolTaskExecutor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
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
    @Bean("chatExecutor")
    public ThreadPoolTaskExecutor executionServiceExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(15);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(1000);
        //线程的名称前缀
        executor.setThreadNamePrefix("joinActivity-pool");
        //线程活跃时间（秒）
//        executor.setKeepAliveSeconds(60);
        //等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

}
