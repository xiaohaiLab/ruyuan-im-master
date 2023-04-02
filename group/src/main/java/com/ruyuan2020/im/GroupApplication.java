package com.ruyuan2020.im;

import com.ruyuan2020.im.common.web.config.FastJsonConfig;
import com.ruyuan2020.im.common.web.config.GlobalExceptionConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@Import({FastJsonConfig.class, GlobalExceptionConfig.class})
@EnableDubbo
public class GroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupApplication.class, args);
    }

    @Bean
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //配置序列化
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //hash
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //value
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
