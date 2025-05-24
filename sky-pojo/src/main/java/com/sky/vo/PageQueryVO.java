package com.sky.vo;

import com.sky.entity.Employee;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分页查询返回的数据格式")
public class PageQueryVO implements Serializable {
    // 数据条数
    private Long total;

    // 所有的数据
    private List records;
}
