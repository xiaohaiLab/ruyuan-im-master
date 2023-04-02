package com.ruyuan2020.im.c2g.dao;

import com.ruyuan2020.im.c2g.domain.C2gMessageAckDO;
import com.ruyuan2020.im.common.persistent.dao.BaseDAO;

import java.util.List;

public interface C2gMessageAckDAO extends BaseDAO<C2gMessageAckDO> {

    List<C2gMessageAckDO> listByMemberId(Long memberId);

    void updateAck(Long chatId, Long memberId, Integer clientId, Long messageId);

    long count(Long chatId, Long memberId, Integer clientId);
}
