package com.fanhoufang.blog.serviceImpl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanhoufang.blog.entity.po.User;
import com.fanhoufang.blog.mapper.UserMapper;
import com.fanhoufang.blog.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author fan
 * @since 2023-02-11
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    @Cacheable("user")
    public User getOne(Integer userId) {
        User user = this.getById(userId);
        return user;
    }

    @Override
    @CachePut(value = "user", key = "#user.userId")
    public User register(User user) {
        this.save(user);
        log.info(user.toString());
        return user;
    }

    @Override
    @CacheEvict(value = "user", key = "#user.userId")
    public String changePassword(User user) {
        this.lambdaUpdate().set(User::getPassword, user.getPassword()).eq(User::getUserId, user.getUserId()).update();
        return "修改成功";
    }

}
