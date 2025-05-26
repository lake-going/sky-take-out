package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.*;
import com.sky.mapper.EmployeeMapper;
import com.sky.service.EmployeeService;
import com.sky.vo.PageQueryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        // 拷贝数据，补充缺失的数据
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
        employee.setStatus(StatusConstant.ENABLE);

        // 获取thread中的empid
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        // 插入数据
        employeeMapper.addEmployee(employee);
    }

    @Override
    public PageQueryVO pageQueryEmployee(EmployeePageQueryDTO employeePageQueryDTO) {
        // 使用page helper
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());

        // 调用mapper查询数据
        List<Employee> employees = employeeMapper.pageQueryEmployee(employeePageQueryDTO);

        // 将数据封装进EmployeePageQueryVO
        Page p = (Page) employees;

        return new PageQueryVO(p.getTotal(),employees);
    }

    @Override
    public void changeStatus(Integer status, Long id) {
        //1、使用mapper方法，修改账户状态
        employeeMapper.changeStatus(status,id);
    }

    @Override
    public Employee queryById(Long id) {
        // 1、调用mapper查询用户信息
        return employeeMapper.queryById(id);
    }

    @Override
    public void updateEmployee(EmployeeDTO employeeDTO) {
        // 1、copy属性值
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);

        // 2、补充数据
//        employee.setUpdateUser(BaseContext.getCurrentId());
//        employee.setUpdateTime(LocalDateTime.now());

        // 3、修改用户信息
        employeeMapper.updateEmployee(employee);
    }

}
