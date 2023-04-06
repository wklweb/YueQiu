package com.yueqiu.common.utils.file;

/**
 * 媒体类型工具
 */
public class MimeTypeUtils {
    public static final String IMAGE_PNG = "image/png";

    public static final String IMAGE_JPG = "image/jpg";

    public static final String IMAGE_JPEG = "image/jpeg";

    public static final String IMAGE_BMP = "image/bmp";

    public static final String IMAGE_GIF = "image/gif";
    public static final String[] IMAGE_EXTENSION = { "bmp", "gif", "jpg", "jpeg", "png" };
    public static final String[] DEFAULT_ALLOW_Type=  {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // 文档
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // 视频格式
            "mp4", "avi", "rmvb",
            // pdf
            "pdf" };
    public static final String[] IMAGE_TYPE = {  "bmp", "gif", "jpg", "jpeg", "png"};
    public static final String[] FLASH_EXTENSION = { "swf", "flv" };;
    public static final String[] MEDIA_EXTENSION = { "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
            "asf", "rm", "rmvb" };
    public static final String[] VIDEO_EXTENSION = {"mp4", "avi", "rmvb" };

    public static String getExtension(String profile) {
        switch (profile){
            case IMAGE_BMP:
                return "bmp";
            case IMAGE_PNG:
                return "png";
            case IMAGE_GIF:
                return "gif";
            case IMAGE_JPEG:
                return "jpeg";
            case IMAGE_JPG:
                return "jpg";
            default:
                return "";
        }
    }
}
