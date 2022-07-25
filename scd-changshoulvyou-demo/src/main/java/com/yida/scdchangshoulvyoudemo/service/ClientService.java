package com.yida.scdchangshoulvyoudemo.service;


import com.yida.scdchangshoulvyoudemo.entity.Client;

import java.util.List;

/**
 * Service层，部门服务接口
 */
public interface ClientService {
     public List<Client> readAll();
     //带条件的增删改查
}
