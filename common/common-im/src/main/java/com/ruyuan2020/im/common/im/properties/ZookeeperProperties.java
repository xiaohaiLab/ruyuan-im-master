package com.ruyuan2020.im.common.im.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author case
 */
@Getter
@Setter
public class ZookeeperProperties {

    /**
     * 是否使用zk注册服务
     */
    private boolean enable = true;

    /**
     * zk地址
     */
    private String zkServer;

    /**
     * 间隔时间
     */
    private int intervalTime = 1000;

    /**
     * 重试次数
     */
    private int retry = 3;
}
