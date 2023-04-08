package com.yueqiu.mobile.controller.index;

import com.yueqiu.common.constant.CacheConstants;
import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.core.redis.RedisCache;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.Message;
import com.yueqiu.common.domain.entity.SysChatRecords;
import com.yueqiu.common.utils.ServletUtils;
import com.yueqiu.framework.web.TaskService;
import com.yueqiu.framework.web.service.TokenService;
import com.yueqiu.framework.web.service.WebSocketServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.Session;
import javax.websocket.server.PathParam;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/websocket")
@Api(value = "前台-聊天接口", tags = "聊天接口")
public class ChatController {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private TaskService taskService;

    @RequestMapping("/onlineUser")
    public Set<String> onlineUsers() {
        ConcurrentHashMap<String, Session> sessionPool = WebSocketServer.getSessionPool();
        LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        Set<String> set = sessionPool.keySet();
        Iterator<String> it = set.iterator();
        Set<String> nameset = new HashSet<String>();
        while (it.hasNext()) {
            String entry = it.next();
            if (!entry.equals(loginUser.getUserId()))
                nameset.add(entry);
        }
        return nameset;
    }

    @GetMapping("/getRecords/{activityId}")
    @ApiOperation(value = "获取聊天记录", notes = "获取聊天记录")
    public AjaxResult getRecords(@PathVariable Long activityId) {
        Future<List<SysChatRecords>> stringFuture = taskService.joinChat(activityId);
        try {
            return AjaxResult.success(stringFuture.get());
        } catch (Exception e){
            return AjaxResult.error("");
        }
    }
}
