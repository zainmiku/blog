package com.fanhoufang.blog.common.utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fanhoufang.blog.entity.po.User;

public class JWTUtils {

    /**
     * 密钥配置文件获取
     */
    private static String secret = "54354325rffsd";
    @Value("${secret}")
    public void setSecret(String secret){
        JWTUtils.secret = secret;
    }

 
    /**
     * 传入payload信息获取token
     * @param map payload
     * @return token
     */
    public static String getToken(User user) {
        Map<String, Object> map = new HashMap<>();

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 1);//默认3天过期

        String token = JWT.create().withHeader(map) //header
                .withClaim("userId", user.getUserId())//payload
                .withClaim("username", user.getUsername())//payload
                .withExpiresAt(instance.getTime())//指定令牌的过期时间
                .sign(Algorithm.HMAC256(secret)) //签名
                ;
                return token;

    }
 
    /**
     * 验证token 合法性
     */
    public static DecodedJWT verify(String token) {
        //如果有任何验证异常，此处都会抛出异常
        return JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
    }
 
    /**
     * 获取token信息方法
     */
    public static User getTokenInfo(String token) {

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        User user = new User();
        user.setUserId(decodedJWT.getClaim("userId").asInt());
        user.setUsername(decodedJWT.getClaim("username").asString());
        return user;
    }

}
