package com.fanhoufang.blog.serviceImpl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanhoufang.blog.dao.entity.User;
import com.fanhoufang.blog.dao.mapper.UserMapper;
import com.fanhoufang.blog.service.UserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fan
 * @since 2023-02-07 09:34:02
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
