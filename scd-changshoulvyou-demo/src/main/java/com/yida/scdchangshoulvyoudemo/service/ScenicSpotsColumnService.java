package com.yida.scdchangshoulvyoudemo.service;


import com.yida.scdchangshoulvyoudemo.entity.ScenicSpotsColumn;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ScenicSpotsColumnService {
    public  void add(ScenicSpotsColumn s);//增
    public void delete(Integer id);//删
    public void update(ScenicSpotsColumn s);//改
    public ScenicSpotsColumn readOne(Integer id);//查一个
    public List<ScenicSpotsColumn> readAll();//查所有

//带条件的增删改查
    public  List<ScenicSpotsColumn> read(@RequestParam("title") String title, @RequestParam("scenic_id ") Integer scenic_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime);
    public  List<ScenicSpotsColumn> readTime(@RequestParam("title") String title, @RequestParam("scenic_id ") Integer scenic_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime);
}
