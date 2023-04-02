package com.ruyuan2020.im;

import com.ruyuan2020.im.route.server.RouteServer;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableDubbo
@EnableKafka
public class ImRouteApplication implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private RouteServer routeServer;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ImRouteApplication.class);
        application.setWebApplicationType(WebApplicationType.SERVLET);
        application.run(args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        routeServer.start();
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
