package com.yueqiu.common.core.text;

import com.yueqiu.common.utils.StringUtils;

//字符串格式化
public class StrFormatter {
    public static final String EMP_JSON = "{}";
    public static final char C_BACKSLASH = '\\';
    public static final char C_DELIM_START = '{';


    public static String format(String template, Object[] params) {
        //判断template,params是否是空
        if (StringUtils.isEmpty(template) || StringUtils.isEmpty(params)) {
            return template;
        }
        //生产一个stringBuilder
        final int size = params.length;
        final StringBuilder stringBuilder = new StringBuilder(size + 50);


        int handlerIndex = 0;
        int delimIndex;
        //找占位符
        for (int startIndex = 0; startIndex < size; startIndex++) {
            delimIndex = template.indexOf(EMP_JSON, handlerIndex);
            if (delimIndex == -1) {
                //没找到分两种情况

                //第一种：从0开始就一直没找到
                if (handlerIndex == 0) {
                    return template;
                } else {
                    //至少找到过一次
                    stringBuilder.append(template, handlerIndex, size);
                }
            } else {
                // "{}" 前的转义符处理
                if (delimIndex > 0 && template.charAt(delimIndex - 1) == C_BACKSLASH) {
                    //分两种情况
                    //第一种："\\" 前面还有 "\\"
                    if (delimIndex > 1 && template.charAt(delimIndex - 2) == C_BACKSLASH) {
                        stringBuilder.append(template, handlerIndex, delimIndex - 1);
                        stringBuilder.append(Convert.utf8Str(params[startIndex]));
                        handlerIndex = delimIndex + 2;
                    } else {
                        //第二种:转义
                        stringBuilder.append(template, handlerIndex, delimIndex - 1);
                        stringBuilder.append(C_DELIM_START);
                        handlerIndex = delimIndex + 1;
                    }


                } else {
                    // 正常占位符
                    stringBuilder.append(template, handlerIndex, delimIndex);
                    stringBuilder.append(Convert.utf8Str(params[startIndex]));
                    handlerIndex = delimIndex + 2;
                }


            }

        }
        // 加入最后一个占位符后所有的字符
        stringBuilder.append(template, handlerIndex, template.length());
        return  stringBuilder.toString();

    }
}
