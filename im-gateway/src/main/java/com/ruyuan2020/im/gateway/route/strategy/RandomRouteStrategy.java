package com.ruyuan2020.im.gateway.route.strategy;

import com.ruyuan2020.im.common.core.exception.SystemException;
import com.ruyuan2020.im.gateway.route.RouteClient;
import io.netty.util.internal.PlatformDependent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * @author case
 */
@Component
public class RandomRouteStrategy implements RouteStrategy {

    @Override
    public RouteClient routeServer(List<RouteClient> clients, Long key) {

        int size = clients.size();
        if (size == 0) {
            throw new SystemException("没有可用的路由服务");
        }
        Random random = PlatformDependent.threadLocalRandom();
        random.setSeed(key);
        int position = random.nextInt(size);

        return clients.get(position);
    }
}
