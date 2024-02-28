package com.fanhoufang.blog.common.lang;


import com.fanhoufang.blog.common.constant.CommonStatusCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private int code;
    private String message;
    private T data;

    public static <T> Result<T> success(int code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(msg);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(T data) {
        return success((CommonStatusCode.SUCCESS.getCode()), CommonStatusCode.SUCCESS.getDesc(), data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> fail(int code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(msg);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(String msg, T data) {
        return fail(CommonStatusCode._1010.getCode(), msg, data);
    }


    public static <T> Result<T> fail(int code, String msg) {
        return fail(code, msg, null);
    }

    public static <T> Result<T> fail(String msg) {
        return fail(CommonStatusCode._1010.getCode(), msg, null);
    }


    public static <T> Result<T> fail(CommonStatusCode code) {
        return fail(code.getCode(), code.getDesc(), null);
    }

    public static <T> Result<T> fail(CommonStatusCode code, String message) {
        return fail(code.getCode(), message, null);
    }

}
