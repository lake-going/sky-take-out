package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.PageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public Result insertSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("");

        setmealService.insertSetmeal(setmealDTO);

        return Result.success();
    }

    @DeleteMapping
    public Result deleteSetmeal(@RequestParam List<Long> ids){
        log.info("ids:{}",ids);

        setmealService.deleteSetmeal(ids);

        return Result.success();
    }
}
