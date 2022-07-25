package com.yida.scdchangshoulvyoudemo.service.impl;


import com.yida.scdchangshoulvyoudemo.entity.ComplaintAndSuggest;
import com.yida.scdchangshoulvyoudemo.mapper.ComplaintAndSuggestMapper;
import com.yida.scdchangshoulvyoudemo.service.ComplaintAndSuggestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *Service层，景点列表接口服务实现
 */
@Service("complaintAndSuggestService")
public class ComplaintAndSuggestServiceImpl implements ComplaintAndSuggestService {
      @Autowired(required = false)
    private ComplaintAndSuggestMapper complaintAndSuggestMapper;



    @Override
    public void delete(Integer id) {
    complaintAndSuggestMapper.deleteByPrimaryKey(id);
    }



    @Override
    public ComplaintAndSuggest readOne(Integer id) {
        return complaintAndSuggestMapper.readOne(id);
    }

    @Override
    public List<ComplaintAndSuggest> readAll() {
        List<ComplaintAndSuggest> complaintAndSuggests = complaintAndSuggestMapper.readAll();
        return complaintAndSuggests;
    }

    //带条件的增删改查
    @Override
    public List<ComplaintAndSuggest> read( @RequestParam("state_id ") Integer state_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime) {
        return  complaintAndSuggestMapper.read(state_id,startTime,endTime);
    }



    /*@Override
    public List<ScenicSpotsColumn> readList(String title) {

            return scenicSpotsColumnMapper.readList(tmpName);

    }*/
}
