package com.yueqiu.common.exception.file;

import java.util.Arrays;

public class InvalidExtension extends FileUploadException {
    private static final long serialVersionUID = 1L;
    private String[] allowType;
    private String fileName;
    private String extension;
    public InvalidExtension(String extension, String[] allowType, String fileName) {
        super("上传文件 ["+fileName+"]失败！,请上传["+ Arrays.toString(allowType)+"类型的文件");
        this.extension = extension;
        this.allowType = allowType;
        this.fileName = fileName;

    }


    public static class InvalidImageExtensionException extends InvalidExtension{
        private static final long serialVersionUID = 1L;
        public InvalidImageExtensionException(String extension,String[] allowType,String fileName) {
            super(extension,allowType,fileName);
        }
    }


    public static class InvalidFlashExtensionException extends InvalidExtension {
        public InvalidFlashExtensionException(String extension, String[] default_allow_type, String fileName) {
            super(extension,default_allow_type,fileName);
        }
    }

    public static class InvalidMediaExtensionException extends InvalidExtension {
        public InvalidMediaExtensionException(String extension, String[] default_allow_type, String fileName) {
            super(extension,default_allow_type,fileName);
        }
    }

    public static class InvalidVideoExtensionException extends InvalidExtension {
        public InvalidVideoExtensionException(String extension, String[] default_allow_type, String fileName) {
            super(extension,default_allow_type,fileName);
        }
    }
}
