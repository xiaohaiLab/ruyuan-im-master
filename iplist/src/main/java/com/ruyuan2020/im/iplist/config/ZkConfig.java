package com.ruyuan2020.im.iplist.config;

import com.ruyuan2020.im.common.im.util.ZkClient;
import com.ruyuan2020.im.iplist.properties.ConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author case
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ZkConfig {

    private final ConfigProperties configProperties;

    @Bean
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(configProperties.getZk().getIntervalTime(), configProperties.getZk().getRetry());
        CuratorFramework framework = CuratorFrameworkFactory.newClient(configProperties.getZk().getZkServer(), retryPolicy);
        framework.start();
        return framework;
    }

    @Bean
    public ZkClient zkClient(CuratorFramework curatorFramework) {
        return new ZkClient(curatorFramework);
    }
}
