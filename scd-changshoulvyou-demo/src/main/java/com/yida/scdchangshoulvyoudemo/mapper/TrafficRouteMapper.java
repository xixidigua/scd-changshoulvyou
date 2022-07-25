package com.yida.scdchangshoulvyoudemo.mapper;


import com.yida.scdchangshoulvyoudemo.entity.TrafficRoute;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TrafficRouteMapper extends Mapper<TrafficRoute> {
    public void  update(TrafficRoute t);//改
    public List<TrafficRoute> readAll();//查所有
}
