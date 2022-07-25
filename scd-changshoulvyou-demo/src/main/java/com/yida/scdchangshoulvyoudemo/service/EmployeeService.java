package com.yida.scdchangshoulvyoudemo.service;


import com.yida.scdchangshoulvyoudemo.entity.Employee;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface EmployeeService {
   //检测用户名是否有效
    public Boolean isValid(String nane, String password);
    public  void add(Employee e);//增
    public void delete(Integer id);//删
    public void update(Employee e);//改
    public Employee readOne(Integer id);//查一个
    public List<Employee> readAll();//查所有
    public List<Employee> readList(@RequestParam("name") String name);//按条件查询
}
