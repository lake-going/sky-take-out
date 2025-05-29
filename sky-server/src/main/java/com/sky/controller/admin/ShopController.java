package com.sky.controller.admin;

import com.sky.anno.AutoFile;
import com.sky.constant.StatusConstant;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @param
 * @return
 */

@Slf4j
@RestController
@RequestMapping("/admin/shop")
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    public Result updateStatus(@PathVariable Integer status){
        log.info("status:{}",status);

        redisTemplate.opsForValue().set(StatusConstant.SHOP_STATUS,status);

        return Result.success();
    }

    @GetMapping("/status")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(StatusConstant.SHOP_STATUS);

        log.info("status:{}",status);

        return Result.success(status == null ? 0:status);
    }
}
