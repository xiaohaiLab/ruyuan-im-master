package com.ruyuan2020.im.chat.dao;

import com.ruyuan2020.im.chat.domain.ChatMemberDO;
import com.ruyuan2020.im.common.persistent.dao.BaseDAO;

import java.util.List;
import java.util.Optional;

/**
 * @author case
 */
public interface ChatMemberDAO extends BaseDAO<ChatMemberDO> {

    List<ChatMemberDO> list(Long memberId);

    Optional<ChatMemberDO> get(Integer type, Long memberId, Long peerId);

    Optional<ChatMemberDO> get(Integer type, Long peerId);
}
