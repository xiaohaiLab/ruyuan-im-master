package com.ruyuan2020.im.security.authentication.assembler;

import com.ruyuan2020.im.common.security.constant.SecurityConstants;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @author case
 */
@Component
public class TokenAssemblerFactory {

    @Resource
    private Map<String, TokenAssembler> tokenAssemblerMap;

    public TokenAssembler getAssembler(String grantType) {
        return tokenAssemblerMap.get(grantType + SecurityConstants.TOKEN_ASSEMBLER_POSTFIX);
    }

}
