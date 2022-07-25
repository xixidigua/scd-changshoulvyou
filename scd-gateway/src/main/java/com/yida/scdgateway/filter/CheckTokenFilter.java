package com.yida.scdgateway.filter;

import com.yida.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
@Slf4j
@RestController
@RequestMapping("/gateway")
public class CheckTokenFilter {

    @PostMapping("/loginData")
    public R loginData(@RequestParam("name") String name,
                            @RequestParam("password") String password
    ){
        if(StringUtils.isEmpty(name)|| StringUtils.isEmpty(password)){
            log.info("用户名或密码不能为空");
           // return "login";
           return  R.error("用户名或密码不能为空");
        }
        //shiro登录认证
        Subject subject = SecurityUtils.getSubject();//获取主题对象
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name, password);
       try {
           subject.login(usernamePasswordToken);
       }catch (Exception e){
           if (e instanceof UnknownAccountException) {
               log.info("登录认证失败,用户不存在! 原因="+e.getMessage());
               return R.error("登录认证失败,用户不存在!");
           } else if (e instanceof LockedAccountException) {
               log.info("登录认证失败,用户被禁用! 原因="+e.getMessage());
               return R.error("登录认证失败,用户被禁用!");
           } else if (e instanceof IncorrectCredentialsException) {
               log.info("登录认证失败,密码不正确! 原因="+e.getMessage());
               return R.error("登录认证失败,密码不正确!");
           } else if (e instanceof AuthenticationException) {
               log.info("登录认证失败! 原因="+e.getMessage());
               return R.error("登录认证失败!");
           } else if (e instanceof UnauthorizedException) {
               log.info("授权认证失败,未授权访问! 原因="+e.getMessage());
               return R.error("授权认证失败,未授权访问!");
           } else if (e instanceof UnauthenticatedException) {
               log.info("授权认证失败,没有登录! 原因="+e.getMessage());
               return R.error("授权认证失败,没有登录!");
           } else if (e instanceof AuthorizationException) {
               log.info("授权认证失败! 原因="+e.getMessage());
               return R.error("授权认证失败!");
           }
           else { // RuntimeException
               //...
           }

           log.info(e.getMessage());
           return R.error("用户名或密码不能为空");
       }
       // return "redirect:/index";
        return  R.ok("认证成功");
    }

}
