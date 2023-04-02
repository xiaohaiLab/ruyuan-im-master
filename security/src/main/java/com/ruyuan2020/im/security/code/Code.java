package com.ruyuan2020.im.security.code;


import com.ruyuan2020.im.common.security.constant.SecurityConstants;
import lombok.*;

/**
 * @author case
 */
@Getter
public class Code {

    private final String code;

    private final int expiresIn;

    public Code(String code, int expiresIn) {
        this.code = code;
        this.expiresIn = expiresIn;
    }

    public enum Type {
        CAPTCHA {
            @Override
            public String toString() {
                return SecurityConstants.PARAM_CAPTCHA;
            }
        },

        SMS {
            @Override
            public String toString() {
                return SecurityConstants.PARAM_SMS;
            }
        }
    }
}
