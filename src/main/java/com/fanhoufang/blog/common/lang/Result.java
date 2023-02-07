package com.fanhoufang.blog.common.lang;



import java.io.Serializable;

import com.fanhoufang.blog.common.constant.ReturnCode;

import lombok.Data;

@Data
public class Result<T> implements Serializable {
    private int code;
    private String msg;
    private Object data;

    public static <T> Result<T> success(int code, String msg, T data){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    public static <T> Result<T> success(String msg) {
        Result<T> result = new Result<>();
        result.setCode(ReturnCode.OK.getCode());
        result.setMsg(msg);
        result.setData(null);
        return result;
      }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ReturnCode.OK.getCode());
        result.setMsg(ReturnCode.OK.getMsg());
        result.setData(data);
        return result;
      }


    public static <T> Result<T> fail(int code, String msg, Object data){
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    public static <T> Result<T> fail(String msg, Object data){

        return fail(ReturnCode.CD999.getCode(),msg,data);
    }
    public static <T> Result<T> fail(String msg){

        return fail(ReturnCode.CD999.getCode(),msg,null);
    }

}
