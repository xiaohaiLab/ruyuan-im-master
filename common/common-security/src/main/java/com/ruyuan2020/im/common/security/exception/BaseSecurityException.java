package com.ruyuan2020.im.common.security.exception;


/**
 * @author case
 */
public class BaseSecurityException extends RuntimeException {

    public BaseSecurityException(String msg) {
        super(msg, null);
    }

    public BaseSecurityException(String msg, Throwable t) {
        super(msg, t);
    }
}
