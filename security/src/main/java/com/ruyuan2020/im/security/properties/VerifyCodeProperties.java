package com.ruyuan2020.im.security.properties;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author case
 */
@Getter
@Setter
public class VerifyCodeProperties {

    /**
     * 验证码长度
     */
    private int length = 6;

    /**
     * 过期时间
     */
    private int expiresIn = 60;

    private List<String> urls = new ArrayList<>();
}
