package com.fanhoufang.blog.entity.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author fan
 * @since 2023-06-03
 */
@Data
@TableName("blog.blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "blog_id", type = IdType.AUTO)
    private Integer blogId;

    private Integer userId;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 正文
     */
    private String text;


}
