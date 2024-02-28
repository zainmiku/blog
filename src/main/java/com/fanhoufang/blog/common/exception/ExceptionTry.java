package com.fanhoufang.blog.common.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionTry {
    public static void handle(ExceptionalTask task) {
        try {
            task.execute();
        } catch (Exception e) {
            // 异常处理逻辑
            log.error("{}发生了异常",e.getMessage());
        }
    }
}