package com.ruyuan2020.im.gateway.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author case
 */
@Getter
@Setter
public class HeartbeatProperties {

    /**
     * 发送心跳间隔
     */
    private int heartbeatInterval = 5000;

    /**
     * 默认10秒
     */
    private int readTimeout = 30000;
}
