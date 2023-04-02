package com.ruyuan2020.im.iplist.gateway;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ruyuan2020.im.common.im.constant.ZkConstants;
import com.ruyuan2020.im.common.im.domain.address.AddressInstance;
import com.ruyuan2020.im.common.im.util.AddressUtils;
import com.ruyuan2020.im.common.im.util.ZkClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.recipes.cache.CuratorCacheListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author case
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AddressManager {

    private final Map<AddressInstance, Integer> onlineMap = new ConcurrentHashMap<>();

    private final ZkClient zkClient;

    @Value("#{'" + ZkConstants.ZK_GATEWAY_PATH + "/'.concat('${spring.profiles.active}')}")
    private String path;

    @PostConstruct
    public void init() {
        zkClient.createNode(path);
        zkClient.addListener(path, (type, oldData, data) -> {
            try {
                if (type == CuratorCacheListener.Type.NODE_CREATED) {
                    if (!StrUtil.equals(path, data.getPath())) {
                        String childPath = data.getPath().replace(path + "/", "");
                        AddressInstance addressInstance = AddressUtils.parseAddress(childPath);
                        log.info("网关服务[{}]上线", addressInstance.getServerId());
                        onlineMap.put(addressInstance, Integer.parseInt(new String(data.getData(), StandardCharsets.UTF_8)));
                    }
                } else if (type == CuratorCacheListener.Type.NODE_CHANGED) {
                    String childPath = data.getPath().replace(path + "/", "");
                    AddressInstance addressInstance = AddressUtils.parseAddress(childPath);
                    int count = Integer.parseInt(new String(data.getData(), StandardCharsets.UTF_8));
                    log.info("网关服务[{}]在线客户端数变化为[{}]", addressInstance.getServerId(), count);
                    onlineMap.put(addressInstance, count);
                } else {
                    String childPath = oldData.getPath().replace(path + "/", "");
                    AddressInstance addressInstance = AddressUtils.parseAddress(childPath);
                    log.info("网关服务[{}]下线", addressInstance.getServerId());
                    onlineMap.remove(addressInstance);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
    }

    public Optional<AddressInstance> get() {
        if (CollUtil.isEmpty(onlineMap)) {
            return Optional.empty();
        }
        List<Map.Entry<AddressInstance, Integer>> list = new ArrayList<>(onlineMap.entrySet());
        list.sort(Comparator.comparingInt(Map.Entry::getValue));
        return Optional.ofNullable(list.get(0).getKey());
    }
}
