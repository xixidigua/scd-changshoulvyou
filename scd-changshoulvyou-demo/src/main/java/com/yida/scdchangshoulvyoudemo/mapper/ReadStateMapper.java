package com.yida.scdchangshoulvyoudemo.mapper;


import com.yida.scdchangshoulvyoudemo.entity.ReadState;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.Mapper;

public interface ReadStateMapper extends Mapper<ReadState> {
    public ReadState read(@RequestParam("id") Integer id);
}
