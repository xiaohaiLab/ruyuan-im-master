package com.ruyuan2020.im.tenant.api;

import com.ruyuan2020.im.common.security.domain.TenantInfo;
import com.ruyuan2020.im.common.security.exception.BaseSecurityException;

/**
 * @author case
 */
public interface TenantApi {

    TenantInfo login(String username, String password) throws BaseSecurityException;

    TenantInfo loginByMobile(String mobile);
}
