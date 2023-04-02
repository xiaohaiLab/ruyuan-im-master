package com.ruyuan2020.im.route.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author case
 */
@Getter
@Setter
public class Session implements Serializable {

    private String gatewayId;

    private Long userId;

    private Integer client;

    private LocalDateTime timestamp;
}
