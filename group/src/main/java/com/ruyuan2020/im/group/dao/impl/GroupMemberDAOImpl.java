package com.ruyuan2020.im.group.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruyuan2020.im.common.web.dao.MybatisPlusDAOImpl;
import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.group.dao.GroupMemberDAO;
import com.ruyuan2020.im.group.domain.GroupMemberDO;
import com.ruyuan2020.im.group.domain.GroupMemberQuery;
import com.ruyuan2020.im.group.mapper.GroupMemberMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author case
 */
@Repository
public class GroupMemberDAOImpl extends MybatisPlusDAOImpl<GroupMemberMapper, GroupMemberDO> implements GroupMemberDAO {

    @Override
    public BasePage<GroupMemberDO> listByPage(GroupMemberQuery query) {
        LambdaQueryWrapper<GroupMemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupMemberDO::getGroupId, query.getGroupId());
        return listPage(query, queryWrapper);
    }

    @Override
    public long count(Long groupId, Long memberId) {
        LambdaQueryWrapper<GroupMemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupMemberDO::getGroupId, groupId);
        queryWrapper.eq(GroupMemberDO::getMemberId, memberId);
        return mapper.selectCount(queryWrapper);
    }

    @Override
    public List<Long> getByGroupId(Long groupId) {
        LambdaQueryWrapper<GroupMemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(GroupMemberDO::getMemberId);
        queryWrapper.eq(GroupMemberDO::getGroupId, groupId);
        return mapper.selectList(queryWrapper).stream().map(GroupMemberDO::getMemberId).collect(Collectors.toList());
    }

    @Override
    public BasePage<Long> listGroupMemberByPage(GroupMemberQuery query, Long memberId) {
        IPage<Long> page = new Page<>(query.getCurrent(), query.getPageSize());
        page.setRecords(mapper.list(page, memberId));
        return new BasePage<>(page.getRecords(), page.getTotal(), page.getSize(), page.getCurrent());
    }

    @Override
    public long count(Long groupId) {
        LambdaQueryWrapper<GroupMemberDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupMemberDO::getGroupId, groupId);
        return mapper.selectCount(queryWrapper);
    }
}
