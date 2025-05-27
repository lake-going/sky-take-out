package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    void addDishFlavor(List<DishFlavor> dishFlavorList);

    void deleteByDishId(Long id);

    List<DishFlavor> selectByDishId(Long id);
}
