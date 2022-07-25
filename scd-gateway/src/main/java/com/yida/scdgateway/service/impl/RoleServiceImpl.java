package com.yida.scdgateway.service.impl;

import com.yida.scdgateway.dao.RoleDao;
import com.yida.scdgateway.entity.Role;
import com.yida.scdgateway.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Role)表服务实现类
 *
 * @author xixidigua
 * @since 2022-01-03 15:00:54
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleDao roleDao;
    @Override
    public Role queryByRoleId(Integer roleId) {
        return roleDao.queryByRoleId(roleId);
    }
}
