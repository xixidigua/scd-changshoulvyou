package com.yida.scdgateway.controller;


import com.yida.scdgateway.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2021-12-28 21:24:36
 */

@Slf4j
@Controller
public class IndexController {

    @Resource
    private UserService userService;

    @GetMapping(value={"/","/login"})
    public String  login() {
        return "login";
    }

    @PostMapping("/loginData")
    public String loginData(@RequestParam("name") String name,
                            @RequestParam("password") String password
                            ){
        if(StringUtils.isEmpty(name)||StringUtils.isEmpty(password)){
            log.info("用户名或密码不能为空");
            return "redirect:/login";
        }
        //shiro登录认证
        Subject subject = SecurityUtils.getSubject();//获取主题对象
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name, password);
        subject.login(usernamePasswordToken);
        return "redirect:/main/index";
    }
    @GetMapping("unauthorized")
    public String  unauthorizedPage() {
        return "unauthorized";
    }
}

