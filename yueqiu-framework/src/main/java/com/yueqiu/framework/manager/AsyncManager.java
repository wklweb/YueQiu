package com.yueqiu.framework.manager;

import com.yueqiu.common.utils.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AsyncManager {
    private static final int OPERATE_DELAY_TIME = 10;
    public static AsyncManager me = new AsyncManager();

    private ScheduledExecutorService scheduledExecutorService = SpringUtils.getBean("scheduledExecutorService");


    public static AsyncManager me() {
        return me;
    }

    public void execute(TimerTask task) {
        scheduledExecutorService.schedule(task,OPERATE_DELAY_TIME,TimeUnit.MILLISECONDS);
    }
}
