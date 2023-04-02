package com.ruyuan2020.im.c2g.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruyuan2020.im.c2g.dao.C2gMessageAckDAO;
import com.ruyuan2020.im.c2g.domain.C2gMessageAckDO;
import com.ruyuan2020.im.c2g.mapper.C2gMessageAckMapper;
import com.ruyuan2020.im.common.web.dao.MybatisPlusDAOImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class C2gMessageAckDAOImpl extends MybatisPlusDAOImpl<C2gMessageAckMapper, C2gMessageAckDO> implements C2gMessageAckDAO {

    @Override
    public List<C2gMessageAckDO> listByMemberId(Long memberId) {
        LambdaQueryWrapper<C2gMessageAckDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(C2gMessageAckDO::getMemberId, memberId);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public void updateAck(Long chatId, Long memberId, Integer clientId, Long messageId) {
        LambdaUpdateWrapper<C2gMessageAckDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(C2gMessageAckDO::getLastAckMessageId, messageId);
        updateWrapper.eq(C2gMessageAckDO::getChatId, chatId);
        updateWrapper.eq(C2gMessageAckDO::getMemberId, memberId);
        mapper.update(null, updateWrapper);
    }

    @Override
    public long count(Long chatId, Long memberId, Integer clientId) {
        LambdaQueryWrapper<C2gMessageAckDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(C2gMessageAckDO::getChatId, chatId);
        queryWrapper.eq(C2gMessageAckDO::getMemberId, memberId);
        queryWrapper.eq(C2gMessageAckDO::getClientId, clientId);
        return mapper.selectCount(queryWrapper);
    }
}
