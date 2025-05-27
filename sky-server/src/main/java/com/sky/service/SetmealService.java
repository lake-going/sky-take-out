package com.sky.service;

import com.sky.dto.SetmealPageQueryDTO;
import com.sky.vo.PageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
public interface SetmealService {
    PageQueryVO pageSetmeal(SetmealPageQueryDTO setmealPageQueryDTO);
}
