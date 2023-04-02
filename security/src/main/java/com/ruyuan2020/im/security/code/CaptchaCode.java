package com.ruyuan2020.im.security.code;

import lombok.Getter;

import java.awt.image.BufferedImage;

/**
 * @author case
 */
@Getter
public class CaptchaCode extends Code {

    private final BufferedImage image;

    public CaptchaCode(String code, int expiresIn, BufferedImage image) {
        super(code, expiresIn);
        this.image = image;
    }
}
