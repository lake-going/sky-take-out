package com.sky.controller.admin;

import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.PageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @param
 * @return
 */

@Slf4j
@RestController
@RequestMapping("/admin/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    @GetMapping("/page")
    public Result<PageQueryVO> pageSetmeal(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("setmealPageQueryDTO: {}",setmealPageQueryDTO);

        PageQueryVO pageQueryVO = setmealService.pageSetmeal(setmealPageQueryDTO);

        return Result.success(pageQueryVO);
    }
}
