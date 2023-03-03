package com.fanhoufang.blog.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fanhoufang.blog.entity.po.User;
import com.fanhoufang.blog.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fan
 * @since 2023-02-11
 */
@Tag(name = "用户")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("test")
    public String test(){
        return "test ok!";
    }
    @GetMapping("getUser")
    public List<User> getUser(){
        return userService.list();
    }
    @GetMapping("getOne")
    public User getOne(Integer userId){
        return userService.getOne(userId);
    }
    
    @PostMapping("register")
    public User register(@RequestBody User user){
        return userService.register(user);
    }
    
    @PostMapping("changePassword")
    public String changePassword(@RequestBody User user){
        return userService.changePassword(user);
    }

}

