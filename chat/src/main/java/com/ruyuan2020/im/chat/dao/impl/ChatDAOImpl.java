package com.ruyuan2020.im.chat.dao.impl;

import com.ruyuan2020.im.chat.dao.ChatDAO;
import com.ruyuan2020.im.chat.domain.ChatDO;
import com.ruyuan2020.im.common.web.dao.MybatisPlusDAOImpl;
import com.ruyuan2020.im.chat.mapper.ChatMapper;
import org.springframework.stereotype.Repository;

/**
 * @author case
 */
@Repository
public class ChatDAOImpl extends MybatisPlusDAOImpl<ChatMapper, ChatDO> implements ChatDAO {

}