package com.ruyuan2020.im.gateway.route.strategy;

import com.ruyuan2020.im.common.core.exception.SystemException;
import com.ruyuan2020.im.common.im.domain.address.AddressInstance;
import com.ruyuan2020.im.gateway.route.RouteClient;
import com.ruyuan2020.im.gateway.route.strategy.consistent.ConsistentHashRoute;
import com.ruyuan2020.im.gateway.route.strategy.consistent.Node;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author case
 */
@Component
public class ConsistentHashRouteStrategy implements RouteStrategy {

    @Override
    public RouteClient routeServer(List<RouteClient> clients, Long key) {
        if (clients.size() == 0) {
            throw new SystemException("没有可用的路由服务");
        }
        ConsistentHashRoute<RouteClient> consistentHashRoute = new ConsistentHashRoute<>(clients, 128);
        return consistentHashRoute.routeNode(String.valueOf(key));
    }
}
