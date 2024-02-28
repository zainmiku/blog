package com.fanhoufang.blog.controller;


import com.fanhoufang.blog.entity.po.User;
import com.fanhoufang.blog.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    UserService userService;

    @GetMapping(value = "test2", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test2(String ajbs) {
        return ajbs + "1111";
    }


    @PostMapping(value = "save")
    public Boolean save(@RequestBody User user) {
        return userService.save(user);
    }


}
