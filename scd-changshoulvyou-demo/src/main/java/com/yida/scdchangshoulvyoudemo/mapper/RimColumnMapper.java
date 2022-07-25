package com.yida.scdchangshoulvyoudemo.mapper;


import com.yida.scdchangshoulvyoudemo.entity.RimColumn;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RimColumnMapper extends Mapper<RimColumn> {
     public  void  add(RimColumn r);//增
    //删 该功能使用通用mapper的功能
    public void  update(RimColumn r);//改
    public List<RimColumn> readAll();//查所有
    public RimColumn  readOne(Integer id);//查一个
  // public List<ScenicSpotsColumn> readList(@RequestParam("title") String title );//按条件查询
  public  List<RimColumn > read(@RequestParam("title") String title, @RequestParam("classify_id ") Integer classify_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime);

}
