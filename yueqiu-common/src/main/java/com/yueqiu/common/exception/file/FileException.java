package com.yueqiu.common.exception.file;

import com.yueqiu.common.exception.BaseException;

public class FileException extends BaseException {

    public FileException(String code, Object[] params) {
        super(code, null, params, "file");
    }
}
