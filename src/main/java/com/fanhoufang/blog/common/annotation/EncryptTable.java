package com.fanhoufang.blog.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})//该参数代表是作用在类
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptTable {
    String value() default "";
}
