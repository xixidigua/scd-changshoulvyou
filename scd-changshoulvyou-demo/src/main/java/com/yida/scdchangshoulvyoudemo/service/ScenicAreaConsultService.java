package com.yida.scdchangshoulvyoudemo.service;


import com.yida.scdchangshoulvyoudemo.entity.ScenicAreaConsult;

import java.util.List;

public interface ScenicAreaConsultService {
    public  void add(ScenicAreaConsult s);//增
    public void delete(Integer id);//删
    public List<ScenicAreaConsult> readAll();//查所有
}
