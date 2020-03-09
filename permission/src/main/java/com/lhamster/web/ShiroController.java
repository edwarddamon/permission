package com.lhamster.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class ShiroController {
    @RequestMapping("/login")
    public String login(){
        return "redirect:/login.jsp";
    }
}