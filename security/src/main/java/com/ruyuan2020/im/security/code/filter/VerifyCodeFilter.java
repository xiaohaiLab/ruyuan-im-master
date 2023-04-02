package com.ruyuan2020.im.security.code.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import com.ruyuan2020.im.common.security.exception.BaseSecurityException;
import com.ruyuan2020.im.common.security.exception.CustomAuthenticationException;
import com.ruyuan2020.im.common.web.util.ServletUtils;
import com.ruyuan2020.im.security.authentication.handler.DefaultAuthenticationFailureHandler;
import com.ruyuan2020.im.security.code.manager.CodeManager;
import com.ruyuan2020.im.security.properties.SecurityProperties;
import com.ruyuan2020.im.security.code.Code;
import com.ruyuan2020.im.security.code.manager.CodeManagerFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.NestedServletException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 验证码过滤器
 *
 * @author case
 */
@Component
@Slf4j
public class VerifyCodeFilter extends OncePerRequestFilter {

    @Resource
    private SecurityProperties securityProperties;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final Map<ParamUrl, Code.Type> urlMap = new HashMap<>();

    @Resource
    private CodeManagerFactory codeManagerFactory;

    @Resource
    private DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        // 设置图形验证码的URL
        putUrl(securityProperties.getCode().getCaptcha().getUrls(), Code.Type.CAPTCHA);
        // 设置短信验证码的URL
        putUrl(securityProperties.getCode().getSms().getUrls(), Code.Type.SMS);
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
        try {
            // 通过URL获取要验证的验证码类型
            Code.Type codeType = getCodeType(request);
            // 如果不是空，说明需要拦截
            if (Objects.nonNull(codeType)) {
                // 获取管理器
                CodeManager codeManager = codeManagerFactory.getManager(codeType);
                // 验证验证码
                codeManager.verify(codeType);
                // 执行业务逻辑
                filterChain.doFilter(request, response);
                // 删除验证码
                codeManager.remove(codeType);
                return;
            }
        } catch (BaseSecurityException e) {
            defaultAuthenticationFailureHandler.onAuthenticationFailure(request, response, new CustomAuthenticationException(e.getMessage()));
            return;
        } catch (NestedServletException e) {
            defaultAuthenticationFailureHandler.onAuthenticationFailure(request, response, new CustomAuthenticationException(e.getRootCause().getMessage()));
            return;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ServletUtils.renderJsonString(JSONUtil.toJsonStr(ResultHelper.fail(HttpStatus.UNAUTHORIZED.toString(), e.getMessage())));
        }
        filterChain.doFilter(request, response);
    }

    private void putUrl(List<String> urls, Code.Type codeType) {
        if (CollectionUtil.isNotEmpty(urls)) {
            for (String url : urls) {
                String[] strArr = StrUtil.splitToArray(url, '?');
                if (strArr.length == 1) {
                    urlMap.put(new ParamUrl(strArr[0], null, null), codeType);
                } else if (strArr.length == 2) {
                    String[] paramArr = StrUtil.splitToArray(strArr[1], "=");
                    if (paramArr.length == 2) {
                        urlMap.put(new ParamUrl(strArr[0], paramArr[0], paramArr[1]), codeType);
                    } else {
                        throw new BaseSecurityException("不支持的验证码验证url");
                    }
                } else {
                    throw new BaseSecurityException("不支持的验证码验证url");
                }
            }
        }
    }

    private Code.Type getCodeType(HttpServletRequest request) {
        for (Map.Entry<ParamUrl, Code.Type> entry : urlMap.entrySet()) {
            ParamUrl paramUrl = entry.getKey();
            if (antPathMatcher.match(paramUrl.url, request.getRequestURI())) {
                if (StrUtil.isNotBlank(paramUrl.paramName)) {
                    String paramValue = CodeManagerFactory.getParam(request, paramUrl.paramName);
                    if (StrUtil.equals(paramUrl.paramValue, paramValue)) {
                        return entry.getValue();
                    }
                } else {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    private static class ParamUrl {
        private final String url;
        private final String paramName;
        private final String paramValue;

        public ParamUrl(String url, String paramName, String paramValue) {
            this.url = url;
            this.paramName = paramName;
            this.paramValue = paramValue;
        }
    }
}
