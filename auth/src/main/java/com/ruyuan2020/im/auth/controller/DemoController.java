package com.ruyuan2020.im.auth.controller;

import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import com.ruyuan2020.im.common.web.util.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author case
 */
@RestController
@RequestMapping("/api/auth/test")
public class DemoController {

    @GetMapping
    public JsonResult<?> get() {
        // 创建内存空间
//        byte[] array = new byte[100 * 1024 * 1024];
        return ResultHelper.ok("userId:" + SecurityUtils.getId());
    }
}
