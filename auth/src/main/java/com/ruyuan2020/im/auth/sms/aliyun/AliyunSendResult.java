package com.ruyuan2020.im.auth.sms.aliyun;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AliyunSendResult {

    private String requestId;

    private String code;

    private String message;

    private String bizId;
}
