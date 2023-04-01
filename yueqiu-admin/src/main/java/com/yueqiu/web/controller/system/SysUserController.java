package com.yueqiu.web.controller.system;

import com.yueqiu.common.annotation.Anonymous;
import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.domain.entity.TableInfo;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.common.utils.page.PageUtils;
import com.yueqiu.system.service.SysUserService;
import com.yueqiu.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/system/user")
@Api(value = "系统用户", tags = "用户接口")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/list")
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息的全部信息")
    public TableInfo users(@RequestBody SysUser sysUser){
        startPage();
        List<SysUser> list = sysUserService.selectUserByUser(sysUser);
        return getTableInfo(list);
    }
    @PostMapping("/add")
    @PreAuthorize("@permission.hasPerms('system:user:add')")
    @ApiOperation(value = "新增用户", notes = "新增用户")
    public AjaxResult add(@RequestBody SysUser user){
        if(UserConstants.USERNAME_NOT_UNIQUE.equals(sysUserService.checkUserName(user))){
            return AjaxResult.error("新增用户:"+user.getUserName()+"失败,该用户已存在");
        }
        if(UserConstants.PHONE_NOT_UNIQUE.equals(sysUserService.checkPhone(user))){
            return AjaxResult.error("手机号:"+user.getPhonenumber()+"已存在");
        }
        if(UserConstants.EMAIL_NOT_UNIQUE.equals(sysUserService.checkEmail(user))){
            return AjaxResult.error("邮箱:"+user.getEmail()+"已存在");
        }
        int result = sysUserService.addSysUser(user);
        if(result==1){
            return AjaxResult.success("新增成功");
        }
        else {
            return AjaxResult.error("新增失败");
        }
    }

    @GetMapping("/checkName")
    @Anonymous
    @ApiOperation(value = "检查用户名",notes = "检查用户名")
    public AjaxResult checkUserName(@RequestParam String username){
        if(StringUtils.isEmpty(username)){
            return AjaxResult.error("用户名不能为空");
        }
        else if (username.length()> UserConstants.USERNAME_MAX_LENGTH) {
            return AjaxResult.error("用户名太长");
        }
        else if(username.length()<UserConstants.USERNAME_MIN_LENGTH){
            return AjaxResult.error("用户名太短");
        }
        SysUser sysUser = sysUserService.selectUserByLoginName(username);
        if(!Objects.isNull(sysUser)){
            return AjaxResult.error("该用户名已存在");
        }
        return AjaxResult.success();
    }
    @GetMapping("/checkPassword")
    @Anonymous
    @ApiOperation(value = "检查密码",notes = "检查密码")
    public AjaxResult checkPassword(@RequestParam String password){
        if(StringUtils.isEmpty(password)){
            return AjaxResult.error("密码不能为空");
        }
        else if(!password.matches(UserConstants.PASSWORD_REGULAR_EXPRESSION)){
            return AjaxResult.error("密码只能由数字和字母组成，且长度在0-9");
        }
        return AjaxResult.success();
    }

    @GetMapping("/checkEmail")
    @Anonymous
    @ApiOperation(value = "检查邮箱",tags = "检查邮箱")
    public AjaxResult checkEmail(@RequestParam String email){
        if(StringUtils.isEmpty(email)){
            return AjaxResult.error("邮箱不能为空");
        } else if (!email.matches(UserConstants.EMAIL_REGULAR_EXPRESSION)) {
            return AjaxResult.error("邮箱格式不正确");
        }
        return AjaxResult.success();
    }
    @GetMapping("/checkPhone")
    @Anonymous
    @ApiOperation(value = "检查手机号",tags = "检查手机号")
    public AjaxResult checkPhone(@RequestParam String phone){
        if(StringUtils.isEmpty(phone)){
            return AjaxResult.error("手机号不能为空");
        } else if (!phone.matches(UserConstants.PHONE_REGULAR_EXPRESSION)) {
            return AjaxResult.error("手机格式不正确");
        }
        return AjaxResult.success();
    }

    private void startPage() {
        PageUtils.startPage();
    }

}
