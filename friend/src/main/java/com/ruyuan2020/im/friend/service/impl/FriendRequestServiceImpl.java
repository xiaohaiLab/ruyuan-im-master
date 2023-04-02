package com.ruyuan2020.im.friend.service.impl;

import com.ruyuan2020.im.account.api.AccountApi;
import com.ruyuan2020.im.account.domain.UserAccountDTO;
import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.common.security.domain.AccountInfo;
import com.ruyuan2020.im.common.core.exception.BusinessException;
import com.ruyuan2020.im.common.core.util.BeanCopierUtils;
import com.ruyuan2020.im.common.web.util.SecurityUtils;
import com.ruyuan2020.im.friend.dao.FriendRelationshipDAO;
import com.ruyuan2020.im.friend.dao.FriendRequestDAO;
import com.ruyuan2020.im.friend.domain.FriendRequestDO;
import com.ruyuan2020.im.friend.domain.FriendRequestDTO;
import com.ruyuan2020.im.friend.domain.FriendRequestQuery;
import com.ruyuan2020.im.friend.service.FriendRequestService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * @author case
 */
@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    @Autowired
    private FriendRequestDAO friendRequestDAO;

    @Autowired
    private FriendRelationshipDAO friendRelationshipDAO;

    @DubboReference(version = "1.0.0")
    private AccountApi accountApi;

    @Override
    @Transactional
    public void submit(FriendRequestDTO friendRequestDTO) {
        AccountInfo accountInfo = (AccountInfo) SecurityUtils.getAuthInfo();
        Long accountId = accountInfo.getId();
        UserAccountDTO userAccountDTO = accountApi.getByUsername(friendRequestDTO.getUsername());
        if (Objects.equals(userAccountDTO.getId(), accountId)) {
            throw new BusinessException("好友不能是自己");
        }
        FriendRequestDO friendRequestDO = new FriendRequestDO();
        friendRequestDO.setFriendId(accountId);
        friendRequestDO.setAccountId(userAccountDTO.getId());
        friendRequestDO.setNickname(accountInfo.getNickname());
        friendRequestDO.setContent(friendRequestDTO.getContent());
        friendRequestDO.setStatus(0);
        long count = friendRelationshipDAO.count(friendRequestDO.getAccountId(), friendRequestDO.getFriendId());
        if (count > 0) {
            throw new BusinessException("已经是好友了");
        }
        count = friendRequestDAO.count(friendRequestDO.getAccountId(), friendRequestDO.getFriendId());
        if (count > 0) {
            throw new BusinessException("已提交好友申请");
        }
        friendRequestDAO.save(friendRequestDO);
    }

    @Override
    public BasePage<FriendRequestDTO> list(FriendRequestQuery query) {
        Long accountId = SecurityUtils.getId();
        query.setAccountId(accountId);
        return BeanCopierUtils.convert(friendRequestDAO.list(query), FriendRequestDTO.class);
    }
}
