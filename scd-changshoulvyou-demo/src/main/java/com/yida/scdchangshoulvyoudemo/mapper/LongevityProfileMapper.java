package com.yida.scdchangshoulvyoudemo.mapper;


import com.yida.scdchangshoulvyoudemo.entity.LongevityProfile;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface LongevityProfileMapper extends Mapper<LongevityProfile> {
    public void  update(LongevityProfile L);//改
    public List<LongevityProfile> readAll();//查所有
}
