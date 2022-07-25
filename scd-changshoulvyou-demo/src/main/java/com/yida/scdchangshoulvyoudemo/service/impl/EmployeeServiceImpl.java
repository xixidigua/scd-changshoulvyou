package com.yida.scdchangshoulvyoudemo.service.impl;


import com.yida.scdchangshoulvyoudemo.entity.Employee;
import com.yida.scdchangshoulvyoudemo.mapper.EmployeeMapper;
import com.yida.scdchangshoulvyoudemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * service层 员工服务接口实现
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
   @Autowired(required = false)
    private EmployeeMapper employeeMapper;
    @Override
    public Boolean isValid(String name, String password) {
        Example em = new Example(Employee.class);
        Example.Criteria ca = em.createCriteria();
        ca.andEqualTo("name",name);
        ca.andEqualTo("password",password);
        List<Employee> list = employeeMapper.selectByExample(em);
        if (list == null || list.size() == 0) return false;
        return true;
    }

    @Override
    public void add(Employee e) {
        employeeMapper.add(e);
    }

    @Override
    public void delete(Integer id) {
    employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Employee e) {
   employeeMapper.update(e);
    }

    @Override
    public Employee readOne(Integer id) {
        return employeeMapper.readOne(id);
    }

    @Override
    public List<Employee> readAll() {
        return employeeMapper.readAll();
    }

    @Override
    public List<Employee> readList(String name) {
        String tmpName= name.trim();
        if(!tmpName.equals("")){
            tmpName="%"+tmpName+"%";
            return employeeMapper.readList(tmpName);
        }
        return readAll();
    }
}
