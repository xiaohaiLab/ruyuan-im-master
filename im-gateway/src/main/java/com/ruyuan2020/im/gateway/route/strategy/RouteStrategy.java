package com.ruyuan2020.im.gateway.route.strategy;

import com.ruyuan2020.im.gateway.route.RouteClient;

import java.util.List;

/**
 * @author case
 */
public interface RouteStrategy {

    RouteClient routeServer(List<RouteClient> clients, Long key);
}
