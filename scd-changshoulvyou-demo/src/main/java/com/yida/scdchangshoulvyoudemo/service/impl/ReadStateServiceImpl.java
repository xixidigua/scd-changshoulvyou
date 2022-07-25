package com.yida.scdchangshoulvyoudemo.service.impl;


import com.yida.scdchangshoulvyoudemo.entity.ReadState;
import com.yida.scdchangshoulvyoudemo.mapper.ReadStateMapper;
import com.yida.scdchangshoulvyoudemo.service.ReadStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("readStateService")
public class ReadStateServiceImpl implements ReadStateService {
    @Autowired(required = false)
    private ReadStateMapper readStateMapper;

    @Override
    public List<ReadState> readAll() {
        List<ReadState> readStates = readStateMapper.selectAll();
        return readStates;
    }

}