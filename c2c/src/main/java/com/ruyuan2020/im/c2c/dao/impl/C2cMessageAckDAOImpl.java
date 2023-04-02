package com.ruyuan2020.im.c2c.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruyuan2020.im.c2c.dao.C2cMessageAckDAO;
import com.ruyuan2020.im.c2c.domain.C2cMessageAckDO;
import com.ruyuan2020.im.c2c.mapper.C2cMessageAckMapper;
import com.ruyuan2020.im.common.web.dao.MybatisPlusDAOImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class C2cMessageAckDAOImpl extends MybatisPlusDAOImpl<C2cMessageAckMapper, C2cMessageAckDO> implements C2cMessageAckDAO {

    @Override
    public List<C2cMessageAckDO> listByMemberId(Long memberId) {
        LambdaQueryWrapper<C2cMessageAckDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(C2cMessageAckDO::getMemberId, memberId);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public void updateAck(Long chatId, Long memberId, Integer clientId, Long messageId) {
        LambdaUpdateWrapper<C2cMessageAckDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(C2cMessageAckDO::getLastAckMessageId, messageId);
        updateWrapper.eq(C2cMessageAckDO::getChatId, chatId);
        updateWrapper.eq(C2cMessageAckDO::getMemberId, memberId);
        updateWrapper.eq(C2cMessageAckDO::getClientId, clientId);
        mapper.update(null, updateWrapper);
    }

    @Override
    public long count(Long chatId, Long memberId, Integer clientId) {
        LambdaQueryWrapper<C2cMessageAckDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(C2cMessageAckDO::getChatId, chatId);
        queryWrapper.eq(C2cMessageAckDO::getMemberId, memberId);
        queryWrapper.eq(C2cMessageAckDO::getClientId, clientId);
        return mapper.selectCount(queryWrapper);
    }
}
