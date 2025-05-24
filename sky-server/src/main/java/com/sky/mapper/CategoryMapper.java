package com.sky.mapper;

import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    void addCategory(Category category);

    void deleteCategory(Integer id);

    void updateCategory(Category category);
}
