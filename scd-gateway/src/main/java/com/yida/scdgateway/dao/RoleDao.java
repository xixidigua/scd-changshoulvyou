package com.yida.scdgateway.dao;


import com.yida.scdgateway.entity.Role;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * (Role)表数据库访问层
 *
 * @author xixidigua
 * @since 2022-01-03 15:00:53
 */
public interface RoleDao extends Mapper<Role> {
    Role queryByRoleId(@Param("roleId") Integer roleId);

}

