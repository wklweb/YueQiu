package com.yueqiu.web.controller.system;

import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysDictData;
import com.yueqiu.system.service.SysDictDataService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/dict/data")
@Api(value = "字典数据", tags = "字典数据接口")
public class SysDictDataController {

    @Autowired
    private SysDictDataService sysDictDataService;

    @GetMapping("/type/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType){
        List<SysDictData> sysDictData = sysDictDataService.selectDictDataList(dictType);
        if(!sysDictData.isEmpty()){
            return AjaxResult.success(sysDictData);
        }
        else {
            return AjaxResult.error("查询失败");
        }
    }



}
