package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.entity.*;
import com.sky.mapper.*;
import com.sky.service.OrderService;
import com.sky.service.UserService;
import com.sky.vo.OrderSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @param
 * @return
 */

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserLoginMapper UserLoginMapper;
    @Autowired
    private AddressBookMapper addressBookMapper;
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Transactional
    @Override
    public OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        // 复制数据
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersSubmitDTO,orders);
        // 补充数据
        orders.setNumber(System.currentTimeMillis()+"");
        orders.setUserId(BaseContext.getCurrentId());
        User user = UserLoginMapper.queryById(BaseContext.getCurrentId());
        if (user != null){
            orders.setUserName(user.getName());
        }
        orders.setOrderTime(LocalDateTime.now());
        orders.setPayStatus(Orders.UN_PAID);

        AddressBook addressBook = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if (addressBook != null){
            orders.setAddress(addressBook.getProvinceName()
                    +addressBook.getCityName()
            +addressBook.getDistrictName()
            +addressBook.getDetail());
            orders.setPhone(addressBook.getPhone());

            orders.setConsignee(addressBook.getConsignee());
        }


        // 插入order表
        orderMapper.submitOrder(orders);

        // 插入order detail表
        List<OrderDetail> orderDetailList = new ArrayList<>();
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.queryByUserId(BaseContext.getCurrentId());

        shoppingCartList.forEach(shoppingCart -> {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(shoppingCart,orderDetail);
            orderDetail.setOrderId(orders.getId());
            orderDetailList.add(orderDetail);
        });

        orderDetailMapper.submitOrderDetail(orderDetailList);

        // 清空购物车
        shoppingCartMapper.deleteShoppingCart(BaseContext.getCurrentId());

        // 构造返回数据
        OrderSubmitVO orderSubmitVO = new OrderSubmitVO();
        orderSubmitVO.setId(orders.getId());
        orderSubmitVO.setOrderTime(orders.getOrderTime());
        orderSubmitVO.setOrderNumber(orders.getNumber());
        orderSubmitVO.setOrderAmount(orders.getAmount());

        return orderSubmitVO;
    }
}
