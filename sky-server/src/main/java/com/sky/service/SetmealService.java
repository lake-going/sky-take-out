package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.vo.PageQueryVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetmealService {
    PageQueryVO pageSetmeal(SetmealPageQueryDTO setmealPageQueryDTO);

    void insertSetmeal(SetmealDTO setmealDTO);

    void deleteSetmeal(List<Long> ids);

    void updateStatus(Integer status, Long id);

    SetmealVO selectById(Long id);

    void updateSetmeal(SetmealDTO setmealDTO);

}
