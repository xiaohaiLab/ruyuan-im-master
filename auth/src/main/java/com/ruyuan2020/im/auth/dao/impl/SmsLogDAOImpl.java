package com.ruyuan2020.im.auth.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruyuan2020.im.auth.domain.SmsLogQuery;
import com.ruyuan2020.im.auth.dao.SmsLogDAO;
import com.ruyuan2020.im.auth.domain.SmsLogDO;
import com.ruyuan2020.im.auth.mapper.SmsLogMapper;
import com.ruyuan2020.im.common.web.dao.MybatisPlusDAOImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * @author case
 */
@Repository
public class SmsLogDAOImpl extends MybatisPlusDAOImpl<SmsLogMapper, SmsLogDO> implements SmsLogDAO {

    @Override
    public long count(SmsLogQuery query) {
        LambdaQueryWrapper<SmsLogDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getIp())) {
            queryWrapper.eq(SmsLogDO::getIp, query.getIp());
        }
        if (StringUtils.isNotBlank(query.getMobile())) {
            queryWrapper.eq(SmsLogDO::getMobile, query.getMobile());
        }
        if (Objects.nonNull(query.getGetGmtCreate())) {
            queryWrapper.gt(SmsLogDO::getGmtCreate, query.getGetGmtCreate());
        }
        return mapper.selectCount(queryWrapper);
    }
}
