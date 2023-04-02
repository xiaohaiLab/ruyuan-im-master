package com.ruyuan2020.im.common.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JsonResult<T> implements Serializable {

    private Boolean success;

    private String errorCode;

    private String errorMessage;

    private T data;
}
