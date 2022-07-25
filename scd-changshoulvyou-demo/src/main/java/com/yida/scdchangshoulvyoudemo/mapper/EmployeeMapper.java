package com.yida.scdchangshoulvyoudemo.mapper;


import com.yida.scdchangshoulvyoudemo.entity.Employee;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface EmployeeMapper extends Mapper<Employee> {
    public  void add(Employee e);//增
    //删 该功能直接交通用mapper执行
    public void update(Employee e);//改
    public Employee readOne(Integer id);//查一个
    public List<Employee> readAll();//查所有
    public List<Employee> readList(@RequestParam("name") String name);//按条件查询
}
