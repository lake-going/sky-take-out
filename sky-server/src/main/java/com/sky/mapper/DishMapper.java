package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.anno.AutoFile;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishMapper {
    Page<DishVO> pageDish(DishPageQueryDTO dishPageQueryDTO);

    void addDish(Dish dish);

    void addDishFlavor(List<DishFlavor> dishFlavorList);
}
