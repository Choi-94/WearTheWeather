package com.example.weartheweather.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/access-denied")
    public String accessDenied(){
        return "accessDenied";
    }


}
