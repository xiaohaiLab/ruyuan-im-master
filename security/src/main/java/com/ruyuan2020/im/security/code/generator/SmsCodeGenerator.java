package com.ruyuan2020.im.security.code.generator;

import com.ruyuan2020.im.security.properties.SecurityProperties;
import com.ruyuan2020.im.security.code.Code;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author case
 */
@Component
public class SmsCodeGenerator implements CodeGenerator {

    @Resource
    private SecurityProperties securityProperties;

    @Override
    public Code generate() {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new Code(code, securityProperties.getCode().getSms().getExpiresIn());
    }
}
