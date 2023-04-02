package com.ruyuan2020.im.auth.sms.aliyun;

import com.alibaba.fastjson.JSONObject;
import com.ruyuan2020.im.auth.sms.AbstractSmsSender;
import com.ruyuan2020.im.auth.sms.aliyun.util.AliYunSmsUtils;
import com.ruyuan2020.im.common.core.exception.BusinessException;
import com.ruyuan2020.im.security.code.sms.SmsLogParams;
import com.ruyuan2020.im.security.util.IpUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

/**
 * @author case
 */
@Component
public class AliyunSmsSender extends AbstractSmsSender {

    private static final String SEND_CODE_TID = "";

    @Override
    @Transactional
    public void send(String mobile, String code) {
        SmsLogParams params = new SmsLogParams();
        params.setMobile(mobile);
        params.setCode(code);
        String ip = IpUtils.getIp();
        params.setIp(ip);
        // 检查发送日志
        checkLog(params);

        String templateParam = MessageFormat.format("'{'\"code\":\"{0}\"'}'", code);
        // 调用阿里云API发送短信
        AliyunSendResult result = AliYunSmsUtils.send(params.getMobile(), SEND_CODE_TID, templateParam);
        if (!"OK".equals(result.getCode())) {
            throw new BusinessException(MessageFormat.format("短信发送失败:{0}", result.getMessage()));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("templateId", SEND_CODE_TID);
        jsonObject.put("templateParam", templateParam);
        jsonObject.put("bizId", result.getBizId());
        jsonObject.put("requestId", result.getRequestId());
        jsonObject.put("resultMessage", result.getMessage());
        params.setParams(jsonObject.toJSONString());
        // 保存发送日志
        saveLog(params);
    }
}
