package com.ruyuan2020.im.common.web.config;


import com.ruyuan2020.im.common.core.exception.BusinessException;
import com.ruyuan2020.im.common.core.domain.JsonResult;
import com.ruyuan2020.im.common.security.exception.BaseSecurityException;
import com.ruyuan2020.im.common.core.exception.NotFoundException;
import com.ruyuan2020.im.common.core.util.ResultHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.xml.bind.ValidationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionConfig {

    @ExceptionHandler(BaseSecurityException.class)
    public void handleException(BaseSecurityException e) {
        throw e;
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public JsonResult<?> handleBusinessException(BusinessException e) {
        return ResultHelper.fail(String.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public JsonResult<?> handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return ResultHelper.fail(String.valueOf(HttpStatus.BAD_REQUEST.value()), "参数验证失败");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public JsonResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResultHelper.fail(String.valueOf(HttpStatus.BAD_REQUEST.value()), "数据验证错误");
    }

    @ExceptionHandler(value = {
            MissingServletRequestParameterException.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(value = HttpStatus.OK)
    public JsonResult<?> handleIllegalArgumentException() {
        return ResultHelper.fail(String.valueOf(HttpStatus.BAD_REQUEST.value()), "参数解析失败");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public JsonResult<?> handleHttpRequestMethodNotSupportedException() {
        return ResultHelper.fail(String.valueOf(HttpStatus.METHOD_NOT_ALLOWED.value()), "不支持当前请求方法");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public JsonResult<?> handleHttpMediaTypeNotSupportedException() {
        return ResultHelper.fail(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()), "不支持的媒体类型");
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public JsonResult<?> handleNotFoundException(NotFoundException e) {
        return ResultHelper.fail(String.valueOf(HttpStatus.NOT_FOUND.value()), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public JsonResult<?> handleAllException(Throwable e) {
        log.error(e.getMessage(), e);
        return ResultHelper.fail(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "服务器错误");
    }
}
