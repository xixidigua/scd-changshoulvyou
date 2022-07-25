package com.yida.scdchangshoulvyoudemo.mapper;


import com.yida.scdchangshoulvyoudemo.entity.ScenicAreaConsult;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ScenicAreaConsultMapper extends Mapper<ScenicAreaConsult> {
     public  void  add(ScenicAreaConsult s);//增
    //删 该功能使用通用mapper的功能
    public List<ScenicAreaConsult> readAll();//查所有
}
