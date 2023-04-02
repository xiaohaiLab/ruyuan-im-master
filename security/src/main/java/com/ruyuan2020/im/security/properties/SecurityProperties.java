package com.ruyuan2020.im.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author case
 */
@Component
@ConfigurationProperties(prefix = SecurityProperties.PREFIX)
@Getter
@Setter
public class SecurityProperties {

    public static final String PREFIX = "security";

    /**
     * 验证码配置
     */
    private CodeProperties code = new CodeProperties();

    /**
     * 认证URL
     */
    private UrlProperties authentication = new UrlProperties();

    /**
     * 白名单
     */
    private List<String> whiteListUrls = new ArrayList<>();
}
