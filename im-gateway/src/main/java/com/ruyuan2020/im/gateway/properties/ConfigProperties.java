package com.ruyuan2020.im.gateway.properties;

import com.ruyuan2020.im.common.im.properties.ZookeeperProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

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
     * 使用协议，ws、tcp
     */
    private String protocol = "ws";

    /**
     * 连接检查间隔
     */
    private int connectionCheckTime = 3000;

    /**
     * 服务Id
     */
    private String serverId;

    private String ipAddress;

    private Integer port;

    private Integer maxOnlineCount = 300000;

    /**
     * 路由服务列表 ip:port,ip:port
     */
    private List<String> routeServers;

    /**
     * 心跳配置
     */
    private HeartbeatProperties heartbeat = new HeartbeatProperties();

    /**
     * zookeeper配置
     */
    private ZookeeperProperties zk = new ZookeeperProperties();
}
