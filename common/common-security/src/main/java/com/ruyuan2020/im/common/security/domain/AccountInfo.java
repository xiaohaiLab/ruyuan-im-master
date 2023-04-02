package com.ruyuan2020.im.common.security.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author case
 */
@Getter
@Setter
public class AccountInfo extends AuthInfo {

    private String nickname;

    private String status;
}
