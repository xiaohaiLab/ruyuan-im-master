package com.ruyuan2020.im.account.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruyuan2020.im.common.persistent.domain.BaseDO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author case
 */
@Getter
@Setter
@TableName("user_account")
public class UserAccountDO extends BaseDO {

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
}