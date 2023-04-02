package com.ruyuan2020.im.security.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author case
 */
@Getter
@Setter
public class CaptchaProperties extends VerifyCodeProperties {

    /**
     * 图片宽
     */
    private int width = 80;

    /**
     * 图片高
     */
    private int height = 30;
}
