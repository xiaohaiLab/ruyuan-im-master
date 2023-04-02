package com.ruyuan2020.im.web.gateway.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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

    private List<String> whiteListUrls;
}
