package com.fanhoufang.blog.common.exception;


import com.fanhoufang.blog.common.constant.CommonStatusCode;
import lombok.Data;

/**
 * @author lixiaoxiang
 * @Description 自定义异常
 * @createTime 2021年12月24日 17:43:00
 */
@Data
public class AppRuntimeException extends RuntimeException {
    private Integer code;


    public AppRuntimeException(Throwable cause, int code, String msg) {
        super(msg, cause);
        this.code = code;
    }

    public AppRuntimeException(int code, String msg) {
        super(msg);
        this.code = code;
    }


    public AppRuntimeException(String msg) {
        this(CommonStatusCode._1010.getCode(), msg);
    }

    public AppRuntimeException(Throwable cause, String msg) {
        super(msg, cause);
        this.code = CommonStatusCode._1010.getCode();
    }

    public AppRuntimeException(CommonStatusCode codeMsg) {
        super(codeMsg.getDesc());
        this.code = codeMsg.getCode();
    }

}

