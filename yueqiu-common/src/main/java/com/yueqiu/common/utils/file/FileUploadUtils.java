package com.yueqiu.common.utils.file;

import com.yueqiu.common.config.YueQiuConfig;
import com.yueqiu.common.constant.Constants;
import com.yueqiu.common.exception.file.FileNameLengthLimitException;
import com.yueqiu.common.exception.file.FileSizeLimitException;
import com.yueqiu.common.exception.file.InvalidExtension;
import com.yueqiu.common.utils.StringUtils;
import com.yueqiu.common.utils.date.DateUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 文件上传工具
 */
public class FileUploadUtils {
    private static final int MAX_FILENAME_LENGTH = 100;
    private static final Long MAX_FILE_SIZE = 52428800L;

    public static String fileUpload(String uploadPath, MultipartFile multipartFile) throws IOException {
        try {
            return fileUpload(uploadPath,multipartFile,MimeTypeUtils.DEFAULT_ALLOW_Type);
        }
        catch (Exception e){
            throw new IOException(e.getMessage(), e);
        }
    }

    public static String fileUpload(String uploadPath, MultipartFile multipartFile, String[] default_allow_type) throws InvalidExtension, IOException {
        int fileNameLength = Objects.requireNonNull(multipartFile.getOriginalFilename()).length();
        //文件名限制
        if(fileNameLength>FileUploadUtils.MAX_FILENAME_LENGTH){
            throw new FileNameLengthLimitException(FileUploadUtils.MAX_FILENAME_LENGTH);
        }
        //文件大小/类型限制
        limitFileSize(multipartFile,default_allow_type);

        String encodingFileName = encodingFileName(multipartFile);

        String filePath = getFileWithPath(uploadPath,encodingFileName).getAbsolutePath();
        Path path = Paths.get(filePath);
        multipartFile.transferTo(path);
        return getPathFileName(uploadPath,encodingFileName);
    }

    private static String getPathFileName(String uploadPath, String encodingFileName) {
        int index = YueQiuConfig.getProfile().length()+1;
        String currentPath = StringUtils.substring(uploadPath,index);
        return Constants.RESOURCE_PREFIX+"/"+currentPath+"/"+encodingFileName;
    }

    /**
     * 获取带路径的文件
     * @param uploadPath
     * @param fileName
     * @return
     */
    private static File getFileWithPath(String uploadPath, String fileName) {
        File file = new File(uploadPath+File.separator+fileName);
        if(!file.exists()){
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
        }
        return file;

    }

    /**
     * 编码文件名（日期+）
     * @param multipartFile
     * @return
     */
    private static String encodingFileName(MultipartFile multipartFile) {
        String encodingName = StringUtils.format("{}/{}.{}", DateUtils.dataPath(),
                FilenameUtils.getBaseName(multipartFile.getOriginalFilename()),FileUploadUtils.getExtension(multipartFile));
        return encodingName;
    }

    private static void limitFileSize(MultipartFile multipartFile, String[] default_allow_type) throws InvalidExtension {
        Long fileSize = multipartFile.getSize();
        String fileName = multipartFile.getOriginalFilename();
        if(fileSize>FileUploadUtils.MAX_FILE_SIZE){
            throw new FileSizeLimitException(FileUploadUtils.MAX_FILE_SIZE/1024/1024);
        }
        String extension = getExtension(multipartFile);
        //类型支持校验
        if(default_allow_type!=null&&!assertFileTyle(extension,default_allow_type)){
            //不被支持
            if(default_allow_type==MimeTypeUtils.IMAGE_TYPE){
                //不支持图片
                throw new InvalidExtension.InvalidImageExtensionException(extension,default_allow_type,fileName);
            }
            else if(default_allow_type==MimeTypeUtils.FLASH_EXTENSION){
                throw new InvalidExtension.InvalidFlashExtensionException(extension,default_allow_type,fileName);
            } else if (default_allow_type == MimeTypeUtils.MEDIA_EXTENSION) {
                throw new InvalidExtension.InvalidMediaExtensionException(extension,default_allow_type,fileName);
            }else if (default_allow_type==MimeTypeUtils.VIDEO_EXTENSION){
                throw new InvalidExtension.InvalidVideoExtensionException(extension,default_allow_type,fileName);
            }
            else {
                throw new InvalidExtension(extension,default_allow_type,fileName);
            }

        }

    }

    /**
     * 校验拓展名是否被允许上传
     * @param extension
     * @param default_allow_type
     * @return
     */
    private static boolean assertFileTyle(String extension, String[] default_allow_type) {
        if(!StringUtils.isEmpty(extension)){
            for(String str : default_allow_type){
                if(str.equalsIgnoreCase(extension)){
                    return true;
                }
            }
        }
       return false;
    }

    /**
     * 获取拓展名
     * @param multipartFile
     * @return
     */
    private static String getExtension(MultipartFile multipartFile) {
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        if(StringUtils.isEmpty(extension)){
          return MimeTypeUtils.getExtension(Objects.requireNonNull(multipartFile.getContentType()));
        }
        return extension;
    }
}
