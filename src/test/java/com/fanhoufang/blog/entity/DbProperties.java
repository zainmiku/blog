package com.fanhoufang.blog.entity;

import lombok.Data;

/**
 * @author fan
 * @Date 2022/7/14 12:05
 */
@Data
public class DbProperties {
    private String host;

    private String port;

    private String username;

    private String password;

    /**
     * 默认的数据库
     */
    private String defaultSchema;
}
