package com.yueqiu.common.utils.email;

import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * email工具
 */
public class EmailUtils {

    /**
     * 检查邮箱是否正确
     * @param email
     * @return
     */
    public static String checkEmail(String email) {
        String msg = null;
        //判空
        if(StringUtils.isEmpty(email)){
            msg = "邮箱为空";
            return msg;
        }
        //检查邮箱的格式
        else if(!email.matches(UserConstants.EMAIL_REGULAR_EXPRESSION)){
            msg = "邮箱格式有误";
            return msg;
        }
        else {
            return msg;
        }
    }
}
