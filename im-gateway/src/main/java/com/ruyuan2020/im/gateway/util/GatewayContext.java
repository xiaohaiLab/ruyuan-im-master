package com.ruyuan2020.im.gateway.util;

import cn.hutool.core.util.StrUtil;
import com.ruyuan2020.im.common.core.exception.SystemException;
import com.ruyuan2020.im.common.im.constant.ZkConstants;
import com.ruyuan2020.im.common.im.util.ZkClient;
import com.ruyuan2020.im.gateway.properties.ConfigProperties;
import com.ruyuan2020.im.gateway.constant.GatewayConstants;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author case
 */

@Component
@RequiredArgsConstructor
public class GatewayContext {

    private final Timer timer = new Timer();

    private final AtomicInteger online = new AtomicInteger(0);

    private final AtomicInteger lastUpdateOnline = new AtomicInteger(0);

    private final ZkClient zkClient;

    private final ConfigProperties configProperties;

    @Value("${spring.profiles.active}")
    private String profile;

    public void schedule() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (online.get() == lastUpdateOnline.get()) {
                    return;
                }
                try {
                    lastUpdateOnline.set(online.get());
                    zkClient.setData(getZkPath(), String.valueOf(online.get()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 5000, 3000);
    }

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

    @SneakyThrows
    public String ipAddress() {
        String ipAddress = System.getenv("im.ip-address");
        if (StrUtil.isBlank(ipAddress)) {
            ipAddress = configProperties.getIpAddress();
            if (StrUtil.isBlank(ipAddress)) {
                ipAddress = InetAddress.getLocalHost().getHostAddress();
            }
        }
        return ipAddress;
    }

    public boolean isWebSocket() {
        return GatewayConstants.PROTOCOL_WS.equals(configProperties.getProtocol());
    }

    public boolean isTcp() {
        return GatewayConstants.PROTOCOL_TCP.equals(configProperties.getProtocol());
    }

    public Integer getPort() {
        return configProperties.getPort();
    }

    public String getZkRootPath() {
        return ZkConstants.ZK_GATEWAY_PATH + "/" + profile;
    }

    public String getZkRouteRootPath() {
        return ZkConstants.ZK_ROUTE_PATH + "/" + profile;
    }

    @SneakyThrows
    public String getZkPath() {
        return getZkRootPath()
                + "/" + serverId() + ":" + ipAddress()
                + ":" + this.getPort();
    }

    public void online() {
        online.incrementAndGet();
    }

    public void offline() {
        online.decrementAndGet();
    }

    public int onlineCount() {
        return online.get();
    }
}
