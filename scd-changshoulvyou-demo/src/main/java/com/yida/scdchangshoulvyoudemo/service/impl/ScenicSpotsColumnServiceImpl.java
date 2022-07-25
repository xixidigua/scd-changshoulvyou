package com.yida.scdchangshoulvyoudemo.service.impl;


import com.yida.scdchangshoulvyoudemo.entity.ScenicSpotsColumn;
import com.yida.scdchangshoulvyoudemo.mapper.ScenicSpotsColumnMapper;
import com.yida.scdchangshoulvyoudemo.service.ScenicSpotsColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *Service层，景点列表接口服务实现
 */
@Service("scenicSpotsColumnService")
public class ScenicSpotsColumnServiceImpl  implements ScenicSpotsColumnService {
      @Autowired(required = false)
    private ScenicSpotsColumnMapper scenicSpotsColumnMapper;

    @Override
    public void add(ScenicSpotsColumn s) {
     scenicSpotsColumnMapper.add(s);

    }

    @Override
    public void delete(Integer id) {
    scenicSpotsColumnMapper.deleteById(id);
    }

    @Override
    public void update(ScenicSpotsColumn s) {
     scenicSpotsColumnMapper.update(s);
    }

    @Override
    public ScenicSpotsColumn readOne(Integer id) {
        return scenicSpotsColumnMapper.readOne(id);
    }

    @Override
    public List<ScenicSpotsColumn> readAll() {
        List<ScenicSpotsColumn> scenicSpotsColumns = scenicSpotsColumnMapper.readAll();
        return scenicSpotsColumns;
    }

    //带条件的增删改查
    @Override
    public List<ScenicSpotsColumn> read(@RequestParam("title") String title, @RequestParam("scenic_id ") Integer scenic_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime) {
        String tmpName= title.trim();
            tmpName="%"+tmpName+"%";
        return  scenicSpotsColumnMapper.read(tmpName,scenic_id,startTime,endTime);
    }

    @Override
    public List<ScenicSpotsColumn> readTime(@RequestParam("title") String title, @RequestParam("scenic_id ") Integer scenic_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime) {
        String tmpName= title.trim();
        tmpName="%"+tmpName+"%";
        return scenicSpotsColumnMapper.readTime(tmpName,scenic_id,startTime,endTime);
    }

}
