package com.yueqiu.common.exception.file;

public class FileSizeLimitException extends FileException{
    public FileSizeLimitException(Long params) {
        super("file.size.limit", new Object[]{params});
    }
}
