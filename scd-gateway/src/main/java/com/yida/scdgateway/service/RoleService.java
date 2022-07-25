package com.yida.scdgateway.service;


import com.yida.scdgateway.entity.Role;

/**
 * (Role)表服务接口
 *
 * @author xixidigua
 * @since 2022-01-03 15:00:54
 */
public interface RoleService {
    Role queryByRoleId(Integer roleId);

}
