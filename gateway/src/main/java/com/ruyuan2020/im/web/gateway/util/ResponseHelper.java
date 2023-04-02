package com.ruyuan2020.im.web.gateway.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
public class ResponseHelper {

    public static Mono<Void> fail(ServerHttpResponse response, Throwable throwable) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        JsonResult<?> result;
        if (throwable instanceof AccessDeniedException) {
            result = ResultHelper.fail(String.valueOf(HttpStatus.FORBIDDEN.value()), throwable.getMessage());
        } else if (throwable instanceof AuthenticationException) {
            result = ResultHelper.fail(String.valueOf(HttpStatus.UNAUTHORIZED.value()), throwable.getMessage());
        } else if (throwable instanceof ResponseStatusException) {
            ResponseStatusException exception = (ResponseStatusException) throwable;
            result = ResultHelper.fail(String.valueOf(exception.getStatus().value()), throwable.getMessage());
        } else {
            result = ResultHelper.fail(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), throwable.getMessage());
        }

        DataBuffer buffer = response.bufferFactory().wrap(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> DataBufferUtils.release(buffer));
    }
}
