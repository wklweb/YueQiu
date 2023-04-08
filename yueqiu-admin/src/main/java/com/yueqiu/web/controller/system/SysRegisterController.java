package com.yueqiu.web.controller.system;
import com.yueqiu.common.annotation.Log;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.Register;
import com.yueqiu.common.enums.BusinessType;
import com.yueqiu.common.enums.OperatorType;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.framework.web.service.SysRegisterService;
import com.yueqiu.system.service.ISysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static com.yueqiu.common.domain.AjaxResult.*;


@RestController
@Api(value = "系统注册", tags = "注册接口")
public class SysRegisterController {
    @Autowired
    private ISysConfigService sysConfigService;
    @Autowired
    private SysRegisterService sysRegisterService;

    @PostMapping("/register")
    @ApiOperation(value = "注册用户",notes = "注册用户")
    public AjaxResult register(@RequestBody Register register){
        if(!("true".equals(sysConfigService.selectConfigByKey("sys.account.registerUser")))){
            return error("系统未开启注册功能");
        }
        String result = sysRegisterService.register(register);
        return StringUtils.isEmpty(result)?success():error(result);
    }
}
