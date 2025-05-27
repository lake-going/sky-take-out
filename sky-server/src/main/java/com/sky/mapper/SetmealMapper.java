package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealMapper {
    Page<Setmeal> pageSetmeal(SetmealPageQueryDTO setmealPageQueryDTO);

    void insertSetmeal(Setmeal setmeal);

    Setmeal selectById(Long id);

    void deleteById(Long id);

    void updateStatus(Integer status, Long id);
}
