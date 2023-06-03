package com.fanhoufang.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fanhoufang.blog.entity.po.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fan
 * @since 2023-02-11
 */
public interface UserService extends IService<User> {

    User getOne(Integer userId);

    User register(User user);
    
    String changePassword(User user);

    User login(User user) throws Exception;



}
