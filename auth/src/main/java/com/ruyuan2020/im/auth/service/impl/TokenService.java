package com.ruyuan2020.im.auth.service.impl;

import com.ruyuan2020.im.common.security.domain.AuthInfo;

/**
 * @author case
 */

public interface TokenService {

    AuthInfo authenticate(String token);
}
