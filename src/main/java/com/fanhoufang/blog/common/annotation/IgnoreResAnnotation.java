package com.fanhoufang.blog.common.annotation;


import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * 不返回Res结果类
 * @author Lixx
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface IgnoreResAnnotation {

}
