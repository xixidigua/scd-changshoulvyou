package com.yida.scdchangshoulvyoudemo.service;


import com.yida.scdchangshoulvyoudemo.entity.VideoColumn;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface VideoColumnService {
    public  void add(VideoColumn v);//增
    public void delete(Integer id);//删
    public void update(VideoColumn v);//改
    public VideoColumn readOne(Integer id);//查一个
    public List<VideoColumn> readAll();//查所有

//带条件的增删改查
    public  List<VideoColumn> read(@RequestParam("title") String title, @RequestParam("classify_id ") Integer classify_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime);

}
