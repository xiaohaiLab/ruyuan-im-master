package com.ruyuan2020.im.common.security.exception;

/**
 * @author case
 */
public enum SecurityCode {

    AUTHENTICATION_ERROR("401"),
    ACCESS_DENIED_ERROR("403"),
    ;

    private final String code;

    SecurityCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
