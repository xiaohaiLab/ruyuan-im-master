package com.ruyuan2020.im.common.security.token.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author case
 */
@Getter
@Setter
public class OAuth2ClientProperties {

    /**
     * 第三方应用clientId
     */
    private String clientId;

    /**
     * 第三方应用secret
     */
    private String secret;

    /**
     * token的有效时间
     */
    private int accessTokenValidateSeconds = 7200;

    private String authorizedGrantTypes;

    private List<String> scopes = new ArrayList<>();

    private String realm;
}
