package com.ruyuan2020.im.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruyuan2020.im.chat.domain.ChatDO;
import com.ruyuan2020.im.chat.domain.ChatMemberDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author case
 */
@Mapper
public interface ChatMemberMapper extends BaseMapper<ChatMemberDO> {

}