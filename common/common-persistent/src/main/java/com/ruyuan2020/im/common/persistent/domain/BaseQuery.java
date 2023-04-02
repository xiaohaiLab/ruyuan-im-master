package com.ruyuan2020.im.common.persistent.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class BaseQuery implements Serializable {

    private Integer current = 1;

    private Integer pageSize = 10;

    private String sorter;
}
