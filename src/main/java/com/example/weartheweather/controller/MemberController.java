package com.example.weartheweather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {
    @GetMapping("/memberLogin")
    public String loginForm(){
        return "/memberPages/memberLogin";
    }

    @GetMapping("/memberSave")
    public String saveForm(){
        return "/memberPages/memberSave";
    }
}
