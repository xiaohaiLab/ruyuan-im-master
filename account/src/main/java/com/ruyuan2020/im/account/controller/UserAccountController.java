package com.ruyuan2020.im.account.controller;

import com.ruyuan2020.im.account.domain.RegisterDTO;
import com.ruyuan2020.im.account.domain.RegisterVO;
import com.ruyuan2020.im.account.service.UserAccountService;
import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author case
 */
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/register")
    public JsonResult<?> register(@RequestBody RegisterVO registerVO) {
        userAccountService.register(registerVO.clone(RegisterDTO.class));
        return ResultHelper.ok();
    }

}
