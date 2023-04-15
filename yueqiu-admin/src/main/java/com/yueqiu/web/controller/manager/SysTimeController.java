package com.yueqiu.web.controller.manager;

import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.BaseEntity;
import com.yueqiu.common.domain.entity.SysTime;
import com.yueqiu.common.domain.entity.TableInfo;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.system.service.SysTimeService;
import com.yueqiu.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/time")
@Api(value = "营销时间接口", tags = "营销时间接口")
public class SysTimeController extends BaseController {
    @Autowired
    private SysTimeService sysTimeService;

    @GetMapping("/list")
    @PreAuthorize("@permission.hasPerms('manager:time:list')")
    @ApiOperation(value = "场地时间列表", notes = "场地时间列表")
    public TableInfo areaTimes(@RequestBody SysTime sysTime){
        startPage();
        List<SysTime> sysTimeList = sysTimeService.selectTimeList(sysTime);
        return getTableInfo(sysTimeList);
    }

    @PostMapping("/add")
    @PreAuthorize("@permission.hasPerms('manager:time:add')")
    @ApiOperation(value = "场地时间新增", notes = "场地时间新增")
    public AjaxResult addAreaTime(@RequestBody SysTime sysTime){
        String msg = sysTimeService.checkSysTime(sysTime);
        if(!StringUtils.isEmpty(msg)){
            return AjaxResult.error(msg);
        }
        return toAjax(sysTimeService.addAreaTime(sysTime));

    }

    @DeleteMapping("/delete/{timeId}")
    @PreAuthorize("@permission.hasPerms('manager:time:remove')")
    @ApiOperation(value = "场地时间删除", notes = "场地时间删除")
    public AjaxResult deleteAreaTime(@PathVariable Long timeId){
        if (timeId!=null){
            return toAjax(sysTimeService.removeAreaTime(timeId));
        }
        return AjaxResult.error();
    }


}
