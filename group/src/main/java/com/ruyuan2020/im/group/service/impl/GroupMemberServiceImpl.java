package com.ruyuan2020.im.group.service.impl;

import com.ruyuan2020.im.account.api.AccountApi;
import com.ruyuan2020.im.account.domain.UserAccountDTO;
import com.ruyuan2020.im.chat.api.ChatApi;
import com.ruyuan2020.im.chat.domain.ChatDTO;
import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.common.core.exception.BusinessException;
import com.ruyuan2020.im.common.core.util.BeanCopierUtils;
import com.ruyuan2020.im.common.im.constant.Constants;
import com.ruyuan2020.im.common.web.util.SecurityUtils;
import com.ruyuan2020.im.group.dao.GroupMemberDAO;
import com.ruyuan2020.im.group.domain.GroupDTO;
import com.ruyuan2020.im.group.domain.GroupMemberDO;
import com.ruyuan2020.im.group.domain.GroupMemberDTO;
import com.ruyuan2020.im.group.domain.GroupMemberQuery;
import com.ruyuan2020.im.group.service.GroupMemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author case
 */
@Slf4j
@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    private static final String GROUP_KEY_PREFIX = "GROUP";

    @Autowired
    private GroupMemberDAO groupMemberDAO;

    @DubboReference(version = "1.0.0")
    private AccountApi accountApi;

    @DubboReference(version = "1.0.0")
    private ChatApi chatApi;

    @Autowired
    private RedisTemplate<String, Long> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public BasePage<GroupMemberDTO> listByPage(GroupMemberQuery query) {
        return BeanCopierUtils.convert(groupMemberDAO.listByPage(query), GroupMemberDTO.class);
    }

    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void join(GroupDTO groupDTO) {
        Long memberId = SecurityUtils.getId();
        long count = groupMemberDAO.count(groupDTO.getId(), memberId);
        if (count > 0) {
            throw new BusinessException("已加入群组");
        }
        String key = "join_group_lock_" + groupDTO.getId();
        RLock lock = redissonClient.getLock(key);
        try {
            //加锁
            lock.lock();
            saveMember(groupDTO, memberId);
        } finally {
            //解锁
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setType(Constants.CHAT_TYPE_C2G);
        chatDTO.setMemberId(memberId);
        chatDTO.setPeerId(groupDTO.getId());
        chatDTO.setNickname(groupDTO.getName());
        // 添加一个会话
        chatApi.addChat(chatDTO);
    }

    public void saveMember(GroupDTO groupDTO, Long memberId) {
        long count = groupMemberDAO.count(groupDTO.getId());
        if (count >= 100) {
            throw new BusinessException("群成员已满");
        }
        log.info("群成员数量：{}", count);
        redisTemplate.delete(GROUP_KEY_PREFIX + "::" + groupDTO.getId());
        GroupMemberDO groupMemberDO = new GroupMemberDO();
        groupMemberDO.setGroupId(groupDTO.getId());
        groupMemberDO.setMemberId(memberId);
        groupMemberDAO.save(groupMemberDO);
    }

    @Override
    public List<Long> getByGroupId(Long groupId) {
        List<Long> cachedMemberIds = redisTemplate.opsForList().range(GROUP_KEY_PREFIX + "::" + groupId, 0, -1);
        if (CollectionUtils.isEmpty(cachedMemberIds)) {
            String key = "get_member" + groupId;
            RLock lock = redissonClient.getLock(key);
            try {
                //加锁
                lock.lock();
                log.info("锁住");
                cachedMemberIds = redisTemplate.opsForList().range(GROUP_KEY_PREFIX + "::" + groupId, 0, -1);
                if (CollectionUtils.isEmpty(cachedMemberIds)) {
                    cachedMemberIds = groupMemberDAO.getByGroupId(groupId);
                } else return cachedMemberIds;
                if (CollectionUtils.isNotEmpty(cachedMemberIds)) {
                    log.info("添加缓存");
                    // 添加到缓存
                    redisTemplate.opsForList().rightPushAll(GROUP_KEY_PREFIX + "::" + groupId, cachedMemberIds);
                }
            } finally {
                //解锁
                if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
        return cachedMemberIds;
    }

    @Override
    public BasePage<GroupMemberDTO> listGroupMember(GroupMemberQuery query) {
        Long accountId = SecurityUtils.getId();
        BasePage<Long> page = groupMemberDAO.listGroupMemberByPage(query, accountId);
        Map<Long, UserAccountDTO> userMap = accountApi.getByIds(page.getList()).stream().collect(Collectors.toMap(UserAccountDTO::getId, it -> it));
        return new BasePage<>(page.getList().stream().map(memberId -> {
            GroupMemberDTO groupMemberDTO = new GroupMemberDTO();
            groupMemberDTO.setMemberId(memberId);
            UserAccountDTO userAccountDTO = userMap.get(memberId);
            groupMemberDTO.setNickname(userAccountDTO.getNickname());
            groupMemberDTO.setAvatar(userAccountDTO.getAvatar());
            return groupMemberDTO;
        }).collect(Collectors.toList()), page.getPagination());

    }
}
