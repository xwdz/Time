package com.xwdz.site.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller  //如果仅仅是返回HTML界面 使用此注解即可.
public class WebController {

    @RequestMapping("/login")
    public String login(){
        return "home.html";
    }
}
