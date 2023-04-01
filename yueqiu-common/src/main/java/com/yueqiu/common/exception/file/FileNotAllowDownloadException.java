package com.yueqiu.common.exception.file;

public class FileNotAllowDownloadException extends FileException{
    public FileNotAllowDownloadException(String code,String filename) {
        super(code, new Object[]{filename});
    }
}
