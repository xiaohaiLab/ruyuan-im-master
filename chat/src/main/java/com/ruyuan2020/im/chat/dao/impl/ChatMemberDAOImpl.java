package com.ruyuan2020.im.chat.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruyuan2020.im.chat.dao.ChatMemberDAO;
import com.ruyuan2020.im.chat.domain.ChatMemberDO;
import com.ruyuan2020.im.chat.mapper.ChatMemberMapper;
import com.ruyuan2020.im.common.web.dao.MybatisPlusDAOImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author case
 */
@Repository
public class ChatMemberDAOImpl extends MybatisPlusDAOImpl<ChatMemberMapper, ChatMemberDO> implements ChatMemberDAO {

    @Override
    public List<ChatMemberDO> list(Long memberId) {
        LambdaQueryWrapper<ChatMemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatMemberDO::getMemberId, memberId);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public Optional<ChatMemberDO> get(Integer chatType, Long memberId, Long peerId) {
        LambdaQueryWrapper<ChatMemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatMemberDO::getMemberId, peerId);
        queryWrapper.eq(ChatMemberDO::getPeerId, memberId);
        return Optional.ofNullable(mapper.selectOne(queryWrapper));
    }

    @Override
    public Optional<ChatMemberDO> get(Integer chatType, Long peerId) {
        LambdaQueryWrapper<ChatMemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ChatMemberDO::getPeerId, peerId);
        return Optional.ofNullable(mapper.selectOne(queryWrapper));
    }
}