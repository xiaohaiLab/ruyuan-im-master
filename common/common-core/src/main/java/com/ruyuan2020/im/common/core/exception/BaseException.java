package com.ruyuan2020.im.common.core.exception;

public abstract class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    Integer errorCode;

    protected BaseException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
