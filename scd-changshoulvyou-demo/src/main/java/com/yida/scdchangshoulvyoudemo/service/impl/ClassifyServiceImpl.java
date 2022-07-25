package com.yida.scdchangshoulvyoudemo.service.impl;


import com.yida.scdchangshoulvyoudemo.entity.Classify;
import com.yida.scdchangshoulvyoudemo.mapper.ClassifyMapper;
import com.yida.scdchangshoulvyoudemo.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("classifyService")
public class ClassifyServiceImpl implements ClassifyService {
    @Autowired(required = false)
    private ClassifyMapper classifyMapper;

    @Override
    public List<Classify> readAll() {
        List<Classify> classifies = classifyMapper.selectAll();
        return classifies;
    }

}