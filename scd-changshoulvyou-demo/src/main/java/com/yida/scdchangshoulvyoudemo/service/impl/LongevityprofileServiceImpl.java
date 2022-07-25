package com.yida.scdchangshoulvyoudemo.service.impl;


import com.yida.scdchangshoulvyoudemo.entity.LongevityProfile;
import com.yida.scdchangshoulvyoudemo.mapper.LongevityProfileMapper;
import com.yida.scdchangshoulvyoudemo.service.LongevityProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *Service层，景点列表接口服务实现
 */
@Service("longevityProfileService")
public class LongevityprofileServiceImpl implements LongevityProfileService {
      @Autowired(required = false)
    private LongevityProfileMapper longevityProfileMapper;

    @Override
    public void update(LongevityProfile L) {
     longevityProfileMapper.update(L);
    }

    @Override
    public List<LongevityProfile> readAll() {
        return longevityProfileMapper.readAll();
    }
}
