package com.ruyuan2020.im.gateway.route.strategy;

import com.ruyuan2020.im.common.core.exception.SystemException;
import com.ruyuan2020.im.gateway.route.RouteClient;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author case
 */
@Component
public class RoundRobinRouteStrategy implements RouteStrategy {

    private final AtomicInteger index = new AtomicInteger();

    @Override
    public RouteClient routeServer(List<RouteClient> clients, Long key) {
        if (clients.size() == 0) {
            throw new SystemException("没有可用的路由服务");
        }
        int position = index.incrementAndGet() % clients.size();
        if (position < 0) {
            position = 0;
        }
        return clients.get(position);
    }
}
