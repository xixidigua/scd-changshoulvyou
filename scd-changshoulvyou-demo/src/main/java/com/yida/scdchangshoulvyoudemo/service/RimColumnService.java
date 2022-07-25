package com.yida.scdchangshoulvyoudemo.service;


import com.yida.scdchangshoulvyoudemo.entity.RimColumn;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface RimColumnService {
    public  void add(RimColumn r);//增
    public void delete(Integer id);//删
    public void update(RimColumn r);//改
    public RimColumn readOne(Integer id);//查一个
    public List<RimColumn> readAll();//查所有

//带条件的增删改查
    public  List<RimColumn> read(@RequestParam("title") String title, @RequestParam("classify_id ") Integer classify_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime);

}
