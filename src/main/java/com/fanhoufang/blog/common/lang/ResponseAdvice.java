package com.fanhoufang.blog.common.lang;

import com.fanhoufang.blog.common.annotation.IgnoreResAnnotation;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Objects;


@RestControllerAdvice(basePackages = "com.fanhoufang.blog.controller")
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 是否开启功能 true：是
     */
    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 处理返回结果
     */
    @SneakyThrows
    @Override
    public Object beforeBodyWrite(@Nullable Object o, MethodParameter methodParameter, @NonNull MediaType mediaType, @NonNull Class<? extends HttpMessageConverter<?>> aClass, @NonNull ServerHttpRequest serverHttpRequest, @NonNull ServerHttpResponse serverHttpResponse) {

        if (Objects.requireNonNull(methodParameter.getMethod()).isAnnotationPresent(IgnoreResAnnotation.class)) {
            return o;
        }
        if (Objects.isNull(o)) {
            return Result.success();
        }
        //处理数据，如果Controller返回Result的话，SpringBoot是直接返回.
        if (o instanceof Result) {
            return o;
        }

        //处理字符串类型数据，如果Controller返回String的话,进行处理
        if (o instanceof String msg) {
            return objectMapper.writeValueAsString(Result.success(msg));
        }
        return Result.success(o);
    }
}