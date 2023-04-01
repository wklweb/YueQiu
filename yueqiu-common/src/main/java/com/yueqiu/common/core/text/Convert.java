package com.yueqiu.common.core.text;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Objects;

public class Convert {

    /**
     * 对象转字符串
     * @param value
     * @return
     */
    public static String toStr(Object value)
    {
        return toStr(value, null);
    }
    public static String toStr(Object value, String defaultValue)
    {
        if (null == value)
        {
            return defaultValue;
        }
        if (value instanceof String)
        {
            return (String) value;
        }
        return value.toString();
    }

    public static String utf8Str(Object obj)
    {
        return str(obj, CharsetKit.CHARSET_UTF_8);
    }
    public static String str(Object obj, Charset charset)
    {
        if (null == obj)
        {
            return null;
        }

        if (obj instanceof String)
        {
            return (String) obj;
        }
        else if (obj instanceof byte[])
        {
            return str((byte[]) obj, charset);
        }
        else if (obj instanceof Byte[])
        {
            byte[] bytes = ArrayUtils.toPrimitive((Byte[]) obj);
            return str(bytes, charset);
        }
        else if (obj instanceof ByteBuffer)
        {
            return str((ByteBuffer) obj, charset);
        }
        return obj.toString();
    }

    public static boolean toBool(Object object) {
        return toBool(object,null);
    }

    private static boolean toBool(Object object, Boolean defaultValue) {
        if(object==null){
           if(defaultValue==null){
               return true;
           }
           else {
               return defaultValue;
           }
        }
        if(object instanceof Boolean){
            return (Boolean) object;
        }
        String strValue = toStr(object,null);
        if(StringUtils.isEmpty(strValue)){
            return defaultValue;
        }
        strValue = strValue.trim().toLowerCase();
        switch (strValue){
            case "true":
            case "1":
            case "yes":
            case "ok":
                return true;
            case "false":
            case "0":
            case "no":
                return false;
            default:
                return defaultValue;
        }
    }

    public static int toInt(Object parameter, Integer defaultInt) {
        if(Objects.isNull(parameter)){
            return defaultInt;
        }
        if(parameter instanceof Integer){
            return (int) parameter;
        }
        if(parameter instanceof Number){
            return ((Number) parameter).intValue();
        }
        String value = toStr(parameter,null);
        if(StringUtils.isEmpty(value)){
            return defaultInt;
        }
        try {
            return Integer.parseInt(value);
        }
        catch (Exception e){
            return defaultInt;
        }

    }
}
