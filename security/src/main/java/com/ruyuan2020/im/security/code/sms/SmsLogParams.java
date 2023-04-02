package com.ruyuan2020.im.security.code.sms;

import lombok.Getter;
import lombok.Setter;

/**
 * @author case
 */
@Getter
@Setter
public class SmsLogParams {

    private String mobile;

    private String ip;

    private String code;

    private String params;
}
