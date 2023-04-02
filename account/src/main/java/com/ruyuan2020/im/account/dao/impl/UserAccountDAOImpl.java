package com.ruyuan2020.im.account.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruyuan2020.im.common.web.dao.MybatisPlusDAOImpl;
import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.account.dao.UserAccountDAO;
import com.ruyuan2020.im.account.domain.UserAccountDO;
import com.ruyuan2020.im.account.domain.UserAccountQuery;
import com.ruyuan2020.im.account.mapper.UserAccountMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author case
 */
@Repository
public class UserAccountDAOImpl extends MybatisPlusDAOImpl<UserAccountMapper, UserAccountDO> implements UserAccountDAO {

    @Override
    public BasePage<UserAccountDO> listByPage(UserAccountQuery query) {
        LambdaQueryWrapper<UserAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        return listPage(query, queryWrapper);
    }

    @Override
    public long countByUsername(String username) {
        LambdaQueryWrapper<UserAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAccountDO::getUsername, username);
        return mapper.selectCount(queryWrapper);
    }

    @Override
    public Optional<UserAccountDO> getByUsername(String username) {
        LambdaQueryWrapper<UserAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAccountDO::getUsername, username);
        return Optional.ofNullable(mapper.selectOne(queryWrapper));
    }

    @Override
    public long countByNickname(String nickname) {
        LambdaQueryWrapper<UserAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAccountDO::getUsername, nickname);
        return mapper.selectCount(queryWrapper);
    }

    @Override
    public long countByMobile(String mobile) {
        LambdaQueryWrapper<UserAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAccountDO::getMobile, mobile);
        return mapper.selectCount(queryWrapper);
    }

    @Override
    public List<UserAccountDO> getByIds(List<Long> ids) {
        LambdaQueryWrapper<UserAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(UserAccountDO::getId, UserAccountDO::getNickname, UserAccountDO::getAvatar);
        queryWrapper.in(UserAccountDO::getId, ids);
        return mapper.selectList(queryWrapper);
    }

    @Override
    public Optional<UserAccountDO> getByMobile(String mobile) {
        LambdaQueryWrapper<UserAccountDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserAccountDO::getUsername, mobile);
        return Optional.ofNullable(mapper.selectOne(queryWrapper));
    }
}