package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @param
 * @return
 */

@Slf4j
@Component
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    @Scheduled(cron = "0 0/1 * * * ?")
    public Result processOutTimeOrder(){
        log.info("{}开始执行order扫描", LocalDateTime.now());

        // 查，订单生成时间已经超过15分钟且状态为未支付的数据
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(15);
        List<Orders> orders = orderMapper.queryOutTimeOrder(localDateTime, Orders.PENDING_PAYMENT);

        // 将数据状态修改为已失效
        if (orders!= null){
            orders.forEach(order -> {
                order.setStatus(Orders.CANCELLED);
                order.setCancelTime(LocalDateTime.now());
                order.setCancelReason("到期未付，自动取消");
                orderMapper.updateStatus(order);
            });
        }

        return Result.success();
    }
}
