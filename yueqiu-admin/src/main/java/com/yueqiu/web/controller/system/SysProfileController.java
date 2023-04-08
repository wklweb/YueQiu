package com.yueqiu.web.controller.system;

import com.yueqiu.common.annotation.Log;
import com.yueqiu.common.config.YueQiuConfig;
import com.yueqiu.common.core.domain.model.LoginUser;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.enums.BusinessType;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.common.utils.file.FileUploadUtils;
import com.yueqiu.common.utils.file.MimeTypeUtils;
import com.yueqiu.framework.web.service.TokenService;
import com.yueqiu.system.service.SysUserService;
import com.yueqiu.web.controller.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TokenService tokenService;

    /**
     * 头像上传
     */
    @Log(title = "用户头像", BusinessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            String avatarPath = YueQiuConfig.getAvatarPath();
            LoginUser loginUser = getLoginUser();
            String imgUrl = FileUploadUtils.fileUpload(avatarPath, file, MimeTypeUtils.IMAGE_EXTENSION);
            //更新用户头像路径
            if (sysUserService.updateUserAvatar(loginUser.getUsername(), imgUrl)) {
                AjaxResult ajaxResult = AjaxResult.success("头像上传成功");
                //当前登录用户信息更改，需要同步
                loginUser.getUser().setAvatar(imgUrl);
                tokenService.setLoginUser(loginUser);
                ajaxResult.put("imgUrl", imgUrl);
                return ajaxResult;
            }

        }
        return AjaxResult.error("上传失败");

    }

}
