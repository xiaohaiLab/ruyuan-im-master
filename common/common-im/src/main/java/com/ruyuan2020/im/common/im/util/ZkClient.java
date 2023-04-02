package com.ruyuan2020.im.common.im.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.CuratorCache;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;

/**
 * @author case
 */
@Slf4j
public class ZkClient {

    private final CuratorFramework framework;

    public ZkClient(CuratorFramework framework) {
        this.framework = framework;
    }

    /**
     * 创建当前服务节点
     */
    @SneakyThrows
    public void createEphemeralNode(String path) {
        if (framework.checkExists().forPath(path) != null) {
            framework.delete().forPath(path);
        }
        framework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
    }

    @SneakyThrows
    public void createEphemeralNode(String path, String data) {
        Stat stat = framework.checkExists().forPath(path);
        if (stat == null) {
            framework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, data.getBytes(StandardCharsets.UTF_8));
        }
    }

    @SneakyThrows
    public void createNode(String path) {
        Stat stat = framework.checkExists().forPath(path);
        if (stat == null) {
            framework.create().creatingParentsIfNeeded().forPath(path);
        }
    }

    @SneakyThrows
    public void addListener(String path, CuratorCacheListener listener) {
        CuratorCache cache = CuratorCache.build(framework, path);
        cache.listenable().addListener(listener);
        cache.start();
    }

    @SneakyThrows
    public void setData(String path, String data) {
        framework.setData().forPath(path, data.getBytes(StandardCharsets.UTF_8));
    }
}
