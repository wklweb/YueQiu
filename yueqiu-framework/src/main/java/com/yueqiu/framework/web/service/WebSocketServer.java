package com.yueqiu.framework.web.service;

import com.alibaba.fastjson.JSON;
import com.yueqiu.common.constant.CacheConstants;
import com.yueqiu.common.constant.Constants;
import com.yueqiu.common.constant.HttpStatus;
import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.core.redis.RedisCache;
import com.yueqiu.common.domain.Message;
import com.yueqiu.common.domain.entity.SysChatRecords;
import com.yueqiu.common.utils.HttpUtils;
import com.yueqiu.common.utils.ServletUtils;
import com.yueqiu.common.utils.SpringUtils;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.framework.web.TaskService;
import com.yueqiu.system.service.SysChatRecordsService;
import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * web套接字服务
 */
@Component
@ServerEndpoint("/websocket/{key}/{activityId}")
public class WebSocketServer {

    public static final Logger log = LoggerFactory.getLogger("sys-user");
    /**
     * 客户端与服务器的传输介质
     */
    private Session session;
    private static CopyOnWriteArraySet<WebSocketServer> copyOnWriteArraySet = new CopyOnWriteArraySet<>();
    private static ConcurrentHashMap<String, Session> sessionPool = new ConcurrentHashMap<>();
    private static AtomicInteger connectNumber = new AtomicInteger(0);
    private TokenService tokenService;
    private SysChatRecordsService sysChatRecordsService = SpringUtils.getBean("chatRecordService");

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "key") String key,@PathParam(value = "activityId") Long activityId) throws IOException {
        log.info("线程-" + Thread.currentThread().getName() + "在执行初始化");
        //将每一个客户端与服务器的会话放入同一个会话池
        this.session = session;
        tokenService = SpringUtils.getBean("tokenService");
        String username = tokenService.getUserName(key);
        sessionPool.put(String.valueOf(username), session);
        if (username != null) {
            sessionPool.put(username, session);
            copyOnWriteArraySet.add(this);
        }
        //连接数+1
        connectNumber.incrementAndGet();
        log.info("[" + username + "]:webSocket连接成功");
        log.info("websocket连接数=" + connectNumber);
    }

    @OnClose
    public void onClose() {
        log.info("线程-" + Thread.currentThread().getName() + "在执行退出");
        copyOnWriteArraySet.remove(this);
        log.info("退出webSocket成功");
    }


    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info("线程-" + Thread.currentThread().getName() + "在执行写入");
        SysChatRecords sysChatRecords = JSON.parseObject(message, SysChatRecords.class);
        sysChatRecords.setSendingTime(new Date());
        if (sysChatRecords.getTarget().equals("-1")) {
            TaskService taskService = SpringUtils.getBean("taskService");
            taskService.broadcast(message);
        }

    }

    /**
     * 单发
      * @param username
     * @param message
     */
    public void sendInfo(String username, String message) {
        Session session = sessionPool.get(username);
        if (StringUtils.isNotNull(session) && session.isOpen()) {
            try {
                sendMessage(session, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    /**
//     * 群发
//     * @param message
//     * @param roomId
//     */
//    public void broadcast(String message) {
//        log.info("线程-" + Thread.currentThread().getName() + "在执行写入");
//        for (Session session : sessionPool.values()) {
//            if (StringUtils.isNotNull(session) && session.isOpen()) {
//                try {
//                    sendMessage(session, message);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    continue;
//                }
//            }
//
//        }
//        //记录聊天记录
//        SysChatRecords sysChatRecords = JSON.parseObject(message, SysChatRecords.class);
//        if(sysChatRecords!=null&&StringUtils.isNotEmpty(sysChatRecords.getTarget())&&StringUtils.isNotEmpty(
//                sysChatRecords.getSender()
//        )){
//            sysChatRecordsService.insertChatRecords(sysChatRecords);
//            log.info("聊天记录新增成功");
//        }
//        else {
//            log.error("发布数据不规范");
//        }
//    }

    public void sendMessage(Session session, String message) throws IOException {
        if (session != null) {
            // synchronized 给 session 加锁 保证同一时刻只能有一个线程执行当前 session 发送消息
            synchronized (session) {
                System.out.println("发送数据：" + message);
                session.getBasicRemote().sendText(message);
            }
        }
    }















    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public static CopyOnWriteArraySet<WebSocketServer> getCopyOnWriteArraySet() {
        return copyOnWriteArraySet;
    }

    public static void setCopyOnWriteArraySet(CopyOnWriteArraySet<WebSocketServer> copyOnWriteArraySet) {
        WebSocketServer.copyOnWriteArraySet = copyOnWriteArraySet;
    }

    public static ConcurrentHashMap<String, Session> getSessionPool() {
        return sessionPool;
    }

    public static void setSessionPool(ConcurrentHashMap<String, Session> sessionPool) {
        WebSocketServer.sessionPool = sessionPool;
    }

    public static AtomicInteger getConnectNumber() {
        return connectNumber;
    }

    public static void setConnectNumber(AtomicInteger connectNumber) {
        WebSocketServer.connectNumber = connectNumber;
    }

}
