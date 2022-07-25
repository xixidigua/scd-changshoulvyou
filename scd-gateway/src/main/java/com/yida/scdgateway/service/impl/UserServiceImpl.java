package com.yida.scdgateway.service.impl;

import com.yida.common.R;
import com.yida.scdgateway.dao.UserDao;
import com.yida.scdgateway.entity.User;
import com.yida.scdgateway.service.UserService;
import com.yida.scdgateway.utils.SaltUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2021-12-28 21:24:42
 */
@Transactional //添加事务
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;
    @Value("${Credential.AlgorithmName}") //从配置参数取过来的是什么加密算法
    private  String algorithmName;
    @Value("${Credential.hashIterations}")//从配置参数取过来的是什么加密次数
    private  Integer hashIterations;
    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    @Override
    public void addUser(String name, String password) {
        User user = new User();
        user.setName(name);
        String salt= SaltUtils.srand(8);//盐值（让密码更安全）
        //参数说明：
        //algorithmName 加密算法名, source 要加密的密码, salt 盐值, hashIterations 加密次数
        SimpleHash newPwd = new SimpleHash(algorithmName, password, salt, hashIterations);
        user.setPassword(newPwd.toString());
        user.setSalt(salt);//添加盐值
        userDao.insertSelective(user);//添加新用户
        userDao.insertUserRole(user.getId(),2);//给新用户添加一个默认角色（user,即：普通用户 根据数据库得知roleId=2）
    }

    @Override
    public List<User> queryAllUsers() {
        List<User> users = userDao.selectAll();
        return  users;
    }

    @Override
    @Transactional
    public R updateUser(User user) {
        String salt = SaltUtils.srand(8);//盐值（让密码更安全）
        //参数说明：
        //algorithmName 加密算法名, source 要加密的密码, salt 盐值, hashIterations 加密次数
        SimpleHash newPwd = new SimpleHash(algorithmName, user.getPassword(), salt, hashIterations);
        user.setPassword(newPwd.toString());
        user.setSalt(salt);//添加盐值
        userDao.updateByPrimaryKeySelective(user);//修改
        return R.ok("修改成功");
    }
}
