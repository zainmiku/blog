package com.fanhoufang.blog.service;

import com.fanhoufang.blog.common.constant.CommonStatusCode;
import com.fanhoufang.blog.common.exception.ServiceException;
import com.fanhoufang.blog.entity.po.User;
import com.fanhoufang.blog.repository.UserRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {
    @Resource
    private UserRepository userRepository;

    public Boolean save(User user) {
        User userByUsername = userRepository.findUserByUsername(user.getUsername());
        if (Objects.nonNull(userByUsername)) {
            throw new ServiceException(CommonStatusCode._2003, "用户已存在");
        }
        userRepository.save(user);
        return true;
    }
}
