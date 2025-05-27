package com.sky.mapper;

import com.sky.anno.AutoFile;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    List<SetmealDish> queryByDishId(Long id);

    void insertSetmeal(List<SetmealDish> setmealDishList);

    void deleteBySetmealId(Long id);
}
