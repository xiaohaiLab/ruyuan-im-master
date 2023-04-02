package com.ruyuan2020.im.client.mock.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.client.config.PropertiesUtils;
import com.ruyuan2020.im.common.core.domain.JsonResult;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

/**
 * @author case
 */
public class AuthRemote {

    private final static String authUrl = PropertiesUtils.getWebGatewayUrl() + "/api/auth/token";

    @SneakyThrows
    public JsonResult<?> login(String mobile) {
        Map<String, Object> params = new HashMap<>();
        params.put("grantType", "mobile");
        params.put("mobile", mobile);
        String jsonStr = HttpRequest.post(authUrl)
                .basicAuth("account", "vXvLIQjvVoqWxbcOV6oyhjZaU89wHMby7H3Hp6iHNkaU6ODF2PasvSZz2MA8aL4j")
                .form(params)
                .execute().body();
        return JSONUtil.toBean(jsonStr, JsonResult.class);
    }
}
