package com.ruyuan2020.im.security.code.manager;

import com.ruyuan2020.im.security.code.Code;

import java.io.IOException;

/**
 * 验证码管理接口
 *
 * @author case
 */
public interface CodeManager {

    /**
     * 创建验证码
     *
     * @throws IOException IO异常
     */
    void create() throws IOException;

    /**
     * 验证验证码
     *
     * @param codeType 验证码类型
     */
    void verify(Code.Type codeType);

    /**
     * 删除验证码
     *
     * @param codeType 验证码类型
     */
    void remove(Code.Type codeType);
}
