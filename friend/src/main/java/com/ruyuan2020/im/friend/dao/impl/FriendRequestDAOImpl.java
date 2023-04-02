package com.ruyuan2020.im.friend.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruyuan2020.im.common.web.dao.MybatisPlusDAOImpl;
import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.friend.dao.FriendRequestDAO;
import com.ruyuan2020.im.friend.domain.FriendRequestDO;
import com.ruyuan2020.im.friend.domain.FriendRequestQuery;
import com.ruyuan2020.im.friend.mapper.FriendRequestMapper;
import org.springframework.stereotype.Repository;

/**
 * @author case
 */
@Repository
public class FriendRequestDAOImpl extends MybatisPlusDAOImpl<FriendRequestMapper, FriendRequestDO> implements FriendRequestDAO {
    @Override
    public long count(Long accountId, Long friendId) {
        LambdaQueryWrapper<FriendRequestDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FriendRequestDO::getAccountId, accountId);
        queryWrapper.eq(FriendRequestDO::getFriendId, friendId);
        return mapper.selectCount(queryWrapper);
    }

    @Override
    public BasePage<FriendRequestDO> list(FriendRequestQuery query) {
        LambdaQueryWrapper<FriendRequestDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FriendRequestDO::getAccountId, query.getAccountId());
        return listPage(query, queryWrapper);
    }
}
