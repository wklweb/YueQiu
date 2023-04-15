package com.yueqiu.web.controller.system;

import com.yueqiu.common.annotation.Anonymous;
import com.yueqiu.common.annotation.Log;
import com.yueqiu.common.annotation.Resubmit;
import com.yueqiu.common.constant.UserConstants;
import com.yueqiu.common.core.redis.RedisCache;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.domain.entity.SysDictData;
import com.yueqiu.common.domain.entity.SysUser;
import com.yueqiu.common.domain.entity.TableInfo;
import com.yueqiu.common.domain.model.MailInfo;
import com.yueqiu.common.enums.BusinessType;
import com.yueqiu.common.enums.OperatorType;
import com.yueqiu.common.enums.UserStatus;
import com.yueqiu.common.utils.SecurityUtils;
import com.yueqiu.common.utils.SpringUtils;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.common.utils.email.EmailUtils;
import com.yueqiu.framework.web.service.EmailService;
import com.yueqiu.framework.web.service.SysResetPasswordService;
import com.yueqiu.system.service.ISysConfigService;
import com.yueqiu.system.service.SysDictDataService;
import com.yueqiu.system.service.SysUserRoleService;
import com.yueqiu.system.service.SysUserService;
import com.yueqiu.web.controller.base.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/system/user")
@Api(value = "系统用户", tags = "用户接口")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysDictDataService sysDictDataService;
    @Autowired
    private SysResetPasswordService sysResetPasswordService;


    @GetMapping("/list")
    @PreAuthorize("@permission.hasPerms('system:user:list')")
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息的全部信息")
    public TableInfo users(@RequestBody SysUser sysUser){
        startPage();
        List<SysUser> list = sysUserService.selectUserByUser(sysUser);
        return getTableInfo(list);

    }
    @PostMapping("/add")
    @PreAuthorize("@permission.hasPerms('system:user:add')")
    @Log(title = "新增用户",BusinessType = BusinessType.INSERT,operatorType = OperatorType.MANAGE)
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
        user.setCreateBy(getUserName());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
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

    /**
     * 找回密码
     */
    @PostMapping("/retrieve")
    @Anonymous
    @Resubmit(interval = 180000,message = "找回操作频繁,请三分钟后重新尝试")
    @ApiOperation(value = "发送找回密码邮件",tags = "发送找回密码邮件")
    public AjaxResult sendRetrieveMail(@RequestParam String email){
        String msg = EmailUtils.checkEmail(email);
        if(!StringUtils.isEmpty(msg)){
            return AjaxResult.error(msg);
        }
        SysUser sysUser = new SysUser();
        sysUser.setEmail(email);
        if(UserConstants.EMAIL_UNIQUE.equals(sysUserService.checkEmail(sysUser))){
            return AjaxResult.error("邮箱未注册");
        }
        SysDictData sysDictData = sysDictDataService.selectDictDataByKeyAndValue("sys_mail_type","0");
        String title = sysDictData.getDictLabel();
        MailInfo mailInfo = new MailInfo("约球账号操作:"+title,email);

        EmailService emailService = SpringUtils.getBean("mail");
        boolean result = false;
        try {
            Future<Boolean> future = emailService.sendTemplateMail(mailInfo);
            result = future.get();
        }
        catch (Exception e){
            return AjaxResult.error(e.getMessage());
        }
        return result?AjaxResult.success("发送成功"):AjaxResult.error("发送失败");

    }

    /**
     * 重置密码
     */
    @PostMapping("/resetPwd")
    @Anonymous
    @ApiOperation(value = "重置密码",tags = "重置密码")
    @Resubmit
    public AjaxResult resetPassword(@RequestParam String newPassword,@RequestParam String resetKey,
                                    @RequestParam String email){
        if(StringUtils.isEmpty(newPassword)||!newPassword.matches(UserConstants.PASSWORD_REGULAR_EXPRESSION)){
            return AjaxResult.error("密码含有数字和字母,且长度要在8-16位之间");
        }
        if(!StringUtils.isEmpty(resetKey)&&sysResetPasswordService.checkResetKey(resetKey)){
            SysUser sysUser = new SysUser();
            sysUser.setEmail(email);
            sysUser.setPassword(SecurityUtils.encryptPassword(newPassword));
            return toAjax(sysResetPasswordService.resetPassword(sysUser,resetKey));
        }
        else {
            return AjaxResult.error("操作失败");
        }
    }
    @PostMapping("/block/{userId}")
    @ApiOperation(value = "封禁用户",tags = "封禁用户")
    @Resubmit
    public AjaxResult blockUser(@PathVariable Long userId){
        if(userId!=null){
            SysUser sysUser = new SysUser();
            sysUser.setUserId(userId);
            sysUser.setStatus(UserStatus.DISABLE.getCode());
            sysUserService.updateUserInfo(sysUser);
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }
}
