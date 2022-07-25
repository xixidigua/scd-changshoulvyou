package com.yida.scdgateway.service;


import com.yida.common.R;
import com.yida.scdgateway.entity.User;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2021-12-28 21:24:41
 */
public interface UserService {
    public User getUserByName(String name);
    public void addUser(String name, String password);
    public List<User> queryAllUsers();
    public R updateUser(User user);
}

