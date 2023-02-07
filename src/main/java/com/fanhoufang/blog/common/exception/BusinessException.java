package com.fanhoufang.blog.common.exception;

import com.fanhoufang.blog.common.constant.ReturnCode;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BusinessException extends RuntimeException{


    private int code;

    private String errMsg;

    public  BusinessException(ReturnCode reeurnCode){
        this.code = reeurnCode.getCode();
        this.errMsg = reeurnCode.getMsg();
    }

}
