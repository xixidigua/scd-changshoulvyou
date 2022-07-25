package com.yida.scdchangshoulvyoudemo.service;


import com.yida.scdchangshoulvyoudemo.entity.FocusMessageColumn;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface FocusMessageColumnService {
    public  void add(FocusMessageColumn f);//增
    public void delete(Integer id);//删
    public void update(FocusMessageColumn f);//改
    public FocusMessageColumn readOne(Integer id);//查一个
    public List<FocusMessageColumn> readAll();//查所有焦点信息列表
    public List<FocusMessageColumn> readAllMessage();//查所有消息列表
    //修改是否为焦点消息
    public void setFocus(FocusMessageColumn f);//改
//带条件的增删改查
    public  List<FocusMessageColumn> read(@RequestParam("title") String title, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime);//查询焦点消息
    public  List<FocusMessageColumn> readMessage(@RequestParam("title") String title, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime);//查询消息
}
