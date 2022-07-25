package com.yida.scdchangshoulvyoudemo.mapper;


import com.yida.scdchangshoulvyoudemo.entity.VersionManage;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VersionManageMapper extends Mapper<VersionManage> {
    public void  update(VersionManage v);//改
    public List<VersionManage> readAll();//查所有
}
