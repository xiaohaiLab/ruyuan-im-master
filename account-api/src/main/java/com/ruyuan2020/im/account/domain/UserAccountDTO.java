package com.ruyuan2020.im.account.domain;

import com.ruyuan2020.im.common.core.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author case
 */
@Getter
@Setter
public class UserAccountDTO extends BaseDomain {

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String mobile;

    private String avatar;

    /**
     * 账号状态
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;
}