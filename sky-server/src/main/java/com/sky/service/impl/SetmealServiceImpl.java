package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.SetmealService;
import com.sky.vo.PageQueryVO;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @param
 * @return
 */
@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Override
    public PageQueryVO pageSetmeal(SetmealPageQueryDTO setmealPageQueryDTO) {
        // 1、设置page helper
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());

        // 2、查询
        Page page = (Page) setmealMapper.pageSetmeal(setmealPageQueryDTO);

        // 3、封装pagequeryvo
        PageQueryVO pageQueryVO = new PageQueryVO(page.getTotal(),page.getResult());
        return pageQueryVO;
    }

    @Transactional
    @Override
    public void insertSetmeal(SetmealDTO setmealDTO) {
        // 1、整理数据，插入setmeal表
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO,setmeal);
        setmealMapper.insertSetmeal(setmeal);


        // 2、整理数据，插入setmeal_dish表
        List<SetmealDish> setmealDishList = setmealDTO.getSetmealDishes();
        setmealDishList.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmeal.getId());
        });
        setmealDishMapper.insertSetmeal(setmealDishList);
    }

    @Transactional
    @Override
    public void deleteSetmeal(List<Long> ids) {
        // 1、起售的套餐不能删
        ids.forEach(id ->{
            Setmeal setmeal = setmealMapper.selectById(id);
            if (setmeal.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        });


        // 1、删除setmeal表
        ids.forEach(id ->{
            setmealMapper.deleteById(id);
        });

        // 2、删除setmeal dish表
        ids.forEach(id ->{
            setmealDishMapper.deleteBySetmealId(id);
        });
    }

    @Override
    public void updateStatus(Integer status, Long id) {
        setmealMapper.updateStatus(status,id);
    }

    @Override
    public SetmealVO selectById(Long id) {
        // 1、查setmeal
        Setmeal setmeal = setmealMapper.selectById(id);

        // 2、查setmealdish
        List<SetmealDish> setmealDishList = setmealDishMapper.queryByDishId(id);


        // 3、组装
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal,setmealVO);
        setmealVO.setSetmealDishes(setmealDishList);

        return setmealVO;
    }

    @Override
    public void updateSetmeal(SetmealDTO setmealDTO) {

    }

}
