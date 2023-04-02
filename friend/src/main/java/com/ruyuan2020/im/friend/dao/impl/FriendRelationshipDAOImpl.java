package com.ruyuan2020.im.friend.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruyuan2020.im.common.web.dao.MybatisPlusDAOImpl;
import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.friend.dao.FriendRelationshipDAO;
import com.ruyuan2020.im.friend.domain.FriendRelationshipDO;
import com.ruyuan2020.im.friend.domain.FriendRelationshipQuery;
import com.ruyuan2020.im.friend.domain.FriendRequestDO;
import com.ruyuan2020.im.friend.mapper.FriendRelationshipMapper;
import org.springframework.stereotype.Repository;

/**
 * @author case
 */
@Repository
public class FriendRelationshipDAOImpl extends MybatisPlusDAOImpl<FriendRelationshipMapper, FriendRelationshipDO> implements FriendRelationshipDAO {

    @Override
    public BasePage<FriendRelationshipDO> listByPage(FriendRelationshipQuery query) {
        LambdaQueryWrapper<FriendRelationshipDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FriendRelationshipDO::getAccountId, query.getAccountId());
        return listPage(query, queryWrapper);
    }

    @Override
    public long count(Long accountId, Long friendId) {
        LambdaQueryWrapper<FriendRelationshipDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FriendRelationshipDO::getAccountId, accountId);
        queryWrapper.eq(FriendRelationshipDO::getFriendId, friendId);
        return mapper.selectCount(queryWrapper);
    }
}
