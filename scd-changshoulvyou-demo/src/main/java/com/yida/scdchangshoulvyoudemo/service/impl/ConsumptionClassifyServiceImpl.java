package com.yida.scdchangshoulvyoudemo.service.impl;


import com.yida.scdchangshoulvyoudemo.entity.ConsumptionClassify;
import com.yida.scdchangshoulvyoudemo.mapper.ConsumptionClassifyMapper;
import com.yida.scdchangshoulvyoudemo.service.ConsumptionClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("consumptionClassifyService")
public class ConsumptionClassifyServiceImpl implements ConsumptionClassifyService {
    @Autowired(required = false)
    private ConsumptionClassifyMapper consumptionClassifyMapper;

    @Override
    public List<ConsumptionClassify> readAll() {
        List<ConsumptionClassify> consumptionClassifies = consumptionClassifyMapper.selectAll();
        return consumptionClassifies;
    }

}