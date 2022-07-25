package com.yida.scdchangshoulvyoudemo.service.impl;


import com.yida.scdchangshoulvyoudemo.entity.VideoColumn;
import com.yida.scdchangshoulvyoudemo.mapper.VideoColumnMapper;
import com.yida.scdchangshoulvyoudemo.service.VideoColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *Service层，景点列表接口服务实现
 */
@Service("videoColumnService")
public class VideoColumnServiceImpl implements VideoColumnService {
      @Autowired(required = false)
    private VideoColumnMapper videoColumnMapper;

    @Override
    public void add(VideoColumn v) {
     videoColumnMapper.add(v);

    }

    @Override
    public void delete(Integer id) {
    videoColumnMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(VideoColumn v) {
     videoColumnMapper.update(v);
    }

    @Override
    public VideoColumn readOne(Integer id) {
        return videoColumnMapper.readOne(id);
    }

    @Override
    public List<VideoColumn> readAll() {
        List<VideoColumn> videoColumns = videoColumnMapper.readAll();
        return videoColumns;
    }

    //带条件的增删改查
    @Override
    public List<VideoColumn> read(@RequestParam("title") String title, @RequestParam("classify_id ") Integer classify_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime) {
        String tmpName= title.trim();
            tmpName="%"+tmpName+"%";
        return  videoColumnMapper.read(tmpName,classify_id,startTime,endTime);
    }

    /*@Override
    public List<ScenicSpotsColumn> readList(String title) {

            return scenicSpotsColumnMapper.readList(tmpName);

    }*/
}
