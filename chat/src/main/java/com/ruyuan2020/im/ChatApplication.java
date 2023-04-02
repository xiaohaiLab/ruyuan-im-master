package com.ruyuan2020.im;

import com.ruyuan2020.im.common.web.config.FastJsonConfig;
import com.ruyuan2020.im.common.web.config.GlobalExceptionConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@Import({FastJsonConfig.class, GlobalExceptionConfig.class})
@EnableDubbo
public class ChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }
}
