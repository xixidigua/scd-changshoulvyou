package com.yida.scdchangshoulvyoudemo.service.impl;

import com.yida.scdchangshoulvyoudemo.entity.RimColumn;
import com.yida.scdchangshoulvyoudemo.mapper.RimColumnMapper;
import com.yida.scdchangshoulvyoudemo.service.RimColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *Service层，景点列表接口服务实现
 */
@Service("rimColumnService")
public class RimColumnServiceImpl implements RimColumnService {
      @Autowired(required = false)
    private RimColumnMapper rimColumnMapper;

    @Override
    public void add(RimColumn s) {
     rimColumnMapper.add(s);

    }

    @Override
    public void delete(Integer id) {
    rimColumnMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(RimColumn r) {
     rimColumnMapper.update(r);
    }

    @Override
    public RimColumn readOne(Integer id) {
        return rimColumnMapper.readOne(id);
    }

    @Override
    public List<RimColumn> readAll() {
        List<RimColumn> rimColumns = rimColumnMapper.readAll();
        return rimColumns;
    }

    //带条件的增删改查
    @Override
    public List<RimColumn> read(@RequestParam("title") String title, @RequestParam("classify_id ") Integer classify_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime) {
        String tmpName= title.trim();
            tmpName="%"+tmpName+"%";
        return  rimColumnMapper.read(tmpName,classify_id,startTime,endTime);
    }



    /*@Override
    public List<ScenicSpotsColumn> readList(String title) {

            return scenicSpotsColumnMapper.readList(tmpName);

    }*/
}
