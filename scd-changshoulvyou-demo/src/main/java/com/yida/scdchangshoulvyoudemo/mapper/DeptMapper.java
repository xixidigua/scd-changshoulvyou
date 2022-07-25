package com.yida.scdchangshoulvyoudemo.mapper;


import com.yida.scdchangshoulvyoudemo.entity.Dept;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.Mapper;

public interface DeptMapper extends Mapper<Dept> {
    public Dept read(@RequestParam("id") Integer id);
}
