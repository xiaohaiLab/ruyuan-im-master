package com.ruyuan2020.im.web.gateway.config;

import com.ruyuan2020.im.web.gateway.util.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Nullable;

/**
 * @author case
 */
@Slf4j
@Component
public class GlobalExceptionConfig implements ErrorWebExceptionHandler, Ordered {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, @Nullable Throwable ex) {
        assert ex != null;
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        log.error(ex.getMessage(), ex);
        return ResponseHelper.fail(response, ex);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
