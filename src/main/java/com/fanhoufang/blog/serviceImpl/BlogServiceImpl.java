package com.fanhoufang.blog.serviceImpl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fanhoufang.blog.dao.entity.Blog;
import com.fanhoufang.blog.dao.mapper.BlogMapper;
import com.fanhoufang.blog.service.BlogService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fan
 * @since 2023-02-07 09:34:02
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
