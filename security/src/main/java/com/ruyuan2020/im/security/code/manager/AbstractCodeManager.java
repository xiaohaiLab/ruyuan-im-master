package com.ruyuan2020.im.security.code.manager;

import com.ruyuan2020.im.common.security.exception.BaseSecurityException;
import com.ruyuan2020.im.common.web.util.ServletUtils;
import com.ruyuan2020.im.security.code.Code;
import com.ruyuan2020.im.security.code.generator.CodeGenerator;
import com.ruyuan2020.im.security.code.service.CodeStoreService;
import com.ruyuan2020.im.common.security.constant.SecurityConstants;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Map;

/**
 * 验证码管理器抽象类
 *
 * @author case
 */
public abstract class AbstractCodeManager<T extends Code> implements CodeManager {

    /**
     * spring注入的验证码生成器集合
     */
    @Resource
    private Map<String, CodeGenerator> codeGeneratorMap;

    /**
     * 验证码存储服务
     */
    @Resource
    private CodeStoreService codeStoreService;

    /**
     * 参数检查
     */
    protected abstract boolean checkParam(Code.Type codeType);

    /**
     * 发送验证码
     *
     * @param code 验证码
     */
    protected abstract void send(T code);

    protected abstract String generateStoreKey(Code.Type codeType);

    @Override
    public void create() {
        // 获取验证码类型
        Code.Type codeType = getCodeType();
        // 参数检查
        if (!checkParam(codeType)) return;
        // 生产验证码
        T code = generate(codeType);
        // 保存验证码
        store(codeType, code);
        // 发送验证码
        send(code);
    }

    /**
     * 生成验证码
     *
     * @param codeType 验证码类型
     * @return 验证码
     */
    @SuppressWarnings("unchecked")
    private T generate(Code.Type codeType) {
        CodeGenerator codeGenerator = codeGeneratorMap.get(codeType + SecurityConstants.CODE_GENERATOR_POSTFIX);
        return (T) codeGenerator.generate();
    }

    /**
     * 存储验证码
     *
     * @param codeType 验证码类型
     * @param code     验证码
     */
    private void store(Code.Type codeType, T code) {
        String key = generateStoreKey(codeType);
        codeStoreService.save(key, code.getCode(), Duration.ofSeconds(code.getExpiresIn()));
    }

    @Override
    public void verify(Code.Type codeType) {
        String storeCode = getStoreValue(codeType);
        if (StringUtils.isBlank(storeCode)) {
            throw new BaseSecurityException("验证码已过期");
        }
        String code = CodeManagerFactory.getParam(ServletUtils.getRequest(), codeType.toString());
        if (StringUtils.isBlank(code)) {
            throw new BaseSecurityException("验证码不能为空");
        }
        if (!StringUtils.equals(storeCode, code)) {
            throw new BaseSecurityException("验证码不正确");
        }
    }

    protected String getStoreValue(Code.Type codeType) {
        String key = generateStoreKey(codeType);
        return codeStoreService.get(key);
    }

    @Override
    public void remove(Code.Type codeType) {
        String key = generateStoreKey(codeType);
        codeStoreService.remove(key);
    }

    /**
     * 通过类名获取验证码类型
     *
     * @return 验证码类型
     */
    protected Code.Type getCodeType() {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), SecurityConstants.CODE_MANAGER_POSTFIX);
        return Code.Type.valueOf(type.toUpperCase());
    }
}
