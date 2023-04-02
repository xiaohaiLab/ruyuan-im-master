package com.ruyuan2020.im.auth.sms;

import com.ruyuan2020.im.auth.domain.SmsLogQuery;
import com.ruyuan2020.im.auth.dao.SmsLogDAO;
import com.ruyuan2020.im.auth.domain.SmsLogDO;
import com.ruyuan2020.im.common.core.exception.BusinessException;
import com.ruyuan2020.im.security.code.sms.SmsLogParams;
import com.ruyuan2020.im.security.code.sms.SmsSender;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.time.LocalDateTime;

/**
 * @author case
 */
public abstract class AbstractSmsSender implements SmsSender {

    @Resource
    private SmsLogDAO smsLogDAO;

    @Transactional
    public void saveLog(SmsLogParams params) {
        SmsLogDO smsLogDO = new SmsLogDO();
        smsLogDO.setMobile(params.getMobile());
        smsLogDO.setIp(params.getIp());
        smsLogDO.setCode(params.getCode());
        smsLogDO.setParams(params.getParams());
        smsLogDAO.save(smsLogDO);
    }

    public void checkLog(SmsLogParams params) {
        LocalDateTime now = LocalDateTime.now();

        long count;
        SmsLogQuery query = new SmsLogQuery();

        query.setIp(params.getIp());
        query.setMobile(null);
        query.setGetGmtCreate(now.plusSeconds(60));
        count = smsLogDAO.count(query);
        if (count > 1) {
            throw new BusinessException(MessageFormat.format("同一IP地址{0}秒内，请勿多次获取动态码！", 60));
        }

        query.setIp(null);
        query.setMobile(params.getMobile());
        query.setGetGmtCreate(now.plusSeconds(60));
        count = smsLogDAO.count(query);
        if (count > 1) {
            throw new BusinessException(MessageFormat.format("同一手机号{0}秒内，请勿多次获取动态码！", 60));
        }

        query.setIp(null);
        query.setMobile(params.getMobile());
        query.setGetGmtCreate(now.plusHours(24));
        count = smsLogDAO.count(query);
        if (count > 5) {
            throw new BusinessException(MessageFormat.format("同一手机号{0}小时内，请勿多次获取动态码！", 24));
        }

        query.setIp(params.getIp());
        query.setMobile(null);
        query.setGetGmtCreate(now.plusHours(24));
        count = smsLogDAO.count(query);
        if (count > 20) {
            throw new BusinessException(MessageFormat.format("同一IP地址{0}小时内，请勿多次获取动态码！", 24));
        }
    }
}
