package com.ruyuan2020.im.auth.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruyuan2020.im.common.persistent.domain.BaseDO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author case
 */
@Getter
@Setter
@TableName("auth_sms_log")
public class SmsLogDO extends BaseDO {

    private String mobile;

    private String ip;

    private String code;

    private String params;
}
