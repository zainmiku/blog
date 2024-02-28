package com.fanhoufang.blog.repository;

import com.fanhoufang.blog.entity.po.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findUserByUsername(String username);
}