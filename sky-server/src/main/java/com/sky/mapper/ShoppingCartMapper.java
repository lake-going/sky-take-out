package com.sky.mapper;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    ShoppingCart queryById(ShoppingCart shoppingCart);

    void add(ShoppingCart shoppingCart);

    void updateShoppingCart(ShoppingCart shoppingCart);

    List<ShoppingCart> selectShoppingCart(Long userId);
}
