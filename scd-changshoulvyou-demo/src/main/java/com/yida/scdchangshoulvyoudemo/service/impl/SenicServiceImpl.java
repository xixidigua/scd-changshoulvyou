package com.yida.scdchangshoulvyoudemo.service.impl;


import com.yida.scdchangshoulvyoudemo.entity.Scenic;
import com.yida.scdchangshoulvyoudemo.mapper.ScenicMapper;
import com.yida.scdchangshoulvyoudemo.service.SenicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("senicService")
public class SenicServiceImpl implements SenicService {
    @Autowired(required = false)
    private ScenicMapper scenicMapper;

    @Override
    public List<Scenic> readAll() {
        List<Scenic> scenics = scenicMapper.selectAll();
        return scenics;
    }
    //带条件的增删改查

    @Override
    public  List<Scenic> selectByExample(Object example){
        return scenicMapper.selectByExample(example) ;
    }

}