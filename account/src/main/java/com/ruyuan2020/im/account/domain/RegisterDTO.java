package com.ruyuan2020.im.account.domain;

import com.ruyuan2020.im.common.core.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO extends BaseDomain {

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;
}
