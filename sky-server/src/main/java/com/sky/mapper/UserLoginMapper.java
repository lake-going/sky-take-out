package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginMapper {
    User queryByOpenId(String openId);

    void createUser(User user);

    User queryById(Long currentId);
}
