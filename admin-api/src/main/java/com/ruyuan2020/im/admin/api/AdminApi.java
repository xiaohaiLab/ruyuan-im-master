package com.ruyuan2020.im.admin.api;


import com.ruyuan2020.im.common.security.domain.AdminInfo;
import com.ruyuan2020.im.common.security.exception.BaseSecurityException;

/**
 * @author case
 */
public interface AdminApi {

    AdminInfo login(String username, String password) throws BaseSecurityException;
}
