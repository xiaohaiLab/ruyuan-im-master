package com.ruyuan2020.im.auth.sms.aliyun.util;

import com.ruyuan2020.im.auth.sms.aliyun.AliyunSendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AliYunSmsUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(AliYunSmsUtils.class);

    //产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";

    //产品名称:云通信短信API产品,开发者无需替换
    private static final String accessKeyId = "";
    //产品域名,开发者无需替换
    private static final String accessKeySecret = "";
    //短信签名
    private static final String signName = "";

    public static AliyunSendResult send(String phoneNumber, String templateId, String templateParam) {
//        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        CommonRequest request = new CommonRequest();
//        request.setSysMethod(MethodType.POST);
//        request.setSysDomain(domain);
//        request.setSysVersion("2017-05-25");
//        request.setSysAction("SendSms");
//        request.putQueryParameter("RegionId", "cn-hangzhou");
//        request.putQueryParameter("PhoneNumbers", phoneNumber);
//        request.putQueryParameter("SignName", signName);
//        request.putQueryParameter("TemplateCode", templateId);
//        request.putQueryParameter("TemplateParam", templateParam);
//        try {
//            CommonResponse response = client.getCommonResponse(request);
//            return JSONObject.parseObject(response.getData(), AliyunSendResult.class);
//        } catch (ClientException e) {
//            throw new BaseException(e.getMessage(), e);
//        }
        LOGGER.info("发送短信:{}", templateParam);
        AliyunSendResult result = new AliyunSendResult();
        result.setCode("OK");
        return result;
    }
}
