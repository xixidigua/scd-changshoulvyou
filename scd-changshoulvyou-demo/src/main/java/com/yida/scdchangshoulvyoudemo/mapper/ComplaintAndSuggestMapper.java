package com.yida.scdchangshoulvyoudemo.mapper;


import com.yida.scdchangshoulvyoudemo.entity.ComplaintAndSuggest;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ComplaintAndSuggestMapper extends Mapper<ComplaintAndSuggest> {
    //删 该功能使用通用mapper的功能
    public List<ComplaintAndSuggest> readAll();//查所有
    public ComplaintAndSuggest readOne(Integer id);//查一个
  // public List<ScenicSpotsColumn> readList(@RequestParam("title") String title );//按条件查询
  public  List<ComplaintAndSuggest > read(@RequestParam("state_id ") Integer state_id, @RequestParam("startTime ") String startTime, @RequestParam("endTime") String endTime);

}
