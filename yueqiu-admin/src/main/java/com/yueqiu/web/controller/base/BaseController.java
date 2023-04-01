package com.yueqiu.web.controller.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yueqiu.common.constant.HttpStatus;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.domain.entity.TableInfo;

import java.util.List;

public class BaseController {

    protected TableInfo getTableInfo(List<?> dataList) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setDate(dataList);
        tableInfo.setCode(HttpStatus.SUCCESS);
        tableInfo.setMsg("查询成功");
        tableInfo.setTotal(new PageInfo(dataList).getTotal());
        return tableInfo;
    }

}
