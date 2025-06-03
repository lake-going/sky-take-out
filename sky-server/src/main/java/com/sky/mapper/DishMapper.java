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
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {
    Page<DishVO> pageDish(DishPageQueryDTO dishPageQueryDTO);

    @AutoFile(OperationType.INSERT)
    void addDish(Dish dish);

    Dish queryById(Long id);

    void deleteById(Long id);

    DishVO selectById(Long id);

    @AutoFile(OperationType.UPDATE)
    void updateDish(Dish dish);

    @AutoFile(OperationType.UPDATE)
    void updateStatus(Dish dish);

    List<Dish> selectByCategory(Integer categoryId);

    @Select("select * from dish where category_id = #{categoryId} and status = #{status}")
    List<Dish> list(Dish dish);

    /**
     * 根据条件统计菜品数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
