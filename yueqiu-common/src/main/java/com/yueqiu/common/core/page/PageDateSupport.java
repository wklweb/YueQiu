package com.yueqiu.common.core.page;

import com.yueqiu.common.core.text.Convert;
import com.yueqiu.common.utils.ServletUtils;

/**
 * 分页数据授予
 */
public class PageDateSupport {


    private static String Page_Num = "pageNum";
    private static String Page_Size = "pageSize";
    private static String Page_Order_Column = "orderByColumn";
    private static String Page_isASC = "isASC";
    private static String REASONABLE = "reasonable";

    public static PageData createPageDateByRequest() {
        return createPageDate();
    }

    private static PageData createPageDate() {
        //从请求得到分页参数
      Integer pageNum = Convert.toInt(ServletUtils.getRequest().getParameter(Page_Num),1);
      Integer pageSize = Convert.toInt(ServletUtils.getRequest().getParameter(Page_Size),10);
      String orderByColumn = ServletUtils.getRequest().getParameter(Page_Order_Column);
      String isASC = ServletUtils.getRequest().getParameter(Page_isASC);

      PageData pageData = new PageData();
      pageData.setPageNum(pageNum);
      pageData.setPageSize(pageSize);
      pageData.setIsASC(isASC==null?"asc":isASC);
      pageData.setOrderBy(orderByColumn);
      pageData.setReasonable(ServletUtils.getParameterToBool(REASONABLE));
      return pageData;
    }
}
