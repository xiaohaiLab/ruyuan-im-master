package com.ruyuan2020.im;

import com.ruyuan2020.im.common.web.config.FastJsonConfig;
import com.ruyuan2020.im.common.web.config.GlobalExceptionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({FastJsonConfig.class, GlobalExceptionConfig.class})
public class IpListApplication {

    public static void main(String[] args) {
        SpringApplication.run(IpListApplication.class, args);
    }
}
