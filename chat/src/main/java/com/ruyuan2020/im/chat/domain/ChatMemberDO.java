package com.ruyuan2020.im.chat.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruyuan2020.im.common.persistent.domain.BaseDO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author case
 */
@Getter
@Setter
@TableName("chat_member")
public class ChatMemberDO extends BaseDO {

    private Long chatId;

    private Integer type;

    private Long peerId;

    private Long memberId;

    private String nickname;

    private String avatar;

    private LocalDateTime activeTime;
}
