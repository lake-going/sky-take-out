package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @param
 * @return
 */
@Slf4j
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    @Override
    @Transactional
    public void add(ShoppingCartDTO shoppingCartDTO) {
        // 复制数据
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);

        // 先查dish id，看是否已存在
        shoppingCart.setUserId(BaseContext.getCurrentId());
        shoppingCart.setCreateTime(LocalDateTime.now());
        ShoppingCart shoppingCartData = shoppingCartMapper.queryById(shoppingCart);


        // 不存在的话就插表
        if (shoppingCartData == null){ //说明购物车没有这个套餐
            if (shoppingCartDTO.getDishId() != null){ // 说明是菜品
                Dish dish = dishMapper.queryById(shoppingCartDTO.getDishId());
                shoppingCart.setNumber(1);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
                shoppingCartMapper.add(shoppingCart);
            }else {
                Setmeal setmeal = setmealMapper.selectById(shoppingCartDTO.getSetmealId());
                shoppingCart.setNumber(1);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
                shoppingCartMapper.add(shoppingCart);
            }

        }
        else { // 已经有数据了，需要更新数据
            shoppingCartData.setNumber(shoppingCartData.getNumber()+1);
            shoppingCartMapper.updateShoppingCart(shoppingCart);
        }
        //
    }

    @Override
    public List<ShoppingCart> selectShoppingCart(Long userId) {
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.selectShoppingCart(userId);
        return shoppingCartList;
    }

    @Override
    public void deleteShoppingCart(Long userId) {
        shoppingCartMapper.deleteShoppingCart(userId);

    }

    @Override
    public void deleteOneShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        // 先补齐数据，userid
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        // 删除
        shoppingCartMapper.deleteOneShoppingCart(shoppingCart);
    }
}
