package com.yueqiu.common.utils.Id;

import java.util.UUID;

/**
 * ID工具类
 */
public class IdUtils {
    public static String fastUUID() {
        return UUID.randomUUID().toString();
    }
}
