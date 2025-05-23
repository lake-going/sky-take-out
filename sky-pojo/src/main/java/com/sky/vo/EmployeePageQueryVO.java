package com.sky.vo;

import com.sky.entity.Employee;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "员工分页查询返回的数据格式")
public class EmployeePageQueryVO implements Serializable {
    // 数据条数
    private Integer total;

    // 所有的employee数据
    private ArrayList<Employee> records;
}
