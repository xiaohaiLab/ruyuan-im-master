package com.ruyuan2020.im.security;

import com.ruyuan2020.im.security.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author case
 */
@EnableConfigurationProperties(SecurityProperties.class)
@ComponentScan
public class SecurityConfig {

}
