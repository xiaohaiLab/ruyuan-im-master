package com.ruyuan2020.im.common.im.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author case
 */
@Getter
@Setter
public class MessageJsonFetch {

    private Long chatId;

    private Long toId;

    private Integer chatType;
}
