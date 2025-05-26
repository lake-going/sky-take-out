package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Category;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.vo.PageQueryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**分类管理
 * @param
 * @return
 */
@RestController
@Slf4j
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public Result<PageQueryVO> pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("CategoryPageQueryDTO: {}", categoryPageQueryDTO);

        PageQueryVO pageQueryVO = categoryService.pageQueryCategory(categoryPageQueryDTO);
        return Result.success(pageQueryVO);
    }

    @PostMapping
    public Result addCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("categoryDto: {}",categoryDTO);

        categoryService.addCategory(categoryDTO);

        return Result.success();
    }

    @DeleteMapping
    public Result deleteCategory(Integer id){
        log.info("id:{}",id);

        categoryService.deleteCategory(id);

        return Result.success();
    }

    @PutMapping
    public Result updateCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("categoryDTO:{}",categoryDTO);

        categoryService.updateCategory(categoryDTO);

        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result updateStatus(@PathVariable Integer status,Integer id){
        log.info("status:{}",status);

        categoryService.updateStatus(status,id);

        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<Category>> queryByType(Integer type){
        log.info("type: {}",type);

        List<Category> categoryList = categoryService.queryByType(type);

        return Result.success(categoryList);
    }
}
