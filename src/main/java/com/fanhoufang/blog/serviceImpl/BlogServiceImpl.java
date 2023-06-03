package com.fanhoufang.blog.serviceImpl;

import com.fanhoufang.blog.entity.po.Blog;
import com.fanhoufang.blog.mapper.BlogMapper;
import com.fanhoufang.blog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fan
 * @since 2023-06-03
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
