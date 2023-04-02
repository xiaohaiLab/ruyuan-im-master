package com.ruyuan2020.im.security.code.generator;

import com.ruyuan2020.im.security.code.CaptchaCode;
import com.ruyuan2020.im.security.code.Code;
import com.ruyuan2020.im.security.properties.SecurityProperties;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;

/**
 * @author case
 */
@Component
public class CaptchaCodeGenerator implements CodeGenerator {

    @Resource
    private DefaultKaptcha defaultKaptcha;

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public Code generate() {
        String code = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(code);
        return new CaptchaCode(code, securityProperties.getCode().getCaptcha().getExpiresIn(), image);
    }
}
