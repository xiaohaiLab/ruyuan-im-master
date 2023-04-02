package com.ruyuan2020.im.common.core.exception;

import com.ruyuan2020.im.common.core.exception.BaseException;

public class NotFoundException extends BaseException {

    private static final long serialVersionUID = 1L;

    public NotFoundException() {
        super("找不到资源", null);
    }
}
