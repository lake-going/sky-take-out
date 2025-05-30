package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserLoginMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @param
 * @return
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserLoginMapper userLoginMapper;
    @Autowired
    private WeChatProperties weChatProperties;

    @Override
    public User userLogin(UserLoginDTO userLoginDTO) {
        // 1、httpclient给wechat发请求
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid",weChatProperties.getAppid());
        paramMap.put("secret",weChatProperties.getSecret());
        paramMap.put("js_code",userLoginDTO.getCode());
        paramMap.put("grand_type","authorization_code");
        String res = HttpClientUtil.doGet("https://api.weixin.qq.com/sns/jscode2session",paramMap);
        log.info("res:{}",res);

        // 将res解析成json格式
        JSONObject jsonObject = JSON.parseObject(res);
        String openId = jsonObject.getString("openid");
        if (openId == null){
            throw new LoginFailedException(MessageConstant.USER_LOGIN_FAIL);
        }
        log.info("openId:{}",openId);

        // 2、使用uuid查数据库，如果能查到，返回user，没查到先插入数据
        User user = userLoginMapper.queryByOpenId(openId);
        log.info("user:{}",user);

        if (user == null){
            user = new User();
            user.setOpenid(openId);
            user.setCreateTime(LocalDateTime.now());
            userLoginMapper.createUser(user);
        }
        log.info("user:{}",user);

        return user;
    }
}
