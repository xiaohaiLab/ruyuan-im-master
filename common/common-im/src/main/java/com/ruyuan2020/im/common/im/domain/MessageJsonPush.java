package com.ruyuan2020.im.common.im.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author case
 */
@Getter
@Setter
public class MessageJsonPush {

    private Long messageId;

    private Integer chatType;

    private Long fromId;

    private Long toId;

    private Long chatId;

    private Integer messageType;

    private String content;

    private Long sequence;

    private Long timestamp;
}
