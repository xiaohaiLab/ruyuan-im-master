package com.ruyuan2020.im.security.code.manager;

import com.ruyuan2020.im.common.security.exception.BaseSecurityException;
import com.ruyuan2020.im.common.web.util.ServletUtils;
import com.ruyuan2020.im.security.code.Code;
import com.ruyuan2020.im.security.code.sms.SmsSender;
import com.ruyuan2020.im.common.security.constant.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * @author case
 */
@Component
@Slf4j
public class SmsCodeManager extends AbstractCodeManager<Code> {

    private static final String MOBILE_PATTERN = "^1[0-9]{10}$";

    @Resource
    private SmsSender smsSender;

    @Override
    protected boolean checkParam(Code.Type codeType) {
        HttpServletRequest request = ServletUtils.getRequest();
        String mobile = CodeManagerFactory.getParam(request, SecurityConstants.PARAM_MOBILE);
        boolean matched = Pattern.matches(MOBILE_PATTERN, mobile);
        if (!matched) {
            throw new BaseSecurityException("手机号格式不正确");
        }
        String storeCode = getStoreValue(codeType);
        // 验证码已发送，并且未过期，不再生成新验证码，防止恶意攻击
        return !StringUtils.isNotBlank(storeCode);
    }

    @Override
    protected void send(Code code) {
        String mobile = CodeManagerFactory.getParam(ServletUtils.getRequest(), SecurityConstants.PARAM_MOBILE);
        smsSender.send(mobile, code.getCode());
    }

    @Override
    protected String generateStoreKey(Code.Type codeType) {
        HttpServletRequest request = ServletUtils.getRequest();
        String mobile = CodeManagerFactory.getParam(request, SecurityConstants.PARAM_MOBILE);
        return codeType + "::" + mobile;
    }

}