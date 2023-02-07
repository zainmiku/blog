package com.fanhoufang.blog.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnCode {
    /**操作成功**/
    OK(0,"操作成功"),
    /**操作失败**/
    CD999(999,"操作失败"),
    /**不支持的认证模式 */
    UNSUPPORTED_GRANT_TYPE(1003, "不支持的认证模式");



    /**自定义状态码**/
    private final int code;
    /**自定义描述**/
    private final String msg;
}
