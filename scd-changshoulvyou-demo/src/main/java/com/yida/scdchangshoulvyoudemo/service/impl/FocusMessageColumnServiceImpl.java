package com.yida.scdchangshoulvyoudemo.service.impl;


import com.yida.scdchangshoulvyoudemo.entity.FocusMessageColumn;
import com.yida.scdchangshoulvyoudemo.mapper.FocusMessageColumnMapper;
import com.yida.scdchangshoulvyoudemo.service.FocusMessageColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *Service层，景点列表接口服务实现
 */
@Service("focusMessageColumnService")
public class FocusMessageColumnServiceImpl implements FocusMessageColumnService {
      @Autowired(required = false)
    private FocusMessageColumnMapper focusMessageColumnMapper;

    @Override
    public void add(FocusMessageColumn f) {
    focusMessageColumnMapper.add(f);

    }

    @Override
    public void delete(Integer id) {
    focusMessageColumnMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(FocusMessageColumn f) {
     focusMessageColumnMapper.update(f);
    }

    @Override
    public FocusMessageColumn readOne(Integer id) {
        return focusMessageColumnMapper.readOne(id);
    }

    @Override
    public List<FocusMessageColumn> readAll() {
        List<FocusMessageColumn> focusMessageColumns = focusMessageColumnMapper.readAll();
        return focusMessageColumns;
    }

    @Override
    public List<FocusMessageColumn> readAllMessage() {
        List<FocusMessageColumn> focusMessageColumns = focusMessageColumnMapper.readAllMessage();
        return focusMessageColumns;
    }


    //带条件的增删改查（查询焦点消息）
    @Override
    public List<FocusMessageColumn> read(@RequestParam("title") String title,  @RequestParam("startTime ") String
            startTime, @RequestParam("endTime") String endTime) {
        String tmpName= title.trim();
        tmpName="%"+tmpName+"%";
        return  focusMessageColumnMapper.read(tmpName,startTime,endTime);
    }

    //带条件的增删改查（查询消息）
    @Override
    public List<FocusMessageColumn> readMessage(@RequestParam("title") String title,  @RequestParam("startTime ") String
            startTime, @RequestParam("endTime") String endTime) {
        String tmpName= title.trim();
        tmpName="%"+tmpName+"%";
        return  focusMessageColumnMapper.readMessage(tmpName,startTime,endTime);
    }

    //修改焦点非焦点
    @Override
    public void setFocus(FocusMessageColumn f) {
        focusMessageColumnMapper.setFocus(f);
    }
}
