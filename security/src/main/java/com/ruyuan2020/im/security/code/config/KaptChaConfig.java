package com.ruyuan2020.im.security.code.config;

import com.ruyuan2020.im.security.properties.SecurityProperties;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author case
 */
@Component
public class KaptChaConfig {

    @Bean
    @ConditionalOnMissingBean(name = "defaultKaptcha")
    public DefaultKaptcha getDefaultKaptcha(SecurityProperties securityProperties) {
        com.google.code.kaptcha.impl.DefaultKaptcha defaultKaptcha = new com.google.code.kaptcha.impl.DefaultKaptcha();
        Properties properties = new Properties();
        // 图片边框
        properties.setProperty("kaptcha.border", "yes");
        // 边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "red");
        // 图片宽
        properties.setProperty("kaptcha.image.width", String.valueOf(securityProperties.getCode().getCaptcha().getWidth()));
        // 图片高
        properties.setProperty("kaptcha.image.height", String.valueOf(securityProperties.getCode().getCaptcha().getHeight()));
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", String.valueOf(securityProperties.getCode().getCaptcha().getLength()));
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
