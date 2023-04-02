package com.ruyuan2020.im.c2c.dao;

import com.ruyuan2020.im.c2c.domain.C2cMessageAckDO;
import com.ruyuan2020.im.common.persistent.dao.BaseDAO;

import java.util.List;

public interface C2cMessageAckDAO extends BaseDAO<C2cMessageAckDO> {

    List<C2cMessageAckDO> listByMemberId(Long memberId);

    void updateAck(Long toId, Long memberId, Integer clientId, Long messageId);

    long count(Long toId, Long memberId, Integer clientId);
}
