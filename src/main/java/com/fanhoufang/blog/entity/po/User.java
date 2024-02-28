package com.fanhoufang.blog.entity.po;

import com.fanhoufang.blog.entity.Base;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;

/**
 * @author fan
 * @since 2023-02-11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class User extends Base {

    /**
     * 用户id
     */
    @Id
    private String userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户手机号
     */
    private String phone;

}
