package com.ruyuan2020.im.common.security.token.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author case
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = Oauth2Properties.PREFIX)
public class Oauth2Properties {

    public static final String PREFIX = "security.oauth2";

    private List<OAuth2ClientProperties> clients;

    /**
     * redis,jwt
     */
    private String tokenStore = "jwt";

    private String keyPair;

    private String publicKey;

    private String alias = "jwt";

    private String keypass = "";

    private String storepass = "";
}
