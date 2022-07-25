package com.yida.scdchangshoulvyoudemo.service;


import com.yida.scdchangshoulvyoudemo.entity.Classify;

import java.util.List;

/**
 * Service层，部门服务接口
 */
public interface ClassifyService {
     public List<Classify> readAll();
     //带条件的增删改查
}
