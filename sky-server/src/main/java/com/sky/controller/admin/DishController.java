package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
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
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    public Result<PageQueryVO> pageDish(DishPageQueryDTO dishPageQueryDTO){

        PageQueryVO pageQueryVO = dishService.pageDish(dishPageQueryDTO);

        return Result.success(pageQueryVO);
    }

    @PostMapping
    public Result addDish(@RequestBody DishDTO dishDTO){
        log.info("dishDto: {}",dishDTO);

        dishService.addDish(dishDTO);

        return Result.success();
    }

    @DeleteMapping
    public Result deleteDish(@RequestParam List<Long> ids){
        log.info("ids: {}",ids);
        dishService.deleteDish(ids);

        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<DishVO> selectById(@PathVariable Long id){
        log.info("id:{}",id);
        DishVO dishVO = dishService.selectById(id);

        return Result.success(dishVO);
    }

    @PutMapping
    public Result updateDish(@RequestBody DishDTO dishDTO){
        log.info("dishDTO:{}",dishDTO);

        dishService.updateDish(dishDTO);

        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result updateStatus(@PathVariable Integer status,Long id){
        log.info("status:{}  id: {}",status,id);

        dishService.updateStatus(status,id);

        return Result.success();
    }
}
