package com.fanhoufang.blog.common.config;

import com.fanhoufang.blog.common.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 跨域配置
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有接口
                .allowCredentials(true) // 是否发送 Cookie
                .allowedOriginPatterns("*") // 支持域
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // 支持方法
                .allowedHeaders("*")
                .exposedHeaders("*");
    }

    /**
     * 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/**") // 拦截所有请求
//                .addPathPatterns("/v1/report/**") // 拦截所有请求
//                .addPathPatterns("/test/**") // 拦截所有请求
                .excludePathPatterns("/login/*")
                .excludePathPatterns("/test/save")
                .excludePathPatterns("/doc.html/**")
                .excludePathPatterns("/doc.html")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/favicon.ico")
                .excludePathPatterns("/swagger-resources")
                .excludePathPatterns("/v3/api-docs/*")
                .excludePathPatterns("/v3/api-docs");
    }
}