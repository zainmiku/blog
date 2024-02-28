package com.fanhoufang.blog.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping(value = "test2", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test2(String ajbs) {
        return ajbs + "1111";
    }
}
