package com.yida.scdchangshoulvyoudemo.mapper;


import com.yida.scdchangshoulvyoudemo.entity.FocusMessageColumn;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FocusMessageColumnMapper extends Mapper<FocusMessageColumn> {
     public  void  add(FocusMessageColumn f);//增
    //删 该功能使用通用mapper的功能
    public void  update(FocusMessageColumn f);//改
    public List<FocusMessageColumn> readAll();//查所有
    public List<FocusMessageColumn> readAllMessage();//查所有焦点信息列表
    public FocusMessageColumn readOne(Integer id);//查一个
  // public List<ScenicSpotsColumn> readList(@RequestParam("title") String title );//按条件查询
    public  List<FocusMessageColumn> read(@RequestParam("title") String title, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime);//查询焦点消息
    public  List<FocusMessageColumn> readMessage(@RequestParam("title") String title, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime);//查询消息

    public void setFocus(FocusMessageColumn f);//修改焦点非焦点功能
}
