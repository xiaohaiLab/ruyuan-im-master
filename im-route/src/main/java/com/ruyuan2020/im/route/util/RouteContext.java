package com.ruyuan2020.im.route.util;

import cn.hutool.core.util.StrUtil;
import com.ruyuan2020.im.common.core.exception.SystemException;
import com.ruyuan2020.im.common.im.constant.ZkConstants;
import com.ruyuan2020.im.route.properties.ConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * @author case
 */

@Component
@RequiredArgsConstructor
public class RouteContext {

    private final ConfigProperties configProperties;

    @Value("${spring.profiles.active}")
    private String profile;

    @SneakyThrows
    public String serverId() {
        String serverId = System.getenv("im.serverId");
        if (StrUtil.isBlank(serverId)) {
            serverId = configProperties.getServerId();
            if (StrUtil.isBlank(serverId)) {
                throw new SystemException("serverId不能为空");
            }
        }
        return serverId;
    }

    public Integer getPort() {
        return configProperties.getPort();
    }

    public String getZkRootPath() {
        return ZkConstants.ZK_ROUTE_PATH + "/" + profile;
    }

    @SneakyThrows
    public String getZkPath() {
        return getZkRootPath()
                + "/" + serverId() + ":" + InetAddress.getLocalHost().getHostAddress()
                + ":" + this.getPort();
    }
}
