package com.yida.scdchangshoulvyoudemo.service.impl;


import com.yida.scdchangshoulvyoudemo.entity.TrafficRoute;
import com.yida.scdchangshoulvyoudemo.mapper.TrafficRouteMapper;
import com.yida.scdchangshoulvyoudemo.service.TrafficRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *Service层，景点列表接口服务实现
 */
@Service("trafficRouteService")
public class TrafficRouteServiceImpl implements TrafficRouteService {
      @Autowired(required = false)
    private TrafficRouteMapper trafficRouteMapper;

    @Override
    public void update(TrafficRoute t) {
     trafficRouteMapper.update(t);
    }

    @Override
    public List<TrafficRoute> readAll() {
        return trafficRouteMapper.readAll();
    }
}
