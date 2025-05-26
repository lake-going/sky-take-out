package com.sky.mapper;

import com.sky.anno.AutoFile;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @AutoFile(OperationType.INSERT)
    void addEmployee(Employee employee);

    List<Employee> pageQueryEmployee(EmployeePageQueryDTO employeePageQueryDTO);

    @AutoFile(OperationType.UPDATE)
    void changeStatus(Integer status, Long id);

    Employee queryById(Long id);

    @AutoFile(OperationType.UPDATE)
    void updateEmployee(Employee employee);
}
