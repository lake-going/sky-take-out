package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.mapper.SetmealMapper;
import com.sky.service.SetmealService;
import com.sky.vo.PageQueryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @param
 * @return
 */
@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
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
}
