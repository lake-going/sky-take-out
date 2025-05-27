package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.anno.AutoFile;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface DishMapper {
    Page<DishVO> pageDish(DishPageQueryDTO dishPageQueryDTO);

    @AutoFile(OperationType.INSERT)
    void addDish(Dish dish);

    Dish queryById(Long id);

    @AutoFile(OperationType.UPDATE)
    void deleteById(Long id);

    DishVO selectById(Long id);

    @AutoFile(OperationType.UPDATE)
    void updateDish(Dish dish);

    @AutoFile(OperationType.UPDATE)
    void updateStatus(Integer status, Long id);

    List<Dish> selectByCategory(Integer categoryId);
}
