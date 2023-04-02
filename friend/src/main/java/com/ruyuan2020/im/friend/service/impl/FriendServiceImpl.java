package com.ruyuan2020.im.friend.service.impl;

import com.ruyuan2020.im.chat.api.ChatApi;
import com.ruyuan2020.im.chat.constant.ChatConstants;
import com.ruyuan2020.im.chat.domain.ChatDTO;
import com.ruyuan2020.im.common.core.domain.BasePage;
import com.ruyuan2020.im.common.im.constant.Constants;
import com.ruyuan2020.im.common.security.domain.AccountInfo;
import com.ruyuan2020.im.common.core.util.BeanCopierUtils;
import com.ruyuan2020.im.common.core.util.DateTimeUtils;
import com.ruyuan2020.im.common.web.util.SecurityUtils;
import com.ruyuan2020.im.friend.dao.FriendRequestDAO;
import com.ruyuan2020.im.friend.domain.*;
import com.ruyuan2020.im.friend.service.FriendService;
import com.ruyuan2020.im.friend.dao.FriendRelationshipDAO;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author case
 */
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRequestDAO friendRequestDAO;

    private final FriendRelationshipDAO friendRelationshipDAO;

    @DubboReference(version = "1.0.0")
    private ChatApi chatApi;

    @Override
    @Transactional
    public void addFriend(Long requestId) {
        Optional<FriendRequestDO> optional = friendRequestDAO.getById(requestId);
        if (optional.isPresent()) {
            FriendRequestDO friendRequestDO = optional.get();
            if (friendRequestDO.getStatus() == 0) {

                FriendRelationshipDO relationshipDO1 = new FriendRelationshipDO();
                relationshipDO1.setAccountId(friendRequestDO.getAccountId());
                relationshipDO1.setFriendId(friendRequestDO.getFriendId());
                relationshipDO1.setNickname(friendRequestDO.getNickname());
                addFriend(relationshipDO1);

                FriendRelationshipDO relationshipDO2 = new FriendRelationshipDO();
                relationshipDO2.setAccountId(friendRequestDO.getFriendId());
                relationshipDO2.setFriendId(friendRequestDO.getAccountId());
                AccountInfo accountInfo = (AccountInfo) SecurityUtils.getAuthInfo();
                relationshipDO2.setNickname(accountInfo.getNickname());
                addFriend(relationshipDO2);

                friendRequestDO.setStatus(1);
                friendRequestDAO.update(friendRequestDO);
            }
        }
    }

    @Override
    public BasePage<FriendRelationshipDTO> listByPage(FriendRelationshipQuery query) {
        Long accountId = SecurityUtils.getId();
        query.setAccountId(accountId);
        return BeanCopierUtils.convert(friendRelationshipDAO.listByPage(query), FriendRelationshipDTO.class);
    }

    private void addFriend(FriendRelationshipDO friendRelationshipDO) {
        friendRelationshipDAO.save(friendRelationshipDO);
        ChatDTO chatDTO = new ChatDTO();
        chatDTO.setType(Constants.CHAT_TYPE_C2C);
        chatDTO.setMemberId(friendRelationshipDO.getAccountId());
        chatDTO.setPeerId(friendRelationshipDO.getFriendId());
        chatDTO.setNickname(friendRelationshipDO.getNickname());
        chatApi.addChat(chatDTO);
    }
}
