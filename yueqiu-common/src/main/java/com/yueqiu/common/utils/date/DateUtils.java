package com.yueqiu.common.utils.date;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils{
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static Date getNowTime() {
        return new Date();
    }

    /**
     * date对象转固定格式日期字符串
     * @param yyyyMmDdHhMmSs
     * @param lastTime
     * @return
     */
    public static String parseDateToStr(String yyyyMmDdHhMmSs, Date lastTime) {
         if(lastTime==null){
             return null;
         }
         return new SimpleDateFormat(yyyyMmDdHhMmSs).format(lastTime);
    }

    public static Date getNowDate() {
        Date date  = new Date();
        return date;
    }

    public static String dataPath() {
        Date date = new Date();
        return DateFormatUtils.format(date,"yyyy/MM/dd");
    }
}
