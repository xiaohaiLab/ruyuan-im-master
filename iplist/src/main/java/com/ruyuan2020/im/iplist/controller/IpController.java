package com.ruyuan2020.im.iplist.controller;

import cn.hutool.http.HttpStatus;
import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import com.ruyuan2020.im.common.im.domain.address.AddressInstance;
import com.ruyuan2020.im.iplist.gateway.AddressManager;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author case
 */
@RestController
@RequestMapping("/api/iplist")
@RequiredArgsConstructor
public class IpController {

    private final AddressManager addressManager;

    @GetMapping
    public JsonResult<?> get() {
        Optional<AddressInstance> optional = addressManager.get();
        if (optional.isPresent()) {
            return ResultHelper.ok(optional.get());
        } else {
            return ResultHelper.fail(String.valueOf(HttpStatus.HTTP_BAD_REQUEST), "没有可用的网关");
        }
    }
}
