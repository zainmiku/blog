package com.fanhoufang.blog.common.exception;

import com.fanhoufang.blog.common.constant.CommonStatusCode;
import com.fanhoufang.blog.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestControllerAdvice(basePackages = "com.fanhoufang.blog.controller")
public class GlobalExceptionHandler {


    /**
     * 运行时异常
     *
     * @param e 异常信息
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> errorHandle(RuntimeException e) {
        log.error("运行时异常,异常信息：", e);
        return Result.fail(CommonStatusCode._1003, e.getMessage());
    }


    /**
     * 编译期异常
     *
     * @param e 异常信息
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> errorHandle(Exception e) {
        log.error("编译期异常,异常信息：", e);
        return Result.fail(CommonStatusCode._1003, e.getMessage());
    }


    /**
     * 解析异常
     *
     * @param e 异常信息
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> errorHandle(HttpMessageNotReadableException e) {
        log.error("解析异常,异常信息：", e);
        return Result.fail(CommonStatusCode._2005);
    }

    /**
     * 请求类型异常 需要post，但请求是get
     *
     * @param e 异常信息
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> errorHandle(HttpRequestMethodNotSupportedException e) {
        log.error("请求类型异常,异常信息：", e);
        return Result.fail(CommonStatusCode._1004);
    }

    /**
     * 请求类型异常 需要json格式，但请求是表单
     *
     * @param e 异常信息
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> errorHandle(HttpMediaTypeNotSupportedException e) {
        log.error("请求类型异常,异常信息：", e);
        return Result.fail(CommonStatusCode._1004);
    }


    /**
     * sql异常
     *
     * @param e 异常信息
     */
    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> errorHandle(SQLException e) {
        log.error("sql异常,异常信息：", e);
        return Result.fail(CommonStatusCode._1009);
    }

    /**
     * 缺少请求参数。@RequestParam
     *
     * @param e 异常信息
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> errorHandle(MissingServletRequestParameterException e) {
        log.error("缺少请求参数,异常信息：", e);
        return Result.fail(CommonStatusCode._2005);
    }


    /**
     * 手动抛出异常
     *
     * @param e 异常信息
     */
    @ExceptionHandler(AppRuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> errorHandle(AppRuntimeException e) {
        log.error("手动抛出异常,异常信息：", e);
        return Result.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> errorHandle(BindException e) {
        List<FieldError> errorList = e.getFieldErrors();
        StringBuilder stringBuilder = getErrorMsg(errorList);
        log.error("异常信息：", e);
        return Result.fail(CommonStatusCode._1003.getCode(), stringBuilder.toString());
    }


    /**
     * 入参异常
     *
     * @param e 异常信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<String> errorHandle(MethodArgumentNotValidException e) {
        List<FieldError> errorList = e.getBindingResult().getFieldErrors();
        StringBuilder stringBuilder = getErrorMsg(errorList);
        log.error("入参异常,异常信息：", e);
        return Result.fail(CommonStatusCode._2015.getCode(), stringBuilder.toString());
    }

    private StringBuilder getErrorMsg(List<FieldError> errorList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError error : errorList) {
            stringBuilder
                    .append("【")
                    .append(error.getDefaultMessage())
                    .append("】")
                    .append("; ");
        }
        return stringBuilder;
    }
}
