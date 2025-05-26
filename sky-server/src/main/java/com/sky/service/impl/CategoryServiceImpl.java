package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
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

    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        // 1、复制数据
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
        // 2、补充数据
        category.setStatus(StatusConstant.DISABLE);
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
//        category.setCreateUser(BaseContext.getCurrentId());
//        category.setUpdateUser(BaseContext.getCurrentId());
        // 3、调用mapper增加数据
        categoryMapper.addCategory(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        // 1、调用mapper删除数据
        categoryMapper.deleteCategory(id);
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        // 1、补充数据
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);
//        category.setUpdateUser(BaseContext.getCurrentId());
//        category.setUpdateTime(LocalDateTime.now());

        // 2、插入数据
        categoryMapper.updateCategory(category);
    }

    @Override
    public void updateStatus(Integer status,Integer id) {
        categoryMapper.updateStatus(status,id);
    }

    @Override
    public List<Category> queryByType(Integer type) {
        List<Category> categoryList = categoryMapper.queryByType(type);
        return categoryList;
    }
}
