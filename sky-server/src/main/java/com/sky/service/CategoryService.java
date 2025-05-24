package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.Result;
import com.sky.vo.PageQueryVO;
import org.springframework.stereotype.Service;


public interface CategoryService {
    PageQueryVO pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    void addCategory(CategoryDTO categoryDTO);

}
