package com.ruyuan2020.im.common.im.domain;

import com.ruyuan2020.im.common.core.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * @author case
 */
@Getter
@Setter
public class MessageAckJsonRequest extends BaseDomain {

    private Integer chatType;

    private Integer clientId;

    private Long chatId;

    private Long memberId;

    private Long messageId;
}
