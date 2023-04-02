package com.ruyuan2020.im.common.core.exception;

public class SystemException extends BaseException {

    private static final long serialVersionUID = 1L;

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(String message) {
        super(message, null);
    }

    public SystemException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
