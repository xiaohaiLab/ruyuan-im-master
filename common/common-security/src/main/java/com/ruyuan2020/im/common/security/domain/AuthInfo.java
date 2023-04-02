package com.ruyuan2020.im.common.security.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author case
 */
@Getter
@Setter
public class AuthInfo implements Serializable {

    private Long id;

    private List<String> priorities;
}
