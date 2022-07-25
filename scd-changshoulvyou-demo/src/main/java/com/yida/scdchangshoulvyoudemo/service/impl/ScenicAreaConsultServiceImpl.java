package com.yida.scdchangshoulvyoudemo.service.impl;


import com.yida.scdchangshoulvyoudemo.entity.ScenicAreaConsult;
import com.yida.scdchangshoulvyoudemo.mapper.ScenicAreaConsultMapper;
import com.yida.scdchangshoulvyoudemo.service.ScenicAreaConsultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *Service层，景点列表接口服务实现
 */
@Service("scenicAreaConsultService")
public class ScenicAreaConsultServiceImpl implements ScenicAreaConsultService {
      @Autowired(required = false)
    private ScenicAreaConsultMapper scenicAreaConsultMapper;

    @Override
    public void add(ScenicAreaConsult s) {
    scenicAreaConsultMapper.add(s);

    }

    @Override
    public void delete(Integer id) {
    scenicAreaConsultMapper.deleteByPrimaryKey(id);
    }



    @Override
    public List<ScenicAreaConsult> readAll() {
        List<ScenicAreaConsult> scenicAreaConsults = scenicAreaConsultMapper.readAll();
        return scenicAreaConsults;
    }

}
