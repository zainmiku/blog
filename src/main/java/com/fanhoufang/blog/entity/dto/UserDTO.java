package com.fanhoufang.blog.entity.dto;

import java.util.List;

import com.fanhoufang.blog.entity.po.Blog;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author fan
 * @since 2023-02-11
 */
@Data
public class UserDTO {

    private Integer userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private List<Blog> blogList;

}
