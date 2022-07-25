package com.yida.scdgateway.shiro;

import com.yida.scdgateway.cache.ShiroByteSource;
import com.yida.scdgateway.entity.Permission;
import com.yida.scdgateway.entity.Role;
import com.yida.scdgateway.entity.User;
import com.yida.scdgateway.service.RoleService;
import com.yida.scdgateway.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.HashSet;

;


public class ShiroRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    //专门提供授权认证相关信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String)principalCollection.getPrimaryPrincipal();
        System.out.println("正在授权认证! 主题对象="+username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //基于角色
        User user= userService.getUserByName(username);
        HashSet<Role> roles = user.getRoles();
        if(!ObjectUtils.isEmpty(roles)){
            for(Role role:roles){
                simpleAuthorizationInfo.addRole(role.getName());
                //基于权限字符串
                Role tmpRole= roleService.queryByRoleId(role.getId());
                HashSet<Permission> permissions = tmpRole.getPermissions();
                for(Permission permission:permissions){
                    simpleAuthorizationInfo.addStringPermission(permission.getName());
                }
            }
        }

        //基于权限字符串(* 星号代表所有)
        //simpleAuthorizationInfo.addStringPermission("user:select:01");//对用户模块下的01号用户拥有查询权限
        //simpleAuthorizationInfo.addStringPermission("user:select:*");//对用户模块下的所有用户实例拥有查询权限
        //simpleAuthorizationInfo.addStringPermission("user:*:*");//对用户模块下的所有用户实例拥有所有操作(增，删，改，查)权限
        //对用户模块下所有用户实例拥有修改权限+对订单模块下的03订单拥有查询权限+对商品下的所有商品实例所有操作(增，删，改，查)权限
       // simpleAuthorizationInfo.addStringPermissions(Arrays.asList("user:update:*","order:select:03", "product:*:*"));
        return simpleAuthorizationInfo;
    }
    //专门提供登录认证相关信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //1.把AuthenticationToken类型转换成UsernamePasswordToken类型
        UsernamePasswordToken upt = (UsernamePasswordToken) authenticationToken;
        //2.从upt中获取传过来的用户名
        String username = upt.getUsername();
        System.out.println("正在登录认证! 主题对象="+username);
      //3.通过username到数据库中查询有没有该用户
        User user = userService.getUserByName(username);
        if(ObjectUtils.isEmpty(user)){
            throw  new UnknownAccountException("该用户不存在");
        }
        //5.如果用户被禁用，抛出锁定用户异常
        if(user.getState()==0){
            throw new LockedAccountException("该用户已被禁用");
        }
        //6.根据用户信息构建AuthenticationToken接口实例对象返回
        // principal:一个对象，可以是user对象，也可以是用户名，用户id等
        // hashedCredentials:从数据库中查询出来的密码
        // credentialsSalt: 从数据库中查询出来的盐值
        // realmName：我们自定义的ShiroRealm的名称
     //   ByteSource byteSourse = ByteSource.Util.bytes(user.getSalt());//颜值
        ByteSource byteSourse =  new ShiroByteSource(user.getSalt());//颜值，针对Redis缓存，使用这个
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                username,user.getPassword(),byteSourse,getName()
        );
        return simpleAuthenticationInfo;
    }

}
