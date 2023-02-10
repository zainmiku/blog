package com.fanhoufang.blog.serviceImpl;

import com.fanhoufang.blog.entity.po.User;
import com.fanhoufang.blog.mapper.UserMapper;
import com.fanhoufang.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fan
 * @since 2023-02-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
