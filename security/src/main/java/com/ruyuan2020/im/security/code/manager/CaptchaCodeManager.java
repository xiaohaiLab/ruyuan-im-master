package com.ruyuan2020.im.security.code.manager;

import com.ruyuan2020.im.common.web.util.ServletUtils;
import com.ruyuan2020.im.security.code.CaptchaCode;
import com.ruyuan2020.im.security.code.Code;
import com.ruyuan2020.im.security.util.IpUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

/**
 * @author case
 */
@Component
public class CaptchaCodeManager extends AbstractCodeManager<CaptchaCode> {

    @Override
    protected boolean checkParam(Code.Type codeType) {
        return true;
    }

    @SneakyThrows
    @Override
    protected void send(CaptchaCode code) {
        HttpServletResponse response = ServletUtils.getResponse();
        // 图片写入请求
        assert response != null;
        ImageIO.write(code.getImage(), "PNG", response.getOutputStream());
    }

    @Override
    protected String generateStoreKey(Code.Type codeType) {
        String ip = IpUtils.getIp();
        return codeType + "::" + ip;
    }
}
