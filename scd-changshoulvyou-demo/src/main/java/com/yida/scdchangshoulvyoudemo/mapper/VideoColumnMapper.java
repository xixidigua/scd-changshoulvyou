package com.yida.scdchangshoulvyoudemo.mapper;


import com.yida.scdchangshoulvyoudemo.entity.VideoColumn;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoColumnMapper extends Mapper<VideoColumn> {
     public  void  add(VideoColumn v);//增
    //删 该功能使用通用mapper的功能
    public void  update(VideoColumn v);//改
    public List<VideoColumn> readAll();//查所有
    public VideoColumn readOne(Integer id);//查一个
  // public List<ScenicSpotsColumn> readList(@RequestParam("title") String title );//按条件查询
public  List<VideoColumn> read(@RequestParam("title") String title, @RequestParam("classify_id ") Integer classify_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime);

}
