package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.anno.AutoFile;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealMapper {
    Page<Setmeal> pageSetmeal(SetmealPageQueryDTO setmealPageQueryDTO);

    @AutoFile(OperationType.INSERT)
    void insertSetmeal(Setmeal setmeal);

    Setmeal selectById(Long id);

    void deleteById(Long id);

    @AutoFile(OperationType.UPDATE)
    void updateStatus(Integer status, Long id);
}
