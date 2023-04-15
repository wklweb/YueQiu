package com.yueqiu.web.controller.monitor;

import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.TableInfo;
import com.yueqiu.system.domain.SysLogininfor;
import com.yueqiu.system.service.Impl.ISysLogininforService;
import com.yueqiu.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "登录注册记录", tags = "登录注册记录")
@RequestMapping("/monitor/logininfor")
public class SysLoginInfoController extends BaseController {

    @Autowired
    private ISysLogininforService sysLogininforService;

    @GetMapping("/list")
    @ApiOperation(value = "登录日志列表", tags = "登录日志列表")
    @PreAuthorize("@permission.hasPerms('monitor:logininfor:list')")
    public TableInfo loginInfo(@RequestBody SysLogininfor sysLogininfor){
        startPage();
        List<SysLogininfor> list = sysLogininforService.selectLogininfors(sysLogininfor);
        return getTableInfo(list);

    }


}
