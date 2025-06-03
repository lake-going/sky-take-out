package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.HashMap;

@Mapper
public interface UserLoginMapper {
    User queryByOpenId(String openId);

    void createUser(User user);

    User queryById(Long currentId);

    Integer queryUserByData(HashMap<Object, Object> objectObjectHashMap);

    Integer queryTotalUserByData(LocalDateTime endTime);
}
