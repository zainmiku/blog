package com.fanhoufang.blog.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fanhoufang.blog.common.constant.ReturnCode;
import com.fanhoufang.blog.common.exception.BusinessException;
import com.fanhoufang.blog.dao.entity.User;
import com.fanhoufang.blog.service.UserService;

import jakarta.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fan
 * @since 2023-02-07 09:34:02
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("test")
    public String test(){
        return "test ok!";
    }
    @GetMapping("test2")
    public String test2(){
       throw new BusinessException(ReturnCode.UNSUPPORTED_GRANT_TYPE);
    }
    @GetMapping("getUser")
    public List<User> getUser(){
       return userService.list();
    }

}

