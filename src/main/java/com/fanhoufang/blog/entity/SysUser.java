package com.fanhoufang.blog.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author fan
 * @since 2023-02-11
 */
@Data
@Builder
public class SysUser {

    private String username;
    private String token;
    private String authorization;
    private String userId;
    private String certNo;
    private String code;

}
