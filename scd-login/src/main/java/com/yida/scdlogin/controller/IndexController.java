package com.yida.scdlogin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2021-12-28 21:24:36
 */


@Controller
public class  IndexController {
    @GetMapping(value={"/","/login"})
    public String  login() {
        return "login";
    }

}

