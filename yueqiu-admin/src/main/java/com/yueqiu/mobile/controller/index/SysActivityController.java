package com.yueqiu.mobile.controller.index;

import com.yueqiu.common.annotation.Resubmit;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysActivity;
import com.yueqiu.common.domain.entity.SysForm;
import com.yueqiu.common.utils.SecurityUtils;
import com.yueqiu.common.utils.SpringUtils;
import com.yueqiu.framework.web.TaskService;
import com.yueqiu.framework.web.service.WebSocketServer;
import com.yueqiu.system.service.SysActivityService;
import com.yueqiu.system.service.SysFormService;
import com.yueqiu.web.controller.base.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@RequestMapping("/index/activity")
public class SysActivityController extends BaseController {

    public static final Logger log = LoggerFactory.getLogger(SysActivityController.class);
    @Autowired
    private SysActivityService sysActivityService;
    @Autowired
    private SysFormService sysFormService;
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private TaskService taskService;

    /**
     * 发布约球活动
     *
     * @param
     * @return
     */
    @PostMapping("/send")
    public AjaxResult sendActivity(@RequestBody SysActivity sysActivity) {
        if (!sysActivityService.checkActivityUnique(sysActivity.getActivityId())) {
            return AjaxResult.error("已经存在该活动了");
        }
        return toAjax(sysActivityService.sendNewActivity(sysActivity));
    }

    /**
     * 加入约球活动
     */
    @PostMapping("/join/{username}/{activityId}")
    @Resubmit
    public AjaxResult joinActivity(@PathVariable String username, @PathVariable Long activityId) throws ExecutionException, InterruptedException {
        Future<AjaxResult> ajaxResult = (Future<AjaxResult>) taskService.joinActivity(username,activityId);
        return ajaxResult.get();

    }
    /**
     * 处理约球活动
     */


}
