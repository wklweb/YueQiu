package com.yueqiu.web.controller.monitor;

import com.yueqiu.common.constant.CacheConstants;
import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.core.redis.RedisCache;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysUserOnline;
import com.yueqiu.common.domain.entity.TableInfo;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.system.service.SysUserOnlineService;
import com.yueqiu.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@RestController
@Api(value = "在线用户服务", tags = "在线用户接口")
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private SysUserOnlineService sysUserOnlineService;

    @GetMapping("/list")
    @PreAuthorize("@permission.hasPerms('monitor:online:query')")
    @ApiOperation(value = "显示在线用户", notes = "显示在线用户")
    public TableInfo showOnlineUser(String username,String ipaddr){
       startPage();
       List<SysUserOnline> userOnlineList = new ArrayList<>();
       Collection<String> tokenKeys = redisCache.getKeys(CacheConstants.LOGIN_TOKEN_KEY+"*");
       for(String tokenKey : tokenKeys){
           LoginUser loginUser = redisCache.getCacheObject(tokenKey);
           if(StringUtils.isNotNull(username)&&StringUtils.isNotNull(ipaddr)){
               userOnlineList.add(sysUserOnlineService.selectOnlineByInfo(username,ipaddr,loginUser));
           }
           else if(StringUtils.isNotNull(ipaddr)){
               userOnlineList.add(sysUserOnlineService.selectOnlineByIpaddr(ipaddr,loginUser));
           }
           else if(StringUtils.isNotNull(username)){
               userOnlineList.add(sysUserOnlineService.selectOnlineByUserName(username,loginUser));
           }
           else {
               userOnlineList.add(sysUserOnlineService.selectOnlineList(loginUser));
           }
       }
       userOnlineList.removeAll(Collections.singleton(null));
       return getTableInfo(userOnlineList);
    }

    @PostMapping("/kick")
    @PreAuthorize("@permission.hasPerms('monitor:online:forceLogout')")
    @ApiOperation(value = "强制下线", notes = "强制下线")
    public AjaxResult kickUser(String token){
        LoginUser loginUser = redisCache.getCacheObject(CacheConstants.LOGIN_TOKEN_KEY+token);
        if(StringUtils.isNotNull(loginUser)){
            redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_KEY+token);
            return AjaxResult.success();
        }
        return AjaxResult.error("用户已下线或者不存在");
    }

    @PostMapping("/batchKick")
    @PreAuthorize("@permission.hasPerms('monitor:online:batchLogout')")
    @ApiOperation(value = "批量强制下线", notes = "批量强制下线")
    public AjaxResult batchKick(String tokens){
        String[] tokenList = StringUtils.split(tokens,",");
        for(String token : tokenList){
            LoginUser loginUser = redisCache.getCacheObject(CacheConstants.LOGIN_TOKEN_KEY+token);
            if(StringUtils.isNotNull(loginUser)){
                redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_KEY+token);
            }
        }
        return AjaxResult.success();
    }

}
