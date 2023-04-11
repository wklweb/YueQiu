package com.yueqiu.web.controller.system;

import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysArea;
import com.yueqiu.common.domain.entity.TableInfo;
import com.yueqiu.system.service.SysAreaService;
import com.yueqiu.system.service.SysDictDataService;
import com.yueqiu.system.service.SysShopService;
import com.yueqiu.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@Api(value = "场地服务", tags = "场地接口")
@RequestMapping("/manager/area")
public class SysAreaController extends BaseController {

    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private SysShopService sysShopService;
    @Autowired
    private SysDictDataService dictDataService;

    @GetMapping("/list")
    @PreAuthorize("@permission.hasPerms('manage:area:list')")
    @ApiOperation(value = "获取场地列表信息", notes = "获取场地列表信息")
    public TableInfo areaList(@RequestBody SysArea sysArea){
            List<SysArea> list = sysAreaService.getAreaList(sysArea,getLoginUser().getUserId());
            return getTableInfo(list);
    }

    @PostMapping("/add/{dictType}")
    @PreAuthorize("@permission.hasPerms('manager:area:add')")
    @ApiOperation(value = "新增场地", notes = "新增场地")
    public AjaxResult addArea(@RequestBody SysArea sysArea,@PathVariable String dictType){
        String areaType = sysArea.getAreaType();
        Set<String> areaTypes = dictDataService.selectAreaValues(dictType);
        Long num = areaTypes.stream().map(str->(str.equals(areaType))).count();
        if(num>0){
            return toAjax(sysAreaService.addNewArea(sysArea, getLoginUser().getUserId()));
        }
        else {
            return AjaxResult.error("场地类型不存在");
        }
    }



}
