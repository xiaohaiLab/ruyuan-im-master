package com.ruyuan2020.im.common.core.exception;

public class UtilException extends BaseException {

    private static final long serialVersionUID = 1L;

    public UtilException(String message, Throwable cause) {
        super(message, cause);
    }

    public UtilException(String message) {
        super(message, null);
    }
}
