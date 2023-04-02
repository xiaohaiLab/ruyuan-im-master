package com.ruyuan2020.im.security.code.service;

import java.time.Duration;

/**
 * @author case
 */
public interface CodeStoreService {

    /**
     * 存储验证码
     *
     * @param key     key
     * @param value   验证码
     * @param timeout 过期时间
     */
    void save(String key, String value, Duration timeout);

    /**
     * 过期存储的验证码
     *
     * @param key key
     * @return 验证码
     */
    String get(String key);

    /**
     * 删除验证码
     *
     * @param key key
     */
    void remove(String key);
}
