package com.yida.scdchangshoulvyoudemo.service;


import com.yida.scdchangshoulvyoudemo.entity.Scenic;

import java.util.List;

/**
 * Service层，部门服务接口
 */
public interface SenicService {
     public List<Scenic> readAll();
     //带条件的增删改查
     public  List<Scenic> selectByExample(Object example);
}
