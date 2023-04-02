package com.ruyuan2020.im.chat.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruyuan2020.im.common.persistent.domain.BaseDO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author case
 */
@TableName("chat")
@Getter
@Setter
public class ChatDO extends BaseDO {

    private Integer type;
}
