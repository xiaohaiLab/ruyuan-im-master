package com.ruyuan2020.im.common.security.token.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * @author case
 */
@Getter
@Setter
@AllArgsConstructor
public class Oauth2Request {

    private String clientId;

    private String grantType;

    private Set<String> scope;

    private String realm;
}
