package com.yida.scdchangshoulvyoudemo.service;


import com.yida.scdchangshoulvyoudemo.entity.ComplaintAndSuggest;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ComplaintAndSuggestService {
    public void delete(Integer id);//删
    public ComplaintAndSuggest readOne(Integer id);//查一个
    public List<ComplaintAndSuggest> readAll();//查所有

//带条件的增删改查
    public  List<ComplaintAndSuggest> read(@RequestParam("state_id ") Integer state_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime);

}
