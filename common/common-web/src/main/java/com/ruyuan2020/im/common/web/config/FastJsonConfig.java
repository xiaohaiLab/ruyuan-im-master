package com.ruyuan2020.im.common.web.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonViewResponseBodyAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class FastJsonConfig implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //自定义配置...
        com.alibaba.fastjson.support.config.FastJsonConfig config = new com.alibaba.fastjson.support.config.FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.DisableCircularReferenceDetect);

        fastJsonHttpMessageConverter.setFastJsonConfig(config);
        ByteArrayHttpMessageConverter byteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
        byteArrayHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
        converters.add(fastJsonHttpMessageConverter);
        converters.add(byteArrayHttpMessageConverter);
    }

    @Bean
    public FastJsonViewResponseBodyAdvice fastJsonViewResponseBodyAdvice(){
        return new FastJsonViewResponseBodyAdvice();
    }
}
