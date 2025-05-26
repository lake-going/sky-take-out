package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.vo.PageQueryVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryService {
    PageQueryVO pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    void addCategory(CategoryDTO categoryDTO);

    void deleteCategory(Integer id);

    void updateCategory(CategoryDTO categoryDTO);

    void updateStatus(Integer status,Integer id);

    List<Category> queryByType(Integer type);

}
