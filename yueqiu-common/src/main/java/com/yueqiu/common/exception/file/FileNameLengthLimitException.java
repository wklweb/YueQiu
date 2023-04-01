package com.yueqiu.common.exception.file;

public class FileNameLengthLimitException extends FileException{
    public FileNameLengthLimitException(int params) {
        super("file.name.length.limit", new Object[]{params});
    }
}
