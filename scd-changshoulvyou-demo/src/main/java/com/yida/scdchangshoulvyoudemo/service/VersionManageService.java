package com.yida.scdchangshoulvyoudemo.service;


import com.yida.scdchangshoulvyoudemo.entity.VersionManage;

import java.util.List;

public interface VersionManageService {
    public void update(VersionManage v);//改
    public List<VersionManage> readAll();//查所有
}
