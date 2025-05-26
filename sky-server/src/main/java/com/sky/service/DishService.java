package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.vo.PageQueryVO;

public interface DishService {
    PageQueryVO pageDish(DishPageQueryDTO dishPageQueryDTO);

    void addDish(DishDTO dishDTO);

}
