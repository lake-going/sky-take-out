package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import com.sky.vo.PageQueryVO;

import java.util.List;

public interface DishService {
    PageQueryVO pageDish(DishPageQueryDTO dishPageQueryDTO);

    void addDish(DishDTO dishDTO);

    void deleteDish(List<Long> ids);

    DishVO selectById(Long id);

    void updateDish(DishDTO dishDTO);
}
