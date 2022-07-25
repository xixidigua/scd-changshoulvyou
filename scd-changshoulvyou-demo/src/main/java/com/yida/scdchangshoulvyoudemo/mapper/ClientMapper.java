package com.yida.scdchangshoulvyoudemo.mapper;


import com.yida.scdchangshoulvyoudemo.entity.Client;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.common.Mapper;

public interface ClientMapper extends Mapper<Client> {
    public Client read(@RequestParam("id") Integer id);
}
