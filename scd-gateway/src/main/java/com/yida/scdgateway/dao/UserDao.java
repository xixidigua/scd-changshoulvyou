package com.yida.scdgateway.dao;


import com.yida.scdgateway.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2021-12-28 21:24:37
 */
public interface UserDao extends Mapper<User> {
//根据用户名查询（注意：注册时，用户名不能相同）
public User getUserByName(@Param("name") String name);
    //给新用户添加默认角色（user，即：普通用户）
    public void insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
}


