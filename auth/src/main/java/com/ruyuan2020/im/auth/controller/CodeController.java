package com.ruyuan2020.im.auth.controller;

import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import com.ruyuan2020.im.security.code.Code;
import com.ruyuan2020.im.security.code.manager.CodeManagerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 验证码控制器类
 *
 * @author case
 */
@RestController
@RequestMapping("/api/auth/code")
public class CodeController {

    /**
     * 验证码工厂
     */
    @Resource
    private CodeManagerFactory codeFactory;

    /**
     * 获取验证码
     *
     * @throws IOException IO异常
     */
    @PostMapping("/{type}")
    public JsonResult<?> getCaptcha(@PathVariable("type") String type) throws IOException {
        codeFactory.getManager(Code.Type.valueOf(type.toUpperCase())).create();
        return ResultHelper.ok("发送成功");
    }
}
