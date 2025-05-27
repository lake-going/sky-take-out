package com.sky.mapper;

import com.sky.anno.AutoFile;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> pageQueryCategory(CategoryPageQueryDTO categoryPageQueryDTO);

    @AutoFile(OperationType.INSERT)
    void addCategory(Category category);

    void deleteCategory(Integer id);

    @AutoFile(OperationType.UPDATE)
    void updateCategory(Category category);

    @AutoFile(OperationType.UPDATE)
    void updateStatus(Category category);

    List<Category> queryByType(Integer type);
}
