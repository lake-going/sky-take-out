package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import com.sky.vo.PageQueryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @param
 * @return
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public PageQueryVO pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO) {
        // 1、使用page helper
        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());

        // 2、调用mapper查询数据
        List<Category> categoryList = categoryMapper.pageQueryCategory(categoryPageQueryDTO);

        // 3、组装数据
        Page p = (Page) categoryList;
        PageQueryVO pageQueryVO = new PageQueryVO(p.getTotal(),categoryList);
        return pageQueryVO;
    }
}
