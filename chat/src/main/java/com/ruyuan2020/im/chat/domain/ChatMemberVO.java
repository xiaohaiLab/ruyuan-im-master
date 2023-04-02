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
public class ChatMemberVO extends BaseDomain {

    private Long id;

    private Integer type;

    private Long peerId;

    private Long memberId;

    private String nickname;

    private String avatar;

    private LocalDateTime activeTime;
}
