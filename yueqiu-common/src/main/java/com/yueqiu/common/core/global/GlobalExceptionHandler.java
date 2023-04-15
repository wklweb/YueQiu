package com.yueqiu.common.core.global;

import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.exception.ServiceException;
import com.yueqiu.common.exception.user.UserPasswordNotMatchException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public AjaxResult resolveServiceException(ServiceException e){
        AjaxResult ajaxResult = AjaxResult.error(e.getMessage());
        ajaxResult.put("code",302);
        return ajaxResult;
    }
    @ExceptionHandler(UserPasswordNotMatchException.class)
    @ResponseBody
    public AjaxResult resolveServiceException(UserPasswordNotMatchException e){
        AjaxResult ajaxResult = AjaxResult.error(e.getMessage());
        ajaxResult.put("code",401);
        return ajaxResult;
    }



}
