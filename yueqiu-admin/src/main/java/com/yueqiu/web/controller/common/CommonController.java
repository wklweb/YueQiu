package com.yueqiu.web.controller.common;

import com.yueqiu.common.config.YueQiuConfig;
import com.yueqiu.common.domain.AjaxResult;
import com.yueqiu.common.exception.file.FileNotAllowDownloadException;
import com.yueqiu.common.utils.file.FileUploadUtils;
import com.yueqiu.common.utils.file.FileUtils;
import com.yueqiu.framework.config.ServiceConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 通用文件服务接口
 */
@RestController
@RequestMapping("/common")
@Api(value = "文件接口",tags = "文件接口")
public class CommonController {

    public static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServiceConfig serviceConfig;

    /**
     * 下载
     */
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    public AjaxResult upload(@RequestParam("file") MultipartFile multipartFile){
       try {
           String uploadPath = YueQiuConfig.getUploadPath();
           String fileName = FileUploadUtils.fileUpload(uploadPath,multipartFile);
           AjaxResult ajaxResult = AjaxResult.success();
           //访问资源的url
           String url = serviceConfig.getUrl() + fileName;
           ajaxResult.put("fileName",fileName);
           ajaxResult.put("url",url);
           ajaxResult.put("newFileName", FileUtils.getBeanName(fileName));
           return ajaxResult;
       }
       catch (Exception e){
           AjaxResult ajaxResult = AjaxResult.error("上传失败");
           ajaxResult.put("code",302);
           return ajaxResult;
       }
    }

    /**
     * 文件下载
     * @param fileName
     * @return
     */
    @GetMapping("/download")
    @ApiOperation(value = "文件下载")
    public void download(@RequestParam("filename") String fileName, Boolean delete, HttpServletRequest request, HttpServletResponse response){
        try {
            //下载的文件名限制
            if(!FileUtils.allowFileDownload(fileName)){
                throw new FileNotAllowDownloadException("file.limit.download",fileName);
            }
            //下载后显示的文件名(当前时间戳+"_"后的内容)
            String realFileName = System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf("_")+1);

            //目标下载路径
            String downloadPath = YueQiuConfig.getDownloadPath()+fileName;

            //告诉浏览器响应类型是文件下载
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttributeResponse(realFileName,response);
            FileUtils.writeFile(downloadPath,response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(downloadPath);
            }


        }
        catch (Exception e){
           log.error("文件下载失败");
        }


    }


}
