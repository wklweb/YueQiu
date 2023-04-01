package com.yueqiu.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Threads {
    private static final Logger logger = LoggerFactory.getLogger(Threads.class);
    /**
     * 打印线程异常信息
     * @param r
     * @param t
     */
    public static void printException(Runnable r, Throwable t) {
        if(t==null&&r instanceof Future<?>){
            try {
                Future<?> future = (Future<?>) r;
                if(future.isDone()){
                    future.get();
                }
            } catch (ExecutionException e) {
                t = e;
            } catch (InterruptedException e) {
                t = e;
            }

        }
        if (t != null)
        {
            logger.error(t.getMessage(), t);
        }
    }
}
