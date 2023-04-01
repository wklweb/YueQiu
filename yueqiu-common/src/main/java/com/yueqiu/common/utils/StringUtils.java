package com.yueqiu.common.utils;
import com.yueqiu.common.core.text.StrFormatter;

import java.util.Set;

public class StringUtils extends org.apache.commons.lang3.StringUtils{


    public static String Underline="_";

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean inStringIgnoreCase(String url, String...args) {
        if(url!=null&&args!=null){
            for(String arg : args){
                if(url.equalsIgnoreCase(trim(arg))){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *  判断字符串是否为空
     */
    public static boolean isNotNull(String s) {
        if(s==null||s==""){
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isNotNull(Object object)
    {
        return !isNull(object);
    }

    public static String format(String template, Object... params)
    {
        if (isEmpty(params) || isEmpty(template))
        {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    public static boolean isEmpty(Object[] objects)
    {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * 下划线转驼峰
     * @param orderBy
     * @return
     */
    public static String underToScoreCase(String orderBy) {
        if(StringUtils.isEmpty(orderBy)){
            return null;
        }
        boolean isUpperCase = true;
        boolean nextIsUpperCase = true;
        boolean preIsUpperCase = true;
        StringBuilder stringBuilder = new StringBuilder(orderBy.length()+50);
        for(int i = 0;i<orderBy.length();i++){
            char c = orderBy.charAt(i);
            if(i>0){
                preIsUpperCase = Character.isUpperCase(orderBy.charAt(i-1));
            }
            else {
                preIsUpperCase = false;
            }
             isUpperCase = Character.isUpperCase(c);
            if(i<orderBy.length()-1){
                nextIsUpperCase = Character.isUpperCase(orderBy.charAt(i+1));
            }
            if(preIsUpperCase&&isUpperCase&&!nextIsUpperCase){
                stringBuilder.append(Underline);
            }
            else if((i!=0&&!preIsUpperCase)&&isUpperCase){
                stringBuilder.append(Underline);
            }
            stringBuilder.append(Character.toLowerCase(c));

        }
        return stringBuilder.toString();
    }
}
