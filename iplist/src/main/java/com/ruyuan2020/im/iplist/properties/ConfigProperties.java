package com.ruyuan2020.im.iplist.properties;

import com.ruyuan2020.im.common.im.properties.ZookeeperProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author case
 */
@ConfigurationProperties(prefix = ConfigProperties.PREFIX)
@Component
@Getter
@Setter
public class ConfigProperties {

    public static final String PREFIX = "im";

    /**
     * zookeeper配置
     */
    private ZookeeperProperties zk = new ZookeeperProperties();
}
