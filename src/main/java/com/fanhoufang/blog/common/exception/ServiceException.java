package com.fanhoufang.blog.common.exception;


import com.fanhoufang.blog.common.constant.CommonStatusCode;

/**
 * @author lixiaoxiang
 * @Description 业务异常
 * @createTime 2021年12月24日 17:42:00
 */
public class ServiceException extends AppRuntimeException {

    public ServiceException(Throwable cause, int code, String msg) {
        super(cause, code, msg);
    }

    public ServiceException(Integer code, String msg) {
        super(code, msg);
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(Throwable cause, String msg) {
        super(cause, msg);
    }

    public ServiceException(CommonStatusCode codeMsg) {
        super(codeMsg);
    }

    public ServiceException(CommonStatusCode codeMsg, String msg) {
        super(codeMsg.getCode(), msg);
    }
}
