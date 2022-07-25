package com.yida.scdchangshoulvyoudemo.mapper;


import com.yida.scdchangshoulvyoudemo.entity.ScenicSpotsColumn;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ScenicSpotsColumnMapper extends Mapper<ScenicSpotsColumn> {
     public  void  add(ScenicSpotsColumn s);//增
    //删 该功能使用通用mapper的功能
    int deleteById(Integer id);
    public void  update(ScenicSpotsColumn s);//改
    public List<ScenicSpotsColumn> readAll();//查所有
    public ScenicSpotsColumn readOne(Integer id);//查一个
  // public List<ScenicSpotsColumn> readList(@RequestParam("title") String title );//按条件查询
  public  List<ScenicSpotsColumn> read(@RequestParam("title") String title, @RequestParam("scenic_id ") Integer scenic_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime);
    public  List<ScenicSpotsColumn> readTime(@RequestParam("title") String title, @RequestParam("scenic_id ") Integer scenic_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime);
}
