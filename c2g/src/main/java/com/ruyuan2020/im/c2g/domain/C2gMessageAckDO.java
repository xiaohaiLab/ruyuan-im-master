package com.ruyuan2020.im.c2g.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruyuan2020.im.common.persistent.domain.BaseDO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("c2g_message_ack")
public class C2gMessageAckDO extends BaseDO {

    private Long chatId;

    private Long memberId;

    private Integer clientId;

    private Long lastAckMessageId;
}

