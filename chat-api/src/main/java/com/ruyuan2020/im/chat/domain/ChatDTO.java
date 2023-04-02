package com.ruyuan2020.im.chat.domain;

import com.ruyuan2020.im.common.core.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author case
 */
@Getter
@Setter
public class ChatDTO extends BaseDomain {

    private Long memberId;

    private Long peerId;

    private String nickname;

    /**
     * 1-好友
     * 2-群组
     */
    private Integer type;

    private String avatar;
}
