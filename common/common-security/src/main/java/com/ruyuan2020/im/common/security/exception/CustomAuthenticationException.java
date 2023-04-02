package com.ruyuan2020.im.common.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author case
 */
public class CustomAuthenticationException extends AuthenticationException {

    public CustomAuthenticationException(String msg) {
        super(msg, null);
    }

    public CustomAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
