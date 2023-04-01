package com.yueqiu.common.utils.page;

import com.github.pagehelper.PageHelper;
import com.yueqiu.common.core.page.PageData;
import com.yueqiu.common.core.page.PageDateSupport;

/**
 * 分页插件工具
 */
public class PageUtils extends PageHelper {


    public static void startPage() {
        PageData pageData = PageDateSupport.createPageDateByRequest();
        int pageNum = pageData.getPageNum();
        int pageSize = pageData.getPageSize();
        String orderBy = pageData.getOrderBy();
        PageHelper.startPage(pageNum,pageSize,orderBy).setReasonable(pageData.getReasonable());
    }
}
