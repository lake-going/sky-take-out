package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
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
    @Autowired
    private SetmealDishMapper setmealDishMapper;

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

    @Transactional
    @Override
    public void deleteDish(List<Long> ids) {
        // 1、起售的菜品不能删
        ids.forEach(id -> {
            Dish dish = dishMapper.queryById(id);
            if (dish.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        });

        // 2、被套餐关联的菜品不能删
        ids.forEach(id->{
            List<SetmealDish> setmealDishList = setmealDishMapper.queryByDishId(id);
            if (!setmealDishList.isEmpty()){
                throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
            }
        });

        // 3、开始删除菜品
        ids.forEach(id->{
            dishMapper.deleteById(id);
        });

        // 4、开始删除口味
        ids.forEach(id->{
            dishFlavorMapper.deleteByDishId(id);
        });

    }

    @Override
    public DishVO selectById(Long id) {
        // 1、查询dish
        DishVO dishVO = dishMapper.selectById(id);

        // 2、查询flavor
        dishVO.setFlavors(dishFlavorMapper.selectByDishId(id));

        return dishVO;
    }

    @Transactional
    @Override
    public void updateDish(DishDTO dishDTO) {
        // 1、复制数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);

        // 2、update dish
        dishMapper.updateDish(dish);

        // 3、删除flavor,之后再加，flavor可能增加也可能减少，所以先删后增
        List<DishFlavor> dishFlavorList = dishDTO.getFlavors();

        if (!dishFlavorList.isEmpty()){
            dishFlavorList.forEach(dishFlavor -> {
                dishFlavor.setDishId(dish.getId());
            });
            dishFlavorMapper.deleteByDishId(dishDTO.getId());

            dishFlavorMapper.addDishFlavor(dishFlavorList);
        }
    }

    @Override
    public void updateStatus(Integer status, Long id) {
        // 1、调用mapper
        dishMapper.updateStatus(status,id);
    }

    @Override
    public List<Dish> selectByCategory(Integer categoryId) {
        List<Dish> dishVOList = dishMapper.selectByCategory(categoryId);
        return dishVOList;
    }
}
