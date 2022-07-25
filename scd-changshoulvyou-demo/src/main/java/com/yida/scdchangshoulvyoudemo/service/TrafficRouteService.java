package com.yida.scdchangshoulvyoudemo.service;


import com.yida.scdchangshoulvyoudemo.entity.TrafficRoute;

import java.util.List;

public interface TrafficRouteService {
    public void update(TrafficRoute t);//改
    public List<TrafficRoute> readAll();//查所有
}
