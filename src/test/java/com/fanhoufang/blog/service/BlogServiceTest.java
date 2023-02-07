package com.fanhoufang.blog.service;

import org.junit.jupiter.api.Test;

import com.fanhoufang.blog.BlogApplicationTests;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlogServiceTest extends BlogApplicationTests{
    @Resource
    private UserService userService;
    @Test
    void test(){
        long count = userService.count();
        log.info("count={}",count);
    }
}
