package com.yueqiu.common.exception.file;

public class FileUploadException extends Exception{

    private static final long serialVersionUID = 1L;
    private final Throwable throwable;

    public FileUploadException() {
        this(null,null);
    }

    public FileUploadException(String msg) {
        this(null,msg);
    }

    public FileUploadException(Throwable throwable, String msg) {
        super(msg);
        this.throwable = throwable;
    }


    @Override
    public Throwable getCause()
    {
        return throwable;
    }


}
