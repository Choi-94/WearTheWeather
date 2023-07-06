package com.example.weartheweather.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MyinfoController {


    @GetMapping("/myBoardList")
    public String myBoardListForm(){
        return "/myInfoPages/myBoardList";
    }
}
