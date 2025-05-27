package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import com.sky.vo.PageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @param
 * @return
 */

@Slf4j
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public PageQueryVO pageDish(DishPageQueryDTO dishPageQueryDTO) {
        // 1、使用page helper
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());

        // 2、查询结果
        Page<DishVO> page = (Page) dishMapper.pageDish(dishPageQueryDTO);

        // 3、封装结果
        PageQueryVO pageQueryVO = new PageQueryVO(page.getTotal(),page.getResult());
        return pageQueryVO;
    }

    @Override
    @Transactional
    public void addDish(DishDTO dishDTO) {
        // 1、复制数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        // 2、调用mapper
        dishMapper.addDish(dish);
        log.info("dish_id = {}",dish.getId());

        // 3、插入id字段
        List<DishFlavor> dishFlavorList = dishDTO.getFlavors();

        // 4、调用mapper
        dishFlavorList.forEach(dishFlavor -> dishFlavor.setDishId(dish.getId()));
        dishFlavorMapper.addDishFlavor(dishFlavorList);
    }
}
