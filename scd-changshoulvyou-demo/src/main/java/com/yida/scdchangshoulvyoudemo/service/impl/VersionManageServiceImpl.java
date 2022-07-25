package com.yida.scdchangshoulvyoudemo.service.impl;


import com.yida.scdchangshoulvyoudemo.entity.VersionManage;
import com.yida.scdchangshoulvyoudemo.mapper.VersionManageMapper;
import com.yida.scdchangshoulvyoudemo.service.VersionManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *Service层，景点列表接口服务实现
 */
@Service("versionManageService")
public class VersionManageServiceImpl implements VersionManageService {
      @Autowired(required = false)
    private VersionManageMapper versionManageMapper;

    @Override
    public void update(VersionManage v) {
        versionManageMapper.update(v);
    }

    @Override
    public List<VersionManage> readAll() {
        return versionManageMapper.readAll();
    }
}
