package com.ruyuan2020.im.security.code.manager;

import com.ruyuan2020.im.security.code.Code;
import com.ruyuan2020.im.common.security.constant.SecurityConstants;
import com.ruyuan2020.im.common.security.exception.BaseSecurityException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

/**
 * CodeManager工厂类
 *
 * @author case
 */
@Component
public class CodeManagerFactory {

    /**
     * spring 注入的CodeManager集合
     */
    @Resource
    private Map<String, CodeManager> codeManagerMap;

    /**
     * 通过枚举参数获取CodeManager
     *
     * @param codeType 枚举类型
     * @return CodeManager
     */
    public CodeManager getManager(Code.Type codeType) {
        return getManager(codeType.toString());
    }

    /**
     * 获取request中的参数值
     *
     * @param request   请求
     * @param paramName 参数名
     * @return 参数值
     */
    public static String getParam(HttpServletRequest request, String paramName) {
        String value;
        try {
            value = ServletRequestUtils.getStringParameter(request, paramName);
            if (StringUtils.isBlank(value)) {
                throw new BaseSecurityException(MessageFormat.format("参数{0}不存在", paramName));
            }
        } catch (ServletRequestBindingException e) {
            throw new BaseSecurityException(MessageFormat.format("参数{0}不存在", paramName));
        }
        return value;
    }

    /**
     * 通过字符串拼接获取CodeManager
     *
     * @param codeType 字符串类型
     * @return CodeManager
     */
    private CodeManager getManager(String codeType) {
        CodeManager codeManager = codeManagerMap.get(codeType.toLowerCase() + SecurityConstants.CODE_MANAGER_POSTFIX);
        if (Objects.isNull(codeManager)) {
            throw new BaseSecurityException(MessageFormat.format("{0}值不正确", SecurityConstants.PARAM_CODE_TYPE));
        }
        return codeManager;
    }
}
