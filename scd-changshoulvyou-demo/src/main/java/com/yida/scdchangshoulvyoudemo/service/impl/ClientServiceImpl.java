package com.yida.scdchangshoulvyoudemo.service.impl;


import com.yida.scdchangshoulvyoudemo.entity.Client;
import com.yida.scdchangshoulvyoudemo.mapper.ClientMapper;
import com.yida.scdchangshoulvyoudemo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clientService")
public class ClientServiceImpl implements ClientService {
    @Autowired(required = false)
    private ClientMapper clientMapper;

    @Override
    public List<Client> readAll() {
        List<Client> clients = clientMapper.selectAll();
        return clients;
    }

}