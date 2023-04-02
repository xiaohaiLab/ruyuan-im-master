package com.ruyuan2020.im.web.gateway.filter;

import com.ruyuan2020.im.web.gateway.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 真实IP转发过滤器
 *
 * @author case
 */
//@Component
@Slf4j
public class IpFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ip = IpUtils.getIp(exchange.getRequest());
        log.info("client ip:" + ip);
        // 该步骤可选。可以传递给下游服务器，用于业务处理
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header("X-Real-IP", ip)
                .build();
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
