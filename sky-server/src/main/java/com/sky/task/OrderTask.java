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
    public void processOutTimeOrder(){
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
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder(){
        log.info("{}开始执行送达状态扫描",LocalDateTime.now());

        // 查询订单,送达状态未完成的
        LocalDateTime localDateTime = LocalDateTime.now().minusHours(2);
        List<Orders> ordersList = orderMapper.queryOutTimeOrder(localDateTime,Orders.DELIVERY_IN_PROGRESS);

        // 将数据修改为已送达
        if (ordersList!= null){
            ordersList.forEach(order -> {
                order.setStatus(Orders.COMPLETED);
                order.setDeliveryTime(LocalDateTime.now());
                orderMapper.updateDeliveryStatus(order);
            });
        }
    }
}
