package com.ruyuan2020.im.auth.service.impl;

import com.ruyuan2020.im.security.code.service.CodeStoreService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * Redis方式实现验证码存储
 *
 * @author case
 */
@Service
public class RedisCodeStoreServiceImpl implements CodeStoreService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String key, String value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
