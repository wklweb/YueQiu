package com.yueqiu.web.controller.monitor;

import com.yueqiu.common.annotation.Log;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysOperLog;
import com.yueqiu.common.domain.entity.TableInfo;
import com.yueqiu.common.enums.BusinessType;
import com.yueqiu.system.service.SysOperLogService;
import com.yueqiu.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 操作日志
 */
@RestController
@Api(value = "操作日志记录", tags = "操作日志记录")
@RequestMapping("/monitor/operlog")
public class SysOperLogController extends BaseController {
    @Autowired
    private SysOperLogService sysOperLogService;

    @GetMapping("/list")
    @ApiOperation(value = "操作日志列表", tags = "操作日志列表")
    @PreAuthorize("@permission.hasPerms('monitor:operlog:list')")
    public TableInfo operLogs(SysOperLog sysOperLog) {
        startPage();
        return getTableInfo(sysOperLogService.selectOperLogList(sysOperLog));
    }

    @DeleteMapping("/{operLogId}")
    @ApiOperation(value = "删除操作日志", tags = "删除操作日志")
    @Log(BusinessType = BusinessType.DELETE,title = "删除操作日志")
    @PreAuthorize("@permission.hasPerms('monitor:operlog:delete')")
    public AjaxResult deleteOperLog(@PathVariable Long operLogId){
        return operLogId==null?AjaxResult.error("参数不能为空"):toAjax(
                sysOperLogService.delOperLogById(operLogId)
        );
    }


}
