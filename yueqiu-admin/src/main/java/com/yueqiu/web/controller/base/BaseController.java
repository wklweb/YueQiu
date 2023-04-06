package com.yueqiu.web.controller.base;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yueqiu.common.constant.HttpStatus;
import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.domain.entity.TableInfo;
import com.yueqiu.common.utils.SecurityUtils;
import com.yueqiu.common.utils.page.PageUtils;

import java.util.List;

public class BaseController {
    protected void startPage() {
        PageUtils.startPage();
    }


    protected TableInfo getTableInfo(List<?> dataList) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setDate(dataList);
        tableInfo.setCode(HttpStatus.SUCCESS);
        tableInfo.setMsg("查询成功");
        tableInfo.setTotal(new PageInfo(dataList).getTotal());
        return tableInfo;
    }
    public String getUserName(){
        return getLoginUser().getUsername();
    }
    public LoginUser getLoginUser()
    {
        return SecurityUtils.getLoginUser();
    }

    public AjaxResult toAjax(int rows){
        return rows>0?AjaxResult.success():AjaxResult.error();
    }
}
