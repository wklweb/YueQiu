package com.yueqiu.framework.web;

import com.alibaba.fastjson.JSON;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysActivity;
import com.yueqiu.common.domain.entity.SysChatRecords;
import com.yueqiu.common.domain.entity.SysForm;
import com.yueqiu.common.utils.SpringUtils;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.common.utils.date.DateUtils;
import com.yueqiu.framework.web.service.WebSocketServer;
import com.yueqiu.system.service.SysActivityService;
import com.yueqiu.system.service.SysChatRecordsService;
import com.yueqiu.system.service.SysFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * 异步任务类
 */
@Component
public class TaskService {
    public static final Logger log = LoggerFactory.getLogger(TaskService.class);
    @Autowired
    private SysActivityService sysActivityService;
    @Autowired
    private SysFormService sysFormService;
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private SysChatRecordsService sysChatRecordsService;


    /**
     * 异步提交约球活动申请
     *
     * @param username
     * @param activityId
     * @return
     */
    @Async("chatExecutor")
    public Future<AjaxResult> joinActivity(String username, Long activityId) {
        log.info("线程-" + Thread.currentThread().getName() + "在执行初始化");
        try {
            Thread.sleep(3000);
            if (activityId != null) {
                SysActivity sysActivity = sysActivityService.getActivityById(activityId);
                if (sysActivity != null) {
                    String target = sysActivity.getCreateBy();
                    if (!target.equals(username) && !sysFormService.checkResubmit(username, target)) {
                        SysForm sysForm = new SysForm();
                        sysForm.setCreateBy(username);
                        sysForm.setApplicant(username);
                        sysForm.setReviewer(target);
                        webSocketServer.sendInfo(target, "你发出的活动有人申请啦,快去看看!");
                        return new AsyncResult<>(toAjax(sysFormService.addShopApplyForm(sysForm, "")));
                    }
                }
            }
            return new AsyncResult<>(AjaxResult.error("申请错误"));
        } catch (Exception e) {
            return new AsyncResult<>(AjaxResult.error("申请错误"));
        }
    }

    /**
     * 加入约球讨论聊天得到聊天记录
     * @param activityId
     */
    @Async("chatExecutor")
    public Future<List<SysChatRecords>> joinChat(Long activityId) {
        try {
            Thread.sleep(3000);
            List<SysChatRecords> records = sysChatRecordsService.getAllRecords(activityId);
            return new AsyncResult<>(records);

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 群发消息
     * @param message
     * @return
     */

    @Async("chatExecutor")
    public void broadcast(String message) {
       try {
           Thread.sleep(3000);
           log.info("线程-" + Thread.currentThread().getName() + "在执行写入");
           for (Session session : webSocketServer.getSessionPool().values()) {
               if (StringUtils.isNotNull(session) && session.isOpen()) {
                   try {
                       webSocketServer.sendMessage(session, message);
                   } catch (Exception e) {
                       e.printStackTrace();
                       continue;
                   }
               }

           }
           //记录聊天记录
           SysChatRecords sysChatRecords = JSON.parseObject(message, SysChatRecords.class);
           if(sysChatRecords!=null&&StringUtils.isNotEmpty(sysChatRecords.getTarget())&&StringUtils.isNotEmpty(
                   sysChatRecords.getSender()
           )){
               sysChatRecords.setSendingTime(DateUtils.getNowTime());
               sysChatRecordsService.insertChatRecords(sysChatRecords);
               log.info("聊天记录新增成功");
           }
           else {
               log.error("发布数据不规范");
           }
       }
       catch (Exception e){
           log.error(e.getMessage());
       }
    }


    public AjaxResult toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

}
